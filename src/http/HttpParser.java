package http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpParser 
{
	private final static Logger log=LoggerFactory.getLogger(HttpParser.class);
	
	private static final int SP=0x20;
	private static final int CR=0x0D;
	private static final int LF=0x0A;
	
	public HttpRequest parseHttpRequest(InputStream is) throws HttpParsingException
	{
		InputStreamReader reader=new InputStreamReader(is, StandardCharsets.US_ASCII);
		
		HttpRequest request=new HttpRequest();
		
		try {
			parseRequestLine(reader, request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parseHeaders(reader, request);
		parseBody(reader, request);
		
		return request;
	}

	private void parseRequestLine(InputStreamReader reader, HttpRequest request) 
			throws IOException, HttpParsingException 
	{
		StringBuilder processingDataBuffer=new StringBuilder();
		int _byte;
		
		boolean methodParsed=false;
		boolean requestTargetParsed=false;
		
		while((_byte=reader.read())>=0)
		{
			if(_byte==CR)
			{
				_byte=reader.read();
				if(_byte==LF)
				{
					if(!methodParsed || !requestTargetParsed)
					{
						throw  new HttpParsingException(HttpStatusCode.Client_ERROR_400_BAD_REQUEST);
					}
					
					try {
						request.setHttpVersion(processingDataBuffer.toString());
					} catch (BadHttpVersionException e) {
						// TODO Auto-generated catch block
						throw new HttpParsingException(HttpStatusCode.Client_ERROR_400_BAD_REQUEST);
					}
					
					return;
				}
				else
				{
					throw new HttpParsingException(HttpStatusCode.Client_ERROR_400_BAD_REQUEST);
				}
			}
			
			if(_byte==SP)
			{
				if(!methodParsed)
				{
					request.setMethod(processingDataBuffer.toString());
					methodParsed=true;
				}
				else if(!requestTargetParsed)
				{
					request.setRequestTarget(processingDataBuffer.toString());
					requestTargetParsed=true;
				}
				else
				{
					throw new HttpParsingException(HttpStatusCode.Client_ERROR_400_BAD_REQUEST);
				}
				processingDataBuffer.delete(0, processingDataBuffer.length());
			}
			else
			{
				processingDataBuffer.append((char)_byte);
				
				if(!methodParsed)
				{
					if(processingDataBuffer.length()>HttpMethod.MAX_LENGTH)
					{
						throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
					}
				}
			}
		}
	}
	
	private void parseHeaders(InputStreamReader ireaders, HttpRequest request) 
	{
		
	}
	
	private void parseBody(InputStreamReader reader, HttpRequest request) 
	{
		
	}
}