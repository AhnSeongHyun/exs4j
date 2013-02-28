package org.espressoOtr.exs.sql;

import org.apache.ibatis.session.SqlSession;
import org.espressoOtr.exs.sql.param.RequestRecord;

public class SqlRequsetTable
{
    
    public static void insert(RequestRecord requestRecord)
    {
        SqlSession session = SqlSessionClient.getSqlSession();
        session.insert("tb_request_insertion.insertRequestRecord", requestRecord);
        session.commit();
         
    }
}
