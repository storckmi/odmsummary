<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8"%>

<c:if test="${studies != null && studies.isEmpty() == false}">             
    <div id="studies" style="width: 100%; float: left; margin-top: 10px;">
        <b>The following ${studies.size()} files were syntactically correct:</b>
        <c:forEach items="${studies}" var="study">
            <div style="width: 95%; float: left;">
                <p><b>File Name:</b> ${study.key}</p>
                <p><b>Study Name:</b> ${study.value.getName()}</p>
                <p><b>Study Description:</b> ${study.value.getDescription()}</p>
                <p><b>Protocol Name:</b> ${study.value.getProtocolName()}</p>
            </div>
            <table border="1" rules="all" style="width: 95%; float: left;">
                <colgroup>
                    <col width="10px" span="4">
                    <col width="200px" span="6">
                </colgroup>
                <tr>
                    <th colspan="5">Metadata Version OID</th>
                    <th>Metadata Version Name</th>
                    <th>Amount of Item Groups</th>
                    <th>Amount of Items</th>
                    <th>Amount of Codelists</th>
                    <th>Amount of Codelist Items</th>
                </tr>
                <c:forEach items="${study.value.getMetaDataVersions()}" var="metaDataVersion">
                    <tr>
                        <td colspan="5" style="text-align: left;">
                            ${metaDataVersion.getOID()}
                        </td>
                        <td style="text-align: left;">
                            ${metaDataVersion.getName()}
                        </td>
                        <td>
                            ${metaDataVersion.getItemGroupCount()}
                        </td>
                        <td>
                            ${metaDataVersion.getItemCount()}
                        </td>
                        <td>
                            ${metaDataVersion.getCodeListCount()}
                        </td>
                        <td>
                            ${metaDataVersion.getCodeListItemCount()}
                        </td>
                    </tr>
                    <c:forEach items="${metaDataVersion.getProtocols()}" var="protocol">
                        <tr>
                            <th></th>
                            <th colspan="5">
                                Protocol
                            </th>
                            <td style="text-align: left;">
                            </td>
                            <td>
                                ${metaDataVersion.getItemCount()}
                            </td>
                        </tr>
                        <tr>
                            <th colspan="2"></th>
                            <th colspan="3">Study Event OID</th>
                            <th>Study Event Name</th>
                            <th colspan="4"></th>
                        </tr>
                        <c:forEach items="${protocol.getStudyEvents()}" var="studyEvent">
                            <tr>
                                <td colspan="2"></td>
                                <td colspan="3" style="text-align: left;">
                                    ${studyEvent.getStudyEvent().getOID()}
                                </td>
                                <td style="text-align: left;">
                                    ${studyEvent.getStudyEvent().getName()}
                                </td>
                                <td>
                                    ${studyEvent.getStudyEvent().getItemGroupCount()}
                                </td>
                                <td>
                                    ${studyEvent.getStudyEvent().getItemCount()}
                                </td>
                                <td>
                                    ${studyEvent.getStudyEvent().getCodeListCount()}
                                </td>
                                <td>
                                    ${studyEvent.getStudyEvent().getCodeListItemCount()}
                                </td>
                            </tr>

                            <c:forEach items="${studyEvent.getStudyEvent().getForms()}" var="form">
                                <tr>
                                    <th colspan="3"></th>
                                    <th colspan="2">Form OID</th>
                                    <th>Form Name</th>
                                    <th colspan="4"></th>
                                </tr>
                                <tr>
                                    <td colspan="3"></td>
                                    <td colspan="2" style="text-align: left;">
                                        ${form.getForm().getOID()}
                                    </td>
                                    <td style="text-align: left;">
                                        ${form.getForm().getName()}
                                    </td>
                                    <td>
                                        ${form.getForm().getItemGroupCount()}
                                    </td>
                                    <td>
                                        ${form.getForm().getItemCount()}
                                    </td>
                                    <td>
                                        ${form.getForm().getCodeListCount()}
                                    </td>
                                    <td>
                                        ${form.getForm().getCodeListItemCount()}
                                    </td>
                                </tr>

                                <tr>
                                    <th colspan="4"></th>
                                    <th>Item Group OID</th>
                                    <th>Item Group Name</th>
                                    <th colspan="4"></th>
                                </tr>
                                <c:forEach items="${form.getForm().getItemGroups()}" var="itemGroup">
                                    <tr>
                                        <td colspan="4"></td>
                                        <td style="text-align: left;">
                                            ${itemGroup.getItemGroup().getOID()}
                                        </td>
                                        <td style="text-align: left;">
                                            ${itemGroup.getItemGroup().getName()}
                                        </td>
                                        <td></td>
                                        <td>
                                            ${itemGroup.getItemGroup().getItemCount()}
                                        </td>
                                        <td>
                                            ${itemGroup.getItemGroup().getCodeListCount()}
                                        </td>
                                        <td>
                                            ${itemGroup.getItemGroup().getCodeListItemCount()}
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:forEach>
                        </c:forEach>
                    </c:forEach>
                </c:forEach>
            </table>
        </c:forEach>
    </div>
</c:if>