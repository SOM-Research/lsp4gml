package es.unex.quercusseg.graphicalserver.util;

import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;

import es.unex.quercusseg.graphicalserver.LanguageServerImpl;


public class Logging {
  
	
	private static Logging instance = null;
    private final  Logger  logger   = Logger.getLogger(Logging.class);
    
    
    public Logging () {
    	
//    	PropertyConfigurator.configure("log4j.properties");
    	
    }
    
    
	public static Logging getInstance() {
		
		if(instance == null)
			instance = new Logging();
		
		return instance;
		
	}

	
    public Logger getLogger() {
    	
    	return this.logger;
    	
    }
    
    
    public static void main(String[] args) {
    	
    	Logging logging = new Logging();
    	Logger logger = logging.getLogger();
    	
    	logger.info("this is a sample log message."); 	
        
    }
    
}
	
