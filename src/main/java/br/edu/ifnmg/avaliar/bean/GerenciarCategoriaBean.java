package br.edu.ifnmg.avaliar.bean;

import br.edu.ifnmg.avaliar.entity.Categoria;
import br.edu.ifnmg.avaliar.logic.CategoriaLogic;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class GerenciarCategoriaBean extends GenericCrudBean<Categoria, CategoriaLogic> {


}
