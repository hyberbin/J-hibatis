/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jplus.hibatis.test;

import org.jplus.contex.core.ObjectContex;
import org.jplus.hibatis.HibatisInit;
import org.jplus.hibatis.beans.Properties;
import org.jplus.hibatis.dao.PropertiesDao;
import org.jplus.hyb.database.config.ConfigCenter;
import org.jplus.hyb.database.config.DbConfig;
import org.jplus.hyb.database.config.SimpleConfigurator;
import org.jplus.hyb.database.sqlite.SqliteUtil;
import org.jplus.hyb.database.transaction.SimpleManager;
import org.jplus.hyb.log.LocalLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hyberbin
 */
public class Test {

    public static void main(String[] args) {
        SimpleConfigurator.addConfigurator(new DbConfig(DbConfig.DRIVER_SQLITE, "jdbc:sqlite:data.db", "", "", DbConfig.DEFAULT_CONFIG_NAME));
        ConfigCenter.INSTANCE.setManager(new SimpleManager(DbConfig.DEFAULT_CONFIG_NAME));
        SqliteUtil.setProperty("name", "hyb");
        LocalLogger.setLevel(LocalLogger.INFO);
        HibatisInit.init();
        PropertiesDao propertiesDao = ObjectContex.CONTEX.getResource(PropertiesDao.class);
        List<Properties> allPropertis = propertiesDao.getAllPropertis();
        for(Properties properties:allPropertis){
            System.out.println(properties.getKey()+":"+properties.getValue());
        }
        Map map=new HashMap();
        map.put("key", "name");
        Properties property = propertiesDao.getProperty(map);
        System.out.println(property.getKey()+":"+property.getValue());
        Properties properties=new Properties("age","8");
        propertiesDao.save(properties);
        Properties age = propertiesDao.getPropertyByKey("age");
        System.out.println(age.getKey()+":"+age.getValue());
    }
}
