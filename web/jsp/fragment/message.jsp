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
            <c:if test="${errorBalance != null}">
                <span><fmt:message key="balance"/></span>
            </c:if>
            <c:if test="${errorFinish != null}">
                <span><fmt:message key="lotFinish"/></span>
            </c:if>
            <c:if test="${betAccept != null}">
                <span><fmt:message key="betAccept"/></span>
            </c:if>
            <c:if test="${changeAccept != null}">
                <span><fmt:message key="changeAccept"/></span>
            </c:if>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>