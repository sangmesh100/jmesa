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
package org.jmesa.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlComponentFactory;
import org.jmesa.view.html.component.HtmlColumn;

/**
 * @since 2.1
 * @author jeff jie
 */
public class ColumnTag extends SimpleTagSupport {
    private String property;
    private String title;
    private boolean sortable = true;
    private boolean filterable = true;
    private String width;

    private HtmlColumn column;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public boolean isFilterable() {
        return filterable;
    }

    public void setFilterable(boolean filterable) {
        this.filterable = filterable;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public HtmlColumn getColumn() {
        if (column != null) {
            return column;
        }

        TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);
        HtmlComponentFactory factory = tableTag.getComponentFactory();
        CellEditor editor = factory.createBasicCellEditor();
        this.column = factory.createColumn(getProperty(), editor);

        RowTag rowTag = (RowTag) findAncestorWithClass(this, RowTag.class);
        rowTag.getRow().addColumn(column);

        return column;
    }

    public void doTag() throws JspException, IOException {
        HtmlColumn column = getColumn();
        column.setTitle(getTitle());
        column.setSortable(isSortable());
        column.setFilterable(isFilterable());
        column.setWidth(getWidth());
    }
}
