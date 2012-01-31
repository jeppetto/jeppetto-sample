package sample;


import sample.model.Blog;
import sample.model.BlogDAO;
import sample.model.BlogPost;
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


    public void addBlogPost(String blogId, String title, String body, String author)
            throws BMSException {
        Blog blog;

        try {
            blog = blogDAO.findById(blogId);
        } catch (NoSuchItemException e) {
            throw new BMSException("No blog with id '{}' exists.");
        }

        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(title);
        blogPost.setBody(body);
        blogPost.setAuthor(author);
        blogPost.setDate(new Date());

        List<BlogPost> blogPosts = blog.getBlogPosts();

        if (blogPosts == null) {
            blogPosts = new ArrayList<BlogPost>();

            blog.setBlogPosts(blogPosts);
        }

        blogPosts.add(blogPost);

        blogDAO.save(blog);
    }


    public List<String> getBlogTitlesWithPostsFromAuthor(String author) {
        Iterable<Blog> blogs = blogDAO.findByHavingBlogPostsWithAuthor(author);
        List<String> titles = new ArrayList<String>();

        for (Blog blog : blogs) {
            titles.add(blog.getTitle());
        }

        return titles;
    }


    public int getBlogPostCountForBlog(String blogId) {
        Blog blog = blogDAO.findById(blogId);

        return (blog == null || blog.getBlogPosts() == null) ? 0 : blog.getBlogPosts().size();
    }


    //-------------------------------------------------------------
    // Methods - IoC
    //-------------------------------------------------------------

    public void setBlogDAO(BlogDAO blogDAO) {
        this.blogDAO = blogDAO;
    }
}
