package org.lsst.dm.portal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.srs.dataportal.model.DataServerConfig;
import org.srs.dataportal.model.Task;
import org.srs.dataportal.model.User;
import org.srs.dataportal.model.util.PipelineJob;
import org.srs.pipeline.client.PipelineClient;

/**
 * A task for use with SimpleSkimmer.
 *
 * @version $Id: SimpleSkimmerTask.java,v 1.24 2012/01/23 22:37:30 bvan Exp $
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
            PreparedStatement stmt = connection.prepareStatement(
                    "select id,submitter_name,submitter_email,location,short_name,description,repo_type,owner_type,"
                            + "ogname,data_release,priority,availability,accessibility,corresponding_type,corresponding_id "
                            + "from registration_request where id is null",ResultSet.FETCH_FORWARD, ResultSet.CONCUR_UPDATABLE);
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
            rs.updateString(12,request.getAvailability());
            rs.updateString(13, request.getAccessibility());
            rs.updateString(14, request.getCorrespondingType());
            //rs.updateString(15, request.getCorrespondingId());
            rs.insertRow();
            // See http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
            rs.last();
            int id = rs.getInt(1);
            rs.close();
            stmt.close();
            Map<String,String> meta = verifier.getMeta();
            if (!meta.isEmpty()) {
                // Now deal with meta-data
                stmt = connection.prepareStatement("select id,name,value from registration_metadata where id is null",
                        ResultSet.FETCH_FORWARD, ResultSet.CONCUR_UPDATABLE);
                rs = stmt.executeQuery();
                for (Map.Entry<String,String> entry : meta.entrySet()) {
                    rs.moveToInsertRow();
                    rs.updateInt(1,id);
                    rs.updateString(2, entry.getKey());
                    rs.updateString(3, entry.getValue());
                    rs.insertRow();
                }
            }
            return new PipelineJob(null, id, null, "LSST-DM", jobName);
        } catch (SQLException ex) {
            throw new RuntimeException("Error commiting registration request",ex);
        }
    }
}
