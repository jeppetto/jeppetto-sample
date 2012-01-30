package sample;


import sample.model.Blog;
import sample.model.BlogDAO;

import org.iternine.jeppetto.dao.DAOBuilder;
import org.iternine.jeppetto.dao.NoSuchItemException;
import org.iternine.jeppetto.dao.QueryModelDAO;
import org.iternine.jeppetto.dao.mongodb.MongoDBQueryModelDAO;

import com.mongodb.Mongo;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;


public class BlogManagementSystem {

    //-------------------------------------------------------------
    // Variables - Private - Static
    //-------------------------------------------------------------

    private static BlogDAO blogDAO;


    //-------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------

    static {
        try {
            //noinspection RedundantCast,unchecked
            blogDAO = DAOBuilder.buildDAO(Blog.class,
                                          BlogDAO.class,
                                          (Class<? extends QueryModelDAO<Blog, String>>) MongoDBQueryModelDAO.class.asSubclass(QueryModelDAO.class),
                                          new HashMap<String, Object>() {{
                                              put("db", new Mongo().getDB("sample"));
                                          }});
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

    }


    //-------------------------------------------------------------
    // Methods - Public
    //-------------------------------------------------------------

    public String createBlog(String title, String description) {
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setDescription(description);
        blog.setCreatedDate(new Date());

        blogDAO.save(blog);

        return blog.getId();  // after save() is called, the blog object will be assigned an id
    }


    public String getBlogTitle(String id)
            throws NoSuchItemException {
        Blog retrieved = blogDAO.findById(id);

        return retrieved.getTitle();
    }

}
