package server;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import server.config.Configuration;
import server.config.ConfigurationManager;
import server.core.ServerListenerThread;

public class Server 
{
	public static final File currDir=new File(".");
	public static final String absolutePath=currDir.getAbsolutePath();
	public static final String path=absolutePath.substring(0, absolutePath.length()-2);	
	public static final String resPath=path+File.separator+"src"+File.separator+"server"
			+File.separator+"resources"+File.separator;
	
	private final static Logger log=LoggerFactory.getLogger(Server.class); //TODO Will probably replace soon
	
    public static void main(String[] args)
    {
    	System.out.println("Server Starting...");
    	
    	ConfigurationManager.getInstance().loadConfigurationFile(resPath+"http.json");
    	Configuration conf=ConfigurationManager.getInstance().getCurrentConfiguration();
    	
    	System.out.println("Using Port: "+conf.getPort());
    	System.out.println("Using Webroot: "+conf.getWebroot());
    	log.info("Using Port: "+conf.getPort());
    	log.info("Using Webroot: "+conf.getWebroot());
    	
    	try {
        	ServerListenerThread slt=new ServerListenerThread(conf.getPort(), conf.getWebroot());
        	slt.start();
    	}catch(IOException ex)
    	{
    		ex.printStackTrace();
    	}
    }
    
    public void FileHandler() //TMP
    {
    	
    }
}