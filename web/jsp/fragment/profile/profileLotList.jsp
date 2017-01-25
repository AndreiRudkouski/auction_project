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
        <c:when test="${requestScope.lotListFinished.size == 0 && requestScope.lotListUnfinished.size == 0 && requestScope.lotListUnchecked.size == 0 && requestScope.lotListRemoved.size == 0}">
            <span><fmt:message key="profileLot.none"/></span>
        </c:when>
        <c:otherwise>
            <c:if test="${requestScope.lotListFinished.size > 0}">
                <span><strong><fmt:message key="profileLot.completed"/></strong></span>
                <div class="lotList">
                    <ctg:list rows="${requestScope.lotListFinished.size}">
                        <c:set var="lot" value="${requestScope.lotListFinished.element}"/>
                        <form id="lot${lot.id}" action="Controller" method="post">
                            <input type="hidden" name="command" value="lot_choice"/>
                            <input type="hidden" name="lotId" value="${lot.id}">
                            <a href="javascript:onClick('lot${lot.id}')">
                                <fmt:formatDate value="${lot.timeStart}" pattern="dd-MM-yyyy, HH:mm:ss"/> -
                                <c:out value="${lot.name}"/>
                            </a>
                        </form>
                    </ctg:list>
                </div>
            </c:if>
            <c:if test="${requestScope.lotListUnfinished.size > 0}">
                <span><strong><fmt:message key="profileLot.notCompleted"/></strong></span>
                <div class="lotList">
                    <ctg:list rows="${requestScope.lotListUnfinished.size}">
                        <c:set var="lot" value="${requestScope.lotListUnfinished.element}"/>
                        <form id="lot${lot.id}" action="Controller" method="post">
                            <input type="hidden" name="command" value="lot_choice"/>
                            <input type="hidden" name="lotId" value="${lot.id}">
                            <a href="javascript:onClick('lot${lot.id}')">
                                <fmt:formatDate value="${lot.timeStart}" pattern="dd-MM-yyyy, HH:mm:ss"/> -
                                <c:out value="${lot.name}"/>
                            </a>
                        </form>
                    </ctg:list>
                </div>
            </c:if>
            <c:if test="${requestScope.lotListUnchecked.size > 0}">
                <span><strong><fmt:message key="profileLot.new"/></strong></span>
                <div class="lotList">
                    <ctg:list rows="${requestScope.lotListUnchecked.size}">
                        <c:set var="lot" value="${requestScope.lotListUnchecked.element}"/>
                        <form id="lot${lot.id}" action="Controller" method="post">
                            <input type="hidden" name="command" value="lot_choice"/>
                            <input type="hidden" name="lotId" value="${lot.id}">
                            <a href="javascript:onClick('lot${lot.id}')">
                                <fmt:formatDate value="${lot.timeStart}" pattern="dd-MM-yyyy, HH:mm:ss"/> -
                                <c:out value="${lot.name}"/>
                            </a>
                        </form>
                    </ctg:list>
                </div>
            </c:if>
            <c:if test="${requestScope.lotListRemoved.size > 0}">
                <span><strong><fmt:message key="profileLot.removed"/></strong></span>
                <div class="lotList">
                    <ctg:list rows="${requestScope.lotListRemoved.size}">
                        <c:set var="lot" value="${requestScope.lotListRemoved.element}"/>
                        <form id="lot${lot.id}" action="Controller" method="post">
                            <input type="hidden" name="command" value="lot_choice"/>
                            <input type="hidden" name="lotId" value="${lot.id}">
                            <a href="javascript:onClick('lot${lot.id}')">
                                <fmt:formatDate value="${lot.timeStart}" pattern="dd-MM-yyyy, HH:mm:ss"/> -
                                <c:out value="${lot.name}"/>
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
