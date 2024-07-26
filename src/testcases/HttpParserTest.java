package testcases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import http.HttpMethod;
import http.HttpParser;
import http.HttpParsingException;
import http.HttpRequest;
import http.HttpStatusCode;
import http.HttpVersion;

@TestInstance(Lifecycle.PER_CLASS)
class HttpParserTest 
{
	private HttpParser httpParser;
	
	@BeforeAll
	public void beforeClass()
	{
		httpParser=new HttpParser();
	}
	
	@Test
	void testParseHttpRequest() 
	{
		HttpRequest request=null;
		try {
			request = httpParser.parseHttpRequest(generateValidGETTestCase());
		} catch (HttpParsingException e) {
			// TODO Auto-generated catch block
			fail(e);
		}
	
		assertNotNull(request);
		assertEquals(request.getRequestTarget(), "/");
		assertEquals(request.getMethod(), HttpMethod.GET);
		assertEquals(request.getBestCompatibleHttpVersion(), HttpVersion.HTTP_1_1);
		assertEquals(request.getOriginalHttpVersion(), "HTTP/1.1");
	}
	
	@Test
	void testSupportedHttpVersion() 
	{
		HttpRequest request=null;
		try {
			request = httpParser.parseHttpRequest(generateSupportedHttpVersionTestCase());
		} catch (HttpParsingException e) {
			// TODO Auto-generated catch block
			fail(e);
		}
	
		assertNotNull(request);
		assertEquals(request.getBestCompatibleHttpVersion(), HttpVersion.HTTP_1_1);
		assertEquals(request.getOriginalHttpVersion(), "HTTP/1.2");
	}
	
	@Test
	void testHttpRequestBadMethod1() 
	{
		HttpRequest request=null;
		try {
			request=httpParser.parseHttpRequest(generateBadTestCase1());
			fail();
		} catch (HttpParsingException e) {
			// TODO Auto-generated catch block
			assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
		}
	
		//assertEquals(request.getMethod(), HttpMethod.GET);
	}
	
	@Test
	void testHttpRequestBadMethod2() 
	{
		HttpRequest request=null;
		try {
			request=httpParser.parseHttpRequest(generateBadTestCase2());
			fail();
		} catch (HttpParsingException e) {
			// TODO Auto-generated catch block
			assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
		}
	
		//assertEquals(request.getMethod(), HttpMethod.GET);
	}
	
	@Test
	void testHttpRequestBadMethod3() 
	{
		HttpRequest request=null;
		try {
			request=httpParser.parseHttpRequest(generateBadTestCase3());
			fail();
		} catch (HttpParsingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			assertEquals(e.getErrorCode(), HttpStatusCode.Client_ERROR_400_BAD_REQUEST);
		}
	
		//assertEquals(request.getMethod(), HttpMethod.GET);
	}
	
	@Test
	void testHttpRequestBadMethod4() 
	{
		HttpRequest request=null;
		try {
			request=httpParser.parseHttpRequest(generateBadTestCase4());
			fail();
		} catch (HttpParsingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			assertEquals(e.getErrorCode(), HttpStatusCode.Client_ERROR_400_BAD_REQUEST);
		}
	
		//assertEquals(request.getMethod(), HttpMethod.GET);
	}
	
	@Test
	void testHttpRequestBadMethod5() 
	{
		HttpRequest request=null;
		try {
			request=httpParser.parseHttpRequest(generateBadTestCase5());
			fail();
		} catch (HttpParsingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			assertEquals(e.getErrorCode(), HttpStatusCode.Client_ERROR_400_BAD_REQUEST);
		}
	
		//assertEquals(request.getMethod(), HttpMethod.GET);
	}
	
	@Test
	void testHttpRequestBadMethod6() 
	{
		HttpRequest request=null;
		try {
			request = httpParser.parseHttpRequest(generateBadTestCase6());
			fail();
		} catch (HttpParsingException e) {
			// TODO Auto-generated catch block
			assertEquals(e.getErrorCode(), HttpStatusCode.Client_ERROR_400_BAD_REQUEST);
		}
	
		//assertNotNull(request);
		//assertEquals(request.getRequestTarget(), "/");
		//assertEquals(request.getMethod(), HttpMethod.GET);
	}
	
	@Test
	void testHttpRequestBadMethod7() 
	{
		HttpRequest request=null;
		try {
			request = httpParser.parseHttpRequest(generateBadTestCase7());
			fail();
		} catch (HttpParsingException e) {
			// TODO Auto-generated catch block
			assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED);
		}
	
		//assertNotNull(request);
		//assertEquals(request.getRequestTarget(), "/");
		//assertEquals(request.getMethod(), HttpMethod.GET);
	}
	
	private InputStream generateValidGETTestCase()
	{
		String rawData="GET / HTTP/1.1\r\n"
				+ "Host: localhost:8080\r\n"
				+ "Connection: keep-alive\r\n"
				+ "Cache-Control: max-age=0\r\n"
				+ "sec-ch-ua: \"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Microsoft Edge\";v=\"126\"\r\n"
				+ "sec-ch-ua-mobile: ?0\r\n"
				+ "sec-ch-ua-platform: \"Windows\"\r\n"
				+ "Upgrade-Insecure-Requests: 1\r\n"
				+ "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36 Edg/126.0.0.0\r\n"
				+ "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r\n"
				+ "Sec-Fetch-Site: cross-site\r\n"
				+ "Sec-Fetch-Mode: navigate\r\n"
				+ "Sec-Fetch-User: ?1\r\n"
				+ "Sec-Fetch-Dest: document\r\n"
				+ "Accept-Encoding: gzip, deflate, br, zstd\r\n"
				+ "Accept-Language: en-US,en;q=0.9\r\n"
				+ "\r\n";
		
		InputStream is=new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
		
		return is;
	}
	
	private InputStream generateSupportedHttpVersionTestCase()
	{
		String rawData="GET / HTTP/1.2\r\n"
				+ "Host: localhost:8080\r\n"
				+ "Connection: keep-alive\r\n"
				+ "Cache-Control: max-age=0\r\n"
				+ "sec-ch-ua: \"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Microsoft Edge\";v=\"126\"\r\n"
				+ "sec-ch-ua-mobile: ?0\r\n"
				+ "sec-ch-ua-platform: \"Windows\"\r\n"
				+ "Upgrade-Insecure-Requests: 1\r\n"
				+ "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36 Edg/126.0.0.0\r\n"
				+ "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r\n"
				+ "Sec-Fetch-Site: cross-site\r\n"
				+ "Sec-Fetch-Mode: navigate\r\n"
				+ "Sec-Fetch-User: ?1\r\n"
				+ "Sec-Fetch-Dest: document\r\n"
				+ "Accept-Encoding: gzip, deflate, br, zstd\r\n"
				+ "Accept-Language: en-US,en;q=0.9\r\n"
				+ "\r\n";
		
		InputStream is=new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
		
		return is;
	}
	
	//Invalid name
	private InputStream generateBadTestCase1()
	{
		String rawData="GeT / HTTP/1.1\r\n"
				+ "Host: localhost:8080\r\n"
				+ "Accept-Language: en-US,en;q=0.9\r\n"
				+ "\r\n";
		
		InputStream is=new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
		
		return is;
	}
	
	//Invalid length of name
	private InputStream generateBadTestCase2()
	{
		String rawData="GETTTTT / HTTP/1.1\r\n"
				+ "Host: localhost:8080\r\n"
				+ "Accept-Language: en-US,en;q=0.9\r\n"
				+ "\r\n";
		
		InputStream is=new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
		
		return is;
	}
	
	//Invalid number of items
	private InputStream generateBadTestCase3()
	{
		String rawData="GET / AAAAA HTTP/1.1\r\n"
				+ "Host: localhost:8080\r\n"
				+ "Accept-Language: en-US,en;q=0.9\r\n"
				+ "\r\n";
		
		InputStream is=new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
		
		return is;
	}
	
	//Empty request line
	private InputStream generateBadTestCase4()
	{
		String rawData="\r\n"
				+ "Host: localhost:8080\r\n"
				+ "Accept-Language: en-US,en;q=0.9\r\n"
				+ "\r\n";
			
		InputStream is=new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
			
		return is;
	}
	
	//Only Carriage Return. No Line Feed
	private InputStream generateBadTestCase5()
	{
		String rawData="GET / HTTP/1.1\r"
				+ "Host: localhost:8080\r\n"
				+ "Accept-Language: en-US,en;q=0.9\r\n"
				+ "\r\n";
				
		InputStream is=new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
				
		return is;
	}

	//Bad HTTP Version request
	private InputStream generateBadTestCase6()
	{
		String rawData="GET / HTP/1.1\r\n"
				+ "Host: localhost:8080\r\n"
				+ "Connection: keep-alive\r\n"
				+ "Cache-Control: max-age=0\r\n"
				+ "sec-ch-ua: \"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Microsoft Edge\";v=\"126\"\r\n"
				+ "sec-ch-ua-mobile: ?0\r\n"
				+ "sec-ch-ua-platform: \"Windows\"\r\n"
				+ "Upgrade-Insecure-Requests: 1\r\n"
				+ "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36 Edg/126.0.0.0\r\n"
				+ "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r\n"
				+ "Sec-Fetch-Site: cross-site\r\n"
				+ "Sec-Fetch-Mode: navigate\r\n"
				+ "Sec-Fetch-User: ?1\r\n"
				+ "Sec-Fetch-Dest: document\r\n"
				+ "Accept-Encoding: gzip, deflate, br, zstd\r\n"
				+ "Accept-Language: en-US,en;q=0.9\r\n"
				+ "\r\n";
					
		InputStream is=new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
					
		return is;
	}
	
	//Bad HTTP Version request
	private InputStream generateBadTestCase7()
	{
		String rawData="GET / HTTP/2.1\r\n"
				+ "Host: localhost:8080\r\n"
				+ "Connection: keep-alive\r\n"
				+ "Cache-Control: max-age=0\r\n"
				+ "sec-ch-ua: \"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Microsoft Edge\";v=\"126\"\r\n"
				+ "sec-ch-ua-mobile: ?0\r\n"
				+ "sec-ch-ua-platform: \"Windows\"\r\n"
				+ "Upgrade-Insecure-Requests: 1\r\n"
				+ "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36 Edg/126.0.0.0\r\n"
				+ "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r\n"
				+ "Sec-Fetch-Site: cross-site\r\n"
				+ "Sec-Fetch-Mode: navigate\r\n"
				+ "Sec-Fetch-User: ?1\r\n"
				+ "Sec-Fetch-Dest: document\r\n"
				+ "Accept-Encoding: gzip, deflate, br, zstd\r\n"
				+ "Accept-Language: en-US,en;q=0.9\r\n"
				+ "\r\n";
						
		InputStream is=new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
						
		return is;
	}
}
