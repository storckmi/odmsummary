package de.imi.odmtoolbox.dao.impl;

import de.imi.odmtoolbox.dao.ODMToolboxDao;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the generic interface ODMToolboxDao, which is inherited by
 * daos within the application.
 * 
 * Implements CRUD methods for all daos.
 *
 */
@Component
public abstract class ODMToolboxDaoImpl<T> implements ODMToolboxDao<T> {   
    // Provides the EntityManager, which manages the persistence layer
    @PersistenceContext(unitName="ODMToolBox")
    protected EntityManager odmToolboxEntityManager;
    
    // Holds the generic entity class T
    private Class<T> entityClass;
    
    /**
     * Constructor, which gets the generic class T
     */
    public ODMToolboxDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        entityClass = (Class) pt.getActualTypeArguments()[0];
    }   

    /**
     * Returns the entity class of the current dao implementation.
     * 
     * @return The entity class of the current dao implementation.
     */    
    public Class<T> getEntityClass() 
    {
	return entityClass;
    }

    @Override
    @Transactional
    public void persist (T element) 
    {
       odmToolboxEntityManager.persist(element);
    }

    @Override
    @Transactional
    public void merge(T element) 
    {
       odmToolboxEntityManager.merge(element);
    }
    
    @Override
    @Transactional
    public void remove (T element)
    {
        odmToolboxEntityManager.remove(odmToolboxEntityManager.merge(element));
    } 
    
    @Override
    public T getElementById(Long id) 
    {
        TypedQuery<T> query = odmToolboxEntityManager.createQuery(
            "SELECT e FROM "+getEntityClass().getSimpleName()+" e WHERE e.id="+(id), getEntityClass());
        return query.getSingleResult();
    }
      
    @Override
    public Collection<T> getElementsById(Collection<Long> ids)
    {
        Collection<T> elements = new ArrayList<T>();
        if (ids.isEmpty() == false)
        {
            Query query = odmToolboxEntityManager.createQuery("SELECT e FROM "+getEntityClass().getSimpleName()+" e WHERE e.id IN :ids");
            query.setParameter("ids", ids);
            elements = query.getResultList();
        }
        return elements;
    }

    @Override
    public List<T> getAllElements() 
    {
        TypedQuery<T> query = odmToolboxEntityManager.createQuery(
        "SELECT e FROM " + getEntityClass().getSimpleName() + " e", getEntityClass());
        return query.getResultList();
    }
}
