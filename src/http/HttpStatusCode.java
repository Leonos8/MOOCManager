package http;

public enum HttpStatusCode 
{
	/* --- CLIENT ERRORS --- */
	Client_ERROR_400_BAD_REQUEST(400, "Bad  Request"),
	Client_ERROR_401_METHOD_NOT_ALLOWED(401, "Method Not Allowed"),
	Client_ERROR_414_BAD_REQUEST(414, "URI Too Long"),
	/* --- SERVER ERRORS --- */
	SERVER_ERROR_500_INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
	SERVER_ERROR_501_NOT_IMPLEMENTED(501, "Not Implemented"), 
	SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED(505, "Http Version Not Supported")
	;
	
	public final int STATUS_CODE;
	public final String MESSAGE;
	
	HttpStatusCode(int STATUS_CODE, String MESSAGE)
	{
		this.STATUS_CODE=STATUS_CODE;
		this.MESSAGE=MESSAGE;
	}
}
