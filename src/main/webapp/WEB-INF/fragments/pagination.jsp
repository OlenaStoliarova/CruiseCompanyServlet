<%@ page pageEncoding="UTF-8" %>
<c:if test="${requestScope.total_pages > 1}">

    <c:url value="${linkForPagination}" var="linkWithOldParams">
        <c:forEach items="${param}" var="entry">
            <c:if test="${entry.key != 'size' && entry.key != 'page'}">
                <c:param name="${entry.key}" value="${entry.value}" />
            </c:if>
        </c:forEach>
    </c:url>

    <nav aria-label="Page navigation">
        <ul class="pagination">
            <c:forEach begin="1" end="${requestScope.total_pages}" var="pageNumber">
                <c:url value="${linkWithOldParams}" var="linkForPageNumber">
                    <c:param name="size" value="${requestScope.page_size}"/>
                    <c:param name="page" value="${pageNumber}"/>
                </c:url>
                <li class="<c:out value="${ pageNumber == requestScope.current_page ? 'page-item active' : 'page-item'}"/>">
                    <a class="page-link"
                       href="<c:url value='${linkForPageNumber}'/>">
                        <c:out value="${pageNumber}"/></a>
                </li>
            </c:forEach>
        </ul>
    </nav>
</c:if>