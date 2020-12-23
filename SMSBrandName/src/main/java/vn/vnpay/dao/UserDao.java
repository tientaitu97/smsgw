package vn.vnpay.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.vnpay.bean.ResponseCustom;
import vn.vnpay.bean.User;
import vn.vnpay.bean.UserRequest;
import vn.vnpay.connect.ConnectDBPostgree;
import vn.vnpay.sercutity.HashUtils;

import java.sql.*;

//FIXME
public class UserDao {

    private final static Logger LOG = LogManager.getLogger(UserDao.class);
    private final static String CHECK_LOGIN = "SELECT smsgw.check_login(?,?)";
    private final static int LOGIN_SUCCESS = 1;
    private static UserDao instance;

    public static UserDao getInstance() {
        if (instance == null) {
            synchronized (UserDao.class) {
                if (instance == null)
                    instance = new UserDao();
            }
        }
        return instance;
    }

    public ResponseCustom checkLogin(UserRequest request) {
        if (request == null) return ResponseCustom.LOGIN_FAIL;
        try (Connection connection = ConnectDBPostgree.getDataSource().getConnection()) {
            if (connection == null) {
                LOG.warn("connection not available");
                return ResponseCustom.SERVER_INTERNAL;
            }
            try (CallableStatement stmt = connection.prepareCall(CHECK_LOGIN)) {
                stmt.setString(1, request.getUsername());
                stmt.setString(2, HashUtils.sha1Base64(request.getPassword()));
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    if (rs.getInt(1) == LOGIN_SUCCESS) {
                        LOG.info("Login success with username: {}", request.getUsername());
                        return ResponseCustom.SUCCESS;
                    }
                }
            }
        } catch (SQLException exception) {
            LOG.error("Error get from database: ", exception);
            return ResponseCustom.SERVER_INTERNAL;
        }
        return ResponseCustom.LOGIN_FAIL;
    }

    public User getUserByUsername(String username) {
        if (username == null || username.isEmpty()) return null;
        try (Connection connection = ConnectDBPostgree.getDataSource().getConnection()) {
            if (connection == null) {
                LOG.warn("connection not available");
                return null;
            }
            String sql = "select u.id, u.user_name, u.pass_word, u.role_id, r.name as role_name from users u, roles r where u.role_id = r.id and u.user_name = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    User user = User.builder().build();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("user_name"));
                    user.setPassword(rs.getString("pass_word"));
                    user.setRole(rs.getString("role_name"));
                    return user;
                }
            }
        } catch (SQLException exception) {
            LOG.error("Error get from database: ", exception);
            System.out.println(exception);
        }
        return null;
    }
}
