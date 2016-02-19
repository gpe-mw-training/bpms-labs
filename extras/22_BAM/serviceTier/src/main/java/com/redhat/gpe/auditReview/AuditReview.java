package com.redhat.gpe.auditReview;

import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.gpe.refarch.bpm_servicetasks.domain.DriverService;
import com.redhat.gpe.refarch.bpm_servicetasks.domain.PolicyService;

@WebService(targetNamespace="urn:com.redhat.gpe.auditReview:1.0",
            serviceName="AuditReview", 
            portName="AuditReviewPort")
public class AuditReview implements IAuditReview {
    
    @PersistenceUnit(unitName="serviceTier")
    EntityManagerFactory emf;
    
    Logger log = LoggerFactory.getLogger("AuditReview");

    public boolean addPolicy(PolicyService policyObj) {
        log.info("addPolicy() policy = "+policyObj);
        if(policyObj.getPrice() > 750)
            return false;
        else
            return true;
    }
    
    
    public boolean savePolicy(PolicyService policyObj) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            DriverService driverService = policyObj.getDriver();
            em.persist(driverService);
            em.persist(policyObj);
            em.flush();
            em.getTransaction().commit();
            return true;
        }finally {
            em.close();
        }
    }

    public void sanityCheck() {
        log.info("sanityCheck() good to go");
    }
}
