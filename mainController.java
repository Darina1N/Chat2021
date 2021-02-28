package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import sk.kosickaakademia.kolesarova.database.Database;
import sk.kosickaakademia.kolesarova.database.entity.Message;
import sk.kosickaakademia.kolesarova.database.entity.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainController {

    public Label lbl_login;
    public Button btn_send;
    public Button btn_refresh;
    public Button btn_logout;
    public TextArea txt_message;
    public TextArea txt_mymessages;
    public Label lbl_warning;
    public ComboBox cmbtn_users;

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void initLoginName(){
        lbl_login.setText(user.getLogin());
    }

    public void listOfUsers(){
        List<String> list= new Database().allUsers();
        for(String s : list){
            cmbtn_users.getItems().add(s);
        }
    }


    public void sendMessage(ActionEvent actionEvent) {
        String message = txt_message.getText();
        String toUser = String.valueOf(cmbtn_users.getValue());
        System.out.println(message +" "+toUser);
        if(message != null && message != "" && toUser != null && toUser != ""){

            Database database = new Database();
            database.sendMessage(database.getUserId(user.getLogin()), toUser, message);
        }
        txt_message.setText("");
    }

    public void logout(ActionEvent actionEvent) {
        btn_logout.getScene().getWindow().hide();
    }

    public void refreshMessage(ActionEvent actionEvent) {
        txt_mymessages.clear();
        List<Message> list = new Database().getMyMessages(user.getLogin());
        System.out.println(list.size());
        if(list==null)
            return;

        if(list.isEmpty()){
            txt_mymessages.appendText("You do not have any new messages");
        }
        else{
            for(int i = list.size()-1; i >= 0 ; i--){
                Message message = list.get(i);
                Date date = message.getDt();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                String strDate = formatter.format(date);
                String od = message.getFrom();
                String komu = user.getLogin();
                String messagee = message.getText();
                txt_mymessages.appendText(strDate + " " +od + " " + '\n' +messagee + '\n');
            }
        }
    }

}