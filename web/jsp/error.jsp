<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isErrorPage="true" %>
<fmt:bundle basename="resource.localization" prefix="error.">
    <html>
    <head>
        <head>
            <link rel="icon" type="image/x-icon" href="/img/site/favicon.ico">
            <style><c:import url="/css/style.css"/></style>
            <script src="http://code.jquery.com/jquery-2.0.2.min.js"></script>
            <script><c:import url="/js/script.js"/></script>
        </head>
    </head>
    <body>
    <c:import url="fragment/header.jsp"/>
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
    <c:import url="fragment/footer.jsp"/>
    </body>
    </html>
</fmt:bundle>