/*
 * Copyright 2019 Steinar Bang
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
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.service.jdbc.DataSourceFactory;

/**
 * Code common to all implementations of {@link DatabaseService}
 */
public abstract class DatabaseServiceBase implements DatabaseService {

    /**
     * Return the {@link DataSource#getConnection()} of the
     * {@link DataSource} held by the class implementing
     * {@link DatabaseService}.
     *
     * Will throw a {@link DatabaseServiceException} if the
     * {@link DataSource} is missing.
     */
    @Override
    public Connection getConnection() throws SQLException {
        if (getDatasource() == null) {
            throw new DatabaseServiceException("No DataSource set");
        }

        return getDatasource().getConnection();
    }

    /**
     * Create JDBC connection properties
     *
     * @param jdbcUrl is set as the value of the {@code DataSourceFactory#JDBC_URL} property
     * @param jdbcUser is set as the value of the {@code DataSourceFactory#JDBC_USER} property, if the argument is an empty string, no {@code DataSourceFactory#JDBC_USER} property is set
     * @param jdbcPassword is set as the value of the {@code DataSourceFactory#JDBC_PASSWORD} property, if the argument is an empty string, no {@code DataSourceFactory#JDBC_PASSWORD} property is set
     * @return a {@code Properties} instance holding connection information for a JDBC connection. At a minimum it will contain a {@code DataSourceFactory#JDBC_URL} value, and it may also contain {@code DataSourceFactory#JDBC_USER} and {@code DataSourceFactory#JDBC_PASSWORD} values
     */
    public static Properties createDatabaseConnectionProperties(String jdbcUrl, String jdbcUser, String jdbcPassword) {
        Properties properties = new Properties();
        properties.setProperty(DataSourceFactory.JDBC_URL, jdbcUrl);
        if (!"".equals(jdbcUser)) {
            properties.setProperty(DataSourceFactory.JDBC_USER, jdbcUser);
        }
        if (!"".equals(jdbcPassword)) {
            properties.setProperty(DataSourceFactory.JDBC_PASSWORD, jdbcPassword);
        }

        return properties;
    }

}
