package com.ebill.ebill.Controllers;

import com.ebill.ebill.DB.DBUtils;
import com.ebill.ebill.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


public class LoginController {
    @FXML
    TextField uname_txt;
    @FXML
    Text error_txt;
    @FXML
    PasswordField pass_txt;


    public void onCloseBtn() {
        Platform.exit();
    }

    public void onLogin() {
        if (!uname_txt.getText().isEmpty() && !pass_txt.getText().isEmpty()) {
            error_txt.setText("");
                /*if (uname_txt.getText().contains(".com")) {
                    //mail id
                } else {
                    //username
                }*/
            DBUtils.loginUser(uname_txt.getText(), pass_txt.getText());
        } else {
            error_txt.setText("Invalid Username or password!");
        }

    }

    public void onSignup(MouseEvent event) {
        Main.changeScene(event, "/FXML/Signup.fxml");
    }

    public void onForgot(MouseEvent event) {
        Main.changeScene(event, "/FXML/ForgotP.fxml");
    }

}
