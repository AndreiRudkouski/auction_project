<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization">
    <html>
    <head>
    </head>
    <body>
    <div>
        <div class="nameProf"><fmt:message key="profile.lot"/></div>
        <form id="lot${lot.id}" action="Controller" method="post">
            <input type="hidden" name="command" value="lot_new"/>
            <input id="save" type="submit" value="<fmt:message key="profileLot.newLot"/>">
        </form>
        <div class="dataLeft">
            <c:if test="${lotListFinished != null}">
                <span><strong><fmt:message key="profileLot.completed"/></strong></span>
                <div class="lotList">
                    <ul>
                        <c:forEach items="${lotListFinished}" var="lot">
                            <li>
                                <form id="lot${lot.id}" action="Controller" method="post">
                                    <input type="hidden" name="command" value="lot_choice"/>
                                    <input type="hidden" name="lotId" value="${lot.id}">
                                    <a href="javascript:onClick('lot${lot.id}')">
                                        <fmt:formatDate value="${lot.timeStart}"
                                                        pattern="dd-MM-yyyy, HH:mm:ss"/> -
                                        <c:out value="${lot.name}"/>
                                    </a>
                                </form>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>
            <c:if test="${lotListChecked != null}">
                <span><strong><fmt:message key="profileLot.notCompleted"/></strong></span>
                <ul class="lotList">
                    <c:forEach items="${lotListChecked}" var="lot">
                        <li>
                            <form id="lot${lot.id}" action="Controller" method="post">
                                <input type="hidden" name="command" value="lot_choice"/>
                                <input type="hidden" name="lotId" value="${lot.id}">
                                <a href="javascript:onClick('lot${lot.id}')">
                                    <fmt:formatDate value="${lot.timeStart}"
                                                    pattern="dd-MM-yyyy, HH:mm:ss"/> -
                                    <c:out value="${lot.name}"/>
                                </a>
                            </form>
                        </li>

                    </c:forEach>
                </ul>
            </c:if>
            <c:if test="${lotListUnchecked != null}">
                <span><strong><fmt:message key="profileLot.new"/></strong></span>
                <ul class="lotList">
                    <c:forEach items="${lotListUnchecked}" var="lot">
                        <li>
                            <form id="lot${lot.id}" action="Controller" method="post">
                                <input type="hidden" name="command" value="lot_choice"/>
                                <input type="hidden" name="lotId" value="${lot.id}">
                                <a href="javascript:onClick('lot${lot.id}')">
                                    <fmt:formatDate value="${lot.timeStart}"
                                                    pattern="dd-MM-yyyy, HH:mm:ss"/> -
                                    <c:out value="${lot.name}"/>
                                </a>
                            </form>
                        </li>

                    </c:forEach>
                </ul>
            </c:if>
            <c:if test="${lotListFinished == null && lotListChecked == null && lotListUnchecked == null}">
                <span><fmt:message key="profileLot.none"/></span>
            </c:if>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>
