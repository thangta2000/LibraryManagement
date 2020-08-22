/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility.Validation;

import javax.swing.InputVerifier;
import javax.swing.JComponent;

/**
 *
 * @author admin
 */
public class GroupVerifier extends InputVerifier
{

    InputVerifier[] verifiers;

    boolean valid;

    int errorIndex = -1;

    public GroupVerifier(InputVerifier... verifiers)
    {
        this.verifiers = verifiers;
    }

    @Override
    public boolean verify(JComponent input)
    {
        for (InputVerifier verifier : verifiers)
        {
            if (!verifier.verify(input))
            {
                valid = false;
                break;
            }
            else
            {
                valid = true;
            }
        }
        return valid;
    }

    @Override
    public boolean shouldYieldFocus(JComponent source, JComponent target)
    {
        return true;
    }
}
