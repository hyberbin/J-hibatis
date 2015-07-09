/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jplus.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jplus.hibatis.core.ConfigManager;
import org.jplus.hibatis.core.ConfigManagerImpl;

/**
 *
 * @author hyberbin
 */
@Target({java.lang.annotation.ElementType.TYPE})//该注解只能用在类上
@Retention(RetentionPolicy.RUNTIME)
public @interface Hibatis {

    Class<? extends ConfigManager> configManager() default ConfigManagerImpl.class;
}
