/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility.Validation;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JPasswordField;

/**
 *
 * @author admin
 */
public class StringMatchVerifier extends InputVerifier
{

    boolean valid;
    
    public String errorMessage = "Chuỗi nhập lại không đúng!";
    
    char[] compareString;

    public StringMatchVerifier(char [] chars)
    {
        compareString = chars;
    }

    @Override
    public boolean verify(JComponent input)
    {
        isTextMatched((JPasswordField) input);
        return valid;
    }

    @Override
    public boolean shouldYieldFocus(JComponent source, JComponent target)
    {
        return true;
    }

    public boolean isTextMatched(JPasswordField jPasswordField)
    {
        if (jPasswordField.getPassword() == compareString)
        {
            valid = true;
            jPasswordField.setToolTipText("");
        }
        else
        {
            valid = false;
            jPasswordField.setToolTipText(errorMessage);
        }

        return valid;
    }
    
}
