package org.espressoOtr.exs.sql;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.espressoOtr.exs.sql.param.RequestRecord;

public class SqlRequestTable
{
    
    public static void insert(RequestRecord requestRecord)
    {
        SqlSession session = SqlSessionClient.getSqlSession();
        session.insert("tb_request_insertion.insertRequestRecord", requestRecord);
        session.commit();
        
    }
    
    public static List<RequestRecord> select(String requestCode)
    {
        SqlSession session = SqlSessionClient.getSqlSession();
        List<RequestRecord> result = session.selectList("tb_request_selection.selectRequestRecord", requestCode);
        
        return result;
    }
}
