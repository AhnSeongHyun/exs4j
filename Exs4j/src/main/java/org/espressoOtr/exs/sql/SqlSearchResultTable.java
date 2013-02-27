package org.espressoOtr.exs.sql;

import org.apache.ibatis.session.SqlSession;
import org.espressoOtr.exs.sql.param.SearchResultRecord;

public class SqlSearchResultTable
{
    
    public static void insert(SearchResultRecord searchResultRecord)
    {
        
        SqlSession session = SqlSessionClient.getSqlSession();
        
        session.insert("tb_search_result_insertion.insertSearchResult", searchResultRecord);
        session.commit();
        
        session.close();
    }
    
}