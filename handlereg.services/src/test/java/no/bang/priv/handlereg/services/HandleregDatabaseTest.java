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
package no.bang.priv.handlereg.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;

class HandleregDatabaseTest {

    @Test
    void testAllOfTheMethods() throws Exception {
        HandleregDatabase database = mock(HandleregDatabase.class);
        ResultSet dummyResults = mock(ResultSet.class);
        when(database.query(any())).thenReturn(dummyResults);

        PreparedStatement statement = database.prepareStatement("some sql");
        ResultSet results = database.query(statement);
        assertFalse(results.next());

        int status = database.update(statement);
        assertEquals(0, status);

        boolean successfullyForced = database.forceReleaseLiquibaseLock();
        assertFalse(successfullyForced);

        DataSource datasource = database.getDatasource();
        assertNull(datasource);
    }

}
