package org.acme.insurance.policyquote;

import static org.junit.Assert.assertEquals;
import org.acme.insurance.Driver;
import org.acme.insurance.Policy;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
/**
 * This is a sample class to test some rules.
 */
public class PolicyQuoteRulesTest {
	static KieBase kbase;
	static KieSession ksession;
	static KieRuntimeLogger klogger;
	@BeforeClass
	public static void setupKsession() {
		try {
			// load up the knowledge base and create session
			ksession = readKnowledgeBase();
			klogger = KieServices.Factory.get().getLoggers().newFileLogger(ksession, "src/test/java/org/acme/insurance/policyquote/policyQuote");
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	@AfterClass
	public static void closeKsession() {
		try {
			// closing resources
			klogger.close();
			ksession.dispose();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	@Test
	public void riskyAdultsTest() {
		//now create some test data
		Driver driver= new Driver();
		driver.setAge(30);
		driver.setCreditScore(500);
		driver.setNumberOfAccidents(1);
		driver.setNumberOfTickets(0);
		Policy policy = new Policy();
		policy.setPolicyType("AUTO");
		policy.setVehicleYear(2004);
		// insert objects into working memory
		FactHandle driverFH = ksession.insert(driver);
		FactHandle policyFH = ksession.insert(policy);
		ksession.fireAllRules();
		ksession.delete(driverFH);
		ksession.delete(policyFH);
		assertEquals("Price is 300", new Integer(300), policy.getPrice());
	}
	@Test
	public void riskyYouthsTest() {
		//now create some test data
		Driver driver= new Driver();
		driver.setAge(20);
		driver.setCreditScore(500);
		driver.setNumberOfAccidents(1);
		driver.setNumberOfTickets(0);
		Policy policy = new Policy();
		policy.setPolicyType("AUTO");
		policy.setVehicleYear(2004);
		// insert objects into working memory
		FactHandle driverFH = ksession.insert(driver);
		FactHandle policyFH = ksession.insert(policy);
		ksession.fireAllRules();
		ksession.delete(driverFH);
		ksession.delete(policyFH);
		assertEquals("Price is 700", new Integer(700), policy.getPrice());
	}
	@Test
	public void safeAdultsTest() {
		//now create some test data
		Driver driver= new Driver();
		driver.setAge(30);
		driver.setCreditScore(500);
		driver.setNumberOfAccidents(0);
		driver.setNumberOfTickets(1);
		Policy policy = new Policy();
		policy.setPolicyType("AUTO");
		policy.setVehicleYear(2004);
		// insert objects into working memory
		FactHandle driverFH = ksession.insert(driver);
		FactHandle policyFH = ksession.insert(policy);
		ksession.fireAllRules();
		ksession.delete(driverFH);
		ksession.delete(policyFH);
		assertEquals("Price is 120", new Integer(120), policy.getPrice());
	}
	@Test
	public void safeYouthsTest() {
		//now create some test data
		Driver driver= new Driver();
		driver.setAge(20);
		driver.setCreditScore(500);
		driver.setNumberOfAccidents(0);
		driver.setNumberOfTickets(0);
		Policy policy = new Policy();
		policy.setPolicyType("AUTO");
		policy.setVehicleYear(2004);
		// insert objects into working memory
		FactHandle driverFH = ksession.insert(driver);
		FactHandle policyFH = ksession.insert(policy);
		ksession.fireAllRules();
		ksession.delete(driverFH);
		ksession.delete(policyFH);
		assertEquals("Price is 450", new Integer(450), policy.getPrice());
	}
	private static KieSession readKnowledgeBase() throws Exception {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession();
		return kSession;
	}
}
