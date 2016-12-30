<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="ctg" %>
<fmt:bundle basename="resource.localization" prefix="nav.">
    <html>
    <head>
    </head>
    <body>
    <c:if test="${sessionScope.categoryList == null}">
        <c:import url="/jsp/Controller?command=setup_category"/>
    </c:if>
    <nav>
        <strong><fmt:message key="category"/></strong>
            ${sessionScope.categoryList.setupIterator()}
        <ctg:category rows="${sessionScope.categoryList.size}">
            <c:set var="category" value="${sessionScope.categoryList.category}"/>
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
        </ctg:category>
    </nav>
    </body>
    </html>
</fmt:bundle>