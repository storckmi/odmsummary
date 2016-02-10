package de.imi.odmtoolbox.dao;

import de.imi.odmtoolbox.model.UMLSCode;
import org.springframework.stereotype.Component;

/**
 * The data access object (DAO) is an object that provides an abstract interface 
 * to some type of database or persistence mechanism, providing some specific 
 * operations without exposing details of the database. In this special case the
 * persistence manager is injected by @PersistenceContext.
 * 
 */
@Component
public interface UMLSCodeDao extends ODMToolboxDao<UMLSCode> {
    /**
     * Gets a {@link UMLSCode} by its code.
     * 
     * @param code The UMLS code which sould be searched for.
     * @return The {@link UMLSCode} with the given code.
     */
    public UMLSCode getUMLSCodeByCode(String code);

}