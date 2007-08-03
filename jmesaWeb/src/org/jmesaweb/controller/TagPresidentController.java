/*
 * Copyright 2004 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jmesaweb.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.limit.Limit;
import org.jmesa.view.TableFacade;
import org.jmesa.view.TableFacadeImpl;
import org.jmesaweb.service.PresidentService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @author Jeff Johnston
 */
public class TagPresidentController extends AbstractController {
    private static String CSV = "csv";
    private static String EXCEL = "excel";

    private PresidentService presidentService;
    private String successView;
    private String id;

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView(successView);

        Collection<Object> items = presidentService.getPresidents();

        TableFacade facade = new TableFacadeImpl(id, request, items, "name.firstName", "name.lastName", "term", "career");
        facade.setExportTypes(response, CSV, EXCEL);

        Limit limit = facade.getLimit();
        if (limit.isExportable()) {
            facade.getTable().setCaption("Presidents");
            facade.getTable().getRow().getColumn("name.firstName").setTitle("First Name");
            facade.getTable().getRow().getColumn("name.lastName").setTitle("Last Name");
            facade.render();
            return null;
        }

        mv.addObject("presidents", items);

        return mv;
    }

    public void setPresidentService(PresidentService presidentService) {
        this.presidentService = presidentService;
    }

    public void setSuccessView(String successView) {
        this.successView = successView;
    }

    public void setId(String id) {
        this.id = id;
    }
}
