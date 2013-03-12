package org.espressoOtr.exs.sql;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.espressoOtr.exs.sql.param.SearchResultRecord;

public class SqlSearchResultTable
{
    /***
     *  Insert SearchResultRecord to tb_search_result table. 
     * @param searchResultRecord
     */
    public static void insert(SearchResultRecord searchResultRecord)
    { 
        SqlSession session = SqlSessionClient.getSqlSession();
        session.insert("tb_search_result_insertion.insertSearchResult", searchResultRecord);
        session.commit();
        
    }
    
    /***
     * Select tb_search_result all fields from tb_search_result_ where requestCode. 
     * @param requestCode
     * @return List of tb_search_result all fields
     */
    public static List<SearchResultRecord> select(String requestCode)
    {
        SqlSession session = SqlSessionClient.getSqlSession();
        List<SearchResultRecord> result = session.selectList("tb_search_result_selection.selectSearchResult", requestCode);
        
        return result;
    }
}