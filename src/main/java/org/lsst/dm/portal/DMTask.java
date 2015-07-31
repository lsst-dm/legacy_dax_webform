package org.lsst.dm.portal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Map;
import org.srs.dataportal.model.DataServerConfig;
import org.srs.dataportal.model.Task;
import org.srs.dataportal.model.User;
import org.srs.dataportal.model.util.PipelineJob;
import org.srs.pipeline.client.PipelineClient;
/*
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
*/

/**
 * A task for use with Data Management registration portal.
 *
 */
public class DMTask extends Task {

    private DMRequest request;
    private DMVerifier verifier;
    private DataServerConfig config;

    /* This constructor is required by the task tag */
    public DMTask(User user, DMRequest request, String clientVersion) {
        this(user, request, DataServerConfig.instance(clientVersion));
    }

    public DMTask(User user, DMRequest request, DataServerConfig config) {
        this(user, request, config, new DMVerifier(config));
    }

    private DMTask(User user, DMRequest request, DataServerConfig config, DMVerifier verifier) {
        super(config, verifier, user, request);
        this.config = config;
        this.verifier = verifier;
        this.request = request;
    }

    @Override
    public PipelineJob submitTask(PipelineClient client, String jobName) {
        // In this case we do not submit a pipeline job, we just create an 
        // entry in the database table for pending registrations.
        try (Connection connection = config.getConnection()) {
            boolean autoCommit = connection.getAutoCommit();
            try {
                connection.setAutoCommit(false);
                int id = recordRegistrationRequest(connection);
                recordOfficialRequest(connection);
                connection.commit();

                return new PipelineJob(null, id, null, "LSST-DM", jobName);
            } finally {
                connection.setAutoCommit(autoCommit);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error commiting registration request", ex);
        }
    }

    /**
     * Records the registration request (in Tony's temporary tables)
     *
     * @param connection The database
     * @return The id assigned to the record.
     * @throws SQLException
     */
    private int recordRegistrationRequest(final Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "select id,submitter_name,submitter_email,location,short_name,description,repo_type,owner_type,"
                + "ogname,data_release,priority,availability,accessibility,project,level,corresponding_type,corresponding_id "
                + "from registration_request where id is null", ResultSet.FETCH_FORWARD, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery();
        rs.moveToInsertRow();
        rs.updateString(2, request.getUserName());
        rs.updateString(3, request.getEmail());
        rs.updateString(4, request.getLocation());
        rs.updateString(5, request.getShortName());
        rs.updateString(6, request.getDescription());
        rs.updateString(7, request.getType());
        rs.updateString(8, request.getOwner());
        rs.updateString(9, request.getOgname());
        rs.updateString(10, request.getDataRelease());
        rs.updateString(11, request.getPriority());
        rs.updateString(12, request.getAvailability());
        rs.updateString(13, request.getAccessibility());
        rs.updateString(14, request.getProject());
        rs.updateString(15, request.getLevel());
        rs.updateString(16, request.getCorrespondingType());
        rs.updateString(17, request.getCorrespondingId());
        rs.insertRow();
        // See http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
        rs.last();
        int id = rs.getInt(1);
        rs.close();
        stmt.close();
        Map<String, String> meta = verifier.getMeta();
        if (!meta.isEmpty()) {
            // Now deal with meta-data
            stmt = connection.prepareStatement("select id,name,value from registration_metadata where id is null",
                    ResultSet.FETCH_FORWARD, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery();
            for (Map.Entry<String, String> entry : meta.entrySet()) {
                rs.moveToInsertRow();
                rs.updateInt(1, id);
                rs.updateString(2, entry.getKey());
                rs.updateString(3, entry.getValue());
                rs.insertRow();
            }
        }
        return id;
    }

    /**
     * Records the registration request (in Jacek's official tables)
     *
     * @param connection The database
     * @return The id assigned to the record.
     * @throws SQLException
     */
    private int recordOfficialRequest(Connection connection) throws SQLException {
        Date now = new Date(Instant.now().toEpochMilli());
        PreparedStatement stmt = connection.prepareStatement(
                "select repoId,url,shortName,description,repoType,"
                + "dataRelease ,priorityLevel ,availability,accessibility, "
                + "lsstLevel, registrationTime "
                + "from Repo where repoId is null", ResultSet.FETCH_FORWARD, ResultSet.CONCUR_UPDATABLE);
        // Note: Some columns remain unhandled for now, particularly those involving references to 
        // other tables. It is not clear that these items should really be filled in here, as opposed
        // to the process which actually handles the registration. Either way a future update will address these items.
        ResultSet rs = stmt.executeQuery();
        rs.moveToInsertRow();
        rs.updateString(2, request.getLocation());
        rs.updateString(3, request.getShortName());
        rs.updateString(4, request.getDescription());
        rs.updateString(5, request.getType());
        rs.updateString(6, request.getDataRelease());
        rs.updateString(7, request.getPriority());
        rs.updateString(8, request.getAvailability());
        rs.updateString(9, request.getAccessibility());
        rs.updateString(10, request.getLevel());
        rs.updateDate(11, now);
        rs.insertRow();
        // See http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
        rs.last();
        int id = rs.getInt(1);
        rs.close();
        stmt.close();
        Map<String, String> meta = verifier.getMeta();
        if (!meta.isEmpty()) {
            // Now deal with meta-data
            stmt = connection.prepareStatement("select repoId,theKey,theValue from RepoAnnotations where repoId is null",
                    ResultSet.FETCH_FORWARD, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery();
            for (Map.Entry<String, String> entry : meta.entrySet()) {
                rs.moveToInsertRow();
                rs.updateInt(1, id);
                rs.updateString(2, entry.getKey());
                rs.updateString(3, entry.getValue());
                rs.insertRow();
            }
        }
        return id;
    }
}
