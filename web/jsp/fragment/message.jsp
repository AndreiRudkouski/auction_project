<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization" prefix="message.">
    <html>
    <head>
    </head>
    <body>
    <div class="changeMessage">
        <div>
            <c:set var="wait" value="${true}"/>
            <c:if test="${sessionScope.errorBalance != null}">
                <span><fmt:message key="balance"/></span>
                <c:set var="wait" value="${false}"/>
            </c:if>
            <c:if test="${sessionScope.errorFinish != null}">
                <span><fmt:message key="lotFinish"/></span>
                <c:set var="wait" value="${false}"/>
            </c:if>
            <c:if test="${sessionScope.betAccept != null}">
                <span><fmt:message key="betAccept"/></span>
                <c:set var="wait" value="${false}"/>
            </c:if>
            <c:if test="${sessionScope.changeAccept != null}">
                <span><fmt:message key="changeAccept"/></span>
                <c:set var="wait" value="${false}"/>
            </c:if>
            <c:if test="${sessionScope.errorMessage != null}">
                <span><fmt:message key="error"/></span>
                <c:set var="wait" value="${false}"/>
            </c:if>
            <c:if test="${sessionScope.mailMessage != null}">
                <span><fmt:message key="mail"/></span>
                <c:set var="wait" value="${false}"/>
            </c:if>
            <c:if test="${wait == true}">
                <span><fmt:message key="wait"/></span>
            </c:if>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>