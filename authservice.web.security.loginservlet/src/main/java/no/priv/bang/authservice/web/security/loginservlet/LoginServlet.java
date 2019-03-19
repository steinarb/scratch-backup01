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
package no.priv.bang.authservice.web.security.loginservlet;


import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.WebConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import no.priv.bang.authservice.definitions.AuthserviceLoginServlet;
import no.priv.bang.osgiservice.users.UserManagementService;

@Component(service=AuthserviceLoginServlet.class, immediate=true)
public class LoginServlet extends ServletContainer implements AuthserviceLoginServlet {
    private static final long serialVersionUID = 7665105986497262446L;
    private LogService logservice;  // NOSONAR Value set by DS injection
    private UserManagementService useradmin;  // NOSONAR Value set by DS injection

    @Reference
    public void setLogservice(LogService logService) {
        this.logservice = logService;
    }

    @Reference
    public void setUserManagementService(UserManagementService useradmin) {
        this.useradmin = useradmin;
    }

    @Activate
    public void activate() {
        // This method is called after all injections have been satisfied
    }

    @Override
    protected void init(WebConfig webConfig) throws ServletException {
        super.init(webConfig);
        ResourceConfig copyOfExistingConfig = new ResourceConfig(getConfiguration());
        copyOfExistingConfig.register(new AbstractBinder() {
                @Override
                protected void configure() {
                    bind(logservice).to(LogService.class);
                    bind(useradmin).to(UserManagementService.class);
                }
            });
        reload(copyOfExistingConfig);
        Map<String, Object> configProperties = getConfiguration().getProperties();
        Set<Class<?>> classes = getConfiguration().getClasses();
        logservice.log(LogService.LOG_INFO, String.format("Ukelonn Jersey servlet initialized with WebConfig, with resources: %s  and config params: %s", classes.toString(), configProperties.toString()));
    }

}
