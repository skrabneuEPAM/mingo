package com.mingo.core;

import com.mingo.context.Context;
import com.mingo.executor.QueryExecutor;
import com.mingo.executor.QueryExecutorFactory;
import com.mongodb.Mongo;

import java.util.List;
import java.util.Map;

/**
 * Copyright 2012-2013 The Mingo Team
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * Interface that specifies a basic set of Mingo operations.
 */
public class MingoTemplate {

    private Mongo mongo;

    private Context context;

    private QueryExecutor queryExecutor;

    /**
     * Constructor with parameters.
     *
     * @param context mingo context{@link Context}
     */
    public MingoTemplate(Context context) {
        this.context = context;
        this.queryExecutor = QueryExecutorFactory.create(context);
    }

    /**
     * Constructor with parameters.
     *
     * @param mongo   mongo {@link Mongo}
     * @param context mingo context{@link Context}
     */
    public MingoTemplate(Mongo mongo, Context context) {
        this.mongo = mongo;
        this.context = context;
        this.queryExecutor = QueryExecutorFactory.create(mongo, context);
    }

    /**
     * Perform query with parameters and return instance with specified type as result.
     *
     * @param queryName  query name
     * @param type       type of object
     * @param <T>        the type of the class modeled by this {@code Class} object.
     * @param parameters query parameters
     * @return object
     */
    public <T> T queryForObject(String queryName, Class<T> type, Map<String, Object> parameters) {
        return queryExecutor.queryForObject(queryName, type, parameters);
    }

    /**
     * Perform query without parameters and return instance with specified type as result.
     *
     * @param queryName query name
     * @param type      type of object
     * @param <T>       the type of the class modeled by this {@code Class} object.
     * @return object
     */
    public <T> T queryForObject(String queryName, Class<T> type) {
        return queryExecutor.queryForObject(queryName, type);
    }

    /**
     * Perform query with parameters and return list of objects.
     * Default implementation of list is {@link java.util.ArrayList}.
     *
     * @param queryName  query name
     * @param type       type of object
     * @param parameters query parameters
     * @param <T>        the type of the class modeled by this {@code Class} object.
     * @return list of objects
     */
    public <T> List<T> queryForList(String queryName, Class<T> type, Map<String, Object> parameters) {
        return queryExecutor.queryForList(queryName, type, parameters);
    }

    /**
     * Perform query without parameters and return list of objects.
     * Default implementation of list is {@link java.util.ArrayList}.
     *
     * @param queryName query name
     * @param type      type of object
     * @param <T>       the type of the class modeled by this {@code Class} object.
     * @return list of objects
     */
    public <T> List<T> queryForList(String queryName, Class<T> type) {
        return queryExecutor.queryForList(queryName, type);
    }

}
