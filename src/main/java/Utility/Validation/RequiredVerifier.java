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
public class RequiredVerifier extends InputVerifier
{

    boolean valid;
    
    public String errorMessage = "Thông tin này không được để trống!";

    @Override
    public boolean verify(JComponent input)
    {
        isValidText((JTextField) input);
        return valid;
    }

    @Override
    public boolean shouldYieldFocus(JComponent source, JComponent target)
    {
        return true;
    }

    public boolean isValidText(JTextField jTextField)
    {
        if (jTextField.getText() != null && !jTextField.getText().trim().isEmpty())
        {
            valid = true;
            jTextField.setToolTipText("");
        }
        else
        {
            valid = false;
            jTextField.setToolTipText(errorMessage);
        }

        return valid;
    }
}
