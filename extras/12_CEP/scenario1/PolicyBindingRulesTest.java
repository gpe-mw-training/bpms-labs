package org.acme.insurance.monitoring;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

import org.acme.insurance.PolicyBinding;
import org.drools.core.time.SessionPseudoClock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.api.runtime.rule.FactHandle;

/**
 * Sample JUnit Test Class for Complex Event Processing Rules
 * Uses the pseudo clock to simulate the passing of time.
 */

public class PolicyBindingRulesTest {

    static KieBase kbase;
    static KieSession ksession;
    static KieRuntimeLogger klogger;
    static SessionPseudoClock clock;

    @BeforeClass
    public static void setupKsession() {
        try {
            // load up the knowledge base
            ksession = readKnowledgeBase();
            clock = ksession.getSessionClock();
            klogger = KieServices.Factory.get().getLoggers().newFileLogger(ksession, "src/test/java/org/acme/insurance/monitoring/policyBindingRulesTest");

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static KieSession readKnowledgeBase() throws Exception {
        
        KieServices ks = KieServices.Factory.get();
        
        KieContainer kContainer = ks.getKieClasspathContainer();
        final KieSession kSession = kContainer.newKieSession();

        // initiate fireUntilHalt on CEP engine in a separate thread because this method blocks
        new Thread() {
            @Override
            public void run() {
                kSession.fireUntilHalt();
            }
        }.start();

        
        return kSession;
    }
    
    @AfterClass
    public static void closeKsession() {
        try {
            // closing resources
            if(klogger != null)
                klogger.close();
            if(ksession != null)
                ksession.dispose();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Test
    public void exceedThresholdTest() throws Exception {

        // Create policy binding list
        ArrayList<PolicyBinding> policyBindingList = new ArrayList<PolicyBinding>();
        int pbvalue = 500;

        while (policyBindingList.size() < 12) {
            policyBindingList.add(new PolicyBinding(pbvalue, new Date()));
            pbvalue += 50;
        }

        // ------------------------------------------- LAB HINT:
        // to use a separate stream for inserts
        EntryPoint pbStream = ksession.getEntryPoint( "policy_binding_stream" );
        
        // Advance clock by either minutes or seconds as per business requirements described in lab instructions
        TimeUnit timeUnit = TimeUnit.MINUTES;
        //TimeUnit timeUnit = TimeUnit.SECONDS;
        System.out.println("exceedThresholdTest() Will advance kieSession clock in the following time increments: "+timeUnit);

        // Set a String global from which to pass a status message if rule fires
        StringBuilder sBuilder = new StringBuilder();
        ksession.setGlobal("policyAverage", sBuilder);


        // /create fact handle list
        // insert objects into working memory while advancing the clock
        ArrayList<FactHandle> factHandleList = new ArrayList<FactHandle>();
        for (int i = 0; i < policyBindingList.size(); i++) {
            factHandleList.add(ksession.insert(policyBindingList.get(i)));
            //factHandleList.add(pbStream.insert(policyBindingList.get(i)));
            
            clock.advanceTime(2, timeUnit);
            //Thread.sleep(2000); 
        }        

        clock.advanceTime(7, timeUnit);


        Thread.sleep(3000);  
        ksession.halt();

        // remove facts
        for (int i = 0; i < factHandleList.size(); i++) {
        	
            //ksession.delete(factHandleList.get(i));
            pbStream.delete(factHandleList.get(i));
            
        }

        if(!sBuilder.toString().isEmpty()){
            String result = sBuilder.substring(0, 45);
            System.out.println("exceedThresholdTest Result: " + sBuilder.toString());
            assertEquals("Average Price is over 710", "Increase Reserves.  Average Price is over 710", result);
        } else {
            System.out.println("exceedThresholdTest() appears that no rules fired");
        }
    }

    @Test
    public void withinThresholdTest() throws Exception{

        // call fireUntilHalt once again (since halt() was called in previous test )
        new Thread() {
            @Override
            public void run() {
                ksession.fireUntilHalt();
            }
        }.start();


        // Create policy binding list
        ArrayList<PolicyBinding> policyBindingList = new ArrayList<PolicyBinding>();
        int pbvalue = 500;
        while (policyBindingList.size() < 12) {
            policyBindingList.add(new PolicyBinding(pbvalue, new Date()));
            pbvalue += 10;
        }

        // ------------------------------------------- LAB HINT:
        // to use a separate stream for inserts
        // EntryPoint pbStream = ksession.getEntryPoint( "policy_binding_stream" );
        
        // Advance clock by either minutes or seconds as per business requirements discussed in lab instructions
        TimeUnit timeUnit = TimeUnit.MINUTES;
        //TimeUnit timeUnit = TimeUnit.SECONDS;
        System.out.println("withinThresholdTest() Will advance kieSession clock in the following time increments: "+timeUnit);

        // Set a String global from which to pass a status message if rule fires
        StringBuilder sBuilder = new StringBuilder();
        ksession.setGlobal("policyAverage", sBuilder);

        // /create fact handle list
        // insert objects into working memory while advancing the clock
        ArrayList<FactHandle> factHandleList = new ArrayList<FactHandle>();
        for (int i = 0; i < policyBindingList.size(); i++) {
            factHandleList.add(ksession.insert(policyBindingList.get(i)));
            //factHandleList.add(pbStream.insert(policyBindingList.get(i)));
            
            clock.advanceTime(2, timeUnit);
            //Thread.sleep();
        }
        
        clock.advanceTime(7, timeUnit);

        Thread.sleep(3000);
        ksession.halt();

        // remove facts
        for (int i = 0; i < factHandleList.size(); i++) {
        	
            ksession.delete(factHandleList.get(i));
            //pbStream.delete(factHandleList.get(i));
        }

        if(!sBuilder.toString().isEmpty()){
            String result = sBuilder.substring(0, 34);
            System.out.println("withinThresholdTest Result: " + sBuilder.toString());
            assertEquals("Average Price is under 710", "Average Price under the threashold", result);
        }else {
            System.out.println("withinThresholdTest() appears that no rules fired");
        }
    }
}
