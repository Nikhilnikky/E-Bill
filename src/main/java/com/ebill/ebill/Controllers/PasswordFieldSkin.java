package com.ebill.ebill.Controllers;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TextFieldSkin;

public class PasswordFieldSkin extends TextFieldSkin {
    private final boolean visibility;
    private char mask_char = '\u25cf';

    public PasswordFieldSkin(TextField textField, boolean visibility, char mask_char) {
        super(textField);
        this.visibility = visibility;
        if (mask_char != '\u0000') this.mask_char = mask_char;
    }

    @Override
    protected String maskText(String var1) {
        if (!(this.getSkinnable() instanceof PasswordField)) {
            return var1;
        } else {
            if (visibility) {
                return var1;
            } else {
                int var2 = var1.length();
                StringBuilder var3 = new StringBuilder(var2);
                for (int var4 = 0; var4 < var2; ++var4) {
                    var3.append(mask_char);
                }
                return var3.toString();
            }
        }
    }
}
