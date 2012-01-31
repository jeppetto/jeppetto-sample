package sample;


import sample.model.Category;

import org.iternine.jeppetto.dao.NoSuchItemException;
import org.iternine.jeppetto.testsupport.MongoDatabaseProvider;
import org.iternine.jeppetto.testsupport.TestContext;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;


public class BMSTest {

    //-------------------------------------------------------------
    // Variables - Private
    //-------------------------------------------------------------

    private TestContext testContext;
    private BlogManagementSystem bms;

    private static Logger logger = LoggerFactory.getLogger("jeppetto-sample");


    //-------------------------------------------------------------
    // Methods - Set-Up / Tear-Down
    //-------------------------------------------------------------

    @Before
    public void setUp() {
        testContext = new TestContext("BMSTest.xml",
                                      "Sample.properties",
                                      new MongoDatabaseProvider(false));

        this.bms = (BlogManagementSystem) testContext.getBean("blogManagementSystem");
    }


    @After
    public void tearDown() {
        if (testContext != null) {
            testContext.close();
        }

        this.bms = null;
    }


    //-------------------------------------------------------------
    // Methods - Public
    //-------------------------------------------------------------

    @Test
    public void createBlogAndGetTitle()
            throws NoSuchItemException, BMSException {
        String blogId = bms.createBlog("Jeppetto Time", "DAOs in a Jiffy!", Category.Programming);
        String retrievedTitle = bms.getBlogTitle(blogId);

        logger.info("Created blog with id '{}' and it has title '{}'", blogId, retrievedTitle);
    }

    @Test
    public void getNewlyCreatedBlogDate()
            throws BMSException {
        bms.createBlog("Jeppetto Time", "DAOs in a Jiffy!", Category.Programming);

        Date date = bms.getBlogCreatedDate("Jeppetto Time", Category.Programming);

        Assert.assertTrue(date.before(new Date()));
    }


    @Test
    public void failToFindBlogDate()
            throws BMSException {
        bms.createBlog("Jeppetto Time", "DAOs in a Jiffy!", Category.Programming);

        Date date = bms.getBlogCreatedDate("Jeppetto Time", Category.Food);

        Assert.assertNull(date);
    }


    @Test
    public void listBlogTitlesByCategory()
            throws BMSException {
        bms.createBlog("Jeppetto Time", "DAOs in a Jiffy!", Category.Programming);
        bms.createBlog("Noodling about Needling", "Needlepoint FTW!", Category.Hobbies);
        bms.createBlog("My Sweet Tooth", "Cookie Monster's guide to delectable desserts.", Category.Food);
        bms.createBlog("Curling IS a Sport", "Canada's other great export.", Category.Hobbies);

        for (Category category : Category.values()) {
            Iterable<String> categoryTitles = bms.getBlogTitlesForCategory(category);

            logger.info("Blogs for category '{}':", category);
            for (String categoryTitle : categoryTitles) {
                logger.info("\t" + categoryTitle);
            }
        }
    }


    @Test
    public void verifyBlogCount()
            throws BMSException {
        bms.createBlog("Jeppetto Time", "DAOs in a Jiffy!", Category.Programming);
        bms.createBlog("Noodling about Needling", "Needlepoint FTW!", Category.Hobbies);
        bms.createBlog("My Sweet Tooth", "Cookie Monster's guide to delectable desserts.", Category.Food);
        bms.createBlog("Curling IS a Sport", "Canada's other great export.", Category.Hobbies);

        Assert.assertEquals(4, bms.totalCount());
    }


    @Test
    public void addAnEntryAndVerifyCount()
            throws BMSException {
        String blogId = bms.createBlog("Jeppetto Time", "DAOs in a Jiffy!", Category.Programming);
        bms.addBlogPost(blogId, "Jeppetto blog's first entry!", "Woot!", "JT");

        Assert.assertEquals(1, bms.getBlogPostCountForBlog(blogId));
    }


    @Test
    public void verifyCountOfBlogsHavingPostsWithAuthor()
            throws BMSException {
        String jeppettoBlogId = bms.createBlog("Jeppetto Time", "DAOs in a Jiffy!", Category.Programming);
        String needlepointBlogId = bms.createBlog("Noodling about Needling", "Needlepoint FTW!", Category.Hobbies);

        bms.addBlogPost(jeppettoBlogId, "Jeppetto blog's first entry!", "Woot!", "JT");
        bms.addBlogPost(jeppettoBlogId, "Another Jeppetto entry.", "Phil here...", "Phil");
        bms.addBlogPost(needlepointBlogId, "Needles: Not Just Pokers", "There can be a misconception...", "Phil");

        Assert.assertEquals(1, bms.getBlogTitlesWithPostsFromAuthor("JT").size());
        Assert.assertEquals(2, bms.getBlogTitlesWithPostsFromAuthor("Phil").size());
    }


    @Test
    public void paginateTitles()
            throws BMSException, InterruptedException {
        String jeppettoBlogId = bms.createBlog("Jeppetto Time", "DAOs in a Jiffy!", Category.Programming);
        String needlepointBlogId = bms.createBlog("Noodling about Needling", "Needlepoint FTW!", Category.Hobbies);
        String sweetBlogId = bms.createBlog("My Sweet Tooth", "Cookie Monster's guide to delectable desserts.", Category.Food);

        bms.addBlogPost(sweetBlogId, "Who doens't like chocolate?", "Since chocolate's discovery...", "Phil");
        Thread.sleep(10);   // sleep() ensures the query sort works and the asserts below are valid
        bms.addBlogPost(jeppettoBlogId, "Jeppetto blog's first entry!", "Woot!", "JT");
        Thread.sleep(10);
        bms.addBlogPost(jeppettoBlogId, "Another Jeppetto entry.", "Phil here...", "Phil");
        Thread.sleep(10);
        bms.addBlogPost(needlepointBlogId, "Needles: Not Just Pokers", "There can be a misconception...", "Phil");

        // First page should have the full page size of results and the titles should be in reverse-update order.
        List<String> page1Titles = bms.paginateAuthorTitles("Phil", 1, 2);
        Assert.assertEquals(2, page1Titles.size());
        Assert.assertEquals("Noodling about Needling", page1Titles.get(0));
        Assert.assertEquals("Jeppetto Time", page1Titles.get(1));

        // Second page should have one more result.
        List<String> page2Titles = bms.paginateAuthorTitles("Phil", 2, 2);
        Assert.assertEquals(1, page2Titles.size());
        Assert.assertEquals("My Sweet Tooth", page2Titles.get(0));

        // Ensure no pages after the second one.
        Assert.assertEquals(0, bms.paginateAuthorTitles("Phil", 3, 2).size());
    }
}
