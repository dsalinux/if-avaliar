package br.edu.ifnmg.avaliar.bean;

import br.edu.ifnmg.avaliar.entity.Questionario;
import br.edu.ifnmg.avaliar.logic.QuestionarioLogic;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class GerenciarQuestionarioBean extends GenericCrudBean<Questionario, QuestionarioLogic> {


}
