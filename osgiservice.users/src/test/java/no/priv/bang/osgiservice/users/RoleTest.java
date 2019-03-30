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

class RoleTest {

    @Test
    void testCreate() {
        int id = 42;
        String rolename = "admin";
        String description = "This is an administrator";
        Role bean = new Role(id, rolename, description);
        assertEquals(id, bean.getId());
        assertEquals(rolename, bean.getRolename());
        assertEquals(description, bean.getDescription());
    }

    @Test
    void testNoArgsConstructor() {
        int id = -1;
        Role bean = new Role();
        assertEquals(id, bean.getId());
        assertNull(bean.getRolename());
        assertNull(bean.getDescription());
    }

    @Test
    void testHashCodeAndEquals() {
        Role roleWithNullRolename = new Role();
        assertEquals(31, roleWithNullRolename.hashCode());

        // Verify that two role objects with the same rolename returns the same hashcode
        Role adminRole1 = new Role(1, "admin", "dummy");
        Role adminRole2 = new Role(2, "admin", "dummy");
        assertEquals(adminRole1.hashCode(), adminRole2.hashCode());
        assertEquals(adminRole1, adminRole2);
        assertEquals(adminRole1, adminRole1);
        assertEquals(new Role(), new Role());

        // Verify that a different role name has a different hashcode
        Role userRole = new Role(3, "user", "dummy");
        assertNotEquals(adminRole1.hashCode(), userRole.hashCode());
        assertNotEquals(adminRole1, userRole);
        assertNotEquals(adminRole1, null); // NOSONAR Argument  order is to test all corners of Role.equals()
        assertNotEquals(adminRole1, new User());
        assertNotEquals(new Role(), adminRole1);
    }

}
