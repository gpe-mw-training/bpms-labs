package org.acme.insurance.monitoring;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.acme.insurance.PolicyBinding;
import org.drools.core.time.SessionPseudoClock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.api.runtime.rule.FactHandle;

/**
 * Sample JUnit Test Class for Complex Event Processing Rules
 */

public class PolicyBindingRulesTest {

	private static final String TARGET_SIZE = "targetSize";
	private static final String TARGET_POLICY_AVERAGE = "targetPolicyAverage";
	private static final String WITHIN_THRESHOLD = "withinThreshold";
	
	private static final int targetSize = 5;
	private static final int targetPolicyAverage = 200;
	
	private static final int testCaseSize = 10;
	private static final int timeAdvancementInterval = 2;
	
    static KieSession ksession;
    static KieRuntimeLogger klogger;
    static SessionPseudoClock clock;

    @BeforeClass
    public static void setupKsession() {
        try {
        	KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            ksession = kContainer.newKieSession();

            // initiate fireUntilHalt on CEP engine in a separate thread because this method blocks
            new Thread() {
                @Override
                public void run() {
                    ksession.fireUntilHalt();
                }
            }.start();
            
            clock = ksession.getSessionClock();
            klogger = KieServices.Factory.get().getLoggers().newFileLogger(ksession, "src/test/java/org/acme/insurance/monitoring/policyBindingRulesTest");

            // Set globals
            ksession.setGlobal(TARGET_SIZE, targetSize);
            ksession.setGlobal(TARGET_POLICY_AVERAGE, targetPolicyAverage);
        } catch (Throwable t) {
            t.printStackTrace();
        }
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

    /* 
     * Insert into working memeory configurable # of PolicyBinding objects whose price increments by 50.
     * Once done, halt the engine and print whether the target average price threshold has been exceeded or not
     */
    @Test
    public void thresholdTest() throws Exception {
   
        // dedicated stream for inserts
        EntryPoint stStream = ksession.getEntryPoint( "sales_team_stream" );
        
        // Advance clock by either minutes or seconds as per business requirements described in lab instructions
        //TimeUnit timeUnit = TimeUnit.MINUTES;   //Scenario1
        TimeUnit timeUnit = TimeUnit.SECONDS; //Scenario2
        System.out.println("exceedThresholdTest() Will advance kieSession clock in the following time increments: "+timeUnit);

        // create fact handle list (useful if need references to inserted facts)
        ArrayList<FactHandle> factHandleList = new ArrayList<FactHandle>();
        int pbvalue = 0;
        while(factHandleList.size() < testCaseSize){
        	PolicyBinding pBinding = new PolicyBinding(pbvalue, new Date());
        	
        	// insert objects into working memory 
            //factHandleList.add(ksession.insert(pBinding));
            factHandleList.add(stStream.insert(pBinding));
            
            // advance the clock
            clock.advanceTime(timeAdvancementInterval, timeUnit);
            
            // increment the price field of each new PolicyBinding
            pbvalue +=50;
            
            // Give the CEP engine a bit of time to process
            // otherwise, ksession.halt() will be invoked before anything meaningful has happened
            Thread.sleep(500);
        } 
        ksession.halt();

        Boolean withinThreshold = (Boolean)ksession.getGlobal(WITHIN_THRESHOLD);
        if(withinThreshold)
        	System.out.println("thresholdTest() Good news!  PolicyBinding price average did not exceed: "+targetPolicyAverage);
        else
        	System.out.println("thresholdTest() Oh-no!  PolicyBinding price average exceeded: "+targetPolicyAverage);
    }
}
