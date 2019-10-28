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

import org.osgi.service.jdbc.DataSourceFactory;

public abstract class DatabaseServiceBase implements DatabaseService {

    @Override
    public Connection getConnection() throws SQLException {
        if (getDatasource() == null) {
            throw new DatabaseServiceException("No DataSource set");
        }

        return getDatasource().getConnection();
    }

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
