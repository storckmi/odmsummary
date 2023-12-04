<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8"%>

    <div style="width: 100%; float: left;" id="about">
        <ul>
            <li><a href="#description">Description</a></li>
            <li><a href="#comparetypes">Compare Types</a></li>
            <li><a href="#datatypes">Data Types</a></li>
        </ul>
        <div id="description" style="width: 100%; float: left;">
            <jsp:include page="description.jsp" flush="true"/>
        </div>
        <div id="comparetypes" style="width: 100%; float: left;">
            <jsp:include page="comparetypes.jsp" flush="true"/>
        </div>
        <div id="datatypes" style="width: 100%; float: left;">
            <jsp:include page="datatypes.jsp" flush="true"/>
        </div>
    </div>
        
<script type="text/javascript">
    $(document).ready(function() {
        $( "#about" ).tabs();
    });
</script>