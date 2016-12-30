package by.rudkouski.auction.service;

import by.rudkouski.auction.service.impl.BetService;
import by.rudkouski.auction.service.impl.CategoryService;
import by.rudkouski.auction.service.impl.LotService;
import by.rudkouski.auction.service.impl.UserService;

public class ServiceManager {
    private static final ServiceManager INSTANCE = new ServiceManager();
    private final CategoryService categoryService = new CategoryService();
    private final LotService lotService = new LotService();
    private final UserService userService = new UserService();
    private final BetService betService = new BetService();

    private ServiceManager() {
    }

    public static ServiceManager getInstance() {
        return INSTANCE;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public LotService getLotService() {
        return lotService;
    }

    public UserService getUserService() {
        return userService;
    }

    public BetService getBetService() {
        return betService;
    }
}
