package org.espressoOtr.exs.sql;

import java.util.List;

import org.apache.ibatis.session.SqlSession; 
import org.espressoOtr.exs.sql.param.SearchResultRecord;

public class SqlSearchResultTable
{
    
    public static void insert(SearchResultRecord searchResultRecord)
    {
        
        SqlSession session = SqlSessionClient.getSqlSession();
             session.insert("tb_search_result_insertion.insertSearchResult", searchResultRecord);
        session.commit();
         
    }
    
    public static List<SearchResultRecord> select(String requestCode)
    {
        SqlSession session = SqlSessionClient.getSqlSession();
        List<SearchResultRecord> result = session.selectList("tb_search_result_selection.selectSearchResult", requestCode);
        
        return result;
    } 
}