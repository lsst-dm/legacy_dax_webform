<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib uri="http://srs.slac.stanford.edu/DataPortal" prefix="dp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>LSST Data Management Registration Portal</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    </head>
    <body>
        <c:if test="${empty req || !empty param.id}">
            <dp:createRequest var="req" className="org.lsst.dm.portal.DMRequest" userName="${userName}" historyEntry="${param.id}"/>
        </c:if>

        <h1>LSST Data Management Registration Portal</h1>
        <p>
            Welcome to LSST Data Management Registration Portal
            Use the history tab to see previous registrations.
        </p>
        <form method="POST" action="confirm.jsp">
            <dp:parameterTable>
                <dp:textRow property="userName" title="Submitter's name <b>(*)</b>"  value="${req.userName}" size="80">
                    <br>Please enter your full name
                    <dp:helpLink id="R4w0Cw"/>
                </dp:textRow>
                <dp:textRow property="email" title="Submitter's e-mail <b>(*)</b>" value="${req.email}" size="80">
                    <br>Please enter your e-mail address. Progress updates will be sent to this address.
                </dp:textRow>
                <dp:textRow property="userComment" title="User Comment" value="${req.userComment}" size="80">
                    <br>Brief explanation of registration request.
                </dp:textRow>  
                <dp:textRow property="location" title="Data Location <b>(*)</b>" value="${req.location}" size="80">
                    <br>The location (at NCSA) of the data to be imported
                </dp:textRow>
                <dp:textRow property="shortName" title="Short name of dataset <b>(*)</b>" value="${req.shortName}" size="80"/>
                <dp:textRow property="description" title="Full Description of dataset" value="${req.description}" size="80" rows="5">
                </dp:textRow>
                <tr>
                    <td class="key">Repo Type <b>(*)</b></td>
                    <td class="value">
                        <select size="1" name="type">
                            <option ${req.type=="db"?"selected":""}>db</option>
                            <option ${req.type=="file"?"selected":""}>file</option>
                            <option ${req.type=="dir"?"selected":""}>dir</option>
                            <option ${req.type=="butler"?"selected":""}>butler</option>
                            <option ${req.type=="custom"?"selected":""}>custom</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="key">Owner Type <b>(*)</b></td>
                    <td class="value">
                        <select size="1" name="owner" onchange="ownerChanged()">
                            <option ${req.owner=="production"?"selected":""}>production</option>
                            <option ${req.owner=="group"?"selected":""}>group</option>
                            <option ${req.owner=="user"?"selected":""}>user</option>
                        </select>
                    </td>
                </tr>
                <tr id="ogname">
                    <td class="key" id="ognameLabel">Owner <b>(*)</b></td>
                    <td class="value">
                        <input type="text" name="ogname" value="${req.ogname}" size="80"/>
                    </td>
                </tr>
                <script>
                    function ownerChanged() {
                        value = $("select[name=owner] option:selected").text();
                        switch (value) {
                            case 'production':
                                $('#ogname').hide();
                                break;
                            case 'group':
                                $('#ognameLabel').html('Group <b>(*)</b>');
                                $('#ogname').show();
                                break;
                            default:
                                $('#ognameLabel').html('Owner <b>(*)</b>');
                                $('#ogname').show();
                        }
                    }
                    $(document).ready(function () {
                        ownerChanged();
                    });
                </script>
                <dp:textRow property="dataRelease" title="Data Release" value="${phosimRequest.dataRelease}" size="80"/>
                <tr>
                    <td class="key">Priority <b>(*)</b></td>
                    <td class="value">
                        <select size="1" name="priority">
                            <option ${req.priority=="scratch"?"selected":""}>scratch</option>
                            <option ${req.priority=="keepShort"?"selected":""}>keepShort</option>
                            <option ${req.priority=="keepLong"?"selected":""}>keepLong</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="key">Availability <b>(*)</b></td>
                    <td class="value">
                        <select size="1" name="availability">
                            <option ${req.priority=="scratch"?"selected":""}>loading</option>
                            <option ${req.priority=="keepShort"?"selected":""}>QA</option>
                            <option ${req.priority=="keepLong"?"selected":""}>published</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="key">Accessibility <b>(*)</b></td>
                    <td class="value">
                        <select size="1" name="accessibility">
                            <option ${req.priority=="scratch"?"selected":""}>public</option>
                            <option ${req.priority=="keepShort"?"selected":""}>private</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="key">Project <b>(*)</b></td>
                    <td class="value">
                        <select size="1" name="project">
                            <option ${req.priority=="LSST"?"selected":""}>LSST</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="key">Level <b>(*)</b></td>
                    <td class="value">
                        <select size="1" name="level">
                            <option ${req.priority=="DC"?"selected":""}>DC</option>
                            <option ${req.priority=="L1"?"selected":""}>L1</option>
                            <option ${req.priority=="L2"?"selected":""}>L2</option>
                            <option ${req.priority=="L3"?"selected":""}>L3</option>
                            <option ${req.priority=="dev"?"selected":""}>dev</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="key">Corresponding dataset type <b>(*)</b></td>
                    <td class="value">
                        <select size="1" name="correspondingType" onchange="correspondingTypeChanged()">
                            <option ${req.correspondingType=="None"?"selected":""}>None</option>
                            <option ${req.correspondingType=="db"?"selected":""}>db</option>
                            <option ${req.correspondingType=="file"?"selected":""}>file</option>
                            <option ${req.correspondingType=="dir"?"selected":""}>dir</option>
                            <option ${req.correspondingType=="butler"?"selected":""}>butler</option>
                        </select>
                    </td>
                </tr>
                <tr id="correspondingId">
                    <td class="key">Corresponding dataset Id <b>(*)</b></td>
                    <td class="value">
                        <input type="text" name="correspondingId" value="${req.correspondingId}" size="80"/>
                    </td>
                </tr>
                <script>
                    function correspondingTypeChanged() {
                        value = $("select[name=correspondingType] option:selected").text();
                        switch (value) {
                            case 'None':
                                $('#correspondingId').hide();
                                break;
                            default:
                                $('#correspondingId').show();
                        }
                    }
                    $(document).ready(function () {
                        correspondingTypeChanged();
                    });
                </script>
                <dp:textRow property="metaData" title="Meta-data" value="${req.metaData}" size="80" rows="5">
                    <br>Add arbitrary meta-data, use name=value one per line.
                </dp:textRow>
                <tr><td>&nbsp;</td><td><b>(*)</b> = required item&nbsp;&nbsp;&nbsp;<input type="submit" value="Proceed" name="B1"></td></tr>
                    </dp:parameterTable>
        </form>
    </body>
</html>
