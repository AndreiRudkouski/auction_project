package by.rudkouski.auction.dao.impl;

import by.rudkouski.auction.bean.impl.Category;
import by.rudkouski.auction.bean.impl.Condition;
import by.rudkouski.auction.bean.impl.Term;
import by.rudkouski.auction.bean.impl.Type;
import by.rudkouski.auction.dao.ICategoryDao;
import by.rudkouski.auction.dao.exception.DaoException;
import by.rudkouski.auction.pool.ProxyConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao implements ICategoryDao<Category> {
    private ProxyConnection con;

    private static final String SQL_CATEGORY = "SELECT category_id, categoryBe, categoryEn FROM category";
    private static final String SQL_TERM = "SELECT term_id, term FROM term ORDER BY term";
    private static final String SQL_CONDITION = "SELECT condition_id, cond.condition FROM cond ORDER BY condition_id";
    private static final String SQL_TYPE = "SELECT type_id, type FROM type ORDER BY type";

    public CategoryDao(ProxyConnection con) {
        this.con = con;
    }

    @Override
    public List<Category> setupCategory() throws DaoException {
        List<Category> categoryList;
        try (Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery(SQL_CATEGORY);
            categoryList = new ArrayList<>();
            while (res.next()) {
                Category category = new Category();
                category.setId(res.getLong(1));
                category.setNameBe(res.getString(2));
                category.setNameEn(res.getString(3));
                categoryList.add(category);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException", e);
        }
        return categoryList;
    }

    @Override
    public List<Term> setupTerm() throws DaoException {
        List<Term> termList;
        try (Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery(SQL_TERM);
            termList = new ArrayList<>();
            while (res.next()) {
                Term term = new Term();
                term.setId(res.getLong(1));
                term.setName(res.getString(2));
                termList.add(term);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException", e);
        }
        return termList;
    }

    @Override
    public List<Condition> setupCondition() throws DaoException {
        List<Condition> condList;
        try (Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery(SQL_CONDITION);
            condList = new ArrayList<>();
            while (res.next()) {
                Condition cond = new Condition();
                cond.setId(res.getLong(1));
                cond.setName(res.getString(2));
                condList.add(cond);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException", e);
        }
        return condList;
    }

    @Override
    public List<Type> setupType() throws DaoException {
        List<Type> typeList;
        try (Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery(SQL_TYPE);
            typeList = new ArrayList<>();
            while (res.next()) {
                Type type = new Type();
                type.setId(res.getLong(1));
                type.setName(res.getString(2));
                typeList.add(type);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException", e);
        }
        return typeList;
    }
}
