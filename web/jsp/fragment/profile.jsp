<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization">
    <html>
    <head>
    </head>
    <body>
    <main>
        <div class="profile">
            <div class="choice">
                <ul>
                    <li>
                        <form class="section" id="profile" action="Controller" method="post">
                            <input type="hidden" name="command" value="profile"/>
                            <a href="javascript:onClick('profile')">
                                <span><fmt:message key="profile.profile"/></span>
                            </a>
                        </form>
                    </li>
                    <li>
                        <form class="section" id="lotHistory" action="Controller" method="post">
                            <input type="hidden" name="command" value="lot_history"/>
                            <a href="javascript:onClick('lotHistory')">
                                <span><fmt:message key="profile.lot"/></span>
                            </a>
                        </form>
                    </li>
                    <li>
                        <form class="section" id="betHistory" action="Controller" method="post">
                            <input type="hidden" name="command" value="bet_history"/>
                            <a href="javascript:onClick('betHistory')">
                                <span><fmt:message key="profile.bet"/></span>
                            </a>
                        </form>
                    </li>
                    <li>
                        <form class="section" id="logOut" action="Controller" method="post">
                            <input type="hidden" name="command" value="logOut"/>
                            <a href="javascript:onClick('logOut')">
                                <span><fmt:message key="profile.logOut"/></span>
                            </a>
                        </form>
                    </li>
                </ul>
            </div>
            <hr>
            <c:choose>
                <c:when test="${requestScope.lotHistory != null}">
                    <jsp:include page="profile/profileLot.jsp"/>
                </c:when>
                <c:when test="${requestScope.betHistory != null}">
                    <jsp:include page="profile/profileBet.jsp"/>
                </c:when>
                <c:when test="${requestScope.newLot != null}">
                    <jsp:include page="profile/profileNewLot.jsp"/>
                </c:when>
                <c:otherwise>
                    <jsp:include page="profile/profileProfile.jsp"/>
                </c:otherwise>
            </c:choose>
        </div>
    </main>
    <c:if test="${sessionScope.errorLogin != null}">
        <script type="text/javascript">
            window.onload = errorLogin;
        </script>
    </c:if>
    <c:if test="${sessionScope.errorExistLogin != null}">
        <script type="text/javascript">
            window.onload = errorExistLogin;
        </script>
    </c:if>
    <c:if test="${sessionScope.errorPwd != null}">
        <script type="text/javascript">
            window.onload = errorPwd;
        </script>
    </c:if>
    <jsp:include page="profile/profileCard.jsp"/>
    </body>
    </html>
</fmt:bundle>