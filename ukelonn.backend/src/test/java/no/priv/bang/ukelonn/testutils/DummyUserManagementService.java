package no.priv.bang.ukelonn.testutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.priv.bang.osgiservice.users.Permission;
import no.priv.bang.osgiservice.users.Role;
import no.priv.bang.osgiservice.users.RolePermissions;
import no.priv.bang.osgiservice.users.User;
import no.priv.bang.osgiservice.users.UserAndPasswords;
import no.priv.bang.osgiservice.users.UserManagementService;
import no.priv.bang.osgiservice.users.UserRoles;

public class DummyUserManagementService implements UserManagementService {
    private User emptyUser = new User();
    List<User> users;
    private ArrayList<Role> roles;
    private HashMap<String, List<Role>> userroles;

    public DummyUserManagementService() {
        User admin = new User(1, "admin", "admin@gmail.com", "Admin", "Istrator");
        User jad = new User(2, "jad", "jane21@gmail.com", "Jane", "Doe");
        this.users = new ArrayList<User>();
        users.add(admin);
        users.add(jad);
        Role user = new Role(1, "user", "Default authservice user");
        roles = new ArrayList<Role>(Arrays.asList(user));
        userroles = new HashMap<String, List<Role>>();
        userroles.put(admin.getUsername(), new ArrayList<Role>(Arrays.asList(user)));
        userroles.put(jad.getUsername(), new ArrayList<Role>(Arrays.asList(user)));
    }

    @Override
    public User getUser(String username) {
        return users.stream().filter(user -> username.equals(user.getUsername())).findFirst().orElse(emptyUser );
    }

    @Override
    public List<Role> getRolesForUser(String username) {
        return userroles.getOrDefault(username, Collections.emptyList());
    }

    @Override
    public List<Permission> getPermissionsForUser(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> getUsers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> modifyUser(User user) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> updatePassword(UserAndPasswords userAndPasswords) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> addUser(UserAndPasswords newUserWithPasswords) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public List<Role> modifyRole(Role role) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Role> addRole(Role newRole) {
        roles.add(newRole);
        return roles;
    }

    @Override
    public List<Permission> getPermissions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Permission> modifyPermission(Permission permission) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Permission> addPermission(Permission newPermission) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, List<Role>> getUserRoles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, List<Role>> addUserRoles(UserRoles userroles) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, List<Role>> removeUserRoles(UserRoles userroles) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, List<Permission>> getRolesPermissions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, List<Permission>> addRolePermissions(RolePermissions rolepermissions) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, List<Permission>> removeRolePermissions(RolePermissions rolepermissions) {
        // TODO Auto-generated method stub
        return null;
    }

}
