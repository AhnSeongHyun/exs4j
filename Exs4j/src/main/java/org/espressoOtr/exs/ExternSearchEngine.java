package org.espressoOtr.exs;

import org.apache.log4j.PropertyConfigurator;
import org.espressoOtr.exs.server.ExsServer; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class
 * 
 * @author AhnSeongHyun(sh84.ahn@gmail.com) Can select Server mode or Command
 *         mode.
 * 
 */
public class ExternSearchEngine
{
    static ExsMode exsMode = ExsMode.NONE;
    
    static ExsServer exsServer = new ExsServer();
    
    static Logger logger = LoggerFactory.getLogger(ExternSearchEngine.class);
    
    public static void main(String[] args) throws Exception
    {
        PropertyConfigurator.configure("./lib/log4j.propertise");
        getExsMode(args);
        
        if (exsMode == ExsMode.SERVER)
            exsServer.run();
        else
            showUsage();
    }
    
    public static void showUsage()
    {
        logger.info("SERVER MODE : java -jar exs.jar -server");
    }
    
    public static void getExsMode(String[] args)
    {
        if (args.length == 1)
        {
            if (args[0].equals("-server"))
                exsMode = ExsMode.SERVER;
            
            else
                exsMode = ExsMode.NONE;
            
        }
        else
        {
            exsMode = ExsMode.NONE;
        }
    }
}
