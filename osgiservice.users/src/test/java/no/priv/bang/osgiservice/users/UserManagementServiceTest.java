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
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class UserManagementServiceTest {

    @Test
    void testServiceDefinition() throws Exception {
        UserManagementService service = mock(UserManagementService.class);
        when(service.getUsers()).thenReturn(Arrays.asList(new User(42, "jdoe", "jdoe42@gmail.com", "John", "Doe")));
        List<User> users = service.getUsers();
        User user = users.get(0);
        List<User> updatedUsers = service.modifyUser(user);
        assertEquals(0, updatedUsers.size());
        UserAndPasswords userPasswords = new UserAndPasswords();
        List<User> usersAfterPasswordUpdate = service.updatePassword(userPasswords);
        assertEquals(0, usersAfterPasswordUpdate.size());
        User newUser = new User(-1, "nuser", "nuser@gmail.com", "New", "User");
        UserAndPasswords newUserWithPasswords = new UserAndPasswords(newUser, "secret", "secret", false);
        List<User> usersAfterUserCreate = service.addUser(newUserWithPasswords);
        assertEquals(0, usersAfterUserCreate.size());

        List<Role> roles = service.getRoles();
        assertEquals(0, roles.size());
        Role role = new Role();
        List<Role> rolesAfterModification = service.modifyRole(role);
        assertEquals(0, rolesAfterModification.size());
        Role newRole = new Role();
        List<Role> rolesAfterAddition = service.addRole(newRole);
        assertEquals(0, rolesAfterAddition.size());

        List<Permission> permissions = service.getPermissions();
        assertEquals(0, permissions.size());
        Permission permission = new Permission();
        List<Permission> permissionsAfterModification = service.modifyPermission(permission);
        assertEquals(0, permissionsAfterModification.size());
        Permission newPermission = new Permission();
        List<Permission> permissionsAfterAddition = service.addPermission(newPermission);
        assertEquals(0, permissionsAfterAddition.size());

        Map<String, List<Role>> userRoles = service.getUserRoles();
        assertEquals(0, userRoles.size());
        UserRoles userroles = new UserRoles(user, roles);
        Map<String, List<Role>> userRolesAfterAdd = service.addUserRoles(userroles);
        assertEquals(0, userRolesAfterAdd.size());
        Map<String, List<Role>> userRolesAfterRemove = service.removeUserRoles(userroles);
        assertEquals(0, userRolesAfterRemove.size());

        Map<String, List<Permission>> rolesPermissions = service.getRolesPermissions();
        assertEquals(0, rolesPermissions.size());
        RolePermissions rolepermissions = new RolePermissions(role, permissions);
        Map<String, List<Permission>> rolesPermissionsAfterAdd = service.addRolePermissions(rolepermissions);
        assertEquals(0, rolesPermissionsAfterAdd.size());
        Map<String, List<Permission>> rolesPermissionsAfterRemove = service.removeRolePermissions(rolepermissions);
        assertEquals(0, rolesPermissionsAfterRemove.size());
    }

}
