package de.imi.odmtoolbox.dao.impl;

import de.imi.odmtoolbox.dao.UMLSCodeDao;
import de.imi.odmtoolbox.model.UMLSCode;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * The data access object (DAO) is an object that provides an abstract interface
 * to some type of database or persistence mechanism, providing some specific
 * operations without exposing details of the database. In this special case the
 * persistence manager is injected by the PersistenceContext.
 *
 */
@Component
public class UMLSCodeDaoImpl extends ODMToolboxDaoImpl<UMLSCode> implements UMLSCodeDao {

    @Override
    public UMLSCode getUMLSCodeByCode(String code) {
        try {
            TypedQuery<UMLSCode> query = odmToolboxEntityManager.createQuery(
                    "SELECT c FROM UMLSCode c WHERE c.code='" + (code) + "'", UMLSCode.class);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}