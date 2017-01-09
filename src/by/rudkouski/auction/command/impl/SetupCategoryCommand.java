package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.CategoryList;
import by.rudkouski.auction.bean.impl.Category;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.exception.ServiceException;
import by.rudkouski.auction.service.impl.CategoryService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.rudkouski.auction.constant.ConstantName.CATEGORY_LIST;

public class SetupCategoryCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(SetupCategoryCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        ServiceManager manager = ServiceManager.getInstance();
        CategoryService categoryService = manager.getCategoryService();
        List<Category> categories;
        try {
            categories = categoryService.setupCategory();
        } catch (ServiceException e) {
            LOGGER.log(Level.FATAL, "Exception: ", e);
            throw new RuntimeException("Error of setup categories", e);
        }
        CategoryList categoryList = new CategoryList(categories);

        HttpSession session = request.getSession();
        session.setAttribute(CATEGORY_LIST, categoryList);
        return null;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
    }
}