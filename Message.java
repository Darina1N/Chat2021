package sk.kosickaakademia.kolesarova.database.entity;

import java.util.Date;

public class Message {
    private int id;
    private String from;
    private String to;
    private String text;
    private Date dt;

    public Message(int id, String from, String to, String text, Date dt) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.text = text;
        this.dt = dt;
    }

    public int getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getText() {
        return text;
    }

    public Date getDt() {
        return dt;
    }
}
