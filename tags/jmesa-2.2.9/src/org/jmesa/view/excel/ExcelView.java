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
package org.jmesa.view.excel;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jmesa.core.CoreContext;
import org.jmesa.view.View;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;

/**
 * @since 2.1
 * @author jeff jie
 */
public class ExcelView implements View {
    private Table table;
    private CoreContext coreContext;

    public ExcelView(Table table, CoreContext coreContext) {
        this.table = table;
        this.coreContext = coreContext;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public byte[] getBytes() {
        HSSFWorkbook render = (HSSFWorkbook) render();
        return render.getBytes();
    }

    public Object render() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        String caption = table.getCaption();
        if (StringUtils.isEmpty(caption)) {
            caption = "JMesa Export";
        }
        HSSFSheet sheet = workbook.createSheet(caption);

        Row row = table.getRow();
        row.getRowRenderer();
        List<Column> columns = table.getRow().getColumns();

        // renderer header
        HSSFRow hssfRow = sheet.createRow(0);
        int columncount = 0;
        for (Column col : columns) {
            HSSFCell cell = hssfRow.createCell((short) columncount++);
            cell.setCellValue(new HSSFRichTextString(col.getTitle()));
        }

        // renderer body
        Collection<?> items = coreContext.getPageItems();
        int rowcount = 1;
        for (Object item : items) {
            HSSFRow r = sheet.createRow(rowcount++);
            columncount = 0;
            for (Column col : columns) {
                HSSFCell cell = r.createCell((short) columncount++);
                Object render = col.getCellRenderer().render(item, rowcount);
                if (render == null) {
                    render = "";
                }
                String value = render.toString();
                if (NumberUtils.isNumber(value)) {
                    Double number = Double.valueOf(value);
                    cell.setCellValue(number);
                } else
                    cell.setCellValue(new HSSFRichTextString(value));

            }
        }
        return workbook;
    }
}
