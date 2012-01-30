package sample;


import org.iternine.jeppetto.dao.NoSuchItemException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BMSTest {

    //-------------------------------------------------------------
    // Variables - Private
    //-------------------------------------------------------------

    private BlogManagementSystem blogManagementSystem = new BlogManagementSystem();


    //-------------------------------------------------------------
    // Variables - Private - Static
    //-------------------------------------------------------------

    protected static Logger logger = LoggerFactory.getLogger("jeppetto-sample");


    //-------------------------------------------------------------
    // Methods - Public
    //-------------------------------------------------------------

    @Test
    public void createBlogAndGetTitle()
            throws NoSuchItemException {
        String blogId = blogManagementSystem.createBlog("Jeppetto Time!", "The blog that shows you the ins and outs of Jeppetto.");
        String retrievedTitle = blogManagementSystem.getBlogTitle(blogId);

        logger.info("Created blog with id '{}' and it has title '{}'", blogId, retrievedTitle);
    }
}
