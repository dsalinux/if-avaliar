package br.edu.ifnmg.avaliar.bean;

import br.edu.ifnmg.avaliar.dao.RespostaDAO;
import br.edu.ifnmg.avaliar.entity.Categoria;
import br.edu.ifnmg.avaliar.entity.CategoriaRespostaVO;
import br.edu.ifnmg.avaliar.entity.Questao;
import br.edu.ifnmg.avaliar.entity.Questionario;
import br.edu.ifnmg.avaliar.entity.Resposta;
import br.edu.ifnmg.avaliar.logic.CategoriaLogic;
import br.edu.ifnmg.avaliar.logic.ConfiguracaoLogic;
import br.edu.ifnmg.avaliar.logic.QuestaoLogic;
import br.edu.ifnmg.avaliar.logic.RespostaLogic;
import br.edu.ifnmg.avaliar.util.exception.BusinessException;
import br.edu.ifnmg.avaliar.util.exception.SystemException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class ResponderQuestionarioBean extends GenericBean{
    
    private List<Categoria> categorias;
    private CategoriaLogic categoriaLogic = new CategoriaLogic();
    private QuestaoLogic questaoLogic = new QuestaoLogic();
    private RespostaLogic respostaLogic = new RespostaLogic();
    private ConfiguracaoLogic configuracaoLogic = new ConfiguracaoLogic();
    private List<CategoriaRespostaVO> listaCategoriasResposta;
    private Questionario questionarioAtual;
    
    @PostConstruct
    public void init(){
        try {
            questionarioAtual = configuracaoLogic.getConfiguracao().getQuestionario();
            categorias = categoriaLogic.find(null);
            listaCategoriasResposta = new ArrayList<>();
            for (Categoria categoria : categorias) {
                CategoriaRespostaVO categoriaResposta = new  CategoriaRespostaVO();
                categoriaResposta.setCategoria(categoria);
                categoriaResposta.setRespostas(buscarRespostasCategoria(questionarioAtual, categoria));
                listaCategoriasResposta.add(categoriaResposta);
            }
        } catch (BusinessException ex) {
            addMessage(ex);
        } catch (SystemException ex) {
            addMessage(ex);
            Logger.getLogger(ResponderQuestionarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<Resposta> buscarRespostasCategoria(Questionario questionario, Categoria categoria){
        List<Resposta> resp = new ArrayList<>();
        List<Questao> questoes = questaoLogic.buscarQuestao(questionario, categoria);
        for (Questao questao : questoes) {
            Resposta resposta = new Resposta();
            resposta.setQuestao(questao);
            resp.add(resposta);
        }
        return resp;
    }
    
    public void sair(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect("login-resposta.jsf");
        } catch (IOException ex) {
            addMessage(null, "Erro ao tentar retornar para página inicial.", ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void redirecionarAgradecimento(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("agradecimento.jsf");
        } catch (IOException ex) {
            addMessage(null, "Erro ao tentar ir para agradecimento.", ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void finalizar(){
        respostaLogic.salvarRespostas(listaCategoriasResposta);
        redirecionarAgradecimento();
    }

    public List<CategoriaRespostaVO> getListaCategoriasResposta() {
        return listaCategoriasResposta;
    }

    public Questionario getQuestionarioAtual() {
        return questionarioAtual;
    }

    public void setQuestionarioAtual(Questionario questionarioAtual) {
        this.questionarioAtual = questionarioAtual;
    }
    
}
