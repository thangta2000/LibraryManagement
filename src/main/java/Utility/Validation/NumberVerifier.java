/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility.Validation;

import java.util.regex.Pattern;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author admin
 */
public class NumberVerifier extends InputVerifier
{

    boolean valid;
    
    public String errorMessage = "Bạn chỉ nhập số từ 0 - 9!";

    @Override
    public boolean verify(JComponent input)
    {
        isDigit((JTextField) input);
        return valid;
    }

    @Override
    public boolean shouldYieldFocus(JComponent source, JComponent target)
    {
        return true;
    }

    public boolean isDigit(JTextField jTextField)
    {
        int n = jTextField.getText().length();
        
        for (int i = 0; i < n; i++)
        {
            if (Character.isDigit(jTextField.getText().charAt(i)))
            {
                jTextField.setToolTipText("");
                valid = true;
            }
            else
            {
                jTextField.setToolTipText("Bạn chỉ nhập số từ 0 - 9!");
                valid = false;
            }
        }

        return valid;
    }

}
