package sample.model;


import java.util.Date;
import java.util.List;


public class Blog {

    //-------------------------------------------------------------
    // Variables - Private
    //-------------------------------------------------------------

    private String id;
    private String title;
    private String description;
    private Date createdDate;
    private Category category;
    private List<BlogPost> blogPosts;


    //-------------------------------------------------------------
    // Methods - Getter/Setter
    //-------------------------------------------------------------

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Date getCreatedDate() {
        return createdDate;
    }


    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    public Category getCategory() {
        return category;
    }


    public void setCategory(Category category) {
        this.category = category;
    }


    public List<BlogPost> getBlogPosts() {
        return blogPosts;
    }


    public void setBlogPosts(List<BlogPost> blogPosts) {
        this.blogPosts = blogPosts;
    }
}
