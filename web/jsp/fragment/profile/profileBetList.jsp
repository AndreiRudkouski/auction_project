<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="ctg" %>
<fmt:bundle basename="resource.localization">
    <html>
    <head>
    </head>
    <body>
    <c:choose>
        <c:when test="${requestScope.betListWin.size == 0 && requestScope.betListDone.size == 0}">
            <span><fmt:message key="profileBet.none"/></span>
        </c:when>
        <c:otherwise>
            <c:if test="${requestScope.betListWin.size > 0}">
                <span><strong><fmt:message key="profileBet.win"/></strong></span>
                <div class="lotList">
                    <ctg:list rows="${requestScope.betListWin.size}">
                        <c:set var="bet" value="${requestScope.betListWin.element}"/>
                        <form id="lot${bet.lot.id}" action="Controller" method="post">
                            <input type="hidden" name="command" value="lot_choice"/>
                            <input type="hidden" name="lotId" value="${bet.lot.id}">
                            <a href="javascript:onClick('lot${bet.lot.id}')">
                                <fmt:formatDate value="${bet.time}" pattern="dd-MM-yyyy, HH:mm:ss"/> -
                                <c:out value="${bet.amount}"/> <fmt:message key="lot.rub"/> -
                                <c:out value="${bet.lot.name}"/>
                            </a>
                        </form>
                    </ctg:list>
                </div>
            </c:if>
            <c:if test="${requestScope.betListDone.size > 0}">
                <span><strong><fmt:message key="profileBet.done"/></strong></span>
                <div class="lotList">
                    <ctg:list rows="${requestScope.betListDone.size}">
                        <c:set var="bet" value="${requestScope.betListDone.element}"/>
                        <form id="lot${bet.lot.id}" action="Controller" method="post">
                            <input type="hidden" name="command" value="lot_choice"/>
                            <input type="hidden" name="lotId" value="${bet.lot.id}">
                            <a href="javascript:onClick('lot${bet.lot.id}')">
                                <fmt:formatDate value="${bet.time}" pattern="dd-MM-yyyy, HH:mm:ss"/> -
                                <c:out value="${bet.amount}"/> <fmt:message key="lot.rub"/> -
                                <c:out value="${bet.lot.name}"/>
                            </a>
                        </form>
                    </ctg:list>
                </div>
            </c:if>
        </c:otherwise>
    </c:choose>
    </body>
    </html>
</fmt:bundle>
