package br.edu.ifnmg.avaliar.dao;

import br.edu.ifnmg.avaliar.util.HibernateUtil;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.resource.transaction.spi.TransactionStatus;

/**
 *
 * @author danilo
 * @param <E>
 * @param <I>
 */
public class HibernateGenericDAO<E, I extends Serializable> implements Serializable {

    private Session session;
    private Class<E> persistentClass;
    private Transaction transaction;
    
    public E save(E entity) {
        if(getSession().getTransaction() == null){
            transaction = getSession().beginTransaction();
        } else {
            transaction = getSession().getTransaction();
            if(!transaction.getStatus().equals(TransactionStatus.ACTIVE)){
                transaction.begin();
            }
        }
        entity = (E)getSession().merge(entity);
        transaction.commit();
        getSession().flush();
        getSession().close();
        return entity;
    }
    
    public E saveNotCommit(E entity) {
        if(getSession().getTransaction() == null){
            transaction = getSession().beginTransaction();
        } else {
            transaction = getSession().getTransaction();
            if(!transaction.getStatus().equals(TransactionStatus.ACTIVE)){
                transaction.begin();
            }
        }
        try{
            entity = (E)getSession().merge(entity);
        } catch (Exception e){
        }
        return entity;
    }
    
    public void commit(){
        if(transaction != null && transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    public void delete(E entity) {
        Transaction transaction = getSession().beginTransaction();
        getSession().delete(entity);
        transaction.commit();
        getSession().close();
    }

    public void refresh(E entity){
        getSession().refresh(entity);
    }
    
    public void initializer(List<E> entitys){
        for (E entity : entitys) {
            Hibernate.initialize(entity);
        }
    }
    
    public E findById(I id) {
        E entity = (E) getSession().get(getPersistentClass(), id);
        return entity;
    }

    public List<E> findByExample(E entity, String... filds) {
        getSession().flush();
        getSession().clear();
        Criteria crit = getSession().createCriteria(getPersistentClass());
        Example example =  Example.create(entity);
        for (String exclude : filds) {
            example.excludeProperty(exclude);
        }
        crit.add(example);
        return crit.list();
    }

    public List<E> findAll(){
//        getSession().flush();Quando o cliente clica em editar depois apaga um campo e tenta pesquisar novametne, da erro! por isso foi comentado
        getSession().clear();
        Criteria crit = getSession().createCriteria(getPersistentClass());
        return crit.list();
    }
    
    public void clear(){
        getSession().clear();
    }
    
    public Criteria createCriteria(){
        return getSession().createCriteria(getPersistentClass());
    }
    
    public Session getSession(){
        if(this.session == null){
            this.session = HibernateUtil.getSessionFactory().openSession();
        }else if(!this.session.isOpen()){
            this.session = HibernateUtil.getSessionFactory().openSession();
        }
        return this.session;
    }
    public void setSession(Session session){
        this.session = session;
    }
    
    public Class<E> getPersistentClass(){
        if(persistentClass == null){
            persistentClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return persistentClass;
    }
    
}
