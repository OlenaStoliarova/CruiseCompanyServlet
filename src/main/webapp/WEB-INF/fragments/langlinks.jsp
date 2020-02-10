<%@ page pageEncoding="UTF-8" %>
<div class="langsDiv">
    <c:url value="" var="oldParams">
       <c:forEach items="${param}" var="entry">
               <c:if test="${entry.key != 'lang'}">
                   <c:param name="${entry.key}" value="${entry.value}" />
               </c:if>
       </c:forEach>
    </c:url>

    <a class="langlinks" href='<c:url value="${oldParams}"><c:param name="lang" value="en"/></c:url>'>English</a>
    &nbsp;|&nbsp;
    <a class="langlinks" href='<c:url value="${oldParams}"><c:param name="lang" value="uk"/></c:url>'>Українська</a>
</div>