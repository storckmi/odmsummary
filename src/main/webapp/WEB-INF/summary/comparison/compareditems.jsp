<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8"%>

<c:if test="${(fileErrors != null && fileErrors.isEmpty() == false) || (studies != null && studies.isEmpty() == false)}">
    <div style="width: 100%; float: left;" id="comparedItems">
        <ul>
            <li><a href="#identical">Identical Items</a></li>
            <li><a href="#matching">Matching Items</a></li>
            <li><a href="#transformable">Transformable Items</a></li>
            <li><a href="#similar">Similar Items</a></li>
            <li><a href="#comparable">Comparable Items</a></li>
            <li><a href="#notcoded">Not Coded Items</a></li>
        </ul>
        <div id="identical" style="width: 100%; float: left;">
            <jsp:include page="identicalitems.jsp" flush="true"/>
        </div>
        <div id="matching" style="width: 100%; float: left;">
            <jsp:include page="matchingitems.jsp" flush="true"/>
        </div>
        <div id="transformable" style="width: 100%; float: left;">
            <jsp:include page="transformableitems.jsp" flush="true"/>
        </div>
        <div id="similar" style="width: 100%; float: left;">
            <jsp:include page="similaritems.jsp" flush="true"/>
        </div>
        <div id="comparable" style="width: 100%; float: left;">
            <jsp:include page="comparableitems.jsp" flush="true"/>
        </div>
        <div id="notcoded" style="width: 100%; float: left;">
            <jsp:include page="notcodeditems.jsp" flush="true"/>
        </div>
    </div>
</c:if>

<script type="text/javascript">
    $(document).ready(function() {
        $( "#comparedItems" ).tabs();
    });

    function tableToExcel(tableid, name, filename) {
        var table= document.getElementById(tableid);
        var html = table.outerHTML;
        document.getElementById("downloadLink").href = 'data:application/vnd.ms-excel;base64,' + btoa(html);
        document.getElementById("downloadLink").download = filename;
        document.getElementById("downloadLink").click();
    }
</script>
