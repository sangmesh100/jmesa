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
package org.jmesa.facade;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jmesa.core.CoreContext;
import org.jmesa.limit.Limit;
import org.jmesa.view.View;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.toolbar.Toolbar;

/**
 * Helper to contain all the exceptions returned from the TableFacadeImpl.
 * 
 * @since 2.2
 * @author Jeff Johnston
 */
final class TableFacadeExceptions {

    private TableFacadeExceptions() {
    }

    static void validateCoreContext(CoreContext coreContext, String object) {
        if (coreContext != null) {
            throw new IllegalStateException(
                "It is too late to set the " + object + ". You need to set the " + object + " before using the CoreContext.");
        }
    }

    static void validateTable(Table table, String object) {
        if (table != null) {
            throw new IllegalStateException(
                "It is too late to set the " + object + ". You need to set the " + object + " before using the Table.");
        }
    }

    static void validateView(View view, String object) {
        if (view != null) {
            throw new IllegalStateException(
                "It is too late to set the " + object + ". You need to set the " + object + " before using the View.");
        }
    }

    static void validateToolbar(Toolbar toolbar, String object) {
        if (toolbar != null) {
            throw new IllegalStateException(
                "It is too late to set the " + object + ". You need to set the " + object + " before using the Toolbar.");
        }
    }

    static void validateLimit(Limit limit, String object) {
        if (limit != null) {
            throw new IllegalStateException(
                "It is too late to set the " + object + ". You need to set the " + object + " before using the Limit.");
        }
    }

    static void validateColumnProperties(String[] columnProperties) {
        if (columnProperties == null || columnProperties.length == 0) {
            throw new IllegalStateException(
                "The column properties are null. You need to set the columnProperties, or build the Table with the factory.");
        }
    }

    static void validateRowSelect(Limit limit) {
        if (limit.getRowSelect() == null) {
            throw new IllegalStateException(
                "The RowSelect is null. You need to set the totalRows on the facade.");
        }
    }

    static void validateItems(Collection items) {
        if (items == null) {
            throw new IllegalStateException("The items are null. You need to set the items on the facade.");
        }
    }
}
