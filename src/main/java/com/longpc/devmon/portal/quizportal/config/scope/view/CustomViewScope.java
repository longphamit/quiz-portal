package com.longpc.devmon.portal.quizportal.config.scope.view;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.Map;

/**
 * Long PC
 * 04/03/2024| 21:02 | 2024
 **/
public class CustomViewScope implements Scope {

    public Object get(String name, ObjectFactory<?> objectFactory) {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, Object> viewMap = facesContext.getViewRoot().getViewMap();
        Object viewScopedBean = viewMap.get(name);

        if (viewScopedBean == null) {
            viewScopedBean = objectFactory.getObject();
            viewMap.put(name, viewScopedBean);
        }

        return viewScopedBean;
    }

    public void registerDestructionCallback(String name, Runnable callback) {
        // Unsupported feature
    }

    public Object remove(String name) {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, Object> viewMap = facesContext.getViewRoot().getViewMap();

        return viewMap.remove(name);
    }

    public Object resolveContextualObject(String key) {

        // Unsupported feature
        return null;
    }

    public String getConversationId() {

        // Unsupported feature
        return null;
    }

}