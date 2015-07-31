package org.lsst.dm.portal;

import org.srs.groupmanager.UserInfo;

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
