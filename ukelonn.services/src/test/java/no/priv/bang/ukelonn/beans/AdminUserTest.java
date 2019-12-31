/*
 * Copyright 2018-2019 Steinar Bang
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
package no.priv.bang.ukelonn.beans;

import static org.junit.Assert.*;

import org.junit.Test;

public class AdminUserTest {

    @Test
    public void testConstructor() {
        AdminUser bean = new AdminUser("jad", 1, 1, "Jane", "Doe");
        assertEquals(1, bean.getUserId());
        assertEquals(1, bean.getAdministratorId());
        assertEquals("jad", bean.getUserName());
        assertEquals("Jane", bean.getFirstname());
        assertEquals("Doe", bean.getSurname());
    }

    @Test
    public void testEquals() {
        AdminUser user = new AdminUser("jad", 1, 1, "Jane", "Doe");
        AdminUser userDifferentAdministratorId = new AdminUser("jad", 1, 2, "Jane", "Doe");
        assertNotEquals(user, userDifferentAdministratorId);
        AdminUser userDifferentUserId = new AdminUser("jad", 2, 1, "Jane", "Doe");
        assertNotEquals(user, userDifferentUserId);
        AdminUser userDifferentUsername = new AdminUser("jadd", 1, 1, "Jane", "Doe");
        assertNotEquals(user, userDifferentUsername);
        AdminUser userDifferentFirstname = new AdminUser("jad", 1, 1, "Julie", "Doe");
        assertNotEquals(user, userDifferentFirstname);
        AdminUser userDifferentLastname = new AdminUser("jad", 1, 1, "Jane", "Deer");
        assertNotEquals(user, userDifferentLastname);
        AdminUser equalUser = new AdminUser("jad", 1, 1, "Jane", "Doe");
        assertEquals(user, equalUser);
        assertEquals(user, user);
        AdminUser userWithNullStrings = new AdminUser(null, 1, 1, null, null);
        assertNotEquals(userWithNullStrings, user);
        assertNotEquals(user, null);
        assertNotEquals(user, "");
    }

    @Test
    public void testToString() {
        AdminUser user = new AdminUser("jad", 1, 1, "Jane", "Doe");
        assertEquals("AdminUser [userName=jad, userId=1, administratorId=1, firstname=Jane, surname=Doe]", user.toString());
        AdminUser userWithNullStrings = new AdminUser(null, 1, 1, null, null);
        assertEquals("AdminUser [userName=null, userId=1, administratorId=1, firstname=null, surname=null]", userWithNullStrings.toString());
    }

}
