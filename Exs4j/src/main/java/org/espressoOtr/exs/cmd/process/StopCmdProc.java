package org.espressoOtr.exs.cmd.process;

import org.apache.ibatis.session.SqlSession;
import org.espressoOtr.exs.cmd.Command;
import org.espressoOtr.exs.mngserver.ExsMngServer;
import org.espressoOtr.exs.server.ExsServer;
import org.espressoOtr.exs.sql.SqlSessionClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StopCmdProc implements CommandProcessor
{
    Logger logger = LoggerFactory.getLogger(StopCmdProc.class);
      
    @Override
    public void process(Command cmd)
    {
        
        ExsServer.getInstance().stop();
        ExsMngServer.getInstance().stop();
        SqlSession session = SqlSessionClient.getSqlSession();
        session.close();
        
    } 
}
