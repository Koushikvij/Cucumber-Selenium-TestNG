package dataHandlers;

import org.testng.Assert;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigProperties
{
    private static String filepath = System.getProperty("user.dir") + File.separator + "src" + File.separator+ "test" + File.separator + "resources"
            + File.separator + "config" + File.separator + "global.properties";

    private static Map<String, String> PropertiesMap = new HashMap<>();
    private static File Propfile;
    private static Properties prop;

    public ConfigProperties(String fileName)
    {
        try
        {
            this.filepath = fileName ;
            prop = new Properties();
            Propfile = new File(fileName);
            FileInputStream propFile = new FileInputStream(Propfile);
            prop.load(propFile);

            for(Map.Entry<Object, Object> entry : prop.entrySet())
            {
                String key = entry.getKey().toString();
                String value = entry.getValue().toString();
                PropertiesMap.put(key, value);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
            Assert.fail("Exception occured while trying to read properties from properties file.", e);
        }
	}

    /**
     * Method to get the properties in as a Map
     *
     * @return Map<String, String>
     */
    public synchronized Map<String, String> getPropertiesMap()
    {
        return PropertiesMap;
    }


    /**
     * Method to get the property value
     *
     * @param: String key
     * @return String property value
     */
    public static synchronized String getPropertyValue(String key)
    {
        new ConfigProperties(filepath);
        return PropertiesMap.get(key);
    }

    /**
     * Method to check the property
     *
     * @param: String key
     * @return boolean property present or not
     */
    public synchronized boolean containsProperty(String key)
    {
        return prop.containsKey(key);
    }

    /**
     *
     * @param environment
     * @param client
     * @param type
     * @return
     */

    /**
     * Method to set property
     *
     * @param: String key, String value
     */
    public synchronized void setProperty(String key, String value)
    {
        OutputStream output = null;
        key = key.toLowerCase();
        try
        {
            output = new FileOutputStream(Propfile);
            if(prop.containsKey(key))
            {
                prop.remove(key);
            }
            prop.setProperty(key, value);
            prop.store(output, null);
        }
        catch(IOException io)
        {
            throw new RuntimeException("Exception occurred while trying to write into properties file.", io);
        }
        finally
        {
            if(output != null)
            {
                try
                {
                    output.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                    Assert.fail(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * Method to set properties
     *
     * @param: Map<Object, Object>
     */
    public synchronized void setProperties(Map<Object, Object> map)
    {
        for(Map.Entry<Object, Object> entry : map.entrySet())
        {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            setProperty(key, value);
        }
    }
}