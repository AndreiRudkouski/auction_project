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
                    <form class="section" id="adminUser" action="Controller" method="get">
                        <input type="hidden" name="command" value="admin_user"/>
                        <a href="javascript:onClick('adminUser')">
                            <span><fmt:message key="admin.user"/></span>
                        </a>
                    </form>
                </li>
                <li>
                    <form class="section" id="adminLot" action="Controller" method="get">
                        <input type="hidden" name="command" value="admin_lot"/>
                        <a href="javascript:onClick('adminLot')">
                            <span><fmt:message key="profile.lot"/></span>
                        </a>
                    </form>
                </li>
                <li>
                    <form class="section" id="adminPwd" action="Controller" method="get">
                        <input type="hidden" name="command" value="admin_pwd"/>
                        <a href="javascript:onClick('adminPwd')">
                            <span><fmt:message key="admin.password"/></span>
                        </a>
                    </form>
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
        <c:choose>
            <c:when test="${requestScope.lotSelect != null}">
                <jsp:include page="admin/adminLot.jsp"/>
            </c:when>
            <c:when test="${requestScope.pwdSelect != null}">
                <jsp:include page="admin/adminPwd.jsp"/>
            </c:when>
            <c:otherwise>
                <jsp:include page="admin/adminUser.jsp"/>
            </c:otherwise>
        </c:choose>
    </div>
    <c:if test="${sessionScope.errorPwd != null}">
        <script type="text/javascript">
            window.onload = errorAdminPwd;
        </script>
    </c:if>
    </body>
    </html>
</fmt:bundle>