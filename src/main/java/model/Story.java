package model;

/**
 * Created by AZagorskyi on 14.04.2017.
 */
public class Story {
    private int id;
    private String name;
    private String text;
    private String author;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String Author) {
        this.author = author;
    }
}
