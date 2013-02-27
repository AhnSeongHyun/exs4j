package org.espressoOtr.exs.sql;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionClient
{
    private static SqlSession session;
    
    private static final String resource = "org/espressoOtr/exs/sql/myBatisConfig.xml";
    static
    {
        try
        { 
            Reader reader = Resources.getResourceAsReader(resource);
            
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
            
            session = sqlMapper.openSession();
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
    }
    
    public static SqlSession getSqlSession()
    {
        return session;
    }
    
}
