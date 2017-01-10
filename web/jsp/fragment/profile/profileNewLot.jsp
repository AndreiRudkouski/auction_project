<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="resource.localization">
    <html>
    <head>
    </head>
    <body>
    <div class="newLot">
        <div class="nameProf"><fmt:message key="profile.newLot"/></div>
        <div class="err" id="errLot"><fmt:message key="newLot.errLot"/></div>
        <form name="newLot" onsubmit="return validateNewLot(${lot.photo})" method="post" action="Controller"
              enctype="multipart/form-data">
            <input type="hidden" name="command" value="lot_save"/>
            <c:if test="${lot != null}">
                <input type="hidden" name="lotId" value="${lot.id}"/>
                <input type="hidden" name="oldPhoto" value="${lot.photo}"/>
            </c:if>
            <input id="save" type="submit" value="<fmt:message key="profile.save"/>">
            <c:set value="${requestScope.lot}" var="lot"/>
            <div class="dataLeft">
                <span><fmt:message key="newLot.title"/></span><br>
                <input type="text" autocomplete="off" name="title" id="title" value="${lot.name}"><br>
                <div class="err" id="errTitle"><fmt:message key="newLot.errTitle"/></div>
                <span><fmt:message key="lot.category"/></span><br>
                <select name="category" id="category">
                    <option selected disabled><fmt:message key="newLot.selectCat"/></option>
                    <c:forEach items="${sessionScope.categoryList}" var="cat">
                        <option value="${cat.id}">
                            <c:if test="${loc == 'be' || loc == null}">
                                <span>${cat.nameBe}</span>
                            </c:if>
                            <c:if test="${loc == 'en'}">
                                <span>${cat.nameEn}</span>
                            </c:if>
                        </option>
                    </c:forEach>
                </select><br>
                <div class="err" id="errCategory"><fmt:message key="newLot.errEmpty"/></div>
                <span><fmt:message key="lot.priceStart"/></span><br>
                <input type="number" class="priceLot" step="0.01" name="priceStart" id="priceStart"
                       oninput="betValue(this)" value="${lot.price}"> <fmt:message key="lot.rub"/><br>
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
                <input type="number" class="priceLot" step="0.01" name="priceStep" id="priceStep"
                       value="${lot.stepPrice}"> <fmt:message key="lot.rub"/><br>
                <div class="err" id="errPriceStep"><fmt:message key="newLot.errPriceStep"/></div>
                <span><fmt:message key="lot.priceBlitz"/></span><br>
                <input type="number" class="priceLot" step="0.01" name="priceBlitz" id="priceBlitz"
                       value="${lot.priceBlitz}"> <fmt:message key="lot.rub"/><br>
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
                <textarea name="description" id="description">${lot.description}</textarea><br>
                <div class="err" id="errDescription"><fmt:message key="newLot.errEmpty"/></div>
                <div class="fileUpload">
                    <label>
                        <span><fmt:message key="newLot.selectPhoto"/></span>
                        <input type="file" name="photo" id="photo" onchange="getFileParam();" accept="image/jpeg">
                    </label>
                </div>
                <div id="preview"><img class="preview-img" src="${lot.photo}" alt="${lot.name}"></div>
                <div class="err" id="errPhoto"><fmt:message key="newLot.errPhoto"/></div>
            </div>
        </form>
    </div>
    <c:if test="${requestScope.errorLot != null}">
        <script type="text/javascript">
            window.onload = errNewLot;
        </script>
    </c:if>
    <c:if test="${lot != null}">
        <script type="text/javascript">
            window.onload = setLotCategory(${lot.categoryId});
            window.onload = setLotType(${lot.type.id});
            window.onload = setLotTerm(${lot.term.id});
            window.onload = setLotCondition(${lot.condition.id});
        </script>
    </c:if>
    </body>
    </html>
</fmt:bundle>
