package by.rudkouski.auction.tag;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ListTag extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger(ListTag.class);
    private int rows;

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    @Override
    public int doStartTag() throws JspTagException {
        try {
            JspWriter out = pageContext.getOut();
            out.write("<ul>");
            out.write("<li>");
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            throw new JspTagException(e);
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doAfterBody() throws JspTagException {
        if (rows-- > 1) {
            try {
                JspWriter out = pageContext.getOut();
                out.write("</li>");
                out.write("<li>");
            } catch (IOException e) {
                LOGGER.log(Level.ERROR, "Exception: ", e);
                throw new JspTagException(e);
            }
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }

    @Override
    public int doEndTag() throws JspTagException {
        try {
            pageContext.getOut().write("</ul>");
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            throw new JspTagException(e);
        }
        return EVAL_PAGE;
    }
}
