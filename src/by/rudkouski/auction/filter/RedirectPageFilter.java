package by.rudkouski.auction.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "RedirectPageFilter",
        urlPatterns = {"/jsp/fragment/*"},
        initParams = {
                @WebInitParam(name = "index_page", value = "/index.jsp")})
public class RedirectPageFilter implements Filter {
    private static final String INDEX_PAGE = "index_page";
    private String page;

    @Override
    public void destroy() {
        page = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.sendRedirect(httpRequest.getContextPath() + page);
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        page = fConfig.getInitParameter(INDEX_PAGE);
    }
}
