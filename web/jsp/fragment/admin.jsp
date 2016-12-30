<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization">
    <html>
    <head>
    </head>
    <body>
    <div class="profile">
        <div class="choice">
            <ul>
                <li>
                    <a class="section" href="javascript:visibleAdmin('adminUser')">
                        <span><fmt:message key="admin.user"/></span>
                    </a>
                </li>
                <li>
                    <a class="section" href="javascript:visibleAdmin('adminLot')">
                        <span><fmt:message key="profile.lot"/></span>
                    </a>
                </li>
                <li>
                    <a class="section" href="javascript:visibleAdmin('adminPwd')">
                        <span><fmt:message key="admin.password"/></span>
                    </a>
                </li>
                <li>
                    <form class="section" id="logOut" action="Controller" method="get">
                        <input type="hidden" name="command" value="logOut"/>
                        <a href="javascript:onClick('logOut')">
                            <span><fmt:message key="profile.logOut"/></span>
                        </a>
                    </form>
                </li>
            </ul>
        </div>
        <hr>
        <div id="adminUser"><jsp:include page="admin/adminUser.jsp"/></div>
        <div id="adminLot"><jsp:include page="admin/adminLot.jsp"/></div>
        <div id="adminPwd"><jsp:include page="admin/adminPwd.jsp"/></div>
    </div>
    </body>
    </html>
</fmt:bundle>