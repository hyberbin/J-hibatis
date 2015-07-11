/*
 * Copyright 2015 www.hyberbin.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Email:hyberbin@qq.com
 */
package org.jplus.hibatis;

import org.jplus.contex.core.ObjectContext;
import org.jplus.hibatis.beans.Properties;
import org.jplus.hibatis.dao.PropertiesDao;
import org.jplus.hyb.database.config.ConfigCenter;
import org.jplus.hyb.database.config.DbConfig;
import org.jplus.hyb.database.config.SimpleConfigurator;
import org.jplus.hyb.database.sqlite.SqliteUtil;
import org.jplus.hyb.database.transaction.SimpleManager;
import org.jplus.hyb.log.LocalLogger;
import org.junit.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author hyberbin
 */
public class PropertiesDaoTest {
    private static PropertiesDao propertiesDao;
    public PropertiesDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        SimpleConfigurator.addConfigurator(new DbConfig(DbConfig.DRIVER_SQLITE, "jdbc:sqlite:data.db", "", "", DbConfig.DEFAULT_CONFIG_NAME));
        ConfigCenter.INSTANCE.setManager(new SimpleManager(DbConfig.DEFAULT_CONFIG_NAME));
        LocalLogger.setLevel(LocalLogger.INFO);
        HibatisInit.init();
        propertiesDao = ObjectContext.CONTEXT.getResource(PropertiesDao.class);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        tearDown();
        SqliteUtil.setProperty("name", "hyb");
        SqliteUtil.setProperty("age", "8");
    }
    
    @After
    public void tearDown() {
        SqliteUtil.clearProperties();
    }

    /**
     * Test of getAllPropertis method, of class PropertiesDao.
     */
    @Test
    public void testGetAllPropertis() {
        System.out.println("getAllPropertis");
        List<Properties> result = propertiesDao.getAllPropertis();
        assertEquals(result.size(), 2);
    }

    /**
     * Test of getProperty method, of class PropertiesDao.
     */
    @Test
    public void testGetProperty() {
        System.out.println("getProperty");
        Map map = new HashMap();
        map.put("key","name");
        Properties property = propertiesDao.getProperty(map);
        assertEquals(property.getKey(), "name");
        assertEquals(property.getValue(), "hyb");
    }

    /**
     * Test of getPropertyByKey method, of class PropertiesDao.
     */
    @Test
    public void testGetPropertyByKey() {
        System.out.println("getPropertyByKey");
        String key = "name";
        Properties property = propertiesDao.getPropertyByKey(key);
        assertEquals(property.getKey(), "name");
        assertEquals(property.getValue(), "hyb");
    }
    @Test
    public void testSave(){
        System.out.println("testSave");
        Properties property = new Properties("site","http://www.hyberbin.com");
        propertiesDao.save(property);
        Properties site = propertiesDao.getPropertyByKey("site");
        assertEquals(site.getKey(), "site");
        assertEquals(site.getValue(), "http://www.hyberbin.com");
    }

    @Test
    public void testSaveOrUpdate(){
        System.out.println("testSaveOrUpdate");
        Properties property = new Properties("site","http://www.hyberbin.com");
        propertiesDao.save(property);
        Properties update = new Properties("site","www.hyberbin.com");
        propertiesDao.saveOrUpdate(update);
        Properties site = propertiesDao.getPropertyByKey("site");
        assertEquals(site.getKey(), "site");
        assertEquals(site.getValue(), "www.hyberbin.com");
    }

    @Test
    public void testGetOneByKey(){
        System.out.println("testGetOneByKey");
        Properties property =propertiesDao.getOneByKey("name");
        assertEquals(property.getKey(), "name");
        assertEquals(property.getValue(), "hyb");

        Properties property2 =propertiesDao.getOneByKey("age");
        assertEquals(property2.getKey(), "age");
        assertEquals(property2.getValue(), "8");
    }

    @Test
    public void testGetOneByKey2(){
        System.out.println("testGetOneByKey");
        Properties property = (Properties)propertiesDao.getOneByKey("key","name");
        assertEquals(property.getKey(), "name");
        assertEquals(property.getValue(), "hyb");

        Properties property2 = (Properties)propertiesDao.getOneByKey("value","8");
        assertEquals(property2.getKey(), "age");
        assertEquals(property2.getValue(), "8");
    }

    @Test
    public void testDeleteByKey(){
        
    }

    @Test
    public void testDeleteByKey2(){
        
    }
}
