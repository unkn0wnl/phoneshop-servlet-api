<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"
         isErrorPage="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product Error Page">
    <tags:header/>
    <b>404</b>
    <p><b>Request URI:</b> ${pageContext.request.scheme}://${header.host}${pageContext.errorData.requestURI}</p>
</tags:master>
<%out.print(exception.getMessage());%>
<tags:foter/>