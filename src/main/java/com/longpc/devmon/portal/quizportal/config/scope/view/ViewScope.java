package com.longpc.devmon.portal.quizportal.config.scope.view;

import org.springframework.context.annotation.Scope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Long PC
 * 04/03/2024| 21:05 | 2024
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Scope("customViewScope")
public @interface ViewScope {

}