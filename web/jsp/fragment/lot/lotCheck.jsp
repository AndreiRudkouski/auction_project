<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization">
    <c:set var="BLIND_TYPE_ID" value="3"/>
    <html>
    <head>
    </head>
    <body>
    <c:choose>
        <c:when test="${lot.finish == false}">
            <div class="price"><strong><fmt:message key="lot.finish"/>
                <fmt:formatDate value="${lot.timeEnd}" pattern="dd MMMM yyyy, HH:mm:ss"/></strong>
            </div>
            <div class="price"><strong><fmt:message
                    key="lot.condition"/> ${lot.condition.name}</strong>
            </div>
            <div class="price"><strong><fmt:message key="lot.type"/> ${lot.type.name}</strong></div>
        </c:when>
        <c:otherwise>
            <div class="price"><strong><fmt:message key="lot.end"/></strong></div>
        </c:otherwise>
    </c:choose>
    <c:set var="size" value="${lot.betList.size()}"/>
    <div class="price">
        <strong>
            <c:choose>
                <c:when test="${size > 0}">
                    <c:choose>
                        <c:when test="${lot.finish == false}">
                            <c:choose>
                                <c:when test="${lot.type.id == BLIND_TYPE_ID || size == 0}">
                                    <fmt:message key="lot.priceStart"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="lot.priceCur"/>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="lot.priceEnd"/>
                        </c:otherwise>
                    </c:choose>
                    ${lot.price} <fmt:message key="lot.rub"/>
                </c:when>
                <c:otherwise>
                    <fmt:message key="lot.noneBet"/>
                </c:otherwise>
            </c:choose>
        </strong>
        <c:if test="${size == 0 || (lot.type.id == BLIND_TYPE_ID && lot.finish == false)}">
            <script type="text/javascript">
                window.onload = betZero;
            </script>
        </c:if>
        <input type="button" id="betButton" onclick="PopUpShow('betList')"
               value="<fmt:message key="lot.betQty"/> ${size}"/>
    </div>
    <c:if test="${lot.finish == false}">
        <div class="price">
            <c:if test="${lot.priceBlitz != null}">
                <strong>
                    <fmt:message key="lot.priceBlitz"/> ${lot.priceBlitz} <fmt:message key="lot.rub"/>
                </strong>
            </c:if>
        </div>
        <div class="bet">
            <form name="inputBet" onsubmit="return validateBet()" action="Controller" method="post">
                <input type="hidden" name="command" value="bet_add"/>
                <input type="hidden" name="lotId" value="${lot.id}">
                <input type="hidden" id="minBet" value="${lot.minBet}"/>
                <input type="number" step="0.01" name="amountBet" id="amountBet"
                       value="${lot.minBet}"
                       oninput="betValue(this)"
                       title="<fmt:message key="lot.minBet"/> ${lot.minBet} <fmt:message key="lot.rub"/>"/>
                <div class="bid">
                    <c:set var="user" value="${sessionScope.user}"/>
                    <c:choose>
                        <c:when test="${user == null}">
                            <input type="button" onclick="PopUpShow('authorization')"
                                   value="<fmt:message key="lot.bet"/>"/>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${sessionScope.user.id != lot.user.id && sessionScope.user.roleId != 2}">
                                    <input type="submit" value="<fmt:message key="lot.bet"/>"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="button" disabled value="<fmt:message key="lot.bet"/>"/>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </div>
            </form>
            <div>
                <span><fmt:message key="lot.minBet"/> ${lot.minBet} <fmt:message key="lot.rub"/></span>
            </div>
        </div>
    </c:if>
    <div>
        <p><strong><fmt:message key="lot.description"/></strong></p>
        <p>${lot.description}</p>
    </div>
    </body>
    </html>
</fmt:bundle>