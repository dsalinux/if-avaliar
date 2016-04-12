package br.edu.ifnmg.avaliar.logic;

import br.edu.ifnmg.avaliar.dao.RespostaDAO;
import br.edu.ifnmg.avaliar.entity.CategoriaRespostaVO;
import br.edu.ifnmg.avaliar.entity.Resposta;
import br.edu.ifnmg.avaliar.util.Assert;
import br.edu.ifnmg.avaliar.util.exception.BusinessException;
import br.edu.ifnmg.avaliar.util.exception.SystemException;
import java.io.Serializable;
import java.util.List;

public class RespostaLogic implements GenericLogic<Resposta>, Serializable {

    private RespostaDAO dao = new RespostaDAO();
    
    @Override
    public Resposta save(Resposta entity) throws BusinessException, SystemException {
        if(Assert.isStringNullOrEmpty(entity.getResposta())){
            throw new BusinessException("Por favor Questão não respondida!");
        }
        return dao.save(entity);
    }

    public void salvarRespostas(List<CategoriaRespostaVO> listaCategoriasResposta){
        for (CategoriaRespostaVO categoriaResposta : listaCategoriasResposta) {
            for (Resposta resposta : categoriaResposta.getRespostas()) {
                dao.saveNotCommit(resposta);
            }
        }
        dao.commit();
    }
    
    @Override
    public void delete(Resposta entity) throws BusinessException, SystemException {
        dao.delete(entity);
    }

    @Override
    public Resposta refresh(Resposta entity) throws BusinessException, SystemException {
        dao.refresh(entity);
        return entity;
    }

    @Override
    public List<Resposta> find(Resposta entity) throws BusinessException, SystemException {
        return dao.findAll();
    }

    
}
