package vn.vnpay.service;

import org.springframework.stereotype.Service;
import vn.vnpay.bean.ResponseCustom;
import vn.vnpay.bean.UserRequest;
import vn.vnpay.bean.User;
import vn.vnpay.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    public static List<User> listUser = new ArrayList<>();

    public List<User> findAll() {
        return listUser;
    }


    public User getUserByUsername(String username) {
        return UserDao.getInstance().getUserByUsername(username);
    }

    public ResponseCustom checkLogin(UserRequest user) {
        return UserDao.getInstance().checkLogin(user);
    }

}
