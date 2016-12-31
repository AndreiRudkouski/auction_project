package by.rudkouski.auction.service.impl;

import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.dao.impl.UserDao;
import by.rudkouski.auction.pool.ConnectionPool;
import by.rudkouski.auction.pool.ProxyConnection;
import by.rudkouski.auction.service.IUserService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserService implements IUserService<User> {
    private static final ConnectionPool POOL = ConnectionPool.getInstance();
    private static final String ZERO = "0";
    private static final String ALGORITHM = "MD5";

    @Override
    public User logInUser(String mail, String password) {
        String md5Password = md5Convert(password);
        ProxyConnection con = null;
        User user;
        try {
            con = POOL.takeConnection();
            UserDao userDao = new UserDao(con);
            user = userDao.logInUser(mail, md5Password);
        } finally {
            POOL.returnConnection(con);
        }
        return user;
    }

    @Override
    public User registerUser(String mail, String password) {
        String md5Password = md5Convert(password);
        ProxyConnection con = null;
        try {
            con = POOL.takeConnection();
            UserDao userDao = new UserDao(con);
            boolean check = userDao.checkUniqueUserMail(mail);
            if (check) {
                boolean register = userDao.registerUser(mail, md5Password);
                return register ? userDao.logInUser(mail, md5Password) : null;
            } else {
                return null;
            }
        } finally {
            POOL.returnConnection(con);
        }
    }

    @Override
    public User receiveUserById(long userId) {
        ProxyConnection con = null;
        User user;
        try {
            con = POOL.takeConnection();
            UserDao userDao = new UserDao(con);
            user = userDao.receiveUserById(userId);
        } finally {
            POOL.returnConnection(con);
        }
        return user;
    }

    @Override
    public User changeUserLogin(long userId, String login) {
        ProxyConnection con = null;
        boolean check = true;
        try {
            con = POOL.takeConnection();
            UserDao userDao = new UserDao(con);
            if (!login.isEmpty()) {
                check = userDao.checkUniqueUserLogin(login);
            }
            if (check) {
                boolean change = userDao.changeUserLogin(userId, login);
                return change ? userDao.receiveUserById(userId) : null;
            } else {
                return null;
            }
        } finally {
            POOL.returnConnection(con);
        }
    }

    @Override
    public User changeUserPassword(long userId, String oldPassword, String newPassword) {
        ProxyConnection con = null;
        String md5OldPassword = md5Convert(oldPassword);
        String md5NewPassword = md5Convert(newPassword);
        try {
            con = POOL.takeConnection();
            UserDao userDao = new UserDao(con);
            boolean check = userDao.checkUserPassword(userId, md5OldPassword);
            if (check) {
                boolean change = userDao.changeUserPassword(userId, md5NewPassword);
                return change ? userDao.receiveUserById(userId) : null;
            } else {
                return null;
            }
        } finally {
            POOL.returnConnection(con);
        }
    }

    @Override
    public User fillUserBalanceById(long userId, BigDecimal amount) {
        ProxyConnection con = null;
        try {
            con = POOL.takeConnection();
            UserDao userDao = new UserDao(con);
            BigDecimal balance = userDao.receiveUserBalance(userId);
            userDao.updateUserBalanceById(userId, balance.add(amount));
            return userDao.receiveUserById(userId);
        } finally {
            POOL.returnConnection(con);
        }
    }

    @Override
    public BigDecimal receiveUserBalance(long userId) {
        ProxyConnection con = null;
        BigDecimal balance;
        try {
            con = POOL.takeConnection();
            UserDao userDao = new UserDao(con);
            balance = userDao.receiveUserBalance(userId);
        } finally {
            POOL.returnConnection(con);
        }
        return balance;
    }

    @Override
    public List<User> searchUser(String search) {

        return null;
    }

    private String md5Convert(String st) {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];
        try {
            messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = ZERO + md5Hex;
        }

        return md5Hex;
    }
}
