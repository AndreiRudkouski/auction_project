<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="loc" value="${sessionScope.loc}"/>
<c:choose>
    <c:when test="${loc == null}">
        <fmt:setLocale value="be" scope="session"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="${loc}" scope="session"/>
    </c:otherwise>
</c:choose>
<html>
<head>
    <link rel="icon" type="image/x-icon" href="../img/site/favicon.ico">
    <style><c:import url="/css/style.css"/></style>
    <script src="http://code.jquery.com/jquery-2.0.2.min.js"></script>
    <script><c:import url="/js/script.js"/></script>
</head>
<body>
<c:import url="fragment/header.jsp"/>
<c:if test="${sessionScope.categoryList == null}">
    <c:import url="/jsp/Controller?command=setup_category"/>
</c:if>
<c:choose>
    <c:when test="${sessionScope.user != null && sessionScope.user.roleId == 2}">
        <c:choose>
            <c:when test="${requestScope.lot == null}">
                <c:import url="fragment/admin.jsp"/>
            </c:when>
            <c:otherwise>
                <c:import url="fragment/lot/lot.jsp"/>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <c:import url="fragment/nav.jsp"/>
        <c:choose>
            <c:when test="${requestScope.profile != null && sessionScope.user != null}">
                <c:import url="fragment/profile.jsp"/>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${requestScope.lot == null}">
                        <c:if test="${requestScope.categoryId == null && requestScope.lotList == null}">
                            <c:import url="/jsp/Controller?command=setup_lot"/>
                        </c:if>
                        <c:import url="fragment/lot/lots.jsp"/>
                    </c:when>
                    <c:otherwise>
                        <c:import url="fragment/lot/lot.jsp"/>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>
<c:if test="${sessionScope.errorBalance != null || sessionScope.errorFinish != null || sessionScope.betAccept != null || sessionScope.changeAccept != null || sessionScope.errorMessage != null || sessionScope.mailMessage != null}">
    <script type="text/javascript">
        window.onload = changeMessage;
    </script>
</c:if>
<jsp:include page="fragment/message.jsp"/>
<c:import url="fragment/footer.jsp"/>
</body>
</html>
