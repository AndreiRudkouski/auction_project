<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization">
    <c:set var="COUNT_VIEW" value="3"/>
    <html>
    <head>
    </head>
    <body>
    <main>
        <c:set value="${requestScope.categoryId}" var="categoryId"/>
        <c:if test="${categoryId != null || lot_search != null}">
        <c:if test="${categoryId != null}">
        <select name="lotTypeSelect" id="lotTypeSelect" onchange="selectType('cat${categoryId}')">
            </c:if>
            <c:if test="${lot_search != null}">
            <form action="Controller" id="lotTypeSearch">
                <input type="hidden" name="command" value="lot_search"/>
                <input type="hidden" name="fieldSearch" value="${lot_search}"/>
            </form>
            <select name="lotTypeSelect" id="lotTypeSelect" onchange="selectType('lotTypeSearch')">
                </c:if>
                <option value="all" selected><fmt:message key="admin.all"/></option>
                <option value="unfinished"><fmt:message key="admin.notCompleted"/></option>
                <option value="finished"><fmt:message key="admin.completed"/></option>
            </select>
            </c:if>
            <c:if test="${categoryId != null}">
            <div class="category"><fmt:message key="lot.category"/>
                <a class="section" href="javascript:onClick('cat${categoryId}')">
                    <c:forEach items="${sessionScope.categoryList}" var="cat">
                        <c:if test="${cat.id == categoryId}">
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
            </c:if>
            <c:if test="${lot_search != null}">
            <div class="category"><fmt:message key="lot.search"/><span>${lot_search}</span></div>
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
                            <input type="hidden" name="lotChoiceType" value="${requestScope.lotChoiceType}">
                            <a class="section" href="javascript:onClick('pageBack')">
                                <span><fmt:message key="lot.back"/></span>
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
                            <input type="hidden" name="lotChoiceType" value="${requestScope.lotChoiceType}">
                            <a class="section" href="javascript:onClick('pageNext')">
                                <span><fmt:message key="lot.next"/></span>
                            </a>
                        </form>
                    </div>
                </c:if>
            </div>
            <table>
                <tbody>
                <c:set var="begin" value="0"/>
                <c:set var="end" value="${COUNT_VIEW - 1}"/>
                <c:forEach items="${lotList}" step="${COUNT_VIEW}" end="${COUNT_VIEW - 1}">
                    <tr>
                        <c:forEach items="${lotList}" var="lot" begin="${begin}" end="${end}">
                            <td>
                                <form id="lot${lot.id}" action="Controller" method="post">
                                    <input type="hidden" name="command" value="lot_choice"/>
                                    <input type="hidden" name="lotId" value="${lot.id}">
                                    <a href="javascript:onClick('lot${lot.id}')">
                                        <div class="title">${lot.name}</div>
                                        <div class="photo"><img src="${lot.photo}" alt="${lot.name}"></div>
                                        <div class="price"><fmt:message key="lot.price"/> ${lot.price} <fmt:message
                                                key="lot.rub"/></div>
                                    </a>
                                </form>
                            </td>
                        </c:forEach>
                    </tr>
                    <c:set var="begin" value="${begin + COUNT_VIEW}"/>
                    <c:set var="end" value="${end + COUNT_VIEW}"/>
                </c:forEach>
                </tbody>
            </table>
            </c:when>
            <c:otherwise>
            <c:if test="${categoryId != null}">
            <div class="category"><fmt:message key="lot.noneCategory"/></div>
            </c:if>
            <c:if test="${lot_search != null}">
            <div class="category"><fmt:message key="lot.noneSearch"/></div>
            </c:if>
            </c:otherwise>
            </c:choose>
            <c:if test="${requestScope.lotChoiceType != null}">
            <script type="text/javascript">
                window.onload = setChoiceType("${requestScope.lotChoiceType}");
            </script>
            </c:if>
    </main>
    </body>
    </html>
</fmt:bundle>