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
package org.jplus.annotation;

import org.jplus.hibatis.core.ConfigManager;
import org.jplus.hibatis.core.ConfigManagerImpl;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *只有带有此注解的接口才会被Hibatis代理
 * @author hyberbin
 */
@Target({java.lang.annotation.ElementType.TYPE})//该注解只能用在类上
@Retention(RetentionPolicy.RUNTIME)
public @interface Hibatis {

    Class<? extends ConfigManager> configManager() default ConfigManagerImpl.class;
}
