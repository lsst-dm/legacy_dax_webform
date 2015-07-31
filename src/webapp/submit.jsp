<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="dp" uri="http://srs.slac.stanford.edu/DataPortal" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
LSST Data Management System
Copyright 2015 AURA/LSST.

This product includes software developed by the
LSST Project (http://www.lsst.org/).

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the LSST License Statement and
the GNU General Public License along with this program.  If not,
see <http://www.lsstcorp.org/LegalNotices/>.

--%>

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
