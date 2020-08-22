package View;

import Model.Categories;
import Model.Countries;
import Model.Publishers;
import Model.BookTitles;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Controller.BookTitlesJpaController;
import Utility.Validation.GroupVerifier;
import Utility.Validation.NumberVerifier;
import Utility.Validation.RequiredVerifier;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Admin
 */
public class BookTitleEditPanel extends javax.swing.JFrame
{

    private ArrayList<Categories> categories;
    private ArrayList<Countries> countries;
    private ArrayList<Publishers> publishers;
    private BookTitlePanel parent;
    private int id;

    JTextField[] jTextFields;

    public BookTitleEditPanel()
    {
        initComponents();
        BookTitles bookTitles = BookTitlesJpaController.findBookTitles(2);
        System.out.println(bookTitles);
    }

    BookTitleEditPanel(ArrayList<Categories> categories, ArrayList<Countries> countries, ArrayList<Publishers> publishers, BookTitlePanel main, int bookTitleId)
    {
        initComponents();

        customizePanel();

        this.categories = categories;
        this.countries = countries;
        this.publishers = publishers;
        this.parent = main;
        this.id = bookTitleId;
        categories.forEach(item -> categoryId.addItem(item.getName()));
        countries.forEach(item -> countryId.addItem(item.getName()));
        publishers.forEach(item -> publisherId.addItem(item.getName()));

        BookTitles bookTitles = BookTitlesJpaController.findBookTitles(id);
        categoryId.setSelectedIndex(categories.get(bookTitles.getCategoryId().getId() - 1).getId() - 1);
        countryId.setSelectedIndex(countries.get(bookTitles.getCountryId().getId() - 1).getId() - 1);
        ibsn.setText(bookTitles.getIbsn());
        pages.setValue(bookTitles.getPages());
        publishYear.setValue(bookTitles.getPublishYear());
        publisherId.setSelectedIndex(publishers.get(bookTitles.getPublisherId().getId() - 1).getId() - 1);
        title.setText(bookTitles.getTitle());
        width.setText(bookTitles.getWidth().toString());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        title = new javax.swing.JTextField();
        categoryId = new javax.swing.JComboBox<>();
        btnSubmit = new javax.swing.JButton();
        countryId = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        publisherId = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        ibsn = new javax.swing.JTextField();
        pages = new javax.swing.JSpinner();
        publishYear = new javax.swing.JSpinner();
        width = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("SỬA THÔNG TIN");

        jLabel2.setText("Tiêu đề sách");

        jLabel3.setText("Thể loại");

        jLabel4.setText("Năm xuất bản");

        jLabel5.setText("Nhà xuất bản");

        jLabel6.setText("Số Trang");

        jLabel7.setText("Khổ sách");

        btnSubmit.setText("Thêm");
        btnSubmit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnSubmitActionPerformed(evt);
            }
        });

        jLabel8.setText("Quốc gia");

        jLabel9.setText("IBSN");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8)
                            .addComponent(jLabel5))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(width))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9))
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ibsn)
                            .addComponent(title)
                            .addComponent(categoryId, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(countryId, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(publisherId, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(pages)
                            .addComponent(publishYear))))
                .addGap(76, 76, 76))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(categoryId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(countryId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(publisherId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(publishYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(pages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(width, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ibsn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(31, 31, 31)
                .addComponent(btnSubmit)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed

        boolean validateForm = true;

        for (JTextField jTextField : jTextFields)
        {
            if (jTextField.getBackground() != Color.WHITE)
            {
                validateForm = false;
                break;
            }
        }

        if (validateForm)
        {
            BookTitles booktitles = new BookTitles();
            booktitles.setId(id);
            booktitles.setCategoryId(categories.get(categoryId.getSelectedIndex()));
            booktitles.setCountryId(countries.get(countryId.getSelectedIndex()));
            booktitles.setIbsn(ibsn.getText());
            booktitles.setPages((Integer) pages.getValue());
            booktitles.setPublishYear((Integer) publishYear.getValue());
            booktitles.setPublisherId(publishers.get(publisherId.getSelectedIndex()));
            booktitles.setTitle(title.getText());
            booktitles.setWidth(Double.valueOf(width.getText()));
            try
            {
                BookTitlesJpaController.edit(booktitles);
                this.setVisible(false);
                this.parent.currentPage = 1;
                this.parent.populateTable(this.parent.currentPage);

                JOptionPane.showMessageDialog(this, "Sửa dữ liệu thành công");
            }
            catch (Exception ex)
            {
                Logger.getLogger(BookTitleEditPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Vui lòng điền thông tin còn thiếu");
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Windows".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(BookTitleEditPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(BookTitleEditPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(BookTitleEditPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(BookTitleEditPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new BookTitleEditPanel().setVisible(true);
            }

        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSubmit;
    private javax.swing.JComboBox<String> categoryId;
    private javax.swing.JComboBox<String> countryId;
    private javax.swing.JTextField ibsn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSpinner pages;
    private javax.swing.JSpinner publishYear;
    private javax.swing.JComboBox<String> publisherId;
    private javax.swing.JTextField title;
    private javax.swing.JTextField width;
    // End of variables declaration//GEN-END:variables

    private void customizePanel()
    {
        title.setInputVerifier(new RequiredVerifier());
        width.setInputVerifier(new GroupVerifier(new RequiredVerifier(), new NumberVerifier()));
        ibsn.setInputVerifier(new GroupVerifier(new RequiredVerifier(), new NumberVerifier()));

        jTextFields = new JTextField[]
        {
            title, width, ibsn
        };

        for (JTextField jTextField : jTextFields)
        {
            jTextField.getDocument().addDocumentListener(new DocumentListener()
            {
                @Override
                public void insertUpdate(DocumentEvent e)
                {
                    displayValidationResult(jTextField);
                }

                @Override
                public void removeUpdate(DocumentEvent e)
                {
                    displayValidationResult(jTextField);
                }

                @Override
                public void changedUpdate(DocumentEvent e)
                {
                }

            });

            jTextField.addFocusListener(new FocusAdapter()
            {
                @Override
                public void focusLost(FocusEvent e)
                {
                    displayValidationResult(jTextField);
                }

            });
        }
    }

    private void displayValidationResult(JComponent jcomponent)
    {

        boolean valid = jcomponent.getInputVerifier().verify(jcomponent);

        if (valid)
        {
            jcomponent.setBackground(Color.white);
        }
        else
        {
            jcomponent.setBackground(Color.red);
        }
    }

}
