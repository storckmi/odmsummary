<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8"%>

<tiles:insertDefinition name="main" flush="true">
    <tiles:putAttribute name="content">
        <h1>ODM Summary</h1>
        <form style="width: 100%; float: left;" enctype="multipart/form-data" method="POST" action="summary.html">         
            <div id="odmFiles" style="width: 100%; float:left;">
                <label for="image">ODM Files:</label>
                <div style="float:left">
                    <input class="textfield" size="54" type="file" name="files" multiple/>
                </div>
            </div>
            <div style="float:left;">
                <button style="width: 100px; height: 30px;" type="submit" value="summary" id="saveButton" name="action"><b>Summary</b></button>
            </div>
        </form>
        <div style="width: 95%; float: left; margin-top: 10px" id="mainSummary">
            <ul>
                <li><a href="#aboutPages">About</a></li>
                <%-- If there are any errors --%>
                <c:if test="${(fileErrors != null && fileErrors.isEmpty() == false)}">
                    <%-- Display the error page --%>
                    <li><a href="#errorSummary">Error Summary</a></li>
                </c:if>
                <%-- If there are any studies readable without errors --%>
                <c:if test="${(studies != null && studies.isEmpty() == false)}">
                    <%-- Display the summary and compare pages --%>
                    <li><a href="#shortSummary">Short Summary</a></li>
                    <li><a href="#summary">Summary</a></li>
                    <li><a href="#comparableItems">Compared Items</a></li>
                </c:if>
        </ul>
        <div id="aboutPages">
            <jsp:include page="about/about.jsp" flush="true"/>
        </div>
        <%-- If there are any errors --%>
        <c:if test="${(fileErrors != null && fileErrors.isEmpty() == false)}">
            <%-- Add the error page to the view --%>
            <div id="errorSummary">
                <jsp:include page="errorsummary.jsp" flush="true"/>
            </div>
        </c:if>
        <%-- If there are any studies readable without errors --%>
        <c:if test="${(studies != null && studies.isEmpty() == false)}">
            <%-- Add the compare pages to the view --%>
            <div id="shortSummary">
                <jsp:include page="shortsummary.jsp" flush="true"/>
            </div>
            <div id="summary">
                <jsp:include page="summary.jsp" flush="true"/>
            </div>
            <div id="comparableItems">
                <jsp:include page="comparison/compareditems.jsp" flush="true"/>
            </div>
        </c:if>
    </tiles:putAttribute>
</tiles:insertDefinition>

<script type="text/javascript">
    $(document).ready(function() {
        $( "#mainSummary" ).tabs();
    });
        
    function showTooltip(umlsCode){
        var contextPath = "${pageContext.request.contextPath}";
        $.ajax({
            method: "POST",
            url: contextPath + "/summary/getUMLSCode.html",
            data: {umlsCode: $(umlsCode).html()},
            dataType: "html"
          }).done(function(data) {
            $(umlsCode).attr("title", data);
          });
    }
</script>
