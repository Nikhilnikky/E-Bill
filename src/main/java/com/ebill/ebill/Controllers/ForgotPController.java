package com.ebill.ebill.Controllers;

import com.ebill.ebill.DB.DBUtils;
import com.ebill.ebill.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPController implements Initializable {
    @FXML
    ChoiceBox<String> secQue;
    @FXML
    TextField uname_txt, ans;
    @FXML
    PasswordField pass_txt;
    String[] userData;
    @FXML
    Text p_txt;


    public void onBack(MouseEvent event) {
        Main.changeScene(event, "/FXML/Login.fxml");
    }

    public void onCloseBtn() {
        Platform.exit();
    }

    public void getUserData() {
        secQue.setValue("");
        if (!uname_txt.getText().isEmpty()) {
            userData = DBUtils.getUserDetails(uname_txt.getText(), null, null, null);
            if (userData[2] != null) {
                secQue.setValue(DBUtils.Questions[Integer.parseInt(userData[2])]);
                uname_txt.setDisable(true);
            }
        }
    }

    public void onPasstxt() {
        String temp = pass_txt.getText();
        if (p_txt.getText().contains("show")) {
            p_txt.setText("hide password");
            pass_txt.setSkin(new PasswordFieldSkin(pass_txt, true, '\u0000'));
        } else {
            p_txt.setText("show password");
            pass_txt.setSkin(new PasswordFieldSkin(pass_txt, false, '\u0000'));
        }
        pass_txt.setText(temp);
    }

    public void onRetrieve() {
        if (!uname_txt.getText().isEmpty() && !ans.getText().isEmpty()) {
            if (userData[3].equals(ans.getText())) {
                pass_txt.setText(userData[1]);
                uname_txt.setDisable(true);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Incorrect answer !");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please fill all the fields.");
            alert.show();
        }

    }

    public void onUpdatePass(MouseEvent event) {
        if (!pass_txt.getText().isEmpty()) {
            if (!userData[1].equals(pass_txt.getText())) {
                DBUtils.updateUserPassword(pass_txt.getText(), uname_txt.getText());
                Main.changeScene(event, "/FXML/Login.fxml");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("New password cannot be old password !");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Password field is empty !");
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        secQue.disabledProperty();
    }
}
