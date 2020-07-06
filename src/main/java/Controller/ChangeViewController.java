/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Bean.CategoryBean;
import View.BookTitlePanel;
import View.BookPanel;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author tkang_85a
 */



public class ChangeViewController {
    
    private JPanel jpnRoot;
    private String kindSelected = "";

    List<CategoryBean> listItem = null;
    
    public ChangeViewController(JPanel jpnRoot) {
        this.jpnRoot = jpnRoot;
    }
    
    public void setView(JPanel jpnItem, JLabel jlbItem) {
        kindSelected = "BookTitle";
        
        jpnRoot.removeAll();
        jpnRoot.setLayout(new BorderLayout());
        jpnRoot.add(new BookTitlePanel());
        jpnRoot.validate();
        jpnRoot.repaint();
    }
    
    public void setEvent(List<CategoryBean> listItem) {
     this.listItem = listItem;
     for (CategoryBean item : listItem) {
           item.getJpn().addMouseListener(new LabelEvent(item.getKind(), item.getJpn(), item.getJlb()));
     }
    }
    
    class LabelEvent implements MouseListener {

      private JPanel node;
      private String kind;

      private JPanel jpnItem;
      private JLabel jlbItem;

      public LabelEvent(String kind, JPanel jpnItem, JLabel jlbItem) {
           this.kind = kind;
           this.jpnItem = jpnItem;
           this.jlbItem = jlbItem;
      }

      @Override
      public void mouseClicked(MouseEvent e) {
            
      }

      @Override
      public void mousePressed(MouseEvent e) {
          switch (kind) {
                case "BookTitle":
                    node = new BookTitlePanel();
                     break;
                case "Book":
                    node = new BookPanel();
                    break;
                // more
                default:
                    break;
           }
           jpnRoot.removeAll();
           jpnRoot.setLayout(new BorderLayout());
           jpnRoot.add(node);
           jpnRoot.validate();
           jpnRoot.repaint();
      }

      @Override
      public void mouseReleased(MouseEvent e) {

      }

      @Override
      public void mouseEntered(MouseEvent e) {
      }

      @Override
      public void mouseExited(MouseEvent e) {
      }

    }
}
