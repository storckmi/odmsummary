package de.imi.odmtoolbox.dao;

import java.util.Collection;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;


/**
 * Generic interface, which is implemented by all data access objects (daos).
 * 
 * Provides CRUD methods for all daos.
 *
 */
@Transactional
public interface ODMToolboxDao<T>
{
    
   /**
    * Persists a object which is defined in the model class of the given type <T>.
    *
    * @param element The <T> object, which will be added to the database.
    */
    public void persist (T element);

   /**
    * Persists a object which is defined in the model class of the given type <T>.
    *
    * @param element The <T> object, which will be changed in the database.
    */
    public void merge(T element);
    
    /**
     * Removes a object which is defined in the model class of the given type <T>.
     *
     * @param element The <T> object, which will be removed in the database.
     */
    public void remove (T element);
    
    /**
     * Searches for a element of type <T> by id.
     *
     * @param id Id of the searched element.
     * 
     * @return The element, which was found by its id.
     */
    public T getElementById(Long id);
    
    /**
     * Searches for set of elements of type <T> by id.
     * 
     * @param ids   Ids of the searched elements.
     * 
     * @return All elements, which were found by there ids.
     */
    public Collection<T> getElementsById(Collection<Long> ids);

    /**
     * Returns all elements of Type <T> in the Database.
     *
     * @return All elements of type <T> within the database.
     */
    public List<T> getAllElements();
}
