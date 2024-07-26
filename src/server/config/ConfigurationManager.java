package server.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import server.util.JSON;


public class ConfigurationManager 
{
	private static ConfigurationManager myConfigurationManager;
	private static Configuration myCurrentConfiguration;
	
	private ConfigurationManager()
	{
		
	}
	
	public static ConfigurationManager getInstance()
	{
		if(myConfigurationManager==null)
			myConfigurationManager=new ConfigurationManager();
			
			return myConfigurationManager;
	}
	
	public void loadConfigurationFile(String filePath)
	{
		FileReader fileReader=null;
		
		try {
			fileReader=new FileReader(filePath);
		}catch(FileNotFoundException ex)
		{
			throw new HttpConfigurationException(ex);
		}
		StringBuffer sb=new StringBuffer();
		
		int i;
		try
		{
			while((i=fileReader.read())!=-1)
			{
				sb.append((char)i);
			}
		}catch(IOException ex)
		{
			throw new HttpConfigurationException(ex);
		}
		
		JsonNode conf=null;
		try {
			conf=JSON.parse(sb.toString());
		}catch(IOException ex)
		{
			throw new HttpConfigurationException("Error parsing the Configuration File", ex);
		}
		
		try {
			myCurrentConfiguration=JSON.fromJson(conf, Configuration.class);
		}catch(JsonProcessingException ex)
		{
			throw new HttpConfigurationException("Error parsing the configuration file, internal", ex);
		}
	}
	
	public Configuration getCurrentConfiguration()
	{
		if(myCurrentConfiguration==null)
			throw new HttpConfigurationException("No Current Configuration Set");
	
		return myCurrentConfiguration;
	}
}
