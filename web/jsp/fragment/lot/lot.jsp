<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization">
    <c:set var="BLIND_TYPE_ID" value="3"/>
    <html>
    <head>
    </head>
    <body>
    <main>
        <c:set value="${requestScope.lot}" var="lot"/>
        <div class="category"><fmt:message key="lot.category"/>
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
                    <c:when test="${lot.check == false}">
                        <jsp:include page="lotUncheck.jsp"/>
                    </c:when>
                    <c:otherwise>
                        <jsp:include page="lotCheck.jsp"/>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <c:if test="${sessionScope.errorBet != null}">
            <script type="text/javascript">
                window.onload = errorBet;
            </script>
        </c:if>
    </main>
    <jsp:include page="../betList.jsp"/>
    </body>
    </html>
</fmt:bundle>