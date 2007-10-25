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
package org.jmesa.view.html.event;

/**
 * @since 2.2
 * @author Jeff Johnston
 */
public class MouseRowEvent implements RowEvent {
    private boolean highlighter;
    private String styleClass;

    public MouseRowEvent(boolean highlighter) {
        this.highlighter = highlighter;
    }

    protected boolean isHighlighter() {
        return highlighter;
    }

    protected String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String execute(Object item, int rowcount) {
        if (isHighlighter()) {
            return "this.className='" + getStyleClass() + "'";
        }

        return "";
    }
}
