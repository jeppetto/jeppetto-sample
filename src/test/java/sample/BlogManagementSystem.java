package sample;


import sample.model.Blog;
import sample.model.BlogDAO;
import sample.model.Category;

import org.iternine.jeppetto.dao.NoSuchItemException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BlogManagementSystem {

    //-------------------------------------------------------------
    // Variables - Private
    //-------------------------------------------------------------

    private BlogDAO blogDAO;


    //-------------------------------------------------------------
    // Methods - Public
    //-------------------------------------------------------------

    public String createBlog(String title, String description, Category category)
            throws BMSException {
        Blog preExistingBlog = blogDAO.findByTitle(title);

        if (preExistingBlog != null) {
            throw new BMSException("Blog with title " + title + " already exists!  Please pick something else.");
        }

        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setDescription(description);
        blog.setCreatedDate(new Date());
        blog.setCategory(category);

        blogDAO.save(blog);

        return blog.getId();
    }


    public String getBlogTitle(String id)
            throws NoSuchItemException {
        Blog retrieved = blogDAO.findById(id);

        return retrieved.getTitle();
    }


    public Date getBlogCreatedDate(String title, Category category) {
        try {
            return blogDAO.findByTitleAndCategory(title, category).getCreatedDate();
        } catch (NoSuchItemException e) {
            return null;
        }
    }


    public List<String> getBlogTitlesForCategory(Category category) {
        Iterable<Blog> blogs = blogDAO.findByCategory(category);
        List<String> titles = new ArrayList<String>();

        for (Blog blog : blogs) {
            titles.add(blog.getTitle());
        }

        return titles;
    }


    public int totalCount() {
        return blogDAO.countBy();
    }


    //-------------------------------------------------------------
    // Methods - IoC
    //-------------------------------------------------------------

    public void setBlogDAO(BlogDAO blogDAO) {
        this.blogDAO = blogDAO;
    }
}
