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
                <c:forEach items="${sessionScope.categoryList}" var="cat">
                    <c:if test="${cat.id == lot.categoryId}">
                        <c:if test="${loc == 'be' || loc == null}">
                            <span>${cat.nameBe}</span>
                        </c:if>
                        <c:if test="${loc == 'en'}">
                            <span>${cat.nameEn}</span>
                        </c:if>
                    </c:if>
                </c:forEach>
            </a>
        </div>
        <div class="lot">
            <div class="photo"><img src="${lot.photo}" alt="${lot.name}"></div>
            <div class="lot-inner">
                <span class="title"><strong>${lot.name}</strong></span>
                <hr/>
                <c:choose>
                    <c:when test="${lot.remove == true}">
                        <div class="price"><u><strong><fmt:message key="lot.removed"/></strong></u></div>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${sessionScope.user.roleId == 2}">
                            <form action="Controller" method="post">
                                <input type="hidden" name="lotId" value="${lot.id}"/>
                                <input type="hidden" name="command" value="lot_remove"/>
                                <input id="lotRemove" type="submit" value="<fmt:message key="admin.remove"/>"/>
                            </form>
                        </c:if>
                    </c:otherwise>
                </c:choose>
                <c:if test="${sessionScope.user.roleId == 2 || lot.remove == false}">
                    <c:choose>
                        <c:when test="${lot.check == false}">
                            <jsp:include page="lotUncheck.jsp"/>
                        </c:when>
                        <c:otherwise>
                            <jsp:include page="lotCheck.jsp"/>
                        </c:otherwise>
                    </c:choose>
                </c:if>
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