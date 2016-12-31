<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization">
    <html>
    <head>
    </head>
    <body>
    <div>
        <div class="nameProf">
            <fmt:message key="admin.user"/>
        </div>
        <div class="dataLeft">
            <div class="userFind">
                <span>Пошук па лагину ци пароли:</span>
                <form onsubmit="return validateFind('userFind')" action="Controller" method="post">
                    <input type="hidden" name="command" value="user_search"/>
                    <input id="userFind" type="text" name="user_search" autocomplete="off"
                           placeholder="Пошук карыстальника">
                    <input type="submit" value="Найти">
                </form>
            </div>
            <div class="clearfix"></div>
            <div>
                <c:if test="${requestScope.userList != null}">
                    <c:choose>
                        <c:when test="${requestScope.userList != 0}">

                        </c:when>
                        <c:otherwise>
                            <span>Карыстальникау не знойдзена</span>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </div>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>