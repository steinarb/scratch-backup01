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
 * Bean used represent user roles in {@link UserManagementService} operations.
 */
public class Role {

    private int id;
    private String rolename;
    private String description;

    public Role(int id, String rolename, String description) {
        this.id = id;
        this.rolename = rolename;
        this.description = description;
    }

    public Role() {
        // Jersey requires a no-args constructor
        id = -1;
    }

    public int getId() {
        return id;
    }

    public String getRolename() {
        return rolename;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rolename == null) ? 0 : rolename.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Role other = (Role) obj;
        if (rolename == null) {
            if (other.rolename != null)
                return false;
        } else if (!rolename.equals(other.rolename))
            return false;
        return true;
    }

}
