package vn.vnpay.until;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Property {
    private static final Logger LOG = LogManager.getLogger(Property.class);

    private static Properties properties;

    private Property() {
    }

    public static Properties getProperties() {
        if (null == properties) {
            properties = new Properties();
            try {
                properties.load(new FileInputStream("D:/ProjectRemote/JAVA/RECONCILE/SMSBrandName/src/main/resources/dev.properties"));
                LOG.info("Load file ./config/dev.properties success");
            } catch (IOException e) {
                LOG.error("Error load file ./config/dev.properties: ", e);
            }
        }
        return properties;
    }
}
