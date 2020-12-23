package vn.vnpay.shutdown;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.vnpay.connect.ConnectDBPostgree;

public class ShutDownHook {
    private static final Logger LOG = LogManager.getLogger(ShutDownHook.class);

    public static void shutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ConnectDBPostgree.close();
            LOG.info("Closed connect postgre.");
        }));
    }
}
