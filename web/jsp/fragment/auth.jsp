<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization" prefix="auth.">
    <html>
    <head>
    </head>
    <body>
    <div class="authorization">
        <form onsubmit="return validateAuth()" name="auth" action="Controller" method="post">
            <input type="hidden" id="com" name="command" value="login"/>
            <label for="mail"><fmt:message key="mail"/></label>
            <input type="text" name="mail" id="mail">
            <div class="err" id="errFillMail"><fmt:message key="errFill"/></div>
            <div class="err" id="errBadMail"><fmt:message key="errBadMail"/></div>
            <label for="pwd"><fmt:message key="password"/></label>
            <a id="fogPwd" href="#"><fmt:message key="fogPwd"/></a>
            <input type="password" name="pwd" id="pwd">
            <div class="err" id="errFillPwd"><fmt:message key="errFill"/></div>
            <div class="err" id="errLenPwd"><fmt:message key="errLenPwd"/></div>
            <div class="err" id="errBadPwd"><fmt:message key="errBadPwd"/></div>
            <div class="errUser" id="errBadUser"><fmt:message key="errUser"/></div>
            <div class="errUser" id="errAuthUser"><fmt:message key="errAuth"/></div>
            <div class="errUser" id="errBan"><fmt:message key="errBan"/></div>
            <div class="lower">
                <div id="enterBlock">
                    <a href="javascript:registration()"><fmt:message key="authLink"/></a>
                    <input type="submit" value="<fmt:message key="enterSubmit"/>">
                </div>
                <div id="authBlock">
                    <a href="javascript:enter()"><fmt:message key="enterSubmit"/></a>
                    <input type="submit" value="<fmt:message key="authSubmit"/>">
                </div>
            </div>
        </form>
    </div>
    <c:choose>
        <c:when test="${sessionScope.errorUser != null}">
            <script type="text/javascript">
                window.onload = errorUser;
            </script>
        </c:when>
        <c:otherwise>
            <c:if test="${sessionScope.errorBan != null}">
                <script type="text/javascript">
                    window.onload = errorBan;
                </script>
            </c:if>
            <c:if test="${sessionScope.errorAuth != null}">
                <script type="text/javascript">
                    window.onload = errorAuth;
                </script>
            </c:if>
        </c:otherwise>
    </c:choose>
    </body>
    </html>
</fmt:bundle>
