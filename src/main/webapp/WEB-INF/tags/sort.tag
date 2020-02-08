<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="text" required="true" %>
<%@ attribute name="sortBy" required="true" %>
<%@ attribute name="order" required="true" %>

<a href="?${'search='.concat(param.search)}&sortBy=${sortBy}&order=${order}"
   style="${sortBy eq param.sortBy and order eq param.order ?
   'font-weight: bold; text-decoration: none;' :
   'text-decoration: none;'}">${text}</a>