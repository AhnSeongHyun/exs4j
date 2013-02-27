package org.espressoOtr.exs.test;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.espressoOtr.exs.sql.SqlSessionClient;
import org.espressoOtr.exs.sql.param.RequestRecord;
import org.espressoOtr.exs.sql.param.SearchResultRecord;
import org.junit.Test;

public class TestSqlSessionClient
{
    
    // @Test
    public void testRequestRecordInsertion()
    {
        
        SqlSession session = SqlSessionClient.getSqlSession();
        
        RequestRecord rr = new RequestRecord();
        rr.setMasterKeyword("test");
        rr.setOrigin("NAVER.BLOG");
        rr.setReqDate(new Date());
        
        int pk = session.insert("tb_request_insertion.insertRequestRecord", rr); // pk
                                                                                 // 반환
        
        System.out.println(pk);
        session.commit();
        
        session.close();
    }
    
    @Test
    public void testSearchResultInsertion()
    {
        
        SqlSession session = SqlSessionClient.getSqlSession();
        
        SearchResultRecord srr = new SearchResultRecord();
        srr.setRequestCode("1");
        srr.setDocId("120130219023244:3");
        srr.setTitle("ste");
        srr.setSnippet("ste");
        srr.setLink("ste");
        
        session.insert("tb_search_result_insertion.insertSearchResult", srr);
        session.commit();
        
        session.close();
    }
    
}
