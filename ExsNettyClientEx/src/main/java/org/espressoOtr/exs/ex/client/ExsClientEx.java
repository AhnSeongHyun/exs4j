package org.espressoOtr.exs.ex.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.Executors;

import org.apache.log4j.PropertyConfigurator;
import org.espressoOtr.exs.ex.data.ExsRequestParam;
import org.jboss.netty.bootstrap.ClientBootstrap; 
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture; 
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

 

public class ExsClientEx
{
    
    final static char CR = '\r';
    
    final static char LF = '\n';
    
    final static String domain = "NAVER.BLOG";
    final static int outputCount = 3;
    final static int pageNo = 1; 
    
    
    public static void main(String[] args) throws IOException, InterruptedException
    {
        PropertyConfigurator.configure("./lib/log4j.propertise");
        
        int port = 6750;
        
        ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
        
        ClientBootstrap bootstrap = new ClientBootstrap(factory);
        
        bootstrap.setPipelineFactory(new JsonPipelineFactory());
        
        bootstrap.setOption("tcpNoDelay", true);
        bootstrap.setOption("keepAlive", true);
        
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost", port));
        
        // 아래 부터는 connection 끊어 졌을 때를 위한 처리
        Channel channel = future.awaitUninterruptibly().getChannel();
        
        if (!future.isSuccess())
        {
            future.getCause().printStackTrace();
            
            factory.releaseExternalResources();// 자원회수
        }
        
        Scanner input = new Scanner(System.in);
        for (;;)
        {
            System.out.print(">");
            String keyword = input.next();
            
            if (keyword.equals("q"))
            {
                channel.close();
                break;
            }
            else
            {
                ExsRequestParam exsReqParam = new ExsRequestParam();
                
                exsReqParam.setDomain(domain);
                exsReqParam.setKeyword(keyword);
                exsReqParam.setOutputCount(outputCount);
                exsReqParam.setPageNo(pageNo);
                
                channel.write(exsReqParam);
            }
        }
        
        // connection close.
        future.getChannel().getCloseFuture().awaitUninterruptibly();
        bootstrap.releaseExternalResources();
        
        return;
        
    }
   
    
 
}
