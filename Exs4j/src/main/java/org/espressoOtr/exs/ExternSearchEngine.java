package org.espressoOtr.exs;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.PropertyConfigurator;
import org.espressoOtr.exs.cmd.Command;
import org.espressoOtr.exs.cmd.CommandType;
import org.espressoOtr.exs.cmd.process.CommandProcGroup;
import org.espressoOtr.exs.cmd.process.CommandProcessor;
import org.espressoOtr.exs.common.Properties;
import org.espressoOtr.exs.conf.ConfigurationReader;
import org.espressoOtr.exs.index.Barista;
import org.espressoOtr.exs.messageq.MessageQueue;
import org.espressoOtr.exs.mngserver.ExsMngServer;
import org.espressoOtr.exs.server.ExsServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExternSearchEngine
{
    
    static Logger logger = LoggerFactory.getLogger(ExternSearchEngine.class);
    
    public static void main(String[] args) throws Exception
    {
        setConfig(args);
        if (!isValidatedArgs(args))
        {
            showUsage();
            return;
        }
        
        Barista barista = Barista.getInstance();
        MessageQueue msgQ = MessageQueue.getInstance();
        CommandProcGroup cmdProcGroup = CommandProcGroup.getInstance();
        
        ExsServer exsServer = ExsServer.getInstance();
        ExsMngServer exsMngServer = ExsMngServer.getInstance();
        
        exsServer.start();
        exsMngServer.start();
        
        
        if(barista.getValidFileList().size()>0)
        { 
            msgQ.add("LOAD");
        }
        
        CommandProcessor cmdProc = null;
        
        for (;;)
        {
            if (msgQ.size() > 0)
            {
                Command cmd = msgQ.get();
                logger.info("{}", cmd.toString());
                
                if (cmd.getCmdType() != CommandType.NONE)
                {
                    cmdProc = cmdProcGroup.getProc(cmd.getCmdType());
                    cmdProc.process(cmd);
                }
                
                if (cmd.getCmdType() == CommandType.STOP)
                {
                    break;
                }
            }
            
            Thread.sleep(100);
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
    
    public static boolean isValidatedArgs(String[] args)
    {
        boolean isValidatedArgs = true;
        if (args.length == 2)
        {
            if (args[0].equals("-server"))
                isValidatedArgs = true;
            
            else
                isValidatedArgs = false;
        }
        else
        {
            isValidatedArgs = false;
        }
        
        return isValidatedArgs;
        
    }
}
