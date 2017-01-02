<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization">
    <html>
    <head>
    </head>
    <body>
    <div class="nameProf"><fmt:message key="admin.password"/></div>
    <div class="dataLeft">
        <div class="userFind">
            <form name="pwdChange" onsubmit="return validatePwdChange()" action="Controller" method="post">
                <input type="hidden" name="command" value="profile_change"/>
                <label for="adminOldPwd"><fmt:message key="profile.oldPassword"/></label><br>
                <input type="password" id="adminOldPwd" name="oldPwd" value=""><br>
                <div class="err" id="errFillAdminOldPwd"><fmt:message key="auth.errFill"/></div>
                <div class="err" id="errLenAdminOldPwd"><fmt:message key="auth.errLenPwd"/></div>
                <div class="err" id="errBadAdminOldPwd"><fmt:message key="auth.errBadPwd"/></div>
                <label for="adminNewPwd"><fmt:message key="profile.newPassword"/></label><br>
                <input type="password" id="adminNewPwd" name="newPwd" value="">
                <div class="err" id="errLenAdminNewPwd"><fmt:message key="auth.errLenPwd"/></div>
                <div class="err" id="errBadAdminNewPwd"><fmt:message key="auth.errBadPwd"/></div>
                <div class="err" id="errOldAdminNewPwd"><fmt:message key="profile.errOldNewPwd"/></div>
                <div class="err" id="errAdminPwd"><fmt:message key="profile.errPwd"/></div>
                <input id="save" type="submit" value="<fmt:message key="profile.save"/>">
            </form>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>