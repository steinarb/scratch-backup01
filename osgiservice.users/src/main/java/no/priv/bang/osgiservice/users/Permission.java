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

/**
 * Bean used represent permissions assigned to {@link Role}s in
 * {@link UserManagementService} operations.
 */
public class Permission {

    private int id;
    private String permissionname;
    private String description;

    public Permission(int id, String permissionname, String description) {
        this.id = id;
        this.permissionname = permissionname;
        this.description = description;
    }

    public Permission() {
        // Jersey requires a no-args constructor
        id = -1;
    }

    public int getId() {
        return id;
    }

    public String getPermissionname() {
        return permissionname;
    }

    public String getDescription() {
        return description;
    }

}
