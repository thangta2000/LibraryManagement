/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility.Validation;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author admin
 */
public class EmailVerifier extends InputVerifier
{
    boolean valid;
    
    public String errorMessage = "Bạn cần nhập đúng email!";

    @Override
    public boolean verify(JComponent input)
    {
        isEmail((JTextField) input);
        return valid;
    }
    
    @Override
    public boolean shouldYieldFocus(JComponent source, JComponent target)
    {
        return true;
    }

    public boolean isEmail(JTextField jTextField)
    {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (jTextField.getText().matches(regex))
        {
            jTextField.setToolTipText("");
            valid = true;
        }
        else
        {
            jTextField.setToolTipText("Bạn cần nhập đúng email!");
            valid = false;
        }

        return valid;
    }
    
}
