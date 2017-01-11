package test.by.rudkouski.auction.command.validation;

import by.rudkouski.auction.command.validation.Validator;
import by.rudkouski.auction.entity.impl.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.rudkouski.auction.constant.ConstantName.USER;
import static org.mockito.Mockito.*;

public class ValidatorTest {
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        validator = new Validator();
    }

    @Test
    public void userMailValidateTest() {
        String nullMail = null;
        String emptyMail = "";
        String wrongMail = "qwerty.mail";
        String correctMail = "qwerty@gmail.com";
        boolean result = validator.userMailValidate(nullMail);
        Assert.assertFalse(result);
        result = validator.userMailValidate(emptyMail);
        Assert.assertFalse(result);
        result = validator.userMailValidate(wrongMail);
        Assert.assertFalse(result);
        result = validator.userMailValidate(correctMail);
        Assert.assertTrue(result);
    }

    @Test
    public void userValidateTest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(new User());
        boolean result = validator.userValidate(request);
        Assert.assertTrue(result);
        when(session.getAttribute(USER)).thenReturn(null);
        result = validator.userValidate(request);
        Assert.assertFalse(result);
    }
}
