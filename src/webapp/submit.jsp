<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="dp" uri="http://srs.slac.stanford.edu/DataPortal" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>LSST Data Management Registration Submit</title>
    </head>
    <body>
        <dp:generic-submit task="${task}" var="job"/>

        <h1>LSST Data Management Registration submitted</h1>

        <p>Your registration ${job.jobName} has been submitted.</p>
        <p>You will be sent an e-mail at ${req.email} when your registration is completed.</p>
    </body>
</html>
