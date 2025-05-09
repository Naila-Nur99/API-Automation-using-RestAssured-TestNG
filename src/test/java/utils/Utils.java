package utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.FileOutputStream;
import java.util.Properties;

public class Utils {
    public static void setEnVar(String key, String value) throws ConfigurationException {
        PropertiesConfiguration config= new PropertiesConfiguration("./src/test/resources/config.properties");
        config.setProperty(key,value);
        config.save();

    }




    public static int generateRandomNumber(int min, int max){
        double random=Math.random()*(max-min)+min;
        return (int)random;
    }

}