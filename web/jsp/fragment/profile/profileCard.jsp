<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization">
    <html>
    <head>
    </head>
    <body>
    <html>
    <head>
    </head>
    <body>
    <div class="bankCard">
        <form onsubmit="return validateCard()" name="card" action="Controller" method="post">
            <input type="hidden" id="com" name="command" value="balance_fill"/>
            <label for="cardNum"><fmt:message key="card.number"/></label><br>
            <input type="number" name="cardNum" id="cardNum" step="1" autocomplete="off" oninput="cardNumber(this)"><br>
            <div class="err" id="errCardNum"><fmt:message key="card.errNumber"/></div>
            <label for="amount"><fmt:message key="card.amount"/></label><br>
            <input type="number" name="amount" id="amount" step="0.01" autocomplete="off" oninput="betValue(this)"
                   value="10.00"><span> <fmt:message key="lot.rub"/></span>
            <div class="err" id="errAmount"><fmt:message key="card.errAmount"/></div>
            <input type="submit" value="<fmt:message key="card.accept"/>">
            <div class="lower">
                <img src="../img/site/card.png">
            </div>
        </form>
    </div>
    <c:choose>
        <c:when test="${sessionScope.errorCard != null && sessionScope.errorAmount != null}">
            <script type="text/javascript">
                window.onload = errCard;
            </script>
        </c:when>
        <c:otherwise>
            <c:if test="${sessionScope.errorCard != null}">
                <script type="text/javascript">
                    window.onload = errCardNumber;
                </script>
            </c:if>
            <c:if test="${sessionScope.errorAmount != null}">
                <script type="text/javascript">
                    window.onload = errAmount;
                </script>
            </c:if>
        </c:otherwise></c:choose>
    </body>
    </html>
</fmt:bundle>