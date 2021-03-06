<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="ctg" %>
<fmt:bundle basename="resource.localization">
    <c:set var="BLIND_TYPE_ID" value="3"/>
    <c:set var="ADMIN_ROLE" value="2"/>
    <html>
    <head>
    </head>
    <body>
    <c:if test="${lot.remove == false}">
        <form action="Controller" method="post">
            <input type="hidden" name="lotId" value="${lot.id}"/>
            <c:choose>
                <c:when test="${sessionScope.user.roleId == ADMIN_ROLE}">
                    <input type="hidden" name="command" value="lot_check"/>
                    <input id="lotEdit" type="submit" value="<fmt:message key="lot.check"/>"/>
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="command" value="lot_new"/>
                    <input id="lotEdit" type="submit" value="<fmt:message key="admin.change"/>"/>
                </c:otherwise>
            </c:choose>
        </form>
    </c:if>
    <c:if test="${sessionScope.user.roleId == ADMIN_ROLE}">
        <form id="user${lot.user.id}" action="Controller" method="post">
            <input type="hidden" name="command" value="user_choice"/>
            <input type="hidden" name="userId" value="${lot.user.id}">
            <div class="price"><strong><fmt:message key="lot.seller"/></strong>
                <a href="javascript:onClick('user${lot.user.id}')">
                    <ctg:user login="${lot.user.login}" mail="${lot.user.mail}"/><br>
                </a>
            </div>
        </form>
    </c:if>
    <div class="price"><strong><fmt:message key="lot.date"/></strong> <fmt:formatDate
            value="${lot.timeStart}" pattern="dd MMMM yyyy, HH:mm:ss"/></div>
    <div class="price"><strong><fmt:message key="lot.priceStart"/></strong> ${lot.price}
        <fmt:message key="lot.rub"/></div>
    <div class="price"><strong><fmt:message key="lot.type"/></strong> ${lot.type.name}</div>

    <div class="price"><strong><fmt:message key="newLot.priceStep"/></strong>
        <c:if test="${lot.stepPrice != null}">${lot.stepPrice} <fmt:message key="lot.rub"/></c:if>
        <c:if test="${lot.stepPrice == null}">--</c:if>
    </div>
    <div class="price"><strong><fmt:message key="lot.priceBlitz"/></strong>
        <c:if test="${lot.priceBlitz != null}">${lot.priceBlitz} <fmt:message key="lot.rub"/></c:if>
        <c:if test="${lot.priceBlitz == null}">--</c:if>
    </div>
    <div class="price"><strong><fmt:message key="newLot.term"/></strong> ${lot.term.name}
        <fmt:message key="lot.term"/></div>
    <div class="price"><strong><fmt:message key="lot.condition"/></strong> ${lot.condition.name}
    </div>
    <div>
        <p><strong><fmt:message key="lot.description"/></strong></p>
        <p>${lot.description}</p>
    </div>
    </body>
    </html>
</fmt:bundle>