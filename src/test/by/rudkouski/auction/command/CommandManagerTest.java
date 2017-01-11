package test.by.rudkouski.auction.command;

import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.command.CommandManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static by.rudkouski.auction.constant.ConstantName.*;
import static org.mockito.Mockito.*;


public class CommandManagerTest {
    private static HttpServletRequest request;

    @BeforeClass
    public static void setUp() {
        request = mock(HttpServletRequest.class);
    }

    @Test
    public void defineCommandRequestTest() {
        when(request.getParameter(COMMAND)).thenReturn(LOGIN);
        ICommand command = new CommandManager().defineCommandRequest(request);
        Assert.assertEquals(command.getClass().getName(), "by.rudkouski.auction.command.impl.LogInCommand");
    }

    @Test(expected = RuntimeException.class)
    public void defineCommandRequestCommandNullExceptionTest() {
        when(request.getParameter(COMMAND)).thenReturn(null);
        new CommandManager().defineCommandRequest(request);
    }

    @Test(expected = RuntimeException.class)
    public void defineCommandRequestWrongCommandExceptionTest() {
        when(request.getParameter(COMMAND)).thenReturn(USER);
        new CommandManager().defineCommandRequest(request);
    }
}
