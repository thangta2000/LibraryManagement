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

// Make CustomTableModel abstract with undefined T class => donot have to recreate CustomTableModel for different T
public abstract class CustomTableModel<T> extends AbstractTableModel
{
    private String[] columnNames;
    private List<T> list;    

    public CustomTableModel(String[] columnNames)
    {
        this.list = new ArrayList<>();
        this.columnNames = columnNames;
    }    
    
    
    public CustomTableModel(List<T> list, String[] columnNames)
    {
        this.list = list;
        this.columnNames = columnNames;
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
    
    public T getDataObject(int rowIndex) {
        return this.list.get(rowIndex);
    }
    
    public void insertRow(T data) 
    {
        int row = getColumnCount();
        list.add(data);
        fireTableRowsInserted(row, row);
    }
    
    public List<T> getListDataObject()
    {
        return this.list;
    }
    
    public void changeData(List<T> newList)
    {
        this.list = newList;
        fireTableDataChanged();
    }
}
