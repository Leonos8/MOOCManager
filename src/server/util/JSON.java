package server.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JSON 
{
	private static ObjectMapper myObjectMapper=defaultObjectMapper();
	
	private static ObjectMapper defaultObjectMapper()
	{
		ObjectMapper om=new ObjectMapper();
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		return om;		
	}
	
	public static JsonNode parse(String jsonSrc) throws IOException
	{
		return myObjectMapper.readTree(jsonSrc);
	}
	
	public static <A> A fromJson(JsonNode node, Class<A> cls) throws JsonProcessingException
	{
		return myObjectMapper.treeToValue(node, cls);
	}
	
	public static JsonNode toJson(Object obj)
	{
		return myObjectMapper.valueToTree(obj);
	}
	
	public static String stringify(JsonNode node) throws JsonProcessingException
	{
		return generateJson(node, false);
	}
	
	public static String stringifyPretty(JsonNode node) throws JsonProcessingException
	{
		return generateJson(node, true);
	}
	
	private static String generateJson(Object o, boolean pretty) throws JsonProcessingException
	{
		ObjectWriter ow=myObjectMapper.writer();
		
		if(pretty)
			ow=ow.with(SerializationFeature.INDENT_OUTPUT);
		
		return ow.writeValueAsString(o);
	}
}
