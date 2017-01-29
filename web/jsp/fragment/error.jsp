<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isErrorPage="true" %>
<fmt:bundle basename="resource.localization" prefix="error.">
    <html>
    <head>
    </head>
    <body>
    <main>
        <div class="category">
            <strong><fmt:message key="message"/></strong><br><br>
            Request from ${pageContext.errorData.requestURI} is failed<br>
            Servlet name: ${pageContext.errorData.servletName}<br>
            Status code: ${pageContext.errorData.statusCode}<br>
            Exception: ${pageContext.exception}<br>
            Message from exception: ${pageContext.exception.message}
        </div>
    </main>
    </body>
    </html>
</fmt:bundle>