package de.imi.odmtoolbox.cron;

import de.imi.odmtoolbox.dao.UMLSCodeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Refreshes the database connection.
 * 
 */
@Service
public class DatabaseConnection {
    
    @Autowired
    private UMLSCodeDao umlsCodeDao;
    
    @Scheduled(fixedRate=25200000)
    public void refresh(){
        // Refresh database connection for umls codes
        try {
            umlsCodeDao.getElementById(0L);
        } catch (Exception e) {
        }
    }    
}
