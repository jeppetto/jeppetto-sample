package sample.model;


import org.iternine.jeppetto.dao.GenericDAO;
import org.iternine.jeppetto.dao.NoSuchItemException;


public interface BlogDAO extends GenericDAO<Blog, String> {

    Blog findByTitle(String title);


    Iterable<Blog> findByCategory(Category category);


    Blog findByTitleAndCategory(String title, Category category)
            throws NoSuchItemException;


    int countBy();
}
