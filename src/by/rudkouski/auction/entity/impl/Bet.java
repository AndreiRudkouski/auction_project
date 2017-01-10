package by.rudkouski.auction.entity.impl;

import by.rudkouski.auction.entity.AbstractEntity;

import java.math.BigDecimal;
import java.util.Date;

public class Bet extends AbstractEntity {
    private User user;
    private BigDecimal amount;
    private Date time;
    private Lot lot;
    private boolean win;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }
}
