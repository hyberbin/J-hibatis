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

import org.jplus.contex.aop.AopHandler;
import org.jplus.contex.aop.ResourceProxy;
import org.jplus.contex.core.ObjectContext;
import org.jplus.hibatis.beans.Properties;
import org.jplus.hibatis.dao.PropertiesDao;
import org.jplus.hyb.database.config.ConfigCenter;
import org.jplus.hyb.database.config.DbConfig;
import org.jplus.hyb.database.config.SimpleConfigurator;
import org.jplus.hyb.database.sqlite.SqliteUtil;
import org.jplus.hyb.database.transaction.SimpleManager;
import org.jplus.hyb.database.transaction.TxManager;
import org.jplus.hyb.log.LocalLogger;
import org.junit.*;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author hyberbin
 */
public class TestTransactionByAOP {
    private static PropertiesDao propertiesDao;
    public TestTransactionByAOP() {
    }

    @BeforeClass
    public static void setUpClass() {
        SimpleConfigurator.addConfigurator(new DbConfig(DbConfig.DRIVER_SQLITE, "jdbc:sqlite:data.db", "", "", DbConfig.DEFAULT_CONFIG_NAME));
        ConfigCenter.INSTANCE.setManager(new TxManager(DbConfig.DEFAULT_CONFIG_NAME));
        LocalLogger.setLevel(LocalLogger.INFO);
        //设置切入点
        ResourceProxy.setMethodAopAfterByRegex(".*hibatis.*dao.*Dao", ".*", AopTransactionHandler.class.getName());
        HibatisInit.init();
        propertiesDao =(PropertiesDao) new ResourceProxy().bind(ObjectContext.CONTEXT.getResource(PropertiesDao.class));
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            ConfigCenter.INSTANCE.getManager().finalCloseConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        tearDown();
        SqliteUtil.setProperty("name", "hyb");
        SqliteUtil.setProperty("age", "8");
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testTransactionByAOP(){
        Properties name = propertiesDao.getPropertyByKey("name");
        propertiesDao.deleteByKey(name);
    }

}
