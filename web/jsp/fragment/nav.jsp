<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization" prefix="nav.">
    <html>
    <head>
    </head>
    <body>
    <nav>
        <strong><fmt:message key="category"/></strong>
        <ul>
            <c:forEach var="category" items="${sessionScope.categoryList}">
                <li>
                    <form class="section" id="cat${category.id}" action="Controller" method="get">
                        <input type="hidden" name="command" value="category_choice"/>
                        <input type="hidden" name="categoryId" value="${category.id}">
                        <a href="javascript:onClick('cat${category.id}')">
                            <c:if test="${loc == 'be' || loc == null}">
                                <span>${category.nameBe}</span>
                            </c:if>
                            <c:if test="${loc == 'en'}">
                                <span>${category.nameEn}</span>
                            </c:if>
                        </a>
                    </form>
                </li>
            </c:forEach>
        </ul>
    </nav>
    </body>
    </html>
</fmt:bundle>