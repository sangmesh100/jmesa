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
package org.jmesa.view.html;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jmesa.core.CoreContext;
import org.jmesa.limit.Filter;
import org.jmesa.limit.Limit;
import org.jmesa.limit.RowSelect;
import org.jmesa.limit.Sort;
import org.jmesa.view.component.Column;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.Toolbar;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlSnippetsImpl implements HtmlSnippets {
    private HtmlTable table;
    private Toolbar toolbar;
    private CoreContext coreContext;

    public HtmlSnippetsImpl(HtmlTable table, Toolbar toolbar, CoreContext coreContext) {
        this.table = table;
        this.toolbar = toolbar;
        this.coreContext = coreContext;
    }

    protected HtmlTable getHtmlTable() {
        return table;
    }

    protected CoreContext getCoreContext() {
        return coreContext;
    }

    public String themeStart() {
        HtmlBuilder html = new HtmlBuilder();
        html.div().styleClass(table.getTheme()).close();
        return html.toString();
    }

    public String themeEnd() {
        HtmlBuilder html = new HtmlBuilder();
        html.newline();
        html.divEnd();
        return html.toString();
    }

    public String tableStart() {
        HtmlBuilder html = new HtmlBuilder();
        html.append(table.getTableRenderer().render());
        return html.toString();
    }

    public String tableEnd() {
        HtmlBuilder html = new HtmlBuilder();
        html.tableEnd(0);
        return html.toString();
    }

    public String theadStart() {
        HtmlBuilder html = new HtmlBuilder();
        html.thead(1).close();
        return html.toString();
    }

    public String theadEnd() {
        HtmlBuilder html = new HtmlBuilder();
        html.theadEnd(1);
        return html.toString();
    }

    public String tbodyStart() {
        HtmlBuilder html = new HtmlBuilder();
        String tbodyClass = coreContext.getPreference(HtmlConstants.TBODY_CLASS);
        html.tbody(1).styleClass(tbodyClass).close();
        return html.toString();
    }

    public String tbodyEnd() {
        HtmlBuilder html = new HtmlBuilder();
        html.tbodyEnd(1);
        return html.toString();
    }

    public String filter() {
        HtmlBuilder html = new HtmlBuilder();
        String filterClass = coreContext.getPreference(HtmlConstants.FILTER_CLASS);
        html.tr(1).styleClass(filterClass).close();

        HtmlRow row = table.getRow();
        List<Column> columns = row.getColumns();

        for (Iterator<Column> iter = columns.iterator(); iter.hasNext();) {
            HtmlColumn column = (HtmlColumn) iter.next();
            if (column.isFilterable()) {
                html.append(column.getFilterRenderer().render());
            } else {
                html.td(2).close().tdEnd();
            }
        }

        html.trEnd(1);
        return html.toString();
    }

    public String header() {
        HtmlBuilder html = new HtmlBuilder();
        String headerClass = coreContext.getPreference(HtmlConstants.HEADER_CLASS);
        html.tr(1).styleClass(headerClass).close();

        HtmlRow row = table.getRow();
        List<Column> columns = row.getColumns();

        for (Iterator<Column> iter = columns.iterator(); iter.hasNext();) {
            HtmlColumn column = (HtmlColumn) iter.next();
            html.append(column.getHeaderRenderer().render());
        }

        html.trEnd(1);
        return html.toString();
    }

    public String footer() {
        return null;
    }

    public String body() {
        HtmlBuilder html = new HtmlBuilder();
        
        int rowcount = 0;
        
        boolean rowcountIncludePagination = new Boolean(coreContext.getPreference(HtmlConstants.ROWCOUNT_INCLUDE_PAGINATION));
        if (rowcountIncludePagination) {
            int page = coreContext.getLimit().getRowSelect().getPage();
            int maxRows = coreContext.getLimit().getRowSelect().getMaxRows();
            rowcount = (page - 1) * maxRows;
        }
        
        Collection<?> items = coreContext.getPageItems();
        for (Object item : items) {
            rowcount++;

            HtmlRow row = table.getRow();
            List<Column> columns = row.getColumns();

            html.append(row.getRowRenderer().render(item, rowcount));

            for (Iterator<Column> iter = columns.iterator(); iter.hasNext();) {
                HtmlColumn column = (HtmlColumn) iter.next();
                html.append(column.getCellRenderer().render(item, rowcount));
            }

            html.trEnd(1);
        }
        return html.toString();
    }

    public String statusBarText() {
        Limit limit = coreContext.getLimit();
        RowSelect rowSelect = limit.getRowSelect();

        if (rowSelect.getTotalRows() == 0) {
            return coreContext.getMessage(HtmlConstants.STATUSBAR_NO_RESULTS_FOUND);
        }

        Integer total = new Integer(rowSelect.getTotalRows());
        Integer from = new Integer(rowSelect.getRowStart() + 1);
        Integer to = new Integer(rowSelect.getRowEnd());
        Object[] messageArguments = { total, from, to };
        return coreContext.getMessage(HtmlConstants.STATUSBAR_RESULTS_FOUND, messageArguments);
    }

    public String toolbar() {
        HtmlBuilder html = new HtmlBuilder();

        HtmlRow row = table.getRow();
        List<Column> columns = row.getColumns();

        String toolbarClass = coreContext.getPreference(HtmlConstants.TOOLBAR_CLASS);
        html.tr(1).styleClass(toolbarClass).close();
        html.td(2).colspan(String.valueOf(columns.size())).close();

        html.append(toolbar.render());

        html.tdEnd();
        html.trEnd(1);

        return html.toString();
    }

    public String statusBar() {
        HtmlBuilder html = new HtmlBuilder();

        HtmlRow row = table.getRow();
        List<Column> columns = row.getColumns();

        html.tbody(1).close();

        String toolbarClass = coreContext.getPreference(HtmlConstants.STATUS_BAR_CLASS);
        html.tr(1).styleClass(toolbarClass).close();
        html.td(2).align("left").colspan(String.valueOf(columns.size())).close();

        html.append(statusBarText());

        html.tdEnd();
        html.trEnd(1);

        html.tbodyEnd(1);

        return html.toString();
    }

    /*
     * TODO: Move this into the LimitImpl class as a toJavaScript() method. 
     */
    public String initJavascriptLimit() {
        HtmlBuilder html = new HtmlBuilder();

        html.newline();
        html.script().type("text/javascript").close();

        html.newline();

        Limit limit = coreContext.getLimit();

        html.append("addLimitToManager('" + limit.getId() + "')").semicolon().newline();

        html.append("setPageToLimit('" + limit.getId() + "','" + limit.getRowSelect().getPage() + "')").semicolon().newline();

        html.append("setMaxRowsToLimit('" + limit.getId() + "','" + limit.getRowSelect().getMaxRows() + "')").semicolon().newline();

        for (Sort sort : limit.getSortSet().getSorts()) {
            html.append(
                    "addSortToLimit('" + limit.getId() + "','" + sort.getPosition() + "','" + sort.getProperty() + "','" + sort.getOrder().toParam()
                            + "')").semicolon().newline();
        }

        for (Filter filter : limit.getFilterSet().getFilters()) {
            html.append("addFilterToLimit('" + limit.getId() + "','" + filter.getProperty() + "','" + filter.getValue() + "')").semicolon().newline();
        }

        html.scriptEnd();

        return html.toString();
    }
}