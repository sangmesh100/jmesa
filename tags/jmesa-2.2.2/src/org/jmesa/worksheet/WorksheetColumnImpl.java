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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jmesa.core.message.Messages;

/**
 * @since 2.3
 * @author Jeff Johnston
 */
public class WorksheetColumnImpl implements WorksheetColumn {
    private String property;
    private Messages messages;
    private String error;
    private Object originalValue;
    private Object changedValue;

    public WorksheetColumnImpl(String property, Object originalValue, Messages messages) {
        this.property = property;
        this.originalValue = originalValue;
        this.messages = messages;
    }

    public String getProperty() {
        return property;
    }

    public Object getOriginalValue() {
        return originalValue;
    }

    public Object getChangedValue() {
        return changedValue;
    }

    public void setChangedValue(Object changedValue) {
        this.changedValue = changedValue;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setErrorKey(String key) {
        setError(messages.getMessage(key));
    }

    public String getError() {
        return error;
    }

    public boolean hasError() {
        return StringUtils.isNotBlank(error);
    }

    /**
     * Equality is based on the property. In other words no two Column Objects can have the same
     * property.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof WorksheetColumn))
            return false;

        WorksheetColumn that = (WorksheetColumn) o;

        return that.getProperty().equals(this.getProperty());
    }

    @Override
    public int hashCode() {
        int result = 17;
        int property = this.getProperty() == null ? 0 : this.getProperty().hashCode();
        result = result * 37 + property;
        return result;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("property", property);
        return builder.toString();
    }
}
