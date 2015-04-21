package com.redhat.gpe.auditReview;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.redhat.gpe.refarch.bpm_servicetasks.domain.PolicyService;



@Path("/")
public class AuditReviewResource {
    
    private static Logger log = LoggerFactory.getLogger("AuditReview");

    //curl -v -H "Content-Type: application/x-www-form-urlencoded" -X POST -d @service/src/test/resources/policy.form.json  http://localhost:8080/auditReview/policy
    @POST
    @Path("/policy")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces({ "text/plain" })
    public Response reviewQuote(@FormParam("policyObj") String pString) {
        log.info("reviewQuote() pString = "+pString);
        ResponseBuilder builder = null;
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            PolicyService policyObj = jsonMapper.readValue(pString, PolicyService.class);
            builder = Response.ok(executeBizLogic(policyObj));
        } catch (Exception e) {
            e.printStackTrace();
            builder = Response.status(Status.INTERNAL_SERVER_ERROR);
        }
        return builder.build();
    }
    
    //curl -v -H "Content-Type: application/json" -X POST -d @service/src/test/resources/policy.json  http://localhost:8080/auditReview/policy
    //curl -v -H "Content-Type: application/xml" -X POST -d @service/src/test/resources/policy.xml  http://localhost:8080/auditReview/policy
    @POST
    @Path("/policy")
    @Consumes({"application/json","application/xml"})
    @Produces({ "text/plain" })
    public Response reviewQuote(@HeaderParam("Content-Type") String contentType, PolicyService policyObj) {
        log.info("reviewQuote() contentType = "+contentType+" : policyObj = "+policyObj);
        ResponseBuilder builder = Response.ok(executeBizLogic(policyObj));
        return builder.build();
    }
    
    private boolean executeBizLogic(PolicyService policyObj){
        if(policyObj.getPrice() > 750)
            return false;
        else
            return true;
    }
}
