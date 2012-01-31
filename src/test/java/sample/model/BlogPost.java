package sample.model;


import java.util.Date;


public class BlogPost {

    //-------------------------------------------------------------
    // Variables - Private
    //-------------------------------------------------------------

    private String title;
    private Date date;
    private String body;
    private String author;


    //-------------------------------------------------------------
    // Methods - Getter/Setter
    //-------------------------------------------------------------

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }


    public String getBody() {
        return body;
    }


    public void setBody(String body) {
        this.body = body;
    }


    public String getAuthor() {
        return author;
    }


    public void setAuthor(String author) {
        this.author = author;
    }
}
