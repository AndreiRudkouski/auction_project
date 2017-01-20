<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization" prefix="auth.">
    <html>
    <head>
    </head>
    <body>
    <div id="forgotPassword">
        <form onsubmit="return validateMail()" name="forgotPwd" action="Controller" method="post">
            <input type="hidden" id="com" name="command" value="pwd_forgot"/>
            <label for="mailForgot"><fmt:message key="mail"/></label>
            <input type="text" name="mail" id="mailForgot">
            <div class="err" id="errFillMailForgot"><fmt:message key="errFill"/></div>
            <div class="err" id="errBadMailForgot"><fmt:message key="errBadMail"/></div>
            <div class="lowerForgot">
                <input type="submit" value="<fmt:message key="remind"/>">
            </div>
        </form>
    </div>
    <c:if test="${sessionScope.errorMail != null}">
        <script type="text/javascript">
            window.onload = errorMail;
        </script>
    </c:if>
    </body>
    </html>
</fmt:bundle>
