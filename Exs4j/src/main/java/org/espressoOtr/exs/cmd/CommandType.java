package org.espressoOtr.exs.cmd;

public enum CommandType
{
    NONE,  // UNKNOWN COMMAND
    STOP,  // exs4j stop.
    STORE, // store searched reuslt db and Barista index.
    SAVE,  // save Barista index to file.
    LOAD   // Load Barista index from file.
}
