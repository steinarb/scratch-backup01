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
package no.priv.bang.authservice.db.postgresql;

import static no.priv.bang.authservice.definitions.AuthserviceConstants.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jdbc.DataSourceFactory;
import org.osgi.service.log.LogService;

import liquibase.exception.DatabaseException;
import liquibase.exception.LockException;
import no.priv.bang.authservice.db.liquibase.AuthserviceLiquibase;
import no.priv.bang.authservice.definitions.AuthserviceDatabaseService;
import no.priv.bang.authservice.definitions.AuthserviceException;

@Component(immediate=true)
public class PostgresqlDatabase implements AuthserviceDatabaseService {

    private LogService logservice;
    private DataSourceFactory dataSourceFactory;
    private DataSource datasource;

    @Reference
    public void setLogservice(LogService logservice) {
        this.logservice = logservice;
    }

    @Reference
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }

    @Activate
    public void activate(Map<String, Object> config) {
        try {
            datasource = createDatasource(config);
            try(Connection connection = datasource.getConnection()) {
                AuthserviceLiquibase liquibase = new AuthserviceLiquibase();
                try {
                    liquibase.createInitialSchema(connection);
                    liquibase.applyChangelist(connection, getClass().getClassLoader(), "db-changelog/db-changelog.xml");
                    liquibase.updateSchema(connection);
                } catch (LockException e) {
                    logservice.log(LogService.LOG_WARNING, "Authservice PostgreSQL component failed to aquire liquibase lock, trying to force liquibase lock", e);
                    liquibase.forceReleaseLocks(connection, logservice);
                    logservice.log(LogService.LOG_WARNING, "Liquibase lock successfully forced, continuing without modifying the schema");
                }
            }
        } catch (DatabaseException e) {
            logservice.log(LogService.LOG_WARNING, "Authservice PostgreSQL component caught exception during liquibase schema setup, continuing without modifying the schema", e);
        } catch (Exception e) {
            String message = "Failed to activate authservice PostgreSQL database component";
            logservice.log(LogService.LOG_ERROR, message, e);
            throw new AuthserviceException(message, e);
        }
    }

    @Override
    public DataSource getDatasource() {
        return datasource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }

    private DataSource createDatasource(Map<String, Object> config) throws SQLException {
        Properties properties = createDatabaseConnectionProperties(config);
        return dataSourceFactory.createDataSource(properties);
    }

    static Properties createDatabaseConnectionProperties(Map<String, Object> config) {
        String jdbcUrl = ((String) config.getOrDefault(AUTHSERVICE_JDBC_URL, "jdbc:postgresql:///authservice")).trim();
        String jdbcUser = ((String) config.getOrDefault(AUTHSERVICE_JDBC_USER, "karaf")).trim();
        String jdbcPassword = ((String) config.getOrDefault(AUTHSERVICE_JDBC_PASSWORD, "karaf")).trim();
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
