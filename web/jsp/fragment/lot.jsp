<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization" prefix="lot.">
    <c:set var="BLIND_TYPE_ID" value="3"/>
    <html>
    <head>
    </head>
    <body>
    <main>
        <div class="category"><fmt:message key="category"/>
            <a class="section" href="javascript:onClick('cat${lot.categoryId}')">
                <c:if test="${loc == 'be' || loc == null}">
                    <span>${sessionScope.categoryList.getNameBe(lot.categoryId)}</span>
                </c:if>
                <c:if test="${loc == 'en'}">
                    <span>${sessionScope.categoryList.getNameEn(lot.categoryId)}</span>
                </c:if>
            </a>
        </div>
        <div class="lot">
            <div class="photo"><img src="${lot.photo}" alt="${lot.name}"></div>
            <div class="lot-inner">
                <span class="title"><strong>${lot.name}</strong></span>

                <hr/>
                <c:choose>
                    <c:when test="${lot.finish == false}">
                        <div class="price"><strong><fmt:message key="finish"/>
                            <fmt:formatDate value="${lot.timeEnd}" pattern="dd MMMM yyyy, HH:mm:ss"/></strong>
                        </div>
                        <div class="price"><strong><fmt:message key="condition"/> ${lot.condition.name}</strong></div>
                        <div class="price"><strong><fmt:message key="type"/> ${lot.type.name}</strong></div>
                    </c:when>
                    <c:otherwise>
                        <div class="price"><strong><fmt:message key="end"/></strong></div>
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
                                                <fmt:message key="priceStart"/>

                                            </c:when>
                                            <c:otherwise>
                                                <fmt:message key="priceCur"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="priceEnd"/>
                                    </c:otherwise>
                                </c:choose>
                                ${lot.price} <fmt:message key="rub"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="noneBet"/>
                            </c:otherwise>
                        </c:choose>
                    </strong>
                    <c:if test="${size == 0 || (lot.type.id == BLIND_TYPE_ID && lot.finish == false)}">
                        <script type="text/javascript">
                            window.onload = betZero;
                        </script>
                    </c:if>
                    <input type="button" id="betButton" onclick="PopUpShow('.betList')" value="<fmt:message
                                    key="betQty"/> ${size}"/>
                </div>
                <c:if test="${lot.finish == false}">
                    <div class="price">
                        <c:if test="${lot.priceBlitz != null}">
                            <strong>
                                <fmt:message key="priceBlitz"/> ${lot.priceBlitz} <fmt:message key="rub"/>
                            </strong>
                        </c:if>
                    </div>
                    <div class="bet">
                        <form name="inputBet" onsubmit="return validateBet()" action="Controller" method="post">
                            <input type="hidden" name="command" value="bet_add"/>
                            <input type="hidden" name="lotId" value="${lot.id}">
                            <input type="hidden" id="minBet" value="${lot.minBet}"/>
                            <input type="number" step="0.01" name="amountBet" id="amountBet" value="${lot.minBet}"
                                   oninput="betValue(this)"
                                   title="<fmt:message key="minBet"/> ${lot.minBet} <fmt:message key="rub"/>"/>
                            <div class="bid">
                                <c:set var="user" value="${sessionScope.user}"/>
                                <c:choose>
                                    <c:when test="${user == null}">
                                        <input type="button" onclick="PopUpShow('.authorization')"
                                               value="<fmt:message key="bet"/>"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${sessionScope.user.id != lot.userId}">
                                                <input type="submit" value="<fmt:message key="bet"/>"/>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="button" disabled value="<fmt:message key="bet"/>"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </form>
                        <div>
                            <span><fmt:message key="minBet"/> ${lot.minBet} <fmt:message key="rub"/></span>
                        </div>
                    </div>
                </c:if>
                <div><p><strong><fmt:message key="description"/></strong></p>
                    <p>${lot.description}</p></div>
            </div>
        </div>
        <c:if test="${errorBet != null}">
            <script type="text/javascript">
                window.onload = errorBet;
            </script>
        </c:if>
        <c:if test="${errorBalance != null || errorFinish != null || betAccept != null}">
            <script type="text/javascript">
                window.onload = changeMessage;
            </script>
        </c:if>
    </main>
    <jsp:include page="betList.jsp"/>
    <jsp:include page="message.jsp"/>
    </body>
    </html>
</fmt:bundle>