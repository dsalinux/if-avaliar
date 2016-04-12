package br.edu.ifnmg.avaliar.bean;

import br.edu.ifnmg.avaliar.logic.GenericLogic;
import br.edu.ifnmg.avaliar.util.exception.BusinessException;
import br.edu.ifnmg.avaliar.util.exception.SystemException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Danilo Souza Almeida
 * @param <E> Entity
 * @param <L> Logic
 */
public abstract class GenericCrudBean<E, L extends GenericLogic<E>> extends GenericBean {
    
    private static final long serialVersionUID = 1L;

    private Class<E> classEntity;
    private L logic;
    private E entity;
    private List<E> entitys;
    
    public GenericCrudBean(){
        try {
            entity = getClassEntity().newInstance();
            entitys = new ArrayList<E>();
            changeStateToSearch();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(GenericBean.class.getName()).log(Level.SEVERE, null, ex);
            addMessage(getSeverityError(), "Erro ao iniciar uma nova entidade");
        }
        
    }
    
    public void newRegistre(){
        try {
            entity = getClassEntity().newInstance();
            changeStateToInsert();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(GenericBean.class.getName()).log(Level.SEVERE, null, ex);
            addMessage(getSeverityError(), "Erro ao iniciar uma nova entidade");
        }
        
    }
    
    public void saveWithoutReturnToSearch(){
        try {
            saveEntity();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso!", "Salvo com sucesso!"));
        } catch (BusinessException ex) {
            addMessage(ex);
        } catch (SystemException ex) {
            addMessage(ex);
            Logger.getLogger(GenericBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveEntity() throws BusinessException, SystemException{
        try {
            getLogic().save(entity);
            if(entitys == null){
                entitys = new ArrayList<E>();
            } else if(getEntitys().contains(entity)){
                getEntitys().remove(entity);
            }
            getEntitys().add(0, entity);
            entity = getClassEntity().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new SystemException("Erro ao iniciar uma nova entidade!", ex);
        }
    }
    
    public void save(){
        try {
            saveEntity();
            changeStateToSearch();
            addMessage("Salvo com sucesso!");
        } catch (BusinessException ex) {
            addMessage(ex);
        } catch (SystemException ex) {
            addMessage(ex);
            Logger.getLogger(GenericCrudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(){
        try {
            getLogic().delete(entity);
            getEntitys().remove(getEntity());
            newRegistre();
            search();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido com sucesso!","Removido com sucesso!"));
        } catch (BusinessException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getCause().getMessage()));
            Logger.getLogger(GenericBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(GenericCrudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void edit(E entity){
        try {
            this.entity = getLogic().refresh(entity);
            
            changeStateToEdit();
        } catch (BusinessException ex) {
            addMessage(ex);
        } catch (SystemException ex) {
            addMessage(ex);
            Logger.getLogger(GenericCrudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void search(){
        try {
            if(isStateSearch()){
                entitys = getLogic().find(entity);
                if(entitys == null || entitys.isEmpty()){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Nenhum dado encontrado!"));
                }
            } else {
                    entity = getClassEntity().newInstance();
                    changeStateToSearch();
            }
        } catch (BusinessException ex) {
            addMessage(ex);
        } catch (SystemException ex) {
            addMessage(ex);
            Logger.getLogger(GenericBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException | IllegalAccessException ex) {
            addMessage(getSeverityError(), "Erro ao iniciar a Entidade!");
            Logger.getLogger(GenericCrudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Getters and setters
    public E getEntity() {
        return entity;
    }
    public void setEntity(E entity) {
        this.entity = entity;
    }

    public List<E> getEntitys() {
        return entitys;
    }
    public void setEntitys(List<E> entitys) {
        this.entitys = entitys;
    }

    public Class<E> getClassEntity() {
        classEntity = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return classEntity;
    }
    public L getLogic() throws SystemException {
        if(logic == null){
            try {
                logic = ((Class<L>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]).newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                throw new SystemException("Erro ao iniciar a classe Logic", ex);
            }
        }
        return logic;
    }
    
}
