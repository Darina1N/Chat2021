package sk.kosickaakademia.kolesarova.database;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Database {

    private String url = "jdbc:mysql://itsovy.sk:3306/chat2021";
    private String nameDatabase = "mysqluser";
    private String passwordDatabase = "Kosice2021!";

    public boolean insertNewUser(String login, String password){
        if(login==null || login.equals("") || password==null || password.equals(""))
            return false;
        String query="INSERT INTO user(login, password) VALUES (?, ?)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, nameDatabase, passwordDatabase);
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1,login);
                ps.setString(2,MD5.getMd5(password));
                ps.executeUpdate();
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public static class MD5 {
        public static String getMd5(String input) {
            try {

                // Static getInstance method is called with hashing MD5
                MessageDigest md = MessageDigest.getInstance("MD5");

                // digest() method is called to calculate message digest
                //  of an input digest() return array of byte
                byte[] messageDigest = md.digest(input.getBytes());

                // Convert byte array into signum representation
                BigInteger no = new BigInteger(1, messageDigest);

                // Convert message digest into hex value
                String hashtext = no.toString(16);
                while (hashtext.length() < 32) {
                    hashtext = "0" + hashtext;
                }
                return hashtext;
            }

            catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
