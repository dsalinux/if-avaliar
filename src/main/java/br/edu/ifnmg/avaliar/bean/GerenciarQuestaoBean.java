package br.edu.ifnmg.avaliar.bean;

import br.edu.ifnmg.avaliar.entity.Categoria;
import br.edu.ifnmg.avaliar.entity.Questao;
import br.edu.ifnmg.avaliar.entity.Questionario;
import br.edu.ifnmg.avaliar.logic.CategoriaLogic;
import br.edu.ifnmg.avaliar.logic.QuestaoLogic;
import br.edu.ifnmg.avaliar.logic.QuestionarioLogic;
import br.edu.ifnmg.avaliar.util.exception.BusinessException;
import br.edu.ifnmg.avaliar.util.exception.SystemException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@ManagedBean
@SessionScoped
public class GerenciarQuestaoBean extends GenericCrudBean<Questao, QuestaoLogic> {

    private CategoriaLogic categoriaLogic = new CategoriaLogic();
    private QuestionarioLogic questionarioLogic = new QuestionarioLogic();
    private List<Categoria> listaCategoria = null;
    private List<Questionario> listaQuestionario = null;

    public List<Categoria> getListaCategoria() {
        if(listaCategoria == null){
            try {
                listaCategoria = categoriaLogic.find(null);
            } catch (BusinessException ex) {
                addMessage(ex);
            } catch (SystemException ex) {
                addMessage(ex);
                Logger.getLogger(GerenciarQuestaoBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaCategoria;
    }
    public List<Questionario> getListaQuestionario() {
        if(listaQuestionario == null){
            try {
                listaQuestionario = questionarioLogic.find(null);
            } catch (BusinessException ex) {
                addMessage(ex);
            } catch (SystemException ex) {
                addMessage(ex);
                Logger.getLogger(GerenciarQuestaoBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaQuestionario;
    }

    public Converter getCategoriaConverter() {
        return new Converter() {
            @Override
            public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
                try {
                    int id = Integer.parseInt(string);
                    for (Categoria categoria : getListaCategoria()) {
                        if (categoria.getId().equals(id)) {
                            return categoria;
                        }
                    }
                } catch (NumberFormatException ex) {
                }
                return null;
            }

            @Override
            public String getAsString(FacesContext fc, UIComponent uic, Object o) {
                Categoria categoria = null;
                if (o != null) {
                    categoria = (Categoria) o;
                    return categoria.getId() + "";
                }
                return "";
            }
        };
    }
    public Converter getQuestionarioConverter() {
        return new Converter() {
            @Override
            public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
                try {
                    int id = Integer.parseInt(string);
                    for (Questionario questionario : getListaQuestionario()) {
                        if (questionario.getId().equals(id)) {
                            return questionario;
                        }
                    }
                } catch (NumberFormatException ex) {
                }
                return null;
            }

            @Override
            public String getAsString(FacesContext fc, UIComponent uic, Object o) {
                Questionario questionario = null;
                if (o != null) {
                    questionario = (Questionario) o;
                    return questionario.getId() + "";
                }
                return "";
            }
        };
    }

}
