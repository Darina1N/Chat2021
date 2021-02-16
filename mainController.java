package sample;

import javafx.scene.control.Label;
import sk.kosickaakademia.kolesarova.database.entity.User;

public class mainController {

    public Label lbl_user;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void initLoginName(){
        lbl_user.setText(user.getLogin());
    }
}
