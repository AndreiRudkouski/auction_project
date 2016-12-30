package by.rudkouski.auction.dao.impl;

import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.dao.IUserDao;
import by.rudkouski.auction.pool.ProxyConnection;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao implements IUserDao<User> {
    private ProxyConnection con;
    private static final String SQL_USER = "SELECT user_id, login, email, balance, ban, role_id FROM user WHERE email = ? AND password = ?";
    private static final String SQL_ADD_USER = "INSERT INTO user (email, password) VALUES (?, ?)";
    private static final String SQL_MAIL = "SELECT email FROM user WHERE email = ?";
    private static final String SQL_USER_ID = "SELECT user_id, login, email, balance, ban, role_id FROM user WHERE user_id = ?";
    private static final String SQL_LOGIN = "SELECT login FROM user WHERE login = ?";
    private static final String SQL_LOGIN_CHANGE = "UPDATE user SET login = ? WHERE user_id = ?";
    private static final String SQL_PASSWORD_CHANGE = "UPDATE user SET password = ? WHERE user_id = ?";
    private static final String SQL_BALANCE = "SELECT balance FROM user WHERE user_id = ?";
    private static final String SQL_UPDATE_BALANCE = "UPDATE user SET balance = ? WHERE user_id = ?";
    private static final String SQL_PREV_MAX_BET = "SELECT bet.user_id, bet, balance FROM bet " +
            "JOIN user ON bet.user_id = user.user_id " +
            "WHERE bet = (SELECT MAX(bet) FROM bet WHERE lot_id = ?)";
    private static final String SQL_PASSWORD = "SELECT password FROM user WHERE user_id = ? AND password = ?";

    public UserDao(ProxyConnection con) {
        this.con = con;
    }

    @Override
    public User logInUser(String mail, String password) {
        User user = null;
        try (PreparedStatement prSt = con.prepareStatement(SQL_USER)) {
            prSt.setString(1, mail);
            prSt.setString(2, password);
            ResultSet res = prSt.executeQuery();

            while (res.next()) {
                user = createUser(res);
            }
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
        return user;
    }

    @Override
    public boolean registerUser(String mail, String password) {
        try (PreparedStatement prSt = con.prepareStatement(SQL_ADD_USER)) {
            prSt.setString(1, mail);
            prSt.setString(2, password);
            prSt.executeUpdate();
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
            return false;
        }
        return true;
    }

    @Override
    public User receiveUserById(long userId) {
        User user = null;
        try (PreparedStatement prSt = con.prepareStatement(SQL_USER_ID)) {
            prSt.setLong(1, userId);
            ResultSet res = prSt.executeQuery();
            while (res.next()) {
                user = createUser(res);
            }
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
        return user;
    }

    @Override
    public boolean changeUserLogin(long userId, String login) {
        try (PreparedStatement prSt = con.prepareStatement(SQL_LOGIN_CHANGE)) {
            changeUserProfile(userId, login, prSt);
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean changeUserPassword(long userId, String password) {
        try (PreparedStatement prSt = con.prepareStatement(SQL_PASSWORD_CHANGE)) {
            changeUserProfile(userId, password, prSt);
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
            return false;
        }
        return true;
    }

    private void changeUserProfile(long userId, String content, PreparedStatement prSt) throws SQLException {
        prSt.setString(1, content);
        prSt.setLong(2, userId);
        prSt.executeUpdate();
    }

    @Override
    public BigDecimal receiveUserBalance(long userId) {
        BigDecimal balance = null;
        try (PreparedStatement prSt = con.prepareStatement(SQL_BALANCE)) {
            prSt.setLong(1, userId);
            ResultSet res = prSt.executeQuery();
            while (res.next()) {
                balance = res.getBigDecimal(1);
            }
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
        return balance;
    }

    @Override
    public boolean checkUserPassword(long userId, String password) {
        try (PreparedStatement prSt = con.prepareStatement(SQL_PASSWORD)) {
            prSt.setLong(1, userId);
            prSt.setString(2, password);
            ResultSet res = prSt.executeQuery();
            while (res.next()) {
                return true;
            }
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
        return false;
    }

    @Override
    public boolean checkUniqueUserMail(String mail) {
        boolean result = false;
        try (PreparedStatement prSt = con.prepareStatement(SQL_MAIL)) {
            result = checkUniqueUserContent(mail, prSt);
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
        return result;
    }

    @Override
    public boolean checkUniqueUserLogin(String login) {
        boolean result = false;
        try (PreparedStatement prSt = con.prepareStatement(SQL_LOGIN)) {
            result = checkUniqueUserContent(login, prSt);
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
        return result;
    }

    @Override
    public boolean updateUserBalanceById(long userId, BigDecimal newBalance) {
        try (PreparedStatement prSt = con.prepareStatement(SQL_UPDATE_BALANCE)) {
            prSt.setBigDecimal(1, newBalance);
            prSt.setLong(2, userId);
            prSt.executeUpdate();
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
            return false;
        }
        return true;
    }

    @Override
    public User receivePrevMaxBetUser(long lotId) {
        User user = null;
        try (PreparedStatement prSt = con.prepareStatement(SQL_PREV_MAX_BET)) {
            prSt.setLong(1, lotId);
            ResultSet res = prSt.executeQuery();
            while (res.next()) {
                user = new User();
                long userId = res.getLong(1);
                BigDecimal bet = res.getBigDecimal(2);
                BigDecimal balance = res.getBigDecimal(3);
                user.setId(userId);
                user.setBalance(balance.add(bet));
            }
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
        return user;
    }

    private User createUser(ResultSet res) throws SQLException {
        User user = new User();
        user.setId(res.getLong(1));
        user.setLogin(res.getString(2));
        user.setMail(res.getString(3));
        user.setBalance(new BigDecimal(res.getString(4)));
        user.setBan(res.getBoolean(5));
        user.setRoleId(res.getLong(6));
        return user;
    }

    private boolean checkUniqueUserContent(String content, PreparedStatement prSt) throws SQLException {
        prSt.setString(1, content);
        ResultSet res = prSt.executeQuery();
        while (res.next()) {
            return false;
        }
        return true;
    }
}
