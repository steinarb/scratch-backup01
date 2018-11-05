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
package no.bang.priv.handlereg.db.derby.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;
import javax.sql.PooledConnection;

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
import no.bang.priv.handlereg.db.liquibase.HandleregLiquibase;
import no.bang.priv.handlereg.services.HandleregDatabase;
import no.bang.priv.handlereg.services.HandleregException;

@Component(service=HandleregDatabase.class, immediate=true)
public class HandleregDerbyTestDatabase implements HandleregDatabase {

    PooledConnection pooledConnect;
    Connection connect;
    private LogService logservice;
    private DataSourceFactory dataSourceFactory;
    private DataSource dataSource;

    @Reference
    public void setLogService(LogService logservice) {
        this.logservice = logservice;
    }

    @Reference
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }

    @Activate
    public void activate() {
        try {
            connect = createConnection();
            HandleregLiquibase handleregLiquibase = new HandleregLiquibase();
            handleregLiquibase.createInitialSchema(connect);
            insertMockData(connect);
            handleregLiquibase.updateSchema(connect);
        } catch (Exception e) {
            String message = "Failed to create handlereg derby test database";
            logError(message, e);
            throw new HandleregException(message, e);
        }
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connect.prepareStatement(sql);
    }

    @Override
    public ResultSet query(PreparedStatement statement) throws SQLException {
        return statement.executeQuery();
    }

    @Override
    public int update(PreparedStatement statement) throws SQLException {
        return statement.executeUpdate();
    }

    @Override
    public boolean forceReleaseLiquibaseLock() {
        try {
            HandleregLiquibase liquibase = new HandleregLiquibase();
            liquibase.forceReleaseLocks(connect);
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

    public void insertMockData(Connection connect) throws LiquibaseException, SQLException {
        DatabaseConnection databaseConnection = new JdbcConnection(connect);
        ClassLoaderResourceAccessor classLoaderResourceAccessor = new ClassLoaderResourceAccessor(getClass().getClassLoader());
        Liquibase liquibase = new Liquibase("sql/data/db-changelog.xml", classLoaderResourceAccessor, databaseConnection);
        liquibase.update("");
    }

    private void logError(String message, Exception exception) {
        logservice.log(LogService.LOG_ERROR, message, exception);
    }

    private Connection createConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty(DataSourceFactory.JDBC_URL, "jdbc:derby:memory:ukelonn;create=true");
        dataSource = dataSourceFactory.createDataSource(properties);
        return dataSource.getConnection();
    }

}
