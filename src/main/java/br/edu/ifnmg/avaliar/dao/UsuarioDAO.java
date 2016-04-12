package br.edu.ifnmg.avaliar.dao;

import br.edu.ifnmg.avaliar.entity.Usuario;
import br.edu.ifnmg.avaliar.util.exception.BusinessException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class UsuarioDAO extends HibernateGenericDAO<Usuario, Integer>{

    public Usuario logar(String login, String senha) throws BusinessException {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("login", login));
        criteria.add(Restrictions.eq("senha", senha));
        Usuario usuario = (Usuario) criteria.uniqueResult();
        if (usuario == null) {
            throw new BusinessException("Email ou senha inválidos!");
        }
        return usuario;
    }

}
