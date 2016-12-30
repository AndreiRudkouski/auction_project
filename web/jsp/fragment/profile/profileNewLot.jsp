<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="ctg" %>
<fmt:bundle basename="resource.localization">
    <html>
    <head>
    </head>
    <body>
    <div class="newLot">
        <div class="nameProf"><fmt:message key="profile.newLot"/></div>
        <div class="err" id="errLot"><fmt:message key="newLot.errLot"/></div>
        <form name="newLot" onsubmit="return validateNewLot()" method="post" action="Controller"
              enctype="multipart/form-data">
            <input type="hidden" name="command" value="lot_save"/>
            <input id="save" type="submit" value="<fmt:message key="profile.save"/>">
            <div class="dataLeft">
                <span><fmt:message key="newLot.title"/></span><br>
                <input type="text" autocomplete="off" name="title" id="title"><br>
                <div class="err" id="errTitle"><fmt:message key="newLot.errTitle"/></div>
                <span><fmt:message key="lot.category"/></span><br>
                <select name="category" id="category">
                    <option selected disabled><fmt:message key="newLot.selectCat"/></option>
                        ${sessionScope.categoryList.setupIterator()}
                    <ctg:category rows="${sessionScope.categoryList.size}">
                        <c:set var="cat" value="${sessionScope.categoryList.category}"/>
                        <option value="${cat.id}">
                            <c:if test="${loc == 'be' || loc == null}">
                                <span>${cat.nameBe}</span>
                            </c:if>
                            <c:if test="${loc == 'en'}">
                                <span>${cat.nameEn}</span>
                            </c:if>
                        </option>
                    </ctg:category>
                </select><br>
                <div class="err" id="errCategory"><fmt:message key="newLot.errEmpty"/></div>
                <span><fmt:message key="lot.priceStart"/></span><br>
                <input type="number" class="priceLot" step="0.01" name="priceStart" id="priceStart"
                       oninput="betValue(this)"> <fmt:message key="lot.rub"/><br>
                <div class="err" id="errPriceStart"><fmt:message key="newLot.errPrice"/></div>
                <span><fmt:message key="lot.type"/></span><br>
                <select name="type" id="type" onchange="javascript:changeActionType()">
                    <option selected disabled><fmt:message key="newLot.selectType"/></option>
                    <c:forEach items="${requestScope.typeList}" var="type">
                        <option value="${type.id}"><span>${type.name}</span></option>
                    </c:forEach>
                </select><br>
                <div class="err" id="errType"><fmt:message key="newLot.errEmpty"/></div>
                <span><fmt:message key="newLot.priceStep"/></span><br>
                <input type="number" class="priceLot" step="0.01" name="priceStep" id="priceStep"> <fmt:message key="lot.rub"/><br>
                <div class="err" id="errPriceStep"><fmt:message key="newLot.errPriceStep"/></div>
                <span><fmt:message key="lot.priceBlitz"/></span><br>
                <input type="number" class="priceLot" step="0.01" name="priceBlitz" id="priceBlitz"> <fmt:message key="lot.rub"/><br>
                <div class="err" id="errPriceBlitz"><fmt:message key="newLot.errPrice"/></div>
                <span><fmt:message key="newLot.term"/></span><br>
                <select name="term" id="term">
                    <option selected disabled><fmt:message key="newLot.selectTerm"/></option>
                    <c:forEach items="${requestScope.termList}" var="term">
                        <option value="${term.id}"><span>${term.name}</span></option>
                    </c:forEach>
                </select><br>
                <div class="err" id="errTerm"><fmt:message key="newLot.errEmpty"/></div>
                <span><fmt:message key="lot.condition"/></span><br>
                <select name="condition" id="condition">
                    <option selected disabled><fmt:message key="newLot.selectCond"/></option>
                    <c:forEach items="${requestScope.condList}" var="cond">
                        <option value="${cond.id}"><span>${cond.name}</span></option>
                    </c:forEach>
                </select>
                <div class="err" id="errCondition"><fmt:message key="newLot.errEmpty"/></div>
            </div>
            <div class="dataRight">
                <span><fmt:message key="lot.description"/></span><br>
                <textarea name="description" id="description"></textarea><br>
                <div class="err" id="errDescription"><fmt:message key="newLot.errEmpty"/></div>
                <div class="fileUpload">
                    <label>
                        <span><fmt:message key="newLot.selectPhoto"/></span>
                        <input type="file" name="photo" id="photo" onchange="getFileParam();" accept="image/jpeg">
                    </label>
                </div>
                <div id="preview">&nbsp;</div>
                <div class="err" id="errPhoto"><fmt:message key="newLot.errPhoto"/></div>
            </div>
        </form>
    </div>
    <c:if test="${requestScope.errorLot != null}">
        <script type="text/javascript">
            window.onload = errNewLot;
        </script>
    </c:if>
    </body>
    </html>
</fmt:bundle>
