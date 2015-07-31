package org.lsst.dm.portal;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.srs.dataportal.model.DataServerConfig;
import org.srs.dataportal.model.Request;
import org.srs.dataportal.model.Verifier;
import org.srs.dataportal.model.VerifierMessage;

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
 * A verifier for Phosim Parallel jobs
 *
 * @version $Id: SimpleSkimmerVerifier.java,v 1.19 2012/01/23 22:37:30 bvan Exp
 * $
 */
class DMVerifier extends Verifier {

    private DMRequest request;
    private final DataServerConfig dataServer;
    Pattern emailRegexp = Pattern.compile(".+@.+\\..+");
    Pattern metaRegexp = Pattern.compile("^\\s*(\\w+)\\s*=\\s*(.*)$", Pattern.MULTILINE);
    Map<String, String> meta = new HashMap<>();

    DMVerifier(DataServerConfig config) {
        this.dataServer = config;
    }

    @Override
    public VerifierMessage.VerifyStatus verify(Request req) {
        request = (DMRequest) req;

        clear();

        if (request.getUserName().isEmpty()) {
            addVerifyMessage("SubmitterNameRequired", "Submitter's name is required", VerifierMessage.VerifyStatus.ERROR);
        }
        if (!emailRegexp.matcher(request.getEmail()).matches()) {
            addVerifyMessage("EMailInvalid", "Submitter's e-mail is invalid", VerifierMessage.VerifyStatus.ERROR);
        }
        if (request.getLocation().isEmpty()) {
            addVerifyMessage("ShortNameMissing", "Data location is missing", VerifierMessage.VerifyStatus.ERROR);
        }
        if (request.getShortName().isEmpty()) {
            addVerifyMessage("ShortNameMissing", "Short name is missing", VerifierMessage.VerifyStatus.ERROR);
        }
        if (!"production".equals(request.getOwner()) && request.getOgname().isEmpty()) {
            addVerifyMessage("OGnameMissing", "Owner or group name missing", VerifierMessage.VerifyStatus.ERROR);
        }
        if (!"None".equals(request.getCorrespondingType()) && request.getCorrespondingId().isEmpty()) {
            addVerifyMessage("CorrespondingIdMissing", "Corresponding id missing", VerifierMessage.VerifyStatus.ERROR);
        }
        String metaData = request.getMetaData();
        if (metaData != null) {
            Matcher matcher = metaRegexp.matcher(metaData);
            while (matcher.find()) {
                meta.put(matcher.group(1), matcher.group(2));
                System.out.println(matcher.group(0));
            }
            if (!matcher.hitEnd()) {
                addVerifyMessage("MalformedMetaData", "Meta data is not well formed", VerifierMessage.VerifyStatus.ERROR);
            }
        }

        return getStatus();
    }

    public Map<String, String> getMeta() {
        return meta;
    }
}
