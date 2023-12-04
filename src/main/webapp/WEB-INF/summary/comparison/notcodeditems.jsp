<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8"%>

<c:set var="notCoded" value="<%=de.imi.odmtoolbox.model.ODMCompareType.NOTCODED%>"/>
<c:forEach items="${comparedItems[notCoded]}" var="comparedItem"> 
    <c:set var="form" value="${comparedItem.getForm()}"/>
    <c:if test="${form ne previousForm}">
    <c:if test="${previousForm ne null}">
        </table>
    </c:if>
    <h3 style="width: 100%;"><c:out value="Study: ${comparedItem.getForm().getStudy().getName()}, Form: ${comparedItem.getForm().getName()}"/></h3>

    <table border="1" rules="all" style="margin-bottom: 10px; width: 95%; margin-right: 5%; float: left;">
        <tr>
            <th>Unique identifier</th>
            <th>Name</th> 
            <th>Datatype</th>
        </tr>
    </c:if>
    <tr>
        <td>
            <c:out value="${comparedItem.getItem().getOID()}"/>
        </td>
        <td>
            <c:out value="${comparedItem.getItem().getName()}"/>
        </td>
        <td>
            <c:out value="${comparedItem.getItem().getDataType().toString()}"/>
        </td>
    </tr>
<c:set var="previousForm" value="${comparedItem.getForm()}"/>
</c:forEach>
    </table>