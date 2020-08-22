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
public class StringLengthVerifier extends InputVerifier
{

    boolean valid;
    int minNumber;
    int maxNumber;

    public String errorMessage;

    public StringLengthVerifier(int min, int max)
    {
        minNumber = min;
        maxNumber = max;
        if (min > 0 && max > 0)
        {
            errorMessage = "Bạn cần nhập từ " + minNumber + " đến " + maxNumber + " ký tự!";
        }
        else if (min > 0 && max < 0)
        {
            errorMessage = "Bạn phải nhập tối thiểu " + maxNumber + " ký tự!";
        }
        else
        {
            errorMessage = "Bạn chỉ nhập tối đa " + maxNumber + " ký tự!";
        }
    }

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
        if (jTextField.getText().length() >= minNumber && jTextField.getText().length() <= maxNumber)
        {
            jTextField.setToolTipText("");
            valid = true;
        }
        else
        {
            jTextField.setToolTipText(errorMessage);
            valid = false;
        }

        return valid;
    }

}
