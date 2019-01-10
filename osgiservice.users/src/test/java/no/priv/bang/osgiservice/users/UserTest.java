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
package no.priv.bang.osgiservice.users;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void testCreate() {
        int userid = 42;
        String username = "jdoe";
        String email = "jdoe31@gmail.com";
        String firstname = "John";
        String lastname = "Doe";
        User user = new User(userid, username, email, firstname, lastname);
        assertEquals(userid, user.getUserid());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(firstname, user.getFirstname());
        assertEquals(lastname, user.getLastname());
    }

    @Test
    void testCreateNoargs() {
        User user = new User();
        assertEquals(-1, user.getUserid());
        assertNull(user.getUsername());
        assertNull(user.getEmail());
        assertNull(user.getFirstname());
        assertNull(user.getLastname());
    }

    @Test
    void testHashCodeAndEquals() {
        User userWithNullUsername = new User();
        assertEquals(31, userWithNullUsername.hashCode());

        // Verify that two users with the same username are equal
        User user1 = new User(1, "jod", "jod123@gmail.com", "Jon", "Dow");
        User user2 = new User(2, "jod", "john@cymbals.com", "John", "Doe");
        assertEquals(user1.hashCode(), user2.hashCode());
        assertEquals(user1, user2);
        assertEquals(user1, user1);
        assertEquals(new User(), new User());

        // Verify that two users with different usernames are not equal
        User user3 = new User(1, "jdo", "jod123@gmail.com", "Jon", "Dow");
        assertNotEquals(user1, user3);
        assertNotEquals(user1, null);
        assertNotEquals(user1, new User());
        assertNotEquals(user1, new Role());
        assertNotEquals(new User(), user1);
    }

}
