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
package no.priv.bang.authservice.definitions;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * This is a {@link Servlet} implementation that holds a reference
 * to a {@link AuthserviceLoginServlet} and will delegate all operations
 * the {@link AuthserviceLoginServlet} reference.
 */
public class DelegateLoginServlet implements Servlet {

    private AuthserviceLoginServlet logindelegate;

    void setDelegate(AuthserviceLoginServlet logindelegate) {
        this.logindelegate = logindelegate;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        logindelegate.init(config);
    }

    @Override
    public ServletConfig getServletConfig() {
        return logindelegate.getServletConfig();
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        logindelegate.service(req, res);
    }

    @Override
    public String getServletInfo() {
        return logindelegate.getServletInfo();
    }

    @Override
    public void destroy() {
        logindelegate.destroy();
    }

}
