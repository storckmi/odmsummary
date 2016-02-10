<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8"%>

<c:if test="${fileErrors != null && fileErrors.isEmpty() == false}">
    <div id="fileErrors" style="width: 100%; float: left; margin-top: 10px;">
        <b>The following ${fileErrors.size()} files were syntactically incorrect:</b>
        <c:forEach items="${fileErrors}" var="file">
            <p style="color: red;"><b>${file.key}</b></p>
            <c:forEach items="${file.value}" var="error">
                <p>Line: ${error.getLineNumber()}, Column: ${error.getColumnNumber()}</p>
                <p>Message: ${error.getMessage()}</p>
            </c:forEach>
        </c:forEach>
    </div>            
</c:if>