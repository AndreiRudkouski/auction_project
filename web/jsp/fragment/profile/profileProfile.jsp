<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization">
    <html>
    <head>
    </head>
    <body>
    <div>
        <div class="nameProf"><fmt:message key="profile.profile"/></div>
        <form name="userChange" onsubmit="return validateUserChange()" action="Controller" method="post">
            <input id="save" type="submit" value="<fmt:message key="profile.save"/>">
            <div class="dataLeft">
                <input type="hidden" name="command" value="profile_change"/>
                <input type="hidden" id="userOldLogin" name="userOldLogin" value="${sessionScope.user.login}">
                <label for="login"><fmt:message key="profile.login"/></label><br>
                <input type="text" autocomplete="off" name="login" id="login" value="${sessionScope.user.login}"><br>
                <div class="err" id="errLoginLength"><fmt:message key="profile.errLength"/></div>
                <div class="err" id="errLogin"><fmt:message key="profile.errLogin"/></div>
                <div class="err" id="errDoubleLogin"><fmt:message key="profile.errExistLogin"/></div>
                <label for="mail"><fmt:message key="auth.mail"/></label><br>
                <input type="text" name="mail" id="mail" value="${sessionScope.user.mail}" disabled><br>
                <label for="balance"><fmt:message key="profile.balance"/></label><br>
                <input type="text" name="balance" id="balance"
                       value="${sessionScope.user.balance} <fmt:message key="lot.rub"/>" disabled>
                <input type="submit" onclick="javascript:PopUpShow('.bankCard')" value="<fmt:message key="profile.fill"/>">
            </div>
            <div class="dataRight">
                <label for="oldPwd"><fmt:message key="profile.oldPassword"/></label><br>
                <input type="password" id="oldPwd" name="oldPwd" value=""><br>
                <div class="err" id="errFillOldPwd"><fmt:message key="auth.errFill"/></div>
                <div class="err" id="errLenOldPwd"><fmt:message key="auth.errLenPwd"/></div>
                <div class="err" id="errBadOldPwd"><fmt:message key="auth.errBadPwd"/></div>
                <label for="newPwd"><fmt:message key="profile.newPassword"/></label><br>
                <input type="password" id="newPwd" name="newPwd" value="">
                <div class="err" id="errLenNewPwd"><fmt:message key="auth.errLenPwd"/></div>
                <div class="err" id="errBadNewPwd"><fmt:message key="auth.errBadPwd"/></div>
                <div class="err" id="errOldNewPwd"><fmt:message key="profile.errOldNewPwd"/></div>
                <div class="err" id="errPwd"><fmt:message key="profile.errPwd"/></div>
            </div>
        </form>
    </div>
    </body>
    </html>
</fmt:bundle>