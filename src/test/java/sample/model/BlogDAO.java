package sample.model;


import org.iternine.jeppetto.dao.ConditionType;
import org.iternine.jeppetto.dao.GenericDAO;
import org.iternine.jeppetto.dao.NoSuchItemException;
import org.iternine.jeppetto.dao.SortDirection;
import org.iternine.jeppetto.dao.annotation.Association;
import org.iternine.jeppetto.dao.annotation.Condition;
import org.iternine.jeppetto.dao.annotation.DataAccessMethod;
import org.iternine.jeppetto.dao.annotation.Sort;


public interface BlogDAO extends GenericDAO<Blog, String> {

    Blog findByTitle(String title);


    Iterable<Blog> findByCategory(Category category);


    Blog findByTitleAndCategory(String title, Category category)
            throws NoSuchItemException;


    int countBy();


    Iterable<Blog> findByHavingBlogPostsWithAuthor(String author);


    @DataAccessMethod(
        associations = { @Association(field = "blogPosts", conditions = { @Condition(field = "author", type = ConditionType.Equal) }) },
        sorts = @Sort(field = "lastUpdatedDate", direction = SortDirection.Descending),
        limitResults = true,
        skipResults = true
    )
    Iterable<Blog> paginateAuthorContributedBlogs(String author, int limitAmount, int resultsToSkip);
}
