package vn.vnpay.connect;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.vnpay.until.Property;

import java.util.Properties;

public class ConnectDBPostgree {
    private static final Logger LOG = LogManager.getLogger(ConnectDBPostgree.class);

    private static final String DB_USERNAME = "db_username";
    private static final String DB_PASSWORD = "db_password";
    private static final String DB_URL = "db_url";
    private static final String DB_CLASS = "db_class";
    private static final String CACHE_PREP_STMTS = "cache_prep_stmts";
    private static final String PREP_STMT_CACHE_SIZE = "prep_stmt_cache_size";
    private static final String PREP_STMT_CACHE_SQL_LIMIT = "prep_stmt_cache_sql_limit";
    private static final String MAXIMUM_POOL_SIZE = "maximum_pool_size";
    private static final String MINIMUM_IDLE = "minimum_idle";
    private static final long TIME_OUT = 2 * 60 * 1000;

    private static HikariDataSource ds;

    public static HikariDataSource getDataSource() {
        if (ds == null) {
            try {
                LOG.info("Begin create HikariDataSource");
                final HikariConfig jdbcConfig = new HikariConfig();
                final Properties properties = Property.getProperties();
                jdbcConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty(MAXIMUM_POOL_SIZE)));
                jdbcConfig.setMinimumIdle(Integer.parseInt(properties.getProperty(MINIMUM_IDLE)));
                jdbcConfig.setJdbcUrl(properties.getProperty(DB_URL));
                jdbcConfig.setUsername(properties.getProperty(DB_USERNAME));
                jdbcConfig.setPassword(properties.getProperty(DB_PASSWORD));
                jdbcConfig.setDriverClassName(properties.getProperty(DB_CLASS));
                jdbcConfig.setConnectionTimeout(TIME_OUT);
                jdbcConfig.addDataSourceProperty("cachePrepStmts", Boolean.parseBoolean(properties.getProperty(CACHE_PREP_STMTS)));
                jdbcConfig.addDataSourceProperty("prepStmtCacheSize", Integer.parseInt(properties.getProperty(PREP_STMT_CACHE_SIZE)));
                jdbcConfig.addDataSourceProperty("prepStmtCacheSqlLimit", Integer.parseInt(properties.getProperty(PREP_STMT_CACHE_SQL_LIMIT)));
                LOG.info(" Db url: {} db user name: {} db password: {}", jdbcConfig.getJdbcUrl(), jdbcConfig.getUsername(), jdbcConfig.getPassword());
                ds = new HikariDataSource(jdbcConfig);
                LOG.info("End create HikariDataSource");
            } catch (Exception e) {
                LOG.error("Error create HikariDataSource: ", e);
                return null;
            }
        }
        return ds;
    }

    public static void close() {
        if (ds != null) {
            ds.close();
        }
    }
}
