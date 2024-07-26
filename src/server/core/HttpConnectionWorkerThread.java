package server.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpConnectionWorkerThread extends Thread
{
	private Socket socket;
	
	private final static Logger log=LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

	
	public HttpConnectionWorkerThread(Socket socket)
	{
		this.socket=socket;
	}
	
	@Override
	public void run()
	{
		InputStream is=null;
		OutputStream os=null;
		
		try
		{
			is=socket.getInputStream();
			os=socket.getOutputStream();
			
			//StringBuilder htmlBuilder = new StringBuilder();
			//htmlBuilder.append("<html>");
			//htmlBuilder.append("<head><title>Hello World</title></head>");
			//htmlBuilder.append("<body><p>Look at my body!</p></body>");
			//htmlBuilder.append("</html>");
			//String html = htmlBuilder.toString();
			
			/*StringBuilder htmlBuilder=new StringBuilder();
			htmlBuilder.append("<html><head><title>Href Attribute Example</title>"
					+ "</head><body><h1>Href Attribute Example</h1><p>"
					+ "<a href=\"https://www.freecodecamp.org\">The freeCodeCamp Contribution Page</a> "
					+ "shows you how and where you can contribute to freeCodeCamp's community and growth."
					+ "</p></body></html>");
			String html=htmlBuilder.toString();*/
			
			StringBuilder htmlBuilder=new StringBuilder();
			htmlBuilder.append("<html><head><title>Redirecting</title><meta http-equiv=\"refresh\" "
					+ "content=\"2; url='C:/Users/covic/eclipse-workspace"
					+ "/MOOCManager/src/Website/Dashboard.html'\" /></head></html>");
			
			/*htmlBuilder.append("<html><head><title>Redirecting</title><meta http-equiv=\"refresh\" "
					+ "content=\"0; url='https://google.com'\" /></head></html>");*/
			String html=htmlBuilder.toString();
			
			//String html="<meta http-equiv=\"refresh\" content=\"2; "
					//+ "url=C:\\Users\\covic\\eclipse-workspace\\MOOCManager\\src\\Website\\Dashboard.html\">";
			
			final String CRLF="\n\r"; //13, 10
			
			String response=
					"HTTP/1.1 200 OK"+CRLF+//Status line : HTTP Version response code response mesage
					"Content-Length: "+html.getBytes().length+CRLF+//header
					CRLF+
					html+
					CRLF+CRLF;
			
			os.write(response.getBytes());
			
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			log.info("Connection processing finished");
		}catch(IOException ex)
		{
			log.error("Problem with communication", ex);
			ex.printStackTrace();
		}finally
		{
			if(is!=null)
			{
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(os!=null)
			{
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(socket!=null)
			{
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void readHTML()
	{
		
	}
}