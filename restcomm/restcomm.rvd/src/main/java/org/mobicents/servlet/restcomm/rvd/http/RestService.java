package org.mobicents.servlet.restcomm.rvd.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.core.Response;

import org.mobicents.servlet.restcomm.rvd.exceptions.RvdException;
import org.mobicents.servlet.restcomm.rvd.validation.ValidationReport;

public class RestService {
    Response buildErrorResponse(Response.Status httpStatus, RvdResponse.Status rvdStatus, RvdException exception) {
        RvdResponse rvdResponse = new RvdResponse(rvdStatus).setException(exception);
        return Response.status(httpStatus).entity(rvdResponse.asJson()).build();
    }

    Response buildInvalidResponse(Response.Status httpStatus, RvdResponse.Status rvdStatus, ValidationReport report ) {
        RvdResponse rvdResponse = new RvdResponse(rvdStatus).setReport(report);
        return Response.status(httpStatus).entity(rvdResponse.asJson()).build();
    }

    //Response buildInvalidResponse(Response.Status httpStatus, RvdResponse.Status rvdStatus, RvdException exception ) {
    //    RvdResponse rvdResponse = new RvdResponse(rvdStatus).setException(exception);
    //    return Response.status(httpStatus).entity(rvdResponse.asJson()).build();
    //}

    Response buildOkResponse() {
        RvdResponse rvdResponse = new RvdResponse( RvdResponse.Status.OK );
        return Response.status(Response.Status.OK).entity(rvdResponse.asJson()).build();
    }

    Response buildOkResponse(Object payload) {
        RvdResponse rvdResponse = new RvdResponse().setOkPayload(payload);
        return Response.status(Response.Status.OK).entity(rvdResponse.asJson()).build();
    }

    protected int size(InputStream stream) {
        int length = 0;
        try {
            byte[] buffer = new byte[2048];
            int size;
            while ((size = stream.read(buffer)) != -1) {
                length += size;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return length;

    }

    protected String read(InputStream stream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
            }
        }
        return sb.toString();

    }
}
