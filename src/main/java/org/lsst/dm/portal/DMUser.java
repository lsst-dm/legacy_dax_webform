package org.lsst.dm.portal;

import org.srs.groupmanager.UserInfo;

/**
 *
 * @author tonyj
 */
public class DMUser implements UserInfo {

    private final String username;
    private final String email;

    public static DMUser createUser(String username, String email) {
        return new DMUser(username, email);
    }

    private DMUser(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Override
    public String getFirstName() {
        return "";
    }

    @Override
    public String getLastName() {
        return "";
    }

    @Override
    public String getFullName() {
        return username;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getSlacId() {
        return username;
    }
}
