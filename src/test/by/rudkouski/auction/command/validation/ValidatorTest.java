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
        Assert.assertFalse(validator.userMailValidate(null));
        Assert.assertFalse(validator.userMailValidate(""));
        Assert.assertFalse(validator.userMailValidate("qwerty.mail"));
        Assert.assertTrue(validator.userMailValidate("qwerty@gmail.com"));
    }

    @Test
    public void userValidateTest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(new User());
        Assert.assertTrue(validator.userValidate(request));
        when(session.getAttribute(USER)).thenReturn(null);
        Assert.assertFalse(validator.userValidate(request));
    }
}
