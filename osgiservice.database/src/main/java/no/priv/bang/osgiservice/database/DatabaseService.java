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
package no.priv.bang.osgiservice.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;


/**
 * This is an OSGi service that encapsulates a JDBC database connection.
 *
 * Implementations of this interface will typically connect to or start
 * an RDBMS like PostgreSQL or Derby.  Implementations will typically
 * also create and/or update the database schema using
 * <a href="no.priv.bang.osgi.service.database">Liquibase</a>.
 */
public interface DatabaseService {

    /**
     * Provide access to a database.
     *
     * @return a {@link DataSource} object that can be used to connect to a database
     */
    DataSource getDatasource();

    /**
     * Convenience function to get a connection to a database
     *
     * <em>Note!</em> Implementations should not cache this value
     * because that may cause table lock issues in some implementations.
     *
     * @return a {@link Connection} to a database
     * @throws SQLException thrown by the underlying {@link DataSource#getConnection()}
     */
    Connection getConnection() throws SQLException;

}
