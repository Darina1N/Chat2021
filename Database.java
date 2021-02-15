package sk.kosickaakademia.kolesarova.database;

import sk.kosickaakademia.kolesarova.database.entity.Message;
import sk.kosickaakademia.kolesarova.database.entity.User;
import sk.kosickaakademia.kolesarova.database.util.Heshing;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database {

    private String url = "jdbc:mysql://itsovy.sk:3306/chat2021";
    private String username = "mysqluser";
    private String password = "Kosice2021!";

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    public boolean insertNewUser(String login, String password){
        if(login==null || login.equals("") || password==null || password.length()<6) //kontrola minimálne 6 miestne heslo
            return false;
        String query="INSERT INTO user(login, password) VALUES (?, ?)";
        try {
            Connection connection=getConnection();
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1,login);
                ps.setString(2, Heshing.MD5.getMd5(password));
                ps.executeUpdate();
                connection.close();
                System.out.println("User "+login+" bol pridaný.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public User loginUser(String login, String password){
        if(login==null || login.equals("") || password==null || password.length()<6)
            return null;
        String query = "SELECT * FROM user WHERE login LIKE ? AND password LIKE ?";
        try{
            Connection connection= getConnection();
            if(connection!=null){
                PreparedStatement ps=connection.prepareStatement(query);
                ps.setString(1,login);
                ps.setString(2,Heshing.MD5.getMd5(password));
                ResultSet rs=ps.executeQuery();
                if(rs.next()){
                    System.out.println("Login is correct.");
                    int id=rs.getInt("id");
                    User user=new User(id,login,password);
                    connection.close();
                    return user;
                }else {
                    connection.close();
                    System.out.println("Incorrectly entered data.");
                    return null;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean changePassword(String login, String oldPassword, String newPassword){
        if(login==null || login.equals("") || oldPassword==null || oldPassword.length()<6 || newPassword==null || newPassword.length()<6 || newPassword==oldPassword)
            return false;
        String query="UPDATE user SET password LIKE ? WHERE login LIKE ? AND password LIKE ?";
        try{
           Connection connection= getConnection();
           if(connection!=null){
               PreparedStatement ps=connection.prepareStatement(query);
               ps.setString(1,Heshing.MD5.getMd5(newPassword));
               ps.setString(2,login);
               ps.setString(3,Heshing.MD5.getMd5(oldPassword));
               int rs=ps.executeUpdate();
               connection.close();
               System.out.println("Password has been changed.");
               return true;
           }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public int getUserId(String login) {
        if(login==null || login.equals(""))
            return -1;
        String query = "SELECT id FROM user WHERE login LIKE ?";
        try {
            Connection connection=getConnection();
            PreparedStatement ps=connection.prepareStatement(query);
            ps.setString(1,login);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                int id= rs.getInt("id");
                return id;
            }
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public boolean sendMessage(int from, String toUser, String text){
        if(text==null || text.equals(""))
            return false;
        int to=getUserId(toUser);
        if(to==-1)
            return false;
        String query="INSERT INTO message( from, to, text) VALUES (?,?,?)";
        try{
            Connection connection=getConnection();
            PreparedStatement ps=connection.prepareStatement(query);
            ps.setInt(1,from);
            ps.setInt(2,to);
            ps.setString(3,text);
            int result=ps.executeUpdate();
            connection.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public List<Message> getMyMessages(String login){
        if(login==null || login.equals(""))
            return null;
        List<Message> list= new ArrayList<>();
        String query="SELECT message.id, message.dt, message.fromUser, message.toUser, text "+
                "FROM message "+
                "INNER JOIN user ON user.id=message.toUser "+
                "WHERE fromUser LIKE ?";
        try{
            Connection connection=getConnection();
            PreparedStatement ps=connection.prepareStatement(query);
            ps.setInt(1,getUserId(login));
            ResultSet rs= ps.executeQuery();
            while (rs.next()){
                int id= rs.getInt("id");
                Date date=rs.getDate("dt");
                String from= rs.getString("fromUser");
                String to= rs.getString("toUser");
                String text=rs.getString("text");
                Message message=new Message(id,from,to,text,date);
                list.add(message);
            }
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}