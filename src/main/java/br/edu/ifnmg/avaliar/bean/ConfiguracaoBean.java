package br.edu.ifnmg.avaliar.bean;

import br.edu.ifnmg.avaliar.entity.Configuracao;
import br.edu.ifnmg.avaliar.entity.Questionario;
import br.edu.ifnmg.avaliar.logic.ConfiguracaoLogic;
import br.edu.ifnmg.avaliar.logic.QuestionarioLogic;
import br.edu.ifnmg.avaliar.util.exception.BusinessException;
import br.edu.ifnmg.avaliar.util.exception.SystemException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@ManagedBean
@SessionScoped
public class ConfiguracaoBean extends GenericBean {

    private Configuracao entity;
    private List<Questionario> listaQuestionario = null;
    private ConfiguracaoLogic logic = new ConfiguracaoLogic();
    private QuestionarioLogic questionarioLogic = new QuestionarioLogic();

    @PostConstruct
    public void init() {
        buscar();
    }

    public void salvar() {
        logic.salvar(entity);
        addMessage("Salvo com sucesso!");
    }
    
    public void buscar(){
        entity = logic.getConfiguracao();
    }

    public Configuracao getEntity() {
        return entity;
    }

    public void setEntity(Configuracao entity) {
        this.entity = entity;
    }

    public List<Questionario> getListaQuestionario() {
        if (listaQuestionario == null) {
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
