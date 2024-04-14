/*
 *  Copyright 2009-2022 PrimeTek.
 *
 *  Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.primefaces.layout.component;

import org.primefaces.component.api.Widget;
import org.primefaces.component.menu.AbstractMenu;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.*;

@ListenerFor(sourceClass = UltimaMenu.class, systemEventClass = PostAddToViewEvent.class)
public class UltimaMenu extends AbstractMenu implements Widget, ComponentSystemEventListener {

    public static final String COMPONENT_TYPE = "org.primefaces.component.UltimaMenu";
    public static final String COMPONENT_FAMILY = "org.primefaces.component";
    private static final String DEFAULT_RENDERER = "org.primefaces.component.UltimaMenuRenderer";
    private static final String[] LEGACY_RESOURCES = new String[]{"primefaces.css", "jquery/jquery.js", "jquery/jquery-plugins.js", "primefaces.js"};
    private static final String[] MODERN_RESOURCES = new String[]{"components.css", "jquery/jquery.js", "jquery/jquery-plugins.js", "core.js"};
    private static final String[] DEPENDENCY_RESOURCES = new String[]{
        "primeicons/primeicons.css",
        "js/layout.menu.js"
    };
    
    protected enum PropertyKeys {

        widgetVar, model, style, styleClass, stateKey, stateStorage;

        String toString;

        PropertyKeys(String toString) {
            this.toString = toString;
        }

        PropertyKeys() {
        }

        public String toString() {
            return ((this.toString != null) ? this.toString : super.toString());
        }
    }

    public UltimaMenu() {
        setRendererType(DEFAULT_RENDERER);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getWidgetVar() {
        return (String) getStateHelper().eval(PropertyKeys.widgetVar, null);
    }

    public void setWidgetVar(String _widgetVar) {
        getStateHelper().put(PropertyKeys.widgetVar, _widgetVar);
    }

    public org.primefaces.model.menu.MenuModel getModel() {
        return (org.primefaces.model.menu.MenuModel) getStateHelper().eval(PropertyKeys.model, null);
    }

    public void setModel(org.primefaces.model.menu.MenuModel _model) {
        getStateHelper().put(PropertyKeys.model, _model);
    }

    public String getStyle() {
        return (String) getStateHelper().eval(PropertyKeys.style, null);
    }

    public void setStyle(String _style) {
        getStateHelper().put(PropertyKeys.style, _style);
    }

    public String getStyleClass() {
        return (String) getStateHelper().eval(PropertyKeys.styleClass, null);
    }

    public void setStyleClass(String _styleClass) {
        getStateHelper().put(PropertyKeys.styleClass, _styleClass);
    }
    
    public String getStateKey() {
        return (String) getStateHelper().eval(PropertyKeys.stateKey, "pf-ultima-menu");
    }

    public void setStateKey(String _stateKey) {
        getStateHelper().put(PropertyKeys.stateKey, _stateKey);
    }

    public String getStateStorage() {
        return (String) getStateHelper().eval(PropertyKeys.stateStorage, "session");
    }

    public void setStateStorage(String _stateStorage) {
        getStateHelper().put(PropertyKeys.stateStorage, _stateStorage);
    }

    public String resolveWidgetVar() {
        FacesContext context = getFacesContext();
        String userWidgetVar = (String) getAttributes().get("widgetVar");

        if (userWidgetVar != null)
            return userWidgetVar;
        else
            return "widget_" + getClientId(context).replaceAll("-|" + UINamingContainer.getSeparatorChar(context), "_");
    }

    public void addComponentResource(String resource, String library) {
        FacesContext context = getFacesContext();
        UIViewRoot root = context.getViewRoot();
        UIComponent component = context.getApplication().createComponent(UIOutput.COMPONENT_TYPE);

        if (resource.endsWith("css"))
            component.setRendererType("javax.faces.resource.Stylesheet");
        else if (resource.endsWith("js"))
            component.setRendererType("javax.faces.resource.Script");

        component.getAttributes().put("library", library);
        component.getAttributes().put("name", resource);

        root.addComponentResource(context, component);
    }
    
    @Override
    public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
        if (event instanceof PostAddToViewEvent) {
            boolean isPrimeConfig;
            try {
                isPrimeConfig = Class.forName("org.primefaces.config.PrimeConfiguration") != null;
            } catch (ClassNotFoundException e) {
                isPrimeConfig = false;
            }

            String[] resources = (isPrimeConfig) ? MODERN_RESOURCES : LEGACY_RESOURCES;

            for (String resource : resources) {
                addComponentResource(resource, "primefaces");
            }

            for (String dependency_resource : DEPENDENCY_RESOURCES) {
                addComponentResource(dependency_resource, "layout");
            }
        }
    }
}
