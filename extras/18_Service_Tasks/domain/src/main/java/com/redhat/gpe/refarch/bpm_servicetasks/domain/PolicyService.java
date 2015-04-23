
package com.redhat.gpe.refarch.bpm_servicetasks.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlTransient;


/**
 * <p>Java class for policy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="policy">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="driver" type="{urn:com.redhat.gpe.auditReview:1.0}driver" minOccurs="0"/>
 *         &lt;element name="policyType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="priceDiscount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="vehicleYear" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="processInstanceId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "policyService", propOrder = {
    "driver",
    "policyType",
    "price",
    "priceDiscount",
    "vehicleYear",
    "processInstanceId"
})
@XmlRootElement(name="Policy")
@Entity
public class PolicyService implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@XmlTransient
	protected Long id;
	
	@ManyToOne
    protected DriverService driver;
    protected String policyType;
    protected Integer price = 0;
    protected Integer priceDiscount = 0;
    protected Integer vehicleYear = 0;
    protected Long processInstanceId;

    public PolicyService() {}

    /**
     * Gets the value of the driver property.
     * 
     * @return
     *     possible object is
     *     {@link DriverService }
     *     
     */
    public DriverService getDriver() {
        return driver;
    }

    /**
     * Sets the value of the driver property.
     * 
     * @param value
     *     allowed object is
     *     {@link DriverService }
     *     
     */
    public void setDriver(DriverService value) {
        this.driver = value;
    }

    /**
     * Gets the value of the policyType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyType() {
        return policyType;
    }

    /**
     * Sets the value of the policyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyType(String value) {
        this.policyType = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPrice(Integer value) {
        this.price = value;
    }

    /**
     * Gets the value of the priceDiscount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPriceDiscount() {
        return priceDiscount;
    }

    /**
     * Sets the value of the priceDiscount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPriceDiscount(Integer value) {
        this.priceDiscount = value;
    }

    /**
     * Gets the value of the vehicleYear property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVehicleYear() {
        return vehicleYear;
    }

    /**
     * Sets the value of the vehicleYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVehicleYear(Integer value) {
        this.vehicleYear = value;
    }

    public Long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("\n\tpolicyType : " +policyType);
        sBuilder.append("\n\tprice : " +price);
        sBuilder.append("\n\tpriceDiscount : " +priceDiscount);
        sBuilder.append("\n\tprocessInstanceId : "+processInstanceId);
        sBuilder.append("\n\tvehicle year : "+vehicleYear);
        sBuilder.append("\n\tdriver : " +driver);
        return sBuilder.toString();
    }


}
