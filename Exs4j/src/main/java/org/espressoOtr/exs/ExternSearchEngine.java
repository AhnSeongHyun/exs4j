package org.espressoOtr.exs;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.PropertyConfigurator;
import org.espressoOtr.exs.cmd.Command;
import org.espressoOtr.exs.cmd.CommandProcGroup;
import org.espressoOtr.exs.cmd.CommandProcessor;
import org.espressoOtr.exs.common.Properties;
import org.espressoOtr.exs.conf.ConfigurationReader;
import org.espressoOtr.exs.messageq.MessageQueue;
import org.espressoOtr.exs.mngserver.ExsMngServer;
import org.espressoOtr.exs.server.ExsServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExternSearchEngine
{
    static ExsMode exsMode = ExsMode.NONE;
    
    static Logger logger = LoggerFactory.getLogger(ExternSearchEngine.class);
    
    public static void main(String[] args) throws Exception
    {
        setConfig(args);
        getExsMode(args);
        
        MessageQueue msgQ = MessageQueue.getInstance();
        CommandProcGroup cmdProcGroup = CommandProcGroup.getInstance();
        
        ExsServer exsServer = ExsServer.getInstance();
        ExsMngServer exsMngServer = ExsMngServer.getInstance();
        
        
        if (exsMode == ExsMode.SERVER_START)
        {
            exsServer.start();
            exsMngServer.start();
            
            CommandProcessor cmdProc = null;
            
            for (;;)
            {
                if (msgQ.size() > 0)
                {
                    Command cmd = msgQ.get();
                    logger.info("process cmd :{}", cmd);
                    
                    if (cmd != Command.NONE)
                    {                        
                        cmdProc = cmdProcGroup.getProc(cmd);
                        cmdProc.process();
                    }
                    
                    if(cmd == Command.STOP)
                    {
                        break;
                    }
                }
                
                Thread.sleep(100);
            }
        }
        else
        {
            showUsage();
        }
        
        logger.info("Exs4j Shutdown..");
    }
    
    private static void setConfig(String[] args)
    {
        String confFilePath = getConfFilePath(args);
        Map<String, String> confKv = ConfigurationReader.settingConfigurations(confFilePath);
        
        PropertyConfigurator.configure(System.getProperty(Properties.LOG4J_PROPERTIES));
        
        Set<String> confKeySet = confKv.keySet();
        
        for (String key : confKeySet)
        {
            logger.info("{}:{}", key, confKv.get(key));
        }
    }
    
    private static String getConfFilePath(String[] args)
    {
        String confFilePath = null;
        if (args.length == 2)
        {
            confFilePath = args[1];
        }
        return confFilePath;
    }
    
    public static void showUsage()
    {
        logger.info("Exs4j : java -jar Exs4j-1.1.0-RELEASE.jar -server [config file path]");
    }
    
    public static void getExsMode(String[] args)
    {
        if (args.length >= 1)
        {
            if (args[0].equals("-server"))
                exsMode = ExsMode.SERVER_START;
            
            else
                exsMode = ExsMode.NONE;
        }
        else
            exsMode = ExsMode.NONE;
        
    }
}
