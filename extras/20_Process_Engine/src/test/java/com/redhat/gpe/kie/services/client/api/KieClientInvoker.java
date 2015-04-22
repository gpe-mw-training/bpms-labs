package com.redhat.gpe.kie.services.client.api;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskSummary;
import org.kie.remote.client.api.RemoteRuntimeEngineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.acme.insurance.Driver;
import org.acme.insurance.Policy;

public class KieClientInvoker {

    public static final String DEPLOYMENT_ID = "deploymentId";
    public static final String DEPLOYMENT_URL = "deployment.url";
    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";
    public static final String PROCESSID = "processId";
    public static final String DRIVER_NAME = "driverName";
    public static final String AGE = "age";
    public static final String POLICY_TYPE = "policyType";
    public static final String NUMBER_OF_ACCIDENTS = "numberOfAccidents";
    public static final String NUMBER_OF_TICKETS = "numberOfTickets";
    public static final String VEHICLE_YEAR = "vehicleYear";
    public static final String CREDIT_SCORE = "creditScore";
    public static final String DRIVER_SERVICE = "driver";
    public static final String POLICY_SERVICE = "policy";
    public static final String INCLUDE_POLICY_PAYLOAD = "includePolicyPayload";

    protected static Logger log = LoggerFactory.getLogger(KieClientInvoker.class);

    private String deploymentId = "org.jbpm:Evaluation:1.0";
    private URL deploymentUrl;
    private String userId;
    private String password;
    private String processId;

    private String name;
    private int age;
    private String policyType;
    private int numAccidents;
    private int numTickets;
    private int vehicleYear;
    private int creditScore;
    private boolean includePolicyPayload;
    
    @Test
    public void remoteRestApi() {
        RuntimeEngine engine = RemoteRuntimeEngineFactory.newRestBuilder()
            .addDeploymentId(deploymentId)
            .addUserName(userId)
            .addPassword(password)
            .addUrl(deploymentUrl)
            .build();

        KieSession ksession = engine.getKieSession();
        Map<String, Object> params = new HashMap<String, Object>();

        // set domain model classes
        if(includePolicyPayload) {
            Policy policyObj = getPolicy();
            params.put(DRIVER_SERVICE, policyObj.getDriver());
            params.put(POLICY_SERVICE, policyObj);
        }
        
        ProcessInstance processInstance = ksession.startProcess(processId, params);
        log.info("Started process instance: " + processInstance + " " + (processInstance == null ? "" : processInstance.getId()));
    }

    //@Test
    public void testJaxb() throws Exception {
        Policy policyObj = getPolicy();
        JAXBContext jaxbContext = JAXBContext.newInstance(Policy.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(policyObj, sw);
        System.out.println("main() policy = "+sw.toString());
    }

    public KieClientInvoker() throws Exception {
        this.deploymentId = System.getProperty(DEPLOYMENT_ID, "git-playground");
        this.deploymentUrl = new URL(System.getProperty(DEPLOYMENT_URL, "http://zareason:8330/kie-jbpm-services/"));
        this.userId = System.getProperty(USER_ID, "jboss");
        this.password = System.getProperty(PASSWORD, "brms");
        this.processId = System.getProperty(PROCESSID, "simpleTask");

        this.name = System.getProperty(DRIVER_NAME, "alex");
        this.age = Integer.parseInt(System.getProperty(AGE, "21"));
        this.policyType = System.getProperty(POLICY_TYPE, "MASTER");
        this.numAccidents = Integer.parseInt(System.getProperty(NUMBER_OF_ACCIDENTS, "0"));
        this.numTickets = Integer.parseInt(System.getProperty(NUMBER_OF_TICKETS, "1"));
        this.vehicleYear = Integer.parseInt(System.getProperty(VEHICLE_YEAR, "2011"));
        this.creditScore = Integer.parseInt(System.getProperty(CREDIT_SCORE, "800"));
        this.includePolicyPayload = Boolean.parseBoolean(System.getProperty(INCLUDE_POLICY_PAYLOAD, "TRUE"));

        StringBuilder sBuilder = new StringBuilder("system properties =");
        sBuilder.append("\n\tdeploymentId : "+deploymentId);
        sBuilder.append("\n\tdeploymentUrl : "+deploymentUrl);
        sBuilder.append("\n\tuserId : "+userId);
        sBuilder.append("\n\tpassword : "+password);
        sBuilder.append("\n\tprocessId : "+processId);
        sBuilder.append("\n\tdriverName : "+name);
        sBuilder.append("\n\tage : "+age);
        sBuilder.append("\n\npolicyType : " +policyType);
        sBuilder.append("\n\t# accidents : "+numAccidents);
        sBuilder.append("\n\t# tickets : "+numTickets);
        sBuilder.append("\n\t# creditScore : "+creditScore);
        sBuilder.append("\n\tvehicle year : "+vehicleYear);
        sBuilder.append("\n\tincludePolicyPayload : "+includePolicyPayload);
        log.info(sBuilder.toString());
    }

    private Policy getPolicy() {
        Driver driverObj = new Driver();
        driverObj.setDriverName(name);
        driverObj.setAge(age);
        driverObj.setNumberOfAccidents(numAccidents);
        driverObj.setNumberOfTickets(numTickets);
        driverObj.setCreditScore(creditScore);
        Policy policyObj = new Policy();
        policyObj.setVehicleYear(vehicleYear);
        policyObj.setDriver(driverObj);
        policyObj.setPolicyType(policyType);
        return policyObj;
    }
}
