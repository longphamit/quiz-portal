package com.longpc.devmon.portal.quizportal;


import com.sun.faces.config.ConfigureListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextListener;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;

@SpringBootApplication
public class QuizPortalApplication implements ServletContextAware {


    public static void main(String[] args) {
        SpringApplication.run(QuizPortalApplication.class, args);
    }

    //
    @Bean
    public ServletRegistrationBean<FacesServlet> servletRegistrationBean() {
        ServletRegistrationBean<FacesServlet> servletRegistrationBean = new ServletRegistrationBean<>(
                new FacesServlet(), "*.xhtml" );
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }



    @Override
    public void setServletContext(ServletContext servletContext) {
        // Iniciar el contexto de JSF
        // http://stackoverflow.com/a/25509937/1199132
        //   servletContext.addListener(new ContextLoaderListener());
        servletContext.addListener(new RequestContextListener());
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
        servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
        servletContext.setInitParameter("primefaces.THEME", "ultima-blue");
        servletContext.setInitParameter("primefaces.UPLOADER", "commons");

    }

}
