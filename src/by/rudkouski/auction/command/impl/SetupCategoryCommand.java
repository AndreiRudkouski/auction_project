package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.CategoryList;
import by.rudkouski.auction.bean.impl.Category;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.impl.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SetupCategoryCommand implements ICommand {
    private static final String CATEGORY_LIST = "categoryList";

    @Override
    public String execute(HttpServletRequest request) {
        ServiceManager manager = ServiceManager.getInstance();
        CategoryService categoryService = manager.getCategoryService();
        List<Category> categories = categoryService.setupCategory();
        CategoryList categoryList = new CategoryList(categories);

        HttpSession session = request.getSession();
        session.setAttribute(CATEGORY_LIST, categoryList);
        return null;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
    }
}
