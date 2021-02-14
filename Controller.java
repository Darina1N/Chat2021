package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sk.kosickaakademia.kolesarova.database.Database;
import sk.kosickaakademia.kolesarova.database.entity.User;

public class Controller {
    public PasswordField txt_password;
    public TextField txt_login;
    public Button btn_log;
    public Label lbl_error;

    public void click(ActionEvent actionEvent) {
        System.out.println("Click is OK");
        String login = txt_login.getText().trim();
        String password = txt_password.getText().trim();
        if (login.length() > 0 && password.length() > 0) {
            Database database = new Database();
            User user = database.loginUser(login, password);
            if (user == null) {
                lbl_error.setVisible(true);
            } else {
                System.out.println("Your login is correct");
            }

        }
    }
}
