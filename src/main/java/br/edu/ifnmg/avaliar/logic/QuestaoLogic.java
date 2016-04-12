package br.edu.ifnmg.avaliar.logic;

import br.edu.ifnmg.avaliar.dao.QuestaoDAO;
import br.edu.ifnmg.avaliar.entity.Categoria;
import br.edu.ifnmg.avaliar.entity.Questao;
import br.edu.ifnmg.avaliar.entity.Questionario;
import br.edu.ifnmg.avaliar.util.Assert;
import br.edu.ifnmg.avaliar.util.exception.BusinessException;
import br.edu.ifnmg.avaliar.util.exception.SystemException;
import java.io.Serializable;
import java.util.List;

public class QuestaoLogic implements GenericLogic<Questao>, Serializable {

    private QuestaoDAO dao = new QuestaoDAO();
    
    @Override
    public Questao save(Questao entity) throws BusinessException, SystemException {
        if(Assert.isStringNullOrEmpty(entity.getQuestao())){
            throw new BusinessException("Por favor informe a Questão!");
        }
        if(Assert.isNull(entity.getCategoria())){
            throw new BusinessException("Por favor informe a Categoria!");
        }
        return dao.save(entity);
    }

    @Override
    public void delete(Questao entity) throws BusinessException, SystemException {
        dao.delete(entity);
    }

    @Override
    public Questao refresh(Questao entity) throws BusinessException, SystemException {
        dao.refresh(entity);
        return entity;
    }

    @Override
    public List<Questao> find(Questao entity) throws BusinessException, SystemException {
        return dao.findAll();
    }

    public List<Questao> buscarQuestao(Questionario questionario, Categoria categoria){
        return dao.buscarQuestao(questionario, categoria);
    }
    
}
