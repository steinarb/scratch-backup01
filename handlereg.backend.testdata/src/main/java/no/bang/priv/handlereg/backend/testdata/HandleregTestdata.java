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
package no.bang.priv.handlereg.backend.testdata;

import java.util.Arrays;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import no.bang.priv.handlereg.services.HandleregService;
import no.priv.bang.osgiservice.users.Role;
import no.priv.bang.osgiservice.users.User;
import no.priv.bang.osgiservice.users.UserManagementService;
import no.priv.bang.osgiservice.users.UserRoles;

@Component(immediate=true)
public class HandleregTestdata {

    private UserManagementService useradmin;

    @Reference
    public void setHandleregService(HandleregService handlereg) {
        // Brukes bare til å bestemme rekkefølge på kjøring
        // Når denne blir kalt vet vi at authservice har
        // rollen handleregbruker lagt til
    }

    @Reference
    public void setUseradmin(UserManagementService useradmin) {
        this.useradmin = useradmin;
    }

    @Activate
    public void activate() {
        addRolesForTestusers();
    }

    void addRolesForTestusers() {
        Role handleregbruker = useradmin.getRoles().stream().filter(r -> "handleregbruker".equals(r.getRolename())).findFirst().get(); // NOSONAR testkode
        User jd = useradmin.getUser("jd");
        useradmin.addUserRoles(new UserRoles(jd, Arrays.asList(handleregbruker)));
        User jad = useradmin.getUser("jad");
        useradmin.addUserRoles(new UserRoles(jad, Arrays.asList(handleregbruker)));
    }

}
