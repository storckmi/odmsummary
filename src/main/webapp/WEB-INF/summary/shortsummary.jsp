<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8"%>

<c:if test="${studies != null && studies.isEmpty() == false}">             
    <div id="studies" style="width: 100%; float: left; margin-top: 10px;">
        <b>The following ${studies.size()} files were syntactically correct:</b>
        <c:forEach items="${studies}" var="study">
            <div style="width: 100%; float: left;">
                <p><b>File Name:</b> ${study.key}</p>
            </div>
            <c:forEach items="${study.value.getMetaDataVersions()}" var="metaDataVersion">
                <c:forEach items="${metaDataVersion.getProtocols()}" var="protocol">
                    <c:forEach items="${protocol.getStudyEvents()}" var="studyEvent">
                        <c:forEach items="${studyEvent.getStudyEvent().getForms()}" var="form">
                            <div style="width: 95%; float: left;">
                                <b>Form OID:</b> ${form.getForm().getOID()}, 
                                <b>Form Name:</b> ${form.getForm().getName()}, 
                                <b>Item Count:</b> ${form.getForm().getItemCount()}</div>
                            <table border="1" rules="all" style="width: 95%; float: left; margin-bottom: 10px">
                                <colgroup>
                                    <col width="200px" span="3">
                                </colgroup>
                                <tr>
                                    <th>Item Group OID</th>
                                    <th>Item Group Name</th>
                                    <th>Amount of Items</th>
                                </tr>
                                <c:forEach items="${form.getForm().getItemGroups()}" var="itemGroup">
                                    <tr>
                                        <td style="text-align: left;">
                                            ${itemGroup.getItemGroup().getOID()}
                                        </td>
                                        <td style="text-align: left;">
                                            ${itemGroup.getItemGroup().getName()}
                                        </td>
                                        <td>
                                            ${itemGroup.getItemGroup().getItemCount()}
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:forEach>
                    </c:forEach>
                </c:forEach>
            </c:forEach>
        </c:forEach>
    </div>
</c:if>