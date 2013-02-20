package org.espressoOtr.exs.cmd;

import org.espressoOtr.exs.mngserver.ExsMngServer;
import org.espressoOtr.exs.server.ExsServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StopCmdProc implements CommandProcessor
{
    Logger logger = LoggerFactory.getLogger(StopCmdProc.class);
    
    @Override
    public void process()
    {
        
        ExsServer.getInstance().stop();
        ExsMngServer.getInstance().stop();
        
    }
    
}
