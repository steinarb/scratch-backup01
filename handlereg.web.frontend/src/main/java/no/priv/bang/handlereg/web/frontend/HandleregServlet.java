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
package no.priv.bang.handlereg.web.frontend;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.service.log.LogService;

@Component(
    property= {
        HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN+"=/*",
        HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT + "=(" + HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME +"=handlereg)",
        HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME+"=handlereg"},
    service=Servlet.class,
    immediate=true
)
public class HandleregServlet extends HttpServlet {
    private static final long serialVersionUID = -3496606785818930881L;
    private LogService logservice; // NOSONAR This is not touched after DS component activate so effectively a constant

    // The paths used by the react router all needs to return the HTML wrapping the bundle.js
    private final List<String> routes = Arrays.asList(
        "/",
        "/statistikk",
        "/statistikk/sumbutikk",
        "/statistikk/handlingerbutikk",
        "/statistikk/sistehandel",
        "/statistikk/sumyear",
        "/statistikk/sumyearmonth",
        "/nybutikk",
        "/endrebutikk",
        "/login");

    @Reference
    public void setLogService(LogService logservice) {
        this.logservice = logservice;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        try {
            if (pathInfo == null) {
                // Browsers won't redirect to bundle.js if the servlet path doesn't end with a "/"
                addSlashToServletPath(request, response);
                return;
            }

            String resource = findResourceFromPathInfo(pathInfo);
            String contentType = guessContentTypeFromResourceName(resource);
            response.setContentType(contentType);
            try(ServletOutputStream responseBody = response.getOutputStream()) {
                try(InputStream resourceFromClasspath = getClass().getClassLoader().getResourceAsStream(resource)) {
                    if (resourceFromClasspath != null) {
                        copyStream(resourceFromClasspath, responseBody);
                        response.setStatus(200);
                        return;
                    }

                    String message = String.format("Resource \"%s\" not found on the classpath", resource);
                    logservice.log(LogService.LOG_ERROR, message);
                    response.sendError(404, message);
                }
            }
        } catch (IOException e) {
            logservice.log(LogService.LOG_ERROR, "Frontend servlet caught exception ", e);
            response.setStatus(500); // Report internal server error
        }
    }

    String guessContentTypeFromResourceName(String resource) {
        String contentType = URLConnection.guessContentTypeFromName(resource);
        if (contentType != null) {
            return contentType;
        }

        String extension = resource.substring(resource.lastIndexOf('.') + 1);
        if ("js".equals(extension)) {
            return "application/javascript";
        }

        if ("css".equals(extension)) {
            return "text/css";
        }

        return null;
    }

    private String findResourceFromPathInfo(String pathInfo) {
        if (routes.contains(pathInfo)) {
            return "index.html";
        }

        return pathInfo;
    }

    private void addSlashToServletPath(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(String.format("%s/", request.getRequestURL().toString()));
    }

    private void copyStream(InputStream input, ServletOutputStream output) throws IOException {
        int c;
        while((c = input.read()) != -1) {
            output.write(c);
        }
    }


}
