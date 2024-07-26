package server.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import server.Server;

public class ServerListenerThread extends Thread
{
	private int port;
	private String webroot;
	private ServerSocket ss;
	
	private final static Logger log=LoggerFactory.getLogger(ServerListenerThread.class);
	
	public ServerListenerThread(int port, String webroot) throws IOException
	{
		this.port=port;
		this.webroot=webroot;
		this.ss=new ServerSocket(this.port);
	}
	
	@Override
	public void run()
	{
		try
    	{
			while(ss.isBound() && !ss.isClosed())
			{
				Socket socket=ss.accept();
	    		
	    		log.info(" * Connection accepted: "+socket.getInetAddress());
	    		
	    		HttpConnectionWorkerThread workerThread=new HttpConnectionWorkerThread(socket);
	    		workerThread.start();
			}
    	}catch(IOException ex)
    	{
    		log.error("Problem with setting socket", ex);
    	}finally
    	{
    		if(ss!=null)
    		{
    			try {
					ss.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
	}
}
