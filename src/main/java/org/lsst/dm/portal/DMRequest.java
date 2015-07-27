package org.lsst.dm.portal;

import org.srs.dataportal.model.Request;

/**
 * A class which encapsulates a Data Management Registration request.
 *
 * @version $Id: SimpleSkimmerRequest.java,v 1.7 2008/09/13 17:39:03 tonyj Exp $
 */
public class DMRequest extends Request {

    private boolean debug = false;
    private String userName;
    private String email;
    private String location;
    private String shortName;
    private String description;
    private String type;
    private String owner;
    private String ogname;
    private String dataRelease;
    private String priority;
    private String availability = "Published";
    private String accessibility;
    private String metaData;
    private String correspondingType;
    private String correspondingId;

    /**
     * Creates a new instance of Pruner
     */
    public DMRequest() {
        super("DM Registration");
    }

    public boolean isDebugJob() {
        return debug;
    }

    public void setDebugJob(boolean debug) {
        this.debug = debug;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOgname() {
        return ogname;
    }

    public void setOgname(String ogname) {
        this.ogname = ogname;
    }

    public String getDataRelease() {
        return dataRelease;
    }

    public void setDataRelease(String dataRelease) {
        this.dataRelease = dataRelease;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public String getCorrespondingType() {
        return correspondingType;
    }

    public void setCorrespondingType(String correspondingType) {
        this.correspondingType = correspondingType;
    }

    public String getCorrespondingId() {
        return correspondingId;
    }

    public void setCorrespondingId(String correspondingId) {
        this.correspondingId = correspondingId;
    }

}
