package com.ebill.ebill.Controllers;

import com.ebill.ebill.DB.DBUtils;
import com.ebill.ebill.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static com.ebill.ebill.DB.DBUtils.Questions;

public class SignupController implements Initializable {
    @FXML
    ChoiceBox<String> secQue;
    @FXML
    TextField uname_txt, ans;
    @FXML
    Text error_txt;
    @FXML
    PasswordField pass_txt, pass_txt2;

    public void onLogin(MouseEvent event) {
        Main.changeScene(event, "/FXML/Login.fxml");
    }

    public void onCloseBtn() {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        secQue.setValue(Questions[0]);
        secQue.getItems().addAll(Questions);
    }

    public void onCreate() {
        if (!uname_txt.getText().isEmpty()) {
            if (pass_txt.getText().length() > 5) {
                if (pass_txt2.getText().equals(pass_txt.getText())) {
                    if (!ans.getText().isEmpty()) {
                        error_txt.setText("");
//                        if (uname_txt.getText().contains(".com")) {
//                            //mail id
//                        } else {
//                            //username
//                        }
                        DBUtils.signUpUser(uname_txt.getText(), pass_txt.getText(), secQue.getSelectionModel().getSelectedIndex(), ans.getText());
                    } else {
                        error_txt.setText("Please fill security question !");
                    }
                } else {
                    error_txt.setText("Passwords do not match !");
                }
            } else {
                error_txt.setText("Password should contain least 6 characters !");
            }

        } else {
            error_txt.setText("Invalid Username !");
        }
    }
}
