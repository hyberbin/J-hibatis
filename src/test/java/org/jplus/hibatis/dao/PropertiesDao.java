/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jplus.hibatis.dao;

import org.jplus.annotation.Hibatis;
import org.jplus.hibatis.beans.Properties;

import java.util.List;
import java.util.Map;

/**
 *
 * @author hyberbin
 */
@Hibatis
public interface PropertiesDao {

    List<Properties> getAllPropertis();

    Properties getProperty(Map key);

    Properties getPropertyByKey(String key);

    void save(Properties properties);

    void saveOrUpdate(Properties properties);
}
