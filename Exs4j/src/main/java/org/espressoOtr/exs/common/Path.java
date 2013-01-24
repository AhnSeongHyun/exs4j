package org.espressoOtr.exs.common;

import org.espressoOtr.exs.api.bing.BingAPI;
import org.espressoOtr.exs.api.daum.DaumAPI;
import org.espressoOtr.exs.api.naver.NaverAPI;


/**
 * PATH(Utility Class)
 * @author AhnSeongHyun(sh84.ahn@gmail.com)
 */
public class Path
{
    
    private static final String KEY_FILE_DIR_ROOT_PATH = "keyfile/";
    
    private static final String REMAIN_KEY_FILE_PATH = "remain_key.txt";
    
    private static final String KEY_FILE_DIR_SUB_PATH_NAVER = "naver/";
    
    private static final String KEY_FILE_DIR_SUB_PATH_BING = "bing/";
    
    private static final String KEY_FILE_DIR_SUB_PATH_DAUM = "daum/";
     
    
    private static final String ROOT_BIN_PATH = "./resource/";
    
    private Path()
    {
       throw new AssertionError();
    }
    
    
    /**
     * get keyfile directory path. 
     * @param classType
     * @return keyfile directory path. 
     */
    public static final String getKeyFileDirPath(Object classType)
    {
        String keyFileDirPath = ROOT_BIN_PATH + Path.KEY_FILE_DIR_ROOT_PATH;
        
        if (classType.getClass() == NaverAPI.class)
        {
            keyFileDirPath += Path.KEY_FILE_DIR_SUB_PATH_NAVER;
        }
        else if (classType.getClass() == BingAPI.class)
        {
            keyFileDirPath += Path.KEY_FILE_DIR_SUB_PATH_BING;
        }
        else if (classType.getClass() == DaumAPI.class)
        {
            keyFileDirPath += Path.KEY_FILE_DIR_SUB_PATH_DAUM;
        } 
        
        return keyFileDirPath;
    }
    
    /**
     * get remain keyfile path
     * @param classType
     * @return 
     */
    public static final String getRemainKeyFilePath(Object classType)
    {
        String keyFileDirPath = getKeyFileDirPath(classType);
        return keyFileDirPath + Path.REMAIN_KEY_FILE_PATH;
    }
    
}
