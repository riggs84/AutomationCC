package selenium.Helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReaderHelper {
    public static String getValueFromFileByName(String str){
        FileInputStream fis = null;
        Properties property = new Properties();
        try {
            fis = new FileInputStream("src/test/resources/config.properties");
            property.load(fis);
            switch(str.toUpperCase()){
                case "SERVER.NAME":
                    return property.getProperty("server.name");
                case "BROWSER.NAME":
                    return property.getProperty("browser.name");
            }
            fis.close();
        } catch(IOException ex) {

        }
        return null;
    }

}
