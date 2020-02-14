<%@ page pageEncoding="UTF-8" %>
<div class="row">
    <div class="col-11">
        <c:if test="${sessionScope.session_user != null}">
            <c:if test="${sessionScope.session_user.role == 'ROLE_TOURIST'}">
                <a class="btn btn-link" href="${pageContext.request.contextPath}/app/tourist/main"><fmt:message key="ui.main.tomain" /></a>
            </c:if>
            <c:if test="${sessionScope.session_user.role == 'ROLE_TRAVEL_AGENT'}">
                <a class="btn btn-link" href="${pageContext.request.contextPath}/app/travel_agent/main"><fmt:message key="ui.main.tomain" /></a>
            </c:if>
            <c:if test="${sessionScope.session_user.role == 'ROLE_ADMIN'}">
                <a class="btn btn-link" href="${pageContext.request.contextPath}/app/admin/main"><fmt:message key="ui.main.tomain" /></a>
            </c:if>
        </c:if>
    </div>
    <div class="col-1">
        <a class="btn btn-link" href="${pageContext.request.contextPath}/app/logout"><fmt:message key="ui.main.logout" /></a>
    </div>
</div>