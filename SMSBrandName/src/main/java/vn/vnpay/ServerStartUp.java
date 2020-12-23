package vn.vnpay;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vn.vnpay.connect.ConnectDBPostgree;
import vn.vnpay.until.Property;

@SpringBootApplication
public class ServerStartUp implements DisposableBean, CommandLineRunner {

    private static final Logger LOG = LogManager.getLogger(ServerStartUp.class);

    public static void main(String[] args) {
        try {
            SpringApplication application = new SpringApplication(ServerStartUp.class);
            application.setDefaultProperties(Property.getProperties());
            application.run(args);
        } catch (Exception e) {
            LOG.error("Error start server ", e);
        }
    }
    @Override
    public void run(String... args) {
        ConnectDBPostgree.getDataSource();
    }

    @Override
    public void destroy(){

    }
}

