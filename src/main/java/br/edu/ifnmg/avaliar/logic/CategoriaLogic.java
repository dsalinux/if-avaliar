package br.edu.ifnmg.avaliar.logic;

import br.edu.ifnmg.avaliar.dao.CategoriaDAO;
import br.edu.ifnmg.avaliar.entity.Categoria;
import br.edu.ifnmg.avaliar.util.Assert;
import br.edu.ifnmg.avaliar.util.exception.BusinessException;
import br.edu.ifnmg.avaliar.util.exception.SystemException;
import java.io.Serializable;
import java.util.List;

public class CategoriaLogic implements GenericLogic<Categoria>, Serializable {

    private CategoriaDAO dao = new CategoriaDAO();
    
    @Override
    public Categoria save(Categoria entity) throws BusinessException, SystemException {
        if(Assert.isStringNullOrEmpty(entity.getNome())){
            throw new BusinessException("Por favor informe o Nome!");
        }
        return dao.save(entity);
    }

    @Override
    public void delete(Categoria entity) throws BusinessException, SystemException {
        dao.delete(entity);
    }

    @Override
    public Categoria refresh(Categoria entity) throws BusinessException, SystemException {
        dao.refresh(entity);
        return entity;
    }

    @Override
    public List<Categoria> find(Categoria entity) throws BusinessException, SystemException {
        return dao.findAll();
    }

    
}
