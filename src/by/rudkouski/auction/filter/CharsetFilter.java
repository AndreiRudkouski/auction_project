package by.rudkouski.auction.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

import static by.rudkouski.auction.constant.ConstantName.ENCODING;

@WebFilter(filterName = "CharsetFilter",
        urlPatterns = {"/*"},
        initParams = {
                @WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")})
public class CharsetFilter implements Filter {
    private String code;

    @Override
    public void destroy() {
        code = null;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String codeRequest = req.getCharacterEncoding();
        if (code != null && !code.equals(codeRequest)) {
            req.setCharacterEncoding(code);
            resp.setCharacterEncoding(code);
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        code = config.getInitParameter(ENCODING);
    }

}
