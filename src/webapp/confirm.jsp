<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://srs.slac.stanford.edu/GroupManager" prefix="gm" %>
<%@taglib prefix="dp" uri="http://srs.slac.stanford.edu/DataPortal" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="dm" uri="/tlds/dm" %>

<html>
    <head>
        <title>LSST Data Management Registration confirmation</title>
    </head>
    <body>
        <jsp:useBean id="req" class="org.lsst.dm.portal.DMRequest" scope="session"/>
        <jsp:setProperty name="req" property="*"/>        
        <jsp:setProperty name="req" property="jobName" value="${param.jobName}"/>
        <jsp:setProperty name="req" property="userName" value="${param.userName}"/>
        <jsp:setProperty name="req" property="email" value="${param.email}"/>
        <jsp:setProperty name="req" property="location" value="${param.location}"/>
        <jsp:setProperty name="req" property="shortName" value="${param.shortName}"/>
        <jsp:setProperty name="req" property="description" value="${param.description}"/>
        <jsp:setProperty name="req" property="type" value="${param.type}"/>
        <jsp:setProperty name="req" property="owner" value="${param.owner}"/>
        <jsp:setProperty name="req" property="ogname" value="${param.ogname}"/>
        <jsp:setProperty name="req" property="dataRelease" value="${param.dataRelease}"/>
        <jsp:setProperty name="req" property="priority" value="${param.priority}"/>
        <jsp:setProperty name="req" property="availability" value="${param.availability}"/>
        <jsp:setProperty name="req" property="accessibility" value="${param.accessibility}"/>
        <jsp:setProperty name="req" property="correspondingType" value="${param.correspondingType}"/>
        <jsp:setProperty name="req" property="correspondingId" value="${param.correspondingId}"/>
        <jsp:setProperty name="req" property="metaData" value="${param.metaData}"/>
        
        <dp:createTask var="task" className="org.lsst.dm.portal.DMTask" request="${req}" userInfo="${dm:createUser(param.userName,param.email)}" clientVersion="${initParam.version}"/>
        <h1>Confirm Registration Details</h1>

        <dp:showMessages messages="${task.messages}"/>

        <dp:parameterTable>
            <dp:displayRow title="Registration id" value="${req.jobName}" />
            <dp:displayRow title="Submitter's name" value="${req.userName}"/>
            <dp:displayRow title="Submitter's e-mail" value="${req.email}"/>
            <dp:displayRow title="Data Location" value="${req.location}"/>
            <dp:displayRow title="Short name of dataset" value="${req.shortName}" />
            <dp:displayRow title="Full Description of dataset" value="${req.description}" />
            <dp:displayRow title="Repo Type" value="${req.type}"/>
            <dp:displayRow title="Owner Type" value="${req.owner}"/>
            <dp:displayRow title="Owner or Group name" value="${req.ogname}"/>
            <dp:displayRow title="Data Release" value="${req.dataRelease}"/>
            <dp:displayRow title="Priority" value="${req.priority}"/>
            <dp:displayRow title="Availability" value="${req.availability}"/>
            <dp:displayRow title="Accessibility" value="${req.accessibility}"/>
            <dp:displayRow title="CorrespondingType" value="${req.correspondingType}"/>
            <dp:displayRow title="CorrespondingId" value="${req.correspondingId}"/>
            <tr>
                <td class="key">Meta-data</td>
                <td class="value"><pre><c:out escapeXml="true" value="${req.metaData}" default="&nbsp;"/></pre></td>
            </tr>
            <dp:backSubmitButtons backURL="index.jsp" submitURL="submit.jsp" status="${task.errorStatus}"/>
        </dp:parameterTable>
    </body>
</html>
