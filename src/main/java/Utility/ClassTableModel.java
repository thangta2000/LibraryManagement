/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import Model.BookTitle;

/**
 *
 * @author tkang_85a
 */
public class ClassTableModel {

    public DefaultTableModel setTableHocVien(List<BookTitle> listItem, String[] listColumn) {
        int columns = listColumn.length;
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        dtm.setColumnIdentifiers(listColumn);
        Object[] obj;
        int num = listItem.size();
        BookTitle bookTitle = null;
        for (int i = 0; i < num; i++) {
            bookTitle = listItem.get(i);
            obj = new Object[columns];
            obj[0] = (i + 1);
            obj[1] = bookTitle.getId();
            obj[2] = bookTitle.getName();
            obj[3] = bookTitle.getAuthor();
            obj[4] = bookTitle.getCreateDate();
            dtm.addRow(obj);
        }
        return dtm;
    }

}
