package com.longpc.devmon.portal.quizportal.config.scope.view;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Long PC
 * 04/03/2024| 21:03 | 2024
 **/
@Configuration
public class ViewScopeConfig {
    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.addScope("customViewScope", new CustomViewScope());
        return configurer;
    }
}
