<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization" prefix="lot.">
    <c:set var="COUNT_VIEW" value="3"/>
    <html>
    <head>
    </head>
    <body>
    <main>
        <c:if test="${categoryId != null}">
            <div class="category"><fmt:message key="category"/>
                <a class="section" href="javascript:onClick('cat${categoryId}')">
                    <c:if test="${loc == 'be' || loc == null}">
                        <span>${sessionScope.categoryList.getNameBe(categoryId)}</span>
                    </c:if>
                    <c:if test="${loc == 'en'}">
                        <span>${sessionScope.categoryList.getNameEn(categoryId)}</span>
                    </c:if>
                </a>
            </div>
        </c:if>
        <c:if test="${lot_search != null}">
            <div class="category"><fmt:message key="search"/><span>${lot_search}</span></div>
        </c:if>
        <c:set var="size" value="${lotList.size()}"/>
        <c:choose>
            <c:when test="${size > 0}">
                <div class="pagination">
                    <c:if test="${pageList > 0}">
                        <div id="back" class="category">
                            <form id="pageBack" action="Controller" method="post">
                                <input type="hidden" name="command" value="page_back"/>
                                <input type="hidden" name="pageList" value="${pageList}">
                                <input type="hidden" name="categoryId" value="${categoryId}">
                                <input type="hidden" name="lot_search" value="${lot_search}">
                                <a class="section" href="javascript:onClick('pageBack')">
                                    <span><fmt:message key="back"/></span>
                                </a>
                            </form>
                        </div>
                    </c:if>
                    <c:if test="${size > COUNT_VIEW}">
                        <div class="category">
                            <form id="pageNext" action="Controller" method="post">
                                <input type="hidden" name="command" value="page_next"/>
                                <input type="hidden" name="pageList" value="${pageList}">
                                <input type="hidden" name="categoryId" value="${categoryId}">
                                <input type="hidden" name="lot_search" value="${lot_search}">
                                <a class="section" href="javascript:onClick('pageNext')">
                                    <span><fmt:message key="next"/></span>
                                </a>
                            </form>
                        </div>
                    </c:if>
                </div>
                <table>
                    <tbody>
                    <c:set var="begin" value="0"/>
                    <c:set var="end" value="2"/>
                    <c:forEach items="${lotList}" step="3" end="${COUNT_VIEW - 1}">
                        <tr>
                            <c:forEach items="${lotList}" var="lot" begin="${begin}" end="${end}">
                                <td>
                                    <form id="lot${lot.id}" action="Controller" method="get">
                                        <input type="hidden" name="command" value="lot_choice"/>
                                        <input type="hidden" name="lotId" value="${lot.id}">
                                        <a href="javascript:onClick('lot${lot.id}')">
                                            <div class="title">${lot.name}</div>
                                            <div class="photo"><img src="${lot.photo}" alt="${lot.name}"></div>
                                            <div class="price"><fmt:message key="price"/> ${lot.price} <fmt:message
                                                    key="rub"/></div>
                                        </a>
                                    </form>
                                </td>
                            </c:forEach>
                        </tr>
                        <c:set var="begin" value="${begin + 3}"/>
                        <c:set var="end" value="${end + 3}"/>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <c:if test="${categoryId != null}">
                    <div class="category"><fmt:message key="noneCategory"/></div>
                </c:if>
                <c:if test="${lot_search != null}">
                    <div class="category"><fmt:message key="noneSearch"/></div>
                </c:if>
            </c:otherwise>
        </c:choose>
    </main>
    </body>
    </html>
</fmt:bundle>