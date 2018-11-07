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
package no.bang.priv.handlereg.backend;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import no.bang.priv.handlereg.services.Butikk;
import no.bang.priv.handlereg.services.HandleregDatabase;
import no.bang.priv.handlereg.services.HandleregException;
import no.bang.priv.handlereg.services.HandleregService;
import no.bang.priv.handlereg.services.NyHandling;
import no.bang.priv.handlereg.services.Oversikt;
import no.bang.priv.handlereg.services.Transaction;

@Component(service=HandleregService.class, immediate=true)
public class HandleregServiceProvider implements HandleregService {

    private LogService logservice;
    private HandleregDatabase database;

    @Reference
    public void setLogservice(LogService logservice) {
        this.logservice = logservice;
    }

    @Reference
    public void setDatabase(HandleregDatabase database) {
        this.database = database;
    }

    @Activate
    public void activate() {
        // Ensures that the component isn't activated until all dependencies are injected
    }

    @Override
    public Oversikt finnOversikt(String brukernavn) {
        String sql = "select u.user_id, u.username, u.email, u.firstname, u.lastname, (select sum(t1.transaction_amount) from transactions t1 where t1.user_id=u.user_id) - (select sum(t1.transaction_amount) from transactions t1 where t1.user_id!=u.user_id) as balance from users u where u.username=?";
        try (PreparedStatement statement = database.prepareStatement(sql)) {
            statement.setString(1, brukernavn);
            ResultSet results = database.query(statement);
            if (results.next()) {
                int userid = results.getInt(1);
                String username = results.getString(2);
                String email = results.getString(3);
                String fornavn = results.getString(4);
                String etternavn = results.getString(5);
                double balanse = results.getDouble(6);
                return new Oversikt(userid, username, email, fornavn, etternavn, balanse);
            }

            return null;
        } catch (SQLException e) {
            String message = String.format("Failed to retrieve an Oversikt for user %s", brukernavn);
            logError(message, e);
            throw new HandleregException(message, e);
        }
    }

    @Override
    public List<Transaction> findLastTransactions(int userId) {
        List<Transaction> handlinger = new ArrayList<>();
        String sql = "select t.transaction_id, t.transaction_time, s.store_name, t.transaction_amount from transactions t join stores s on s.store_id=t.store_id where t.transaction_id in (select transaction_id from transactions where user_id=? order by transaction_time desc fetch next 10 rows only) order by t.transaction_time asc";
        try (PreparedStatement statement = database.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet results = database.query(statement);
            while(results.next()) {
                int transactionId = results.getInt(1);
                Date transactionTime = new Date(results.getTimestamp(2).getTime());
                String butikk = results.getString(3);
                double belop = results.getDouble(4);
                Transaction transaction = new Transaction(transactionId, transactionTime, butikk, belop);
                handlinger.add(transaction);
            }
        } catch (SQLException e) {
            String message = String.format("Failed to retrieve a list of transactions for user %d", userId);
            logError(message, e);
            throw new HandleregException(message, e);
        }
        return handlinger;
    }

    @Override
    public Oversikt registrerHandling(NyHandling handling) {
        Date transactionTime = handling.getTransactionTime() == null ? new Date() : handling.getTransactionTime();
        String sql = "insert into transactions (user_id, store_id, transaction_amount, transaction_time) values ((select user_id from users where username=?), ?, ?, ?)";
        try(PreparedStatement statement = database.prepareStatement(sql)) {
            statement.setString(1, handling.getUsername());
            statement.setInt(2, handling.getStoreId());
            statement.setDouble(3, handling.getBelop());
            statement.setTimestamp(4, Timestamp.from(transactionTime.toInstant()));
            database.update(statement);
            return finnOversikt(handling.getUsername());
        } catch (SQLException e) {
            String message = String.format("Failed to register purchase for user %s", handling.getUsername());
            logError(message, e);
            throw new HandleregException(message, e);
        }
    }

    @Override
    public List<Butikk> finnButikker() {
        List<Butikk> butikker = new ArrayList<>();
        String sql = "select * from stores order by gruppe, rekkefolge";
        try (PreparedStatement statement = database.prepareStatement(sql)) {
            ResultSet results = database.query(statement);
            while(results.next()) {
                int storeId = results.getInt(1);
                String storeName = results.getString(2);
                int gruppe = results.getInt(3);
                int rekkefolge = results.getInt(4);
                Butikk butikk = new Butikk(storeId, storeName, gruppe, rekkefolge);
                butikker.add(butikk);
            }
        } catch (SQLException e) {
            String message = "Failed to retrieve a list of stores";
            logError(message, e);
            throw new HandleregException(message, e);
        }
        return butikker;
    }

    @Override
    public List<Butikk> leggTilButikk(Butikk nybutikk) {
        int gruppe = nybutikk.getGruppe() < 1 ? 2 : nybutikk.getGruppe();
        int rekkefolge = nybutikk.getRekkefolge() < 1 ? finnNesteLedigeRekkefolgeForGruppe(gruppe) : nybutikk.getRekkefolge();
        String sql = "insert into stores (store_name, gruppe, rekkefolge) values (?, ?, ?)";
        try(PreparedStatement statement = database.prepareStatement(sql)) {
            statement.setString(1, nybutikk.getButikknavn());
            statement.setInt(2, gruppe);
            statement.setInt(3, rekkefolge);
            database.update(statement);
            return finnButikker();
        } catch (SQLException e) {
            String message = String.format("Failed to insert store \"%s\" in group %d, sort order %s", nybutikk.getButikknavn(), gruppe, rekkefolge);
            logError(message, e);
            throw new HandleregException(message, e);
        }
    }

    int finnNesteLedigeRekkefolgeForGruppe(int gruppe) {
        String sql = "select rekkefolge from stores where gruppe=? order by rekkefolge desc fetch next 1 rows only";
        try(PreparedStatement statement = database.prepareStatement(sql)) {
            statement.setInt(1, gruppe);
            ResultSet results = database.query(statement);
            while(results.next()) {
                int sortorderValueOfLastStore = results.getInt(1);
                return sortorderValueOfLastStore + 10;
            }
        } catch (SQLException e) {
            String message = String.format("Failed to retrieve the next store sort order value for group %d", gruppe);
            logError(message, e);
            throw new HandleregException(message, e);
        }

        return 0;
    }

    private void logError(String message, SQLException e) {
        logservice.log(LogService.LOG_ERROR, message, e);
    }

}
