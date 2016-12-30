package by.rudkouski.auction.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class UserTag extends TagSupport {
    private static final long ADMIN_ROLE_ID = 2;

    private String login;
    private String mail;
    private String balance;
    private Long roleId;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            String result = login != null && !login.isEmpty() ? login : mail;
            result = balance != null && (roleId == null || roleId != ADMIN_ROLE_ID)  ? result + " (" + balance + ")" : result;
            pageContext.getOut().write(result);
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
