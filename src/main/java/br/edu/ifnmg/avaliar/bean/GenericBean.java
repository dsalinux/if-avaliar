package br.edu.ifnmg.avaliar.bean;

import br.edu.ifnmg.avaliar.util.exception.BusinessException;
import br.edu.ifnmg.avaliar.util.exception.SystemException;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Danilo Souza Almeida
 */
public class GenericBean implements Serializable {
    
    private State currentState;
    
    public enum State {
        SEARCH,
        INSERT,
        EDIT
    }

    public GenericBean() {
        currentState = State.SEARCH;
    }
    
    public void changeStateToSearch(){
        currentState = State.SEARCH;
    }
    public void changeStateToEdit(){
        currentState = State.EDIT;
    }
    public void changeStateToInsert(){
        currentState = State.INSERT;
    }
    
    public boolean isStateEdit(){
        return State.EDIT.equals(currentState);
    }
    public boolean isStateInsert(){
        return State.INSERT.equals(currentState);
    }
    public boolean isStateSearch(){
        return State.SEARCH.equals(currentState);
    }
    public boolean isStateNotEdit(){
        return !State.EDIT.equals(currentState);
    }
    public boolean isStateNotInsert(){
        return !State.INSERT.equals(currentState);
    }
    public boolean isStateNotSearch(){
        return !State.SEARCH.equals(currentState);
    }
    
    public String getCurrentStateName() {
        return currentState.name();
    }
    public State getCurrentState() {
        return currentState;
    }
    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
    
    protected void addMessage(String message) {
        addMessage(null, message, message, FacesMessage.SEVERITY_INFO);
    }

    protected void addMessage(String componentId, String message) {
        addMessage(componentId, message, message, FacesMessage.SEVERITY_INFO);
    }

    protected void addMessage(FacesMessage.Severity severity, String message) {
        addMessage(null, message, message, severity);
    }
    protected void addMessage(BusinessException ex) {
        addMessage(null, ex.getMessage(), ex.getCause().getMessage(), getSeverityWarn());
    }
    protected void addMessage(SystemException ex) {
        addMessage(null, ex.getMessage(), ex.getCause().getMessage(), getSeverityError());
    }

    protected void addMessage(String componentId, String sumary, String detail, FacesMessage.Severity severity) {
        FacesContext.getCurrentInstance().addMessage(componentId, new FacesMessage(severity, sumary, detail));
    }

    protected String getMessageForKey(String key) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ResourceBundle rb = ctx.getApplication().getResourceBundle(ctx, "i18n");
        return rb.getString(key);
    }
    
    protected FacesMessage getFacesMessageForKey(String key) {
        return new FacesMessage(getMessageForKey(key));
    }
    
    public FacesMessage.Severity getSeverityInfo(){
        return FacesMessage.SEVERITY_INFO;
    }
    
    public FacesMessage.Severity getSeverityWarn(){
        return FacesMessage.SEVERITY_WARN;
    }
    
    public FacesMessage.Severity getSeverityError(){
        return FacesMessage.SEVERITY_ERROR;
    }
    
    public FacesMessage.Severity getSeverityFatal(){
        return FacesMessage.SEVERITY_FATAL;
    }
}
