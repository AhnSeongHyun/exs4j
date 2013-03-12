package org.espressoOtr.exs.cmd.process;


import org.espressoOtr.exs.cmd.Command;
import org.espressoOtr.exs.index.Barista;

public class SaveCmdProc implements CommandProcessor
{
    
    @Override
    public void process(Command cmd)
    {
        Barista barista = Barista.getInstance(); 
        barista.save(); 
    }
    
}