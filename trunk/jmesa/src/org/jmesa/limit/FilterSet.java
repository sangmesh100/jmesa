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
package org.jmesa.limit;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * The FilterSet is an Collection of Filter objects. A Filter contains a bean 
 * property and the filter value. Or, in other words, it is simply the column 
 * that the user is trying to filter and the value that they entered.
 * </p>
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public class FilterSet implements Serializable {
	private Logger logger = Logger.getLogger(FilterSet.class.getName());
	private Set<Filter> filters;

	public FilterSet() {
		filters = new HashSet<Filter>();
	}

    /**
     * @return Is true if there are any columns that need to be filtered.
     */
	public boolean isFiltered() {
		return filters != null && !filters.isEmpty();
	}

	public Set<Filter> getFilters() {
		return filters;
	}

	/**
	 * For a given property, retrieve the Filter.
	 * 
	 * @param property The Filter property.
	 * @return The Filter value.
	 */
	public Filter getFilter(String property) {
		for (Iterator iter = filters.iterator(); iter.hasNext();) {
			Filter filter = (Filter) iter.next();
			if (filter.getProperty().equals(property)) {
				return filter;
			}
		}

		throw new IllegalArgumentException("There is no Filter with the property [" + property + "]");
	}
	
	/**
	 * For a given property, retrieve the Filter value.
	 * 
	 * @param property The Filter property.
	 * @return The Filter value.
	 */
	public String getFilterValue(String property) {
		return getFilter(property).getValue();
	}

    /**
     * @param filter The Filter to add to the Set.  
     */
	public void addFilter(Filter filter) {
		filters.add(filter);
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Added Filter: " + filter.toString());
		}
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);

		for (Iterator iter = filters.iterator(); iter.hasNext();) {
			Filter filter = (Filter) iter.next();
			builder.append(filter.toString());
		}

		return builder.toString();
	}
}
