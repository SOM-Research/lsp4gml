package es.unex.quercusseg.graphicalserver.util;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class Logging {
  
	
	private static final Logger logger = LogManager.getLogger(Logging.class);
	private static       Logging instance = null;
      
    
	public static Logging getInstance() {
		
		if(instance == null)
			instance = new Logging();
		
		return instance;
		
	}

	
    public Logger getLogger() {
    	
    	return logger;
    	
    }
    
    
    public static void main(String[] args) {
    	 
        logger.info("Entering application.");
        logger.info("Exiting application.");
        
    }
    
}
	
