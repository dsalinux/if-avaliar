package br.edu.ifnmg.avaliar.bean;

import br.edu.ifnmg.avaliar.entity.Usuario;
import br.edu.ifnmg.avaliar.logic.UsuarioLogic;
import br.edu.ifnmg.avaliar.util.StringHelper;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class GerenciarUsuarioBean extends GenericCrudBean<Usuario, UsuarioLogic> {

    private String senha;

    @Override
    public void save() {
        if (!StringHelper.isEmpty(senha)) {
            getEntity().setSenha(senha);
        }
        super.save();
    }

    public void aplicarEmailToLogin() {
        if (StringHelper.isEmpty(getEntity().getLogin())) {
            getEntity().setLogin(getEntity().getEmail());
        }
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
