<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization">
    <html>
    <head>
    </head>
    <body>
    <c:if test="${requestScope.betListWin != null}">
        <span><strong><fmt:message key="profileBet.win"/></strong></span>
        <div class="lotList">
            <ul>
                <c:forEach items="${requestScope.betListWin}" var="bet">
                    <li>
                        <form id="lot${bet.lot.id}" action="Controller" method="post">
                            <input type="hidden" name="command" value="lot_choice"/>
                            <input type="hidden" name="lotId" value="${bet.lot.id}">
                            <a href="javascript:onClick('lot${bet.lot.id}')">
                                <fmt:formatDate value="${bet.time}"
                                                pattern="dd-MM-yyyy, HH:mm:ss"/> -
                                <c:out value="${bet.amount}"/> <fmt:message key="lot.rub"/> -
                                <c:out value="${bet.lot.name}"/>
                            </a>
                        </form>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <c:if test="${requestScope.betListDone != null}">
        <span><strong><fmt:message key="profileBet.done"/></strong></span>
        <div class="lotList">
            <ul>
                <c:forEach items="${requestScope.betListDone}" var="bet">
                    <li>
                        <form id="lot${bet.lot.id}" action="Controller" method="post">
                            <input type="hidden" name="command" value="lot_choice"/>
                            <input type="hidden" name="lotId" value="${bet.lot.id}">
                            <a href="javascript:onClick('lot${bet.lot.id}')">
                                <fmt:formatDate value="${bet.time}"
                                                pattern="dd-MM-yyyy, HH:mm:ss"/> -
                                <c:out value="${bet.amount}"/> <fmt:message key="lot.rub"/> -
                                <c:out value="${bet.lot.name}"/>
                            </a>
                        </form>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <c:if test="${requestScope.betListWin == null && requestScope.betListDone == null}">
        <span><fmt:message key="profileBet.none"/></span>
    </c:if>
    </body>
    </html>
</fmt:bundle>
