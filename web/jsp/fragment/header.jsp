<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="ctg" %>
<fmt:bundle basename="resource.localization" prefix="header.">
    <html>
    <head>
        <title><fmt:message key="title"/></title>
    </head>
    <body>
    <header>
        <div class="logo">
            <div class="logo-inner">
                <a href="/auction/jsp/main.jsp" title="<fmt:message key="main"/>"><img src="/img/site/logo.png" alt="logo"></a>
            </div>
        </div>
        <div class="image">
            <div class="image-inner">
                <img src="/img/site/img-head.png" alt="auction">
            </div>
        </div>
        <div class="find">
            <div class="find-other">
                <div class="auth">
                    <c:set var="user" value="${sessionScope.user}"/>
                    <c:choose>
                        <c:when test="${user == null}">
                            <a href="javascript:PopUpShow('.authorization')" title="<fmt:message key="enter"/>">
                                <img src="/img/site/auth.png">
                                <span><fmt:message key="enter"/></span>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <form id="profile" action="Controller" method="post">
                                <input type="hidden" name="command" value="profile"/>
                                <a href="javascript:onClick('profile')">
                                    <img src="/img/site/auth.png">
                                    <span>
                                        <ctg:user login="${user.login}" mail="${user.mail}" balance="${user.balance}"/>
                                    </span>
                                </a>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="lang">
                    <form id="lang_submit" action="Controller" method="post">
                        <input type="hidden" name="command" value="locale"/>
                        <a href="javascript:onClick('lang_submit')"
                           title="<fmt:message key="lang"/>"><span><fmt:message
                                key="langChoice"/></span></a>
                    </form>
                </div>
            </div>
            <div class="find-inner">
                <form action="Controller">
                    <input type="hidden" name="command" value="lot_search"/>
                    <input type="text" name="field_search" placeholder="<fmt:message key="find"/>">
                    <input type="submit" value=" ">
                </form>
            </div>
        </div>
    </header>
    <div class="clearfix"></div>
    <jsp:include page="auth.jsp"/>
    </body>
    </html>
</fmt:bundle>