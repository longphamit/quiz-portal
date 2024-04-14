package com.longpc.devmon.portal.quizportal.view;

import lombok.SneakyThrows;
import org.primefaces.PrimeFaces;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Map;

/**
 * Long PC
 * 04/03/2024| 21:01 | 2024
 **/
public abstract class BaseView {
    public enum DetailPageMode {
        VIEW,
        CREATE
    }

    protected boolean checkPostBack() {
        return !FacesContext.getCurrentInstance().isPostback();
    }

    protected void addError(String errorMessage) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "", errorMessage));
        updateComponents("mgsForm:mgs");
    }

    protected void addMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "", message));
        updateComponents("mgsForm:mgs");
    }

    public void redirectPageInNewTab(String url) {
        PrimeFaces.current().executeScript("window.open('" + url + "');");
    }

    @SneakyThrows
    public void redirectPageInCurrentTab(String url) {
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }
    public void updateComponents(String... components) {
        PrimeFaces.current().ajax().update(components);
    }

    public Map<String, String> getRequestParam() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    }

    public String getRequestParam(String name) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
    }

    public void setValueCrossBean(String function) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ELContext elContext = fc.getELContext();
        ExpressionFactory factory = fc.getApplication().getExpressionFactory();
        MethodExpression methodExpression = factory.createMethodExpression(fc.getELContext(), function, String.class, new Class[]{});
        methodExpression.invoke(elContext, new Object[0]);
    }

    public void setValueCrossBean(String function, Object arg) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ELContext elContext = fc.getELContext();
        ExpressionFactory factory = fc.getApplication().getExpressionFactory();
        MethodExpression methodExpression = factory.createMethodExpression(fc.getELContext(), function, Object.class, new Class[]{Object.class});
        methodExpression.invoke(elContext, new Object[]{arg});
    }
    public void showDialog(String dialog) {
        PrimeFaces.current().executeScript("PF('" + dialog + "').show()");
    }

    public void hideDialog(String dialog) {
        PrimeFaces.current().executeScript("PF('" + dialog + "').hide()");
    }
}
