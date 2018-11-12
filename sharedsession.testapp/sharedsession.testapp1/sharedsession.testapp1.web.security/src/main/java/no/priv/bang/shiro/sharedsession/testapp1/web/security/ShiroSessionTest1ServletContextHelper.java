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
package no.priv.bang.shiro.sharedsession.testapp1.web.security;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

@Component(
    property= {
        HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME+"=shirotest1",
        HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH+"=/shirotest1"},
    service=ServletContextHelper.class,
    immediate=true
)
public class ShiroSessionTest1ServletContextHelper extends ServletContextHelper { }
