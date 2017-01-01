<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization">
    <html>
    <head>
    </head>
    <body>
    <div>
        <div class="nameProf"><fmt:message key="profile.lot"/></div>
        <div class="dataLeft">
            <div class="userFind">
                <span>Пошук па типу:</span><br>
                <form action="Controller" method="post">
                    <input type="hidden" name="command" value="lot_select"/>
                    <select name="lotSelect" onchange="this.form.submit()">
                        <option selected disabled>Выберите тип лота</option>
                        <option value="lotListFinished">Завершенные</option>
                        <option value="lotListUnfinished">Незавершенные</option>
                        <option value="lotListUnchecked">Новые</option>
                    </select>
                </form>
            </div>
            <div class="userList">
                <c:if test="${requestScope.lotSelect != null}">
                    <jsp:include page="../profile/profileLotList.jsp"/>
                </c:if>
            </div>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>