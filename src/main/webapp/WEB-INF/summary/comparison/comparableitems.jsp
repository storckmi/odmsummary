<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8"%>

<c:set var="formWidth" value="${tableStructure.getComparableFormWidth()}"/>
<c:if test="${studyForms != null && studyForms.isEmpty() == false && formWidth != null && formWidth.isEmpty() == false}">
    <div id="studyForms" style="width: 100%; float: left; margin-top: 10px;">

        <a id="downloadLink"  style="display:none;"></a>
        <button style="margin-bottom: 10px;" onclick="tableToExcel('exportComparableTable', 'ComparableItems', 'ComparableItems.xls')">Export to Excel</button>
        (only works in Firefox 28+ and Google Chrome 35+)
        <table id="exportComparableTable" border="1" rules="all" style="width: 95%; float: left;">
            <c:set var="tableWidth" value="0"/>
            <c:forEach items="${formWidth}" var="structureItem">
                <c:set var="tableWidth" value="${tableWidth + structureItem.value}"/> 
            </c:forEach>
            <colgroup>
                <col width="250px" span="${tableWidth + 3}">
            </colgroup>
            <tr>
                <th rowspan="2">Item</th>
                <th rowspan="2">UMLS Codelist</th>
                <c:forEach items="${studyForms}" var="studyForm">
                    <c:set var="studyWidth" value="0"/>
                    <c:forEach items="${studyForm.value}" var="form">
                        <c:set var="studyWidth" value="${formWidth[form.toString()] + studyWidth}"/>
                    </c:forEach>
                    <th title="Study" colspan="${studyWidth}">${studyForm.key.getName()}</th>
                </c:forEach>
                <th rowspan="2">Number of marked forms</th>
            </tr>
            <tr>
                <c:forEach items="${studyForms}" var="studyForm">
                    <c:forEach items="${studyForm.value}" var="form">
                        <th title="Form" colspan="${formWidth[form.toString()]}">
                            ${form.getName()}
                        </th>
                    </c:forEach>
                </c:forEach>
            </tr>
            <c:set var="comparable" value="<%=de.imi.odmtoolbox.model.ODMCompareType.COMPARABLE%>"/>
            <c:forEach items="${comparedItems[comparable]}" var="comparedItem"> 
                <tr>
                    <td>
                        <span title="<c:out value="Form: ${comparedItem.getForm().getName()}"/>&#10;<c:out value="Unique identifier: ${comparedItem.getItem().getOID()}"/>&#10;<c:out value="Datatype: ${comparedItem.getItem().getDataType().toString()}"/>">
                            <c:out value="Name: ${comparedItem.getItem().getName()}"/>
                        </span>
                    </td>
                    <td>
                        <c:forEach items="${comparedItem.getItem().getUmlsCodeList()}" var="umlsCode">
                            <span onmouseover="showTooltip($(this))"><c:out value="${umlsCode.getCode()}"/></span>
                        </c:forEach>
                    </td>
                    <c:set var="formCount" value="0"/> 
                    <c:forEach items="${comparedItem.getComparableFormItems()}" var="form">
                        <c:if test="${form.value.size() eq 0}">
                            <td colspan="${formWidth[form.key.toString()]}"></td>
                        </c:if>
                        <c:if test="${form.value.size() > 0}">
                            <c:set var="formCount" value="${formCount +1}"/> 
                        </c:if>
                        <c:forEach items="${form.value}" var="item"> 
                            <td colspan="${formWidth[form.key.toString()] / form.value.size() }">
                                <span title="Unique identifier: ${item.getOID()}&#10;Name: ${item.getName()}&#10;Datatype: ${item.getDataType().toString()}">
                                    x
                                </span>
                            </td>
                        </c:forEach>
                    </c:forEach>
                    <td><c:out value="${formCount}"/></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>
<c:if test="${studyForms == null || studyForms.isEmpty() == true || formWidth == null || formWidth.isEmpty() == true}">
    <p>The submitted ODM-Forms do not contain any comparable items.</p>
</c:if>