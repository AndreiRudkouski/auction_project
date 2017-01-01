<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="ctg" %>
<fmt:bundle basename="resource.localization">
    <html>
    <head>
    </head>
    <body>
    <div>
        <div class="nameProf">
            <fmt:message key="admin.user"/>
        </div>
        <div class="dataLeft">
            <div class="userFind">
                <span>Пошук па лагину ци пароли:</span>
                <form onsubmit="return validateFind('userFind')" action="Controller" method="post">
                    <input type="hidden" name="command" value="user_search"/>
                    <input id="userFind" type="text" name="userSearch" autocomplete="off"
                           placeholder="Пошук карыстальника">
                    <input type="submit" value="Найти">
                </form>
            </div>

            <div class="userList">
                <c:if test="${requestScope.userList != null}">
                    <c:choose>
                        <c:when test="${requestScope.userList.size() != 0}">
                            <div class="userFindList">
                                <c:forEach items="${requestScope.userList}" var="user">
                                    <form id="user${user.id}" action="Controller" method="post">
                                        <input type="hidden" name="command" value="user_choice"/>
                                        <input type="hidden" name="userId" value="${user.id}">
                                        <a href="javascript:onClick('user${user.id}')">
                                            <ctg:user login="${user.login}" mail="${user.mail}"
                                                      balance="${user.balance}"/><br>
                                        </a>
                                    </form>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <span>Карыстальникау не знойдзена</span>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <c:if test="${requestScope.user != null}">
                    <c:set value="${requestScope.user}" var="user"/>
                        <span>Login: ${user.login}</span><br>
                        <span>E-mail: ${user.mail}</span><br>
                        <span>Balance: ${user.balance}</span><br>
                        <span>Ban: ${user.ban}</span><br>
                    <div class="userLot">
                        <form class="section" id="lotHistory" action="Controller" method="get">
                            <input type="hidden" name="command" value="lot_history"/>
                            <input type="hidden" name="userId" value="${user.id}">
                            <a href="javascript:onClick('lotHistory')">
                                <span><fmt:message key="profile.lot"/></span>
                            </a>
                        </form>
                    </div>
                    <div class="userBet">
                        <form class="section" id="betHistory" action="Controller" method="get">
                            <input type="hidden" name="command" value="bet_history"/>
                            <input type="hidden" name="userId" value="${user.id}">
                            <a href="javascript:onClick('betHistory')">
                                <span><fmt:message key="profile.bet"/></span>
                            </a>
                        </form>
                    </div>
                </c:if>
                <c:if test="${requestScope.lotHistory != null}">
                    <jsp:include page="../profile/profileLotList.jsp"/>
                </c:if>
                <c:if test="${requestScope.betHistory != null}">
                    <jsp:include page="../profile/profileBetList.jsp"/>
                </c:if>
            </div>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>