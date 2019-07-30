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
package no.priv.bang.handlereg.db.postgresql;

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

import liquibase.Liquibase;
import liquibase.database.DatabaseConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import no.priv.bang.handlereg.db.liquibase.HandleregLiquibase;
import no.priv.bang.handlereg.services.HandleregDatabase;
import no.priv.bang.handlereg.services.HandleregException;
import static no.priv.bang.handlereg.services.HandleregConstants.*;

@Component(service=HandleregDatabase.class, immediate=true)
public class HandleregPostgresqlDatabase implements HandleregDatabase {

    private LogService logservice;
    private DataSourceFactory dataSourceFactory;
    DataSource dataSource;

    @Reference
    public void setLogService(LogService logservice) {
        this.logservice = logservice;
    }

    @Reference
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }

    @Activate
    public void activate(Map<String, Object> config) {
        try {
            dataSource = createDatasource(config);
            try (Connection connect = getConnection()) {
                HandleregLiquibase handleregLiquibase = new HandleregLiquibase();
                handleregLiquibase.createInitialSchema(connect);
                insertMockData(connect);
                handleregLiquibase.updateSchema(connect);
            }
        } catch (Exception e) {
            String message = "Failed to create handlereg derby test database";
            logError(message, e);
            throw new HandleregException(message, e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public boolean forceReleaseLiquibaseLock() {
        try (Connection connection = getConnection()) {
            HandleregLiquibase liquibase = new HandleregLiquibase();
            liquibase.forceReleaseLocks(connection);
            return true;
        } catch (Exception e) {
            logError("Failed to force release Liquibase changelog lock on database", e);
            return false;
        }
    }

    @Override
    public DataSource getDatasource() {
        return dataSource;
    }

    @Override
    public String sumOverYearQuery() {
        return "select sum(t.transaction_amount), extract(year from t.transaction_time) as year from transactions t group by extract(year from t.transaction_time) order by extract(year from t.transaction_time)";
    }

    @Override
    public String sumOverMonthQuery() {
        return "select sum(t.transaction_amount), extract(year from t.transaction_time) as year, extract(month from t.transaction_time) as month from transactions t group by extract(year from t.transaction_time), extract(month from t.transaction_time) order by extract(year from t.transaction_time), extract(month from t.transaction_time)";
    }

    public void insertMockData(Connection connect) throws LiquibaseException {
        DatabaseConnection databaseConnection = new JdbcConnection(connect);
        ClassLoaderResourceAccessor classLoaderResourceAccessor = new ClassLoaderResourceAccessor(getClass().getClassLoader());
        Liquibase liquibase = new Liquibase("sql/data/db-changelog.xml", classLoaderResourceAccessor, databaseConnection);
        liquibase.update("");
    }

    private void logError(String message, Exception exception) {
        logservice.log(LogService.LOG_ERROR, message, exception);
    }


    private DataSource createDatasource(Map<String, Object> config) throws SQLException {
        Properties properties = createDatabaseConnectionProperties(config);
        return dataSourceFactory.createDataSource(properties);
    }

    static Properties createDatabaseConnectionProperties(Map<String, Object> config) {
        String jdbcUrl = ((String) config.getOrDefault(HANDLEREG_JDBC_URL, "jdbc:postgresql:///handlereg")).trim();
        String jdbcUser = ((String) config.getOrDefault(HANDLEREG_JDBC_USER, "karaf")).trim();
        String jdbcPassword = ((String) config.getOrDefault(HANDLEREG_JDBC_PASSWORD, "karaf")).trim();
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
