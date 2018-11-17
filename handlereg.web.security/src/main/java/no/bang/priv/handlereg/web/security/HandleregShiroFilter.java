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
package no.bang.priv.handlereg.web.security;

import javax.servlet.Filter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.config.Ini;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.apache.shiro.web.config.WebIniSecurityManagerFactory;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

import no.bang.priv.handlereg.services.HandleregDatabase;

/**
 * This is an OSGi DS component that provides a {@link Filter} service.  This filter service will
 * be put in front of the servlets in the "/handlereg" webcontext, and
 * will handle authentication and verify authorization to the servlet paths.
 *
 * @author Steinar Bang
 *
 */
@Component(
    property= {
        HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN+"=/*",
        HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT + "=(" + HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME +"=ukelonn)",
        "servletNames=ukelonn"},
    service=Filter.class,
    immediate=true)
public class HandleregShiroFilter extends AbstractShiroFilter { // NOSONAR

    private static final Ini INI_FILE = new Ini();
    static {
        // Can't use the Ini.fromResourcePath(String) method because it can't find "shiro.ini" on the classpath in an OSGi context
        INI_FILE.load(HandleregShiroFilter.class.getClassLoader().getResourceAsStream("shiro.ini"));
    }
    private HandleregDatabase database;

    @Activate
    public void activate() {
        WebIniSecurityManagerFactory securityManagerFactory = new WebIniSecurityManagerFactory(INI_FILE);
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) securityManagerFactory.createInstance();
        setSecurityManager(securityManager);

        JdbcRealm realm = createRealm();
        securityManager.setRealm(realm);

        IniFilterChainResolverFactory filterChainResolverFactory = new IniFilterChainResolverFactory(INI_FILE, securityManagerFactory.getBeans());
        FilterChainResolver resolver = filterChainResolverFactory.createInstance();
        setFilterChainResolver(resolver);
    }

    @Reference
    public void setDatabase(HandleregDatabase database) {
        this.database = database;
    }

    public HandleregDatabase getDatabase() {
        return database;
    }

    private JdbcRealm createRealm() {
        JdbcRealm realm = new JdbcRealm();
        realm.setDataSource(database.getDatasource());
        realm.setAuthenticationQuery("select password, salt from password where username=?");
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("SHA-256");
        credentialsMatcher.setStoredCredentialsHexEncoded(false); // base64 encoding, not hex
        credentialsMatcher.setHashIterations(1024);
        realm.setCredentialsMatcher(credentialsMatcher);
        return realm;
    }

}
