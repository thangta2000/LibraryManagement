/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility.Validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author admin
 */
public class DateTimeVerifier extends InputVerifier
{

    boolean valid;
    
    public String errorMessage = "Bạn cần nhập ngày tháng theo dạng dd/MM/yyyy!";

    @Override
    public boolean verify(JComponent input)
    {
        isDateTime((JTextField) input);
        return valid;
    }

    @Override
    public boolean shouldYieldFocus(JComponent source, JComponent target)
    {
        return true;
    }

    private boolean isDateTime(JTextField jTextField)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false); // will return false when parse if date is wrong such as 31/2/2020
        try
        {
            Date date = formatter.parse(jTextField.getText());
            jTextField.setToolTipText("");
            valid = true;
        }
        catch (ParseException ex)
        {
            //Logger.getLogger(DateTimeVerifier.class.getName()).log(Level.SEVERE, null, ex);
            jTextField.setToolTipText("Bạn cần nhập ngày tháng theo dạng dd/MM/yyyy!");
            valid = false;
        }
        
        return valid;
    }

}
