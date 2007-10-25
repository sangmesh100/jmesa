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
package org.jmesa.worksheet;

import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.jmesa.core.message.Messages;
import org.jmesa.core.message.ResourceBundleMessages;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetColumnTest {

    @Test
    public void hasError() {
        WorksheetColumn column = new WorksheetColumnImpl("name.firstName", null, null);
        column.setError("Cannot be null value.");
        assertTrue("The column does not have an error.", column.hasError());
    }

    @Test
    public void hasErrorByKey() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        WebContext webContext = new HttpServletRequestWebContext(request);
        webContext.setLocale(Locale.US);

        Messages messages = new ResourceBundleMessages("org.jmesa.worksheet.testResourceBundle", webContext);
        
        WorksheetColumn column = new WorksheetColumnImpl("name.firstName", null, messages);
        column.setErrorKey("column.nullvalue");
        assertTrue("The column does not have an error.", column.hasError());
    }
}
