<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization" prefix="message.">
    <html>
    <head>
    </head>
    <body>
    <div class="userMessage">
        <div>
            <c:set value="${sessionScope.userMessage}" var="userMessage"/>
            <c:choose>
                <c:when test="${userMessage != null}">
                    <c:if test="${userMessage == 'errorBalance'}">
                        <span><fmt:message key="balance"/></span>
                    </c:if>
                    <c:if test="${userMessage == 'errorFinish'}">
                        <span><fmt:message key="lotFinish"/></span>
                    </c:if>
                    <c:if test="${userMessage == 'betAccept'}">
                        <span><fmt:message key="betAccept"/></span>
                    </c:if>
                    <c:if test="${userMessage == 'changeAccept'}">
                        <span><fmt:message key="changeAccept"/></span>
                    </c:if>
                    <c:if test="${userMessage == 'errorMessage'}">
                        <span><fmt:message key="error"/></span>
                    </c:if>
                    <c:if test="${userMessage == 'mailMessage'}">
                        <span><fmt:message key="mail"/></span>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <span><fmt:message key="wait"/></span>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>