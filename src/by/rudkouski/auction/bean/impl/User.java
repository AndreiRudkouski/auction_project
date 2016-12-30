package by.rudkouski.auction.bean.impl;

import by.rudkouski.auction.bean.AbstractEntity;

import java.math.BigDecimal;

public class User extends AbstractEntity {
    private String login;
    private String mail;
    private BigDecimal balance;
    private boolean ban;
    private long roleId;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean isBan() {
        return ban;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
