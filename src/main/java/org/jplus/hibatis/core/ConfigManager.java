/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jplus.hibatis.core;

import org.jplus.hibatis.bean.HibatisClassBean;

/**
 *
 * @author hyberbin
 */
public interface ConfigManager {

    public HibatisClassBean getHibatisBean(Class clazz);
}
