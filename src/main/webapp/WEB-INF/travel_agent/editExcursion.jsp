<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<%@ include file="/WEB-INF/fragments/head.jsp" %>
<body>
<%@ include file="/WEB-INF/fragments/langlinks.jsp" %>
<%@ include file="/WEB-INF/fragments/topMenu.jsp" %>

<div class="container">
<div class="row">
    <div class="col-12">
        <c:if test="${requestScope.no_excursion_found == true}">
            <h3 class="text-danger"><fmt:message key="ui.error.excursion.not.found" /></h3>
        </c:if>
        <c:if test="${requestScope.no_excursion_found == null}">
            <div class="card">
                <div class="card-header">
                    <h3 class="card-title"><fmt:message key="ui.excursion.edit.invitation" /></h3>
                </div><br/>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/app/travel_agent/edit_excursion" method="post">
                        <input type="hidden" name="excursionId" value="${param.excursionId}">
                        <div class="row">
                            <div class="col-6">
                                <div class="form-group">
                                    <label for="nameEn"><fmt:message key="ui.excursion.name.en" /></label>
                                    <input class="form-control" id="nameEn" name="nameEn" required
                                           pattern="<fmt:message key='regex.excursion.name.en'/>" title="<fmt:message key='regex.excursion.name.en.tip'/>"
                                           value="<c:out value='${requestScope.excursion.nameEn}'/>" />
                                </div>
                                <div class="form-group">
                                    <label for="descriptionEn"><fmt:message key="ui.excursion.description.en" /></label>
                                    <textarea class="form-control" id="descriptionEn" name="descriptionEn" required
                                              rows="6"><c:out value="${requestScope.excursion.descriptionEn}"/></textarea>
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="form-group">
                                    <label for="nameUkr"><fmt:message key="ui.excursion.name.ukr" /></label>
                                    <input class="form-control" id="nameUkr" name="nameUkr" required
                                           pattern="<fmt:message key='regex.excursion.name.ukr'/>" title="<fmt:message key='regex.excursion.name.ukr.tip'/>"
                                           value="<c:out value='${requestScope.excursion.nameUkr}'/>" />
                                </div>
                                <div class="form-group">
                                    <label for="descriptionUkr"><fmt:message key="ui.excursion.description.ukr" /></label>
                                    <textarea class="form-control" id="descriptionUkr" name="descriptionUkr" required
                                              rows="6"><c:out value="${requestScope.excursion.descriptionUkr}"/></textarea>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12">
                                <div class="form-group row">
                                    <label for="approximateDurationHr" class="col-4 col-form-label"><fmt:message key="ui.excursion.duration" /></label>
                                    <div class="col-8">
                                        <input class="form-control" id="approximateDurationHr" name="approximateDurationHr" required
                                               pattern="\d{1,2}" title="<fmt:message key='regex.number.upto2digit.tip'/>"
                                               value="<c:out value='${requestScope.excursion.approximateDurationHr}'/>" />
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="priceUSD" class="col-4 col-form-label"><fmt:message key="ui.excursion.price.usd" /></label>
                                    <div class="col-8">
                                        <input class="form-control" id="priceUSD" name="priceUSD" required
                                               pattern="<fmt:message key='regex.price'/>" title="<fmt:message key='regex.price.tip'/>"
                                               value="<c:out value='${requestScope.excursion.priceUSD}'/>" />
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label for="seaportId" class="col-4 col-form-label"><fmt:message key="ui.excursion.port" /></label>
                                    <div class="col-8">
                                        <select class="form-control" id="seaportId" name="seaportId" required>
                                            <option hidden disabled selected value>-</option>
                                            <c:forEach var="seaport" items="${requestScope.all_seaports}">
                                                <option value="${seaport.id}" <c:if test="${seaport.id == requestScope.excursion.seaportId}">selected</c:if>>
                                                    <c:out value="${seaport.name}"/></option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col text-right">
                                <c:if test="${requestScope.validation_errors != null}">
                                    <h6 class="text-danger"><fmt:message key="ui.error.form.validation" /></h6>
                                </c:if>
                                <c:if test="${requestScope.non_unique == true}">
                                    <h6 class="text-danger"><fmt:message key="ui.error.add.excursion" /></h6>
                                </c:if>
                                <c:if test="${requestScope.no_port_found == true}">
                                    <h6 class="text-danger"><fmt:message key="ui.error.not.saved" /></h6>
                                    <h6 class="text-danger"><fmt:message key="ui.error.excursion.port.not.fond" /></h6>
                                </c:if>
                                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/app/travel_agent/excursions"><fmt:message key="ui.button.cancel" /></a>
                                <input type="submit" class="btn btn-success" value="<fmt:message key='ui.button.save'/>" />
                            </div>
                        </div>

                    </form>
                </div>
            </div>
        </c:if>
    </div>
</div>
</div>

</body>
</html>
