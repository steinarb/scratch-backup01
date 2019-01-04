/*
 * Copyright 2018 Steinar Bang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */
package no.priv.bang.osgi.service.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;


/**
 * This is an OSGi service that encapsulates a JDBC database connection.
 *
 * Implementations of this interface will typically connect to or start
 * an RDBMS like PostgreSQL or Derby.  Implementations will typically
 * also create and/or update the database schema using
 * <a href="no.priv.bang.osgi.service.database">Liquibase</a>.
 *
 * @author Steinar Bang
 *
 */
public interface DatabaseService {

    /**
     * Provide access to a database.
     *
     * @return a {@link DataSource} object that can be used to connect to a database
     */
    DataSource getDatasource();

    /**
     * Get a connection to a database
     *
     * @return a {@link Connection} to a database
     */
    Connection getConnection();

    /**
     * Create a {@link PreparedStatement} that can have parameters replaced
     * and then used to retrieve data with {@link PreparedStatement#executeQuery()}
     * or modify the database with {@link PreparedStatement#executeUpdate()}.
     *
     * @param sql a {@link String} containing an SQL query with '?' where parts of the query should be replaced by parameters
     * @return a {@link PreparedStatement} that can be used to retrieve data from or update data in the database
     * @throws SQLException som er kastet av av {@link Connection#prepareStatement(String)}
     */
    PreparedStatement prepareStatement(String sql) throws SQLException;

}
