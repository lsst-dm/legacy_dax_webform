<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib uri="http://srs.slac.stanford.edu/DataPortal" prefix="dp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>LSST Data Management Registration Portal</title>
    </head>
    <body>
        <c:if test="${empty req || !empty param.id}">
            <dp:createRequest var="req" className="org.lsst.dm.portal.DMRequest" userName="${userName}" historyEntry="${param.id}"/>
        </c:if>

        <h1>LSST Data Management Registration Portal</h1>

        <p>
            Welcome to LSST Data Management Registration Portal</a>.
        Use the history tab to see previous registrations.
    </p>
    <form method="POST" action="confirm.jsp">
        <dp:parameterTable>
            <dp:textRow property="jobName" title="Registration id" value="${req.jobName}" size="20">
                Arbitrary name: %u=user name, %t=job type, %n=unique id
                <dp:helpLink id="R4w0Cw"/>
            </dp:textRow>
            <dp:textRow property="userName" title="Submitter's name" value="${req.userName}" size="80">
                <br>Please enter your full name
                <dp:helpLink id="R4w0Cw"/>
            </dp:textRow>
            <dp:textRow property="email" title="Submitter's e-mail" value="${req.email}" size="80">
                <br>Please enter your e-mail address. Progress updates will be sent to this address.
            </dp:textRow>
            <dp:textRow property="location" title="Data Location" value="${req.location}" size="80">
                <br>The location (at NCSA) of the data to be imported
            </dp:textRow>
            <dp:textRow property="shortName" title="Short name of dataset" value="${req.shortName}" size="80"/>
            <dp:textRow property="description" title="Full Description of dataset" value="${req.description}" size="80" rows="5">
            </dp:textRow>
            <tr>
                <td class="key">Repo Type</td>
                <td class="value">
                    <select size="1" name="type">
                        <option ${req.type=="db"?"selected":""}>db</option>
                        <option ${req.type=="file"?"selected":""}>file</option>
                        <option ${req.type=="butler"?"selected":""}>butler</option>
                        <option ${req.type=="custom"?"selected":""}>custom</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="key">Owner Type</td>
                <td class="value">
                    <select size="1" name="owner">
                        <option ${req.owner=="production"?"selected":""}>production</option>
                        <option ${req.owner=="group"?"selected":""}>group</option>
                        <option ${req.owner=="user"?"selected":""}>user</option>
                    </select>
                </td>
            </tr>
            <dp:textRow property="ogname" title="Owner or Group name" value="${req.ogname}" size="80"/>
            <dp:textRow property="dataRelease" title="Data Release" value="${phosimRequest.dataRelease}" size="80"/>
            <tr>
                <td class="key">Priority</td>
                <td class="value">
                    <select size="1" name="priority">
                        <option ${req.priority=="scratch"?"selected":""}>scratch</option>
                        <option ${req.priority=="keepShort"?"selected":""}>keepShort</option>
                        <option ${req.priority=="keepLong"?"selected":""}>keepLong</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="key">Availability</td>
                <td class="value">
                    <select size="1" name="availability">
                        <option ${req.priority=="scratch"?"selected":""}>loading</option>
                        <option ${req.priority=="keepShort"?"selected":""}>QA</option>
                        <option ${req.priority=="keepLong"?"selected":""}>published</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="key">Accessibility</td>
                <td class="value">
                    <select size="1" name="accessibility">
                        <option ${req.priority=="scratch"?"selected":""}>public</option>
                        <option ${req.priority=="keepShort"?"selected":""}>private</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="key">Corresponding dataset type</td>
                <td class="value">
                    <select size="1" name="correspondingType">
                        <option ${req.correspondingType=="None"?"selected":""}>None</option>
                        <option ${req.correspondingType=="db"?"selected":""}>db</option>
                        <option ${req.correspondingType=="file"?"selected":""}>file</option>
                        <option ${req.correspondingType=="butler"?"selected":""}>butler</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="key">Corresponding dataset Id</td>
                <td class="value">
                    <select size="1" name="correspondingId">
                    </select>
                </td>
            </tr>
            <dp:textRow property="metaData" title="Meta-data" value="${req.metaData}" size="80" rows="5">
                <br>Add arbitrary meta-data, use name=value one per line.
            </dp:textRow>
            <tr><td>&nbsp;</td><td><input type="submit" value="Proceed" name="B1"></td></tr>
                </dp:parameterTable>
    </form>
</body>
</html>
