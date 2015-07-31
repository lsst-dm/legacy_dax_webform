<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@taglib uri="http://srs.slac.stanford.edu/DataPortal" prefix="dp" %>

<html>
   <head>
      <title><decorator:title default="LSST DM Data Registration Portal" /></title>
      <link href="http://srs.slac.stanford.edu/Commons/css/srsCommons.jsp" rel="stylesheet" type="text/css">
      <link href="css/dataserver.css" rel="stylesheet" type="text/css">
      <decorator:head />
   </head>
   <body>
      <dp:header title="Registration">
         <jsp:attribute name="version">
            DM Registration Portal ${initParam.version} |
         </jsp:attribute>
      </dp:header>
      <div class="pageBody">
         <decorator:body />
      </div>
   </body>
</html>