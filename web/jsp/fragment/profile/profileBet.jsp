<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization">
    <html>
    <head>
    </head>
    <body>
    <div>
        <div class="nameProf"><fmt:message key="profile.bet"/></div>
        <div class="dataLeft">
            <jsp:include page="profileBetList.jsp"/>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>
