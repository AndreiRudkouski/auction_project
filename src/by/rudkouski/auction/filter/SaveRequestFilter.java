package by.rudkouski.auction.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

import static by.rudkouski.auction.constant.ConstantName.*;

@WebFilter(filterName = "SaveRequestFilter",
        dispatcherTypes = {DispatcherType.FORWARD},
        urlPatterns = {"/jsp/*"})
public class SaveRequestFilter implements Filter {

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
                saveReq.append(PARAMETER_DIVIDER);
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
