/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Model.BookTitles;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author admin
 */
public class CustomTableModel extends AbstractTableModel
{

    private String[] columnNames = {"No.", "Title", "Pages", "Publish Year"};
    private ArrayList<BookTitles> list = new ArrayList<>();

    public CustomTableModel(ArrayList<BookTitles> list)
    {
        this.list = list;
        //this.columnNames = columnNames;
    }

    @Override
    public int getRowCount()
    {
        return list.size();
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col)
    {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {

        Object temp = null;

        switch (columnIndex)
        {
            case 0:
                temp = rowIndex + 1;
                break;
            case 1:
                temp = list.get(rowIndex).getTitle();
                break;
            case 2:
                temp = list.get(rowIndex).getPages();
                break;
            case 3:
                temp = list.get(rowIndex).getPublishYear();
                break;
            default:
                break;
        }
        return temp;
    }

}
