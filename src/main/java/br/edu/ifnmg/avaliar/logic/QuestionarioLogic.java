package br.edu.ifnmg.avaliar.logic;

import br.edu.ifnmg.avaliar.dao.QuestionarioDAO;
import br.edu.ifnmg.avaliar.entity.Questionario;
import br.edu.ifnmg.avaliar.util.Assert;
import br.edu.ifnmg.avaliar.util.exception.BusinessException;
import br.edu.ifnmg.avaliar.util.exception.SystemException;
import java.io.Serializable;
import java.util.List;

public class QuestionarioLogic implements GenericLogic<Questionario>, Serializable {

    private QuestionarioDAO dao = new QuestionarioDAO();
    
    @Override
    public Questionario save(Questionario entity) throws BusinessException, SystemException {
        if(Assert.isStringNullOrEmpty(entity.getNome())){
            throw new BusinessException("Por favor informe o Nome!");
        }
        return dao.save(entity);
    }

    @Override
    public void delete(Questionario entity) throws BusinessException, SystemException {
        dao.delete(entity);
    }

    @Override
    public Questionario refresh(Questionario entity) throws BusinessException, SystemException {
        dao.refresh(entity);
        return entity;
    }

    @Override
    public List<Questionario> find(Questionario entity) throws BusinessException, SystemException {
        return dao.findAll();
    }

    
}
