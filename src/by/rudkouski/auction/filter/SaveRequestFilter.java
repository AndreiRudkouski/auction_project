package by.rudkouski.auction.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@WebFilter(filterName = "SaveRequestFilter",
        dispatcherTypes = {DispatcherType.FORWARD},
        urlPatterns = {"/jsp/*"})
public class SaveRequestFilter implements Filter {
    private static final String URL = "../jsp/Controller?";
    private static final String DIVIDER = "&";
    private static final String EQUAL = "=";
    private static final String SAVE_REQ = "saveReq";

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        StringBuilder saveReq = new StringBuilder(URL);
        Map<String, String[]> map = req.getParameterMap();
        boolean divider = false;
        for (Map.Entry<String, String[]> m : map.entrySet()) {
            if (divider) {
                saveReq.append(DIVIDER);
            }
            saveReq.append(m.getKey() + EQUAL + m.getValue()[0]);
            divider = true;
        }
        ((HttpServletRequest) req).getSession().setAttribute(SAVE_REQ, saveReq.toString());
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

}
