<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="ctg" %>
<fmt:bundle basename="resource.localization">
<html>
<head>
</head>
<body>
<div id="betList">
    <div>
        <table class="tableBet">
            <caption><fmt:message key="bet.history"/></caption>
            <hr/>
            <tbody>
            <c:forEach items="${requestScope.lot.betList}" var="bet">
                <tr>
                    <td class="tdUser">
                        <ctg:user login="${bet.user.login}" mail="${bet.user.mail}"/>
                    </td>
                    <td class="tdAmount">
                        <c:out value="${bet.amount}"/> <fmt:message key="lot.rub"/>
                    </td>
                    <td class="tdTime">
                        <fmt:formatDate value="${bet.time}" pattern="dd-MM-yyyy, HH:mm:ss"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
</fmt:bundle>