package vn.vnpay.until;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GsonCustom {
    private static final Logger LOG = LogManager.getLogger(GsonCustom.class);
    private static Gson gson;

    private GsonCustom() {
    }

    public static Gson getGsonBuilder() {
        if (gson == null) {
            synchronized (GsonCustom.class) {
                if (null == gson) {
                    gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
                    LOG.info("Create GsonBuilder success");
                }
            }
        }
        return gson;
    }
}
