package br.edu.ifnmg.avaliar.logic;

import br.edu.ifnmg.avaliar.dao.UsuarioDAO;
import br.edu.ifnmg.avaliar.entity.Usuario;
import br.edu.ifnmg.avaliar.util.Assert;
import br.edu.ifnmg.avaliar.util.exception.BusinessException;
import br.edu.ifnmg.avaliar.util.exception.SystemException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UsuarioLogic implements GenericLogic<Usuario>, Serializable {

    private UsuarioDAO dao = new UsuarioDAO();
    
    @Override
    public Usuario save(Usuario entity) throws BusinessException, SystemException {
        if(Assert.isStringNullOrEmpty(entity.getNome())){
            throw new BusinessException("Por favor informe o Nome!");
        }
        if(Assert.isStringNullOrEmpty(entity.getLogin())){
            throw new BusinessException("Por favor informe o Login!");
        }
        if(Assert.isStringNullOrEmpty(entity.getSenha())){
            throw new BusinessException("Por favor informe a Senha!");
        }
        if(entity.getDataCadastro() == null){
            entity.setDataCadastro(new Date());
        }
        return dao.save(entity);
    }

    @Override
    public void delete(Usuario entity) throws BusinessException, SystemException {
        dao.delete(entity);
    }

    @Override
    public Usuario refresh(Usuario entity) throws BusinessException, SystemException {
        dao.refresh(entity);
        return entity;
    }

    @Override
    public List<Usuario> find(Usuario entity) throws BusinessException, SystemException {
        return dao.findAll();
    }
    
    public Usuario loggar(String login, String senha) throws BusinessException{
        return dao.logar(login, senha);
    }
    
}
