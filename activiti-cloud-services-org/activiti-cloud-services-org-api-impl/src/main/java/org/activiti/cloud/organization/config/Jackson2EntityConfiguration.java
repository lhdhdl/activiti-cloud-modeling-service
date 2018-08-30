/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.cloud.organization.config;

import com.fasterxml.jackson.databind.JsonDeserializer;
import org.activiti.cloud.organization.api.Application;
import org.activiti.cloud.organization.api.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Entity jackson configuration
 */
@Configuration
public class Jackson2EntityConfiguration {

    private final JsonDeserializer<Application> applicationDeserializer;

    private final JsonDeserializer<Model> modelDeserializer;

    @Autowired
    public Jackson2EntityConfiguration(JsonDeserializer<Application> applicationDeserializer,
                                       JsonDeserializer<Model> modelDeserializer) {
        this.applicationDeserializer = applicationDeserializer;
        this.modelDeserializer = modelDeserializer;
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer addApplicationDeserializer() {
        return builder -> builder.deserializerByType(Application.class,
                                                     applicationDeserializer);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer addModelDeserializer() {
        return builder -> builder.deserializerByType(Model.class,
                                                     modelDeserializer);
    }

    @Bean
    @ConditionalOnMissingBean
    public JsonDeserializer<Application> applicationDeserializer() {
        return new ApplicationDeserializer();
    }

    @Bean
    @ConditionalOnMissingBean
    public JsonDeserializer<Model> modelDeserializer() {
        return new ModelDeserializer();
    }
}