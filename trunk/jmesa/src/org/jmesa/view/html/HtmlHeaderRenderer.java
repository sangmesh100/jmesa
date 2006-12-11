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

import org.jmesa.core.CoreContext;
import org.jmesa.view.AbstractHeaderRenderer;
import org.jmesa.view.Column;

/**
 * @since 2.0
 * @author Jeff Johnston
 */
public class HtmlHeaderRenderer extends AbstractHeaderRenderer {
	private String style;
	private String styleClass;

	public HtmlHeaderRenderer(CoreContext coreContext) {
		setCoreContext(coreContext);
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public Object render(Column column) {
		HtmlBuilder html = new HtmlBuilder();
		html.td(2);
		html.style(style);
		html.styleClass(styleClass);
		html.close();
		html.append(getTitle(column.getTitle(), column.getProperty()));
		html.tdEnd();
		
		return html;
	}
}
