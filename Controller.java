package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
                openMainForm();
            }
        }
    }

    private void openMainForm(){//metoda na otvorenie noveho okna
        try{
            Parent root= FXMLLoader.load(getClass().getResource("main.fxml"));
            Stage stage= new Stage();
            stage.setTitle("My chat 2021 application"); //názov druhého okna
            stage.setScene(new Scene(root,800,500)); //veľkosť druhého okna
            stage.show();
            btn_log.getScene().getWindow().hide();//po kliknutí na buton logovania sa mi povodne okno zatvori
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
