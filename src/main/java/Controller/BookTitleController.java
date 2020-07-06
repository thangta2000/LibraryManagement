/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author tkang_85a
 */
import Model.BookTitle;
//import com.teamvietdev.qlhv.service.HocVienService;
//import com.teamvietdev.qlhv.service.HocVienServiceImpl;
import Utility.ClassTableModel;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author TVD
 */
public class BookTitleController {

    private JPanel jpnView;
    private JButton btnAdd;
    private JTextField jtfSearch;

    private ClassTableModel classTableModel = null;

    private final String[] COLUMNS = {"STT", "Mã đầu sách", "Tên đầu sách", "Tác giả", "Ngày tạo"};

//    private HocVienService hocVienService = null;

    private TableRowSorter<TableModel> rowSorter = null;

    public BookTitleController(JPanel jpnView, JButton btnAdd, JTextField jtfSearch) {
        this.jpnView = jpnView;
        this.btnAdd = btnAdd;
        this.jtfSearch = jtfSearch;

        this.classTableModel = new ClassTableModel();

//        this.hocVienService = new HocVienServiceImpl();
    }

    public BookTitleController(JPanel jpnView, Button btnAdd, TextField bookTitleName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setDataToTable() {
        List<BookTitle> listItem = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            BookTitle bookTitle = new BookTitle();
            bookTitle.setId(i);
            bookTitle.setName(i == 0 ? "Ngữ văn" : "Vật lý");
            bookTitle.setAuthor(i == 0 ? "Alexander" : "Christopher");
            bookTitle.setCreateDate(new Date());
            listItem.add(bookTitle);
        }
        for(int i = 0; i < 2; i++){
            BookTitle bookTitle = new BookTitle();
            bookTitle.setId(i + 2);
            bookTitle.setName(i == 0 ? "Toán học" : "Đạo đức");
            bookTitle.setAuthor(i == 0 ? "Alice" : "James");
            bookTitle.setCreateDate(new Date());
            listItem.add(bookTitle);
        }
        DefaultTableModel model = classTableModel.setTableHocVien(listItem, COLUMNS);
        JTable table = new JTable(model);

        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);

        jtfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        //  design
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(100, 50));
        table.setRowHeight(50);
        table.validate();
        table.repaint();
        
        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(table);
        scroll.setPreferredSize(new Dimension(1350, 400));
        jpnView.removeAll();
        jpnView.setLayout(new CardLayout());
        jpnView.add(scroll);
        jpnView.validate();
        jpnView.repaint();
    }

}
