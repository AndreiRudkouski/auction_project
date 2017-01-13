package by.rudkouski.auction.service.impl;

import by.rudkouski.auction.entity.impl.User;
import by.rudkouski.auction.dao.exception.DaoException;
import by.rudkouski.auction.dao.impl.UserDao;
import by.rudkouski.auction.mail.MailSender;
import by.rudkouski.auction.mail.MailSenderException;
import by.rudkouski.auction.pool.ConnectionPool;
import by.rudkouski.auction.pool.ConnectionPoolException;
import by.rudkouski.auction.pool.ProxyConnection;
import by.rudkouski.auction.service.IUserService;
import by.rudkouski.auction.service.exception.ServiceException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import static by.rudkouski.auction.constant.ConstantName.*;

public class UserService implements IUserService<User> {
    private static final ConnectionPool POOL = ConnectionPool.getInstance();

    @Override
    public User logInUser(String mail, String password) throws ServiceException {
        String md5Password = md5Convert(password);
        ProxyConnection con = null;
        User user;
        try {
            con = POOL.takeConnection();
            UserDao userDao = new UserDao(con);
            user = userDao.logInUser(mail, md5Password);
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        return user;
    }

    @Override
    public User registerUser(String mail, String password) throws ServiceException {
        String md5Password = md5Convert(password);
        ProxyConnection con = null;
        User user;
        try {
            con = POOL.takeConnection();
            UserDao userDao = new UserDao(con);
            long check = userDao.checkUniqueUserMail(mail);
            if (check == -1) {
                userDao.registerUser(mail, md5Password);
                user = userDao.logInUser(mail, md5Password);
            } else {
                return null;
            }
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        return user;
    }

    @Override
    public User receiveUserById(long userId) throws ServiceException {
        ProxyConnection con = null;
        User user;
        try {
            con = POOL.takeConnection();
            UserDao userDao = new UserDao(con);
            user = userDao.receiveUserById(userId);
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        return user;
    }

    @Override
    public User changeBanUserById(long userId) throws ServiceException {
        ProxyConnection con = null;
        User user;
        try {
            con = POOL.takeConnection();
            UserDao userDao = new UserDao(con);
            user = userDao.receiveUserById(userId);
            userDao.changeBanUserById(userId, !user.isBan());
            user = userDao.receiveUserById(userId);
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        return user;
    }

    @Override
    public User changeUserLogin(long userId, String login) throws ServiceException {
        ProxyConnection con = null;
        User user;
        try {
            con = POOL.takeConnection();
            UserDao userDao = new UserDao(con);
            boolean check = false;
            if (!login.isEmpty()) {
                check = userDao.checkUniqueUserLogin(login);
            }
            if (check) {
                userDao.changeUserLogin(userId, login);
                user = userDao.receiveUserById(userId);
            } else {
                return null;
            }
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        return user;
    }

    @Override
    public User changeUserPassword(long userId, String oldPassword, String newPassword) throws ServiceException {
        ProxyConnection con = null;
        User user;
        String md5OldPassword = md5Convert(oldPassword);
        String md5NewPassword = md5Convert(newPassword);
        try {
            con = POOL.takeConnection();
            UserDao userDao = new UserDao(con);
            boolean check = userDao.checkUserPassword(userId, md5OldPassword);
            if (check) {
                userDao.changeUserPassword(userId, md5NewPassword);
                user = userDao.receiveUserById(userId);
            } else {
                return null;
            }
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        return user;
    }

    @Override
    public User fillUserBalanceById(long userId, BigDecimal amount) throws ServiceException {
        ProxyConnection con = null;
        User user;
        try {
            con = POOL.takeConnection();
            UserDao userDao = new UserDao(con);
            BigDecimal balance = userDao.receiveUserBalance(userId);
            userDao.updateUserBalanceById(userId, balance.add(amount));
            user = userDao.receiveUserById(userId);
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        return user;
    }

    @Override
    public BigDecimal receiveUserBalance(long userId) throws ServiceException {
        ProxyConnection con = null;
        BigDecimal balance;
        try {
            con = POOL.takeConnection();
            UserDao userDao = new UserDao(con);
            balance = userDao.receiveUserBalance(userId);
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        return balance;
    }

    @Override
    public List<User> searchUserByLoginMail(String search) throws ServiceException {
        ProxyConnection con = null;
        List<User> userList;
        try {
            con = POOL.takeConnection();
            UserDao userDao = new UserDao(con);
            userList = userDao.searchUserByLoginMail(search);
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        return userList;
    }

    @Override
    public boolean forgotUserPassword(String mail) throws ServiceException {
        ProxyConnection con = null;
        try {
            con = POOL.takeConnection();
            UserDao userDao = new UserDao(con);
            long checkId = userDao.checkUniqueUserMail(mail);
            if (checkId == -1) {
                return false;
            }
            String newPassword = generateNewPassword();
            String md5NewPassword = md5Convert(newPassword);
            con.setAutoCommit(false);
            userDao.changeUserPassword(checkId, md5NewPassword);
            MailSender.getInstance().send(MAIL_THEME, MAIL_CONTENT + newPassword, mail);
            con.commit();
        } catch (SQLException | DaoException | ConnectionPoolException | MailSenderException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                throw new ServiceException("SQLException during rollback", e);
            }
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        return true;
    }

    private String md5Convert(String st) {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];
        try {
            messageDigest = MessageDigest.getInstance(MD5_ALGORITHM);
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

    private String generateNewPassword() {
        StringBuilder newPwd = new StringBuilder();
        for (int i = 0; i < QTY_PASSWORD_GENERATION; i++) {
            Random random = new Random();
            newPwd.append((char) (CHAR_START_NUMBER + random.nextInt(QTY_NUMBER)));
            newPwd.append((char) (CHAR_START_SMALL_LETTER + random.nextInt(QTY_LETTER)));
            newPwd.append((char) (CHAR_START_BIG_LETTER + random.nextInt(QTY_LETTER)));
        }
        return newPwd.toString();
    }
}
