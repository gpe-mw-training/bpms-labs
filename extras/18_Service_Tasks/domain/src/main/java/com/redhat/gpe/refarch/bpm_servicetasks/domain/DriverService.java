
package com.redhat.gpe.refarch.bpm_servicetasks.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for driver complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="driver">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="age" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="creditScore" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dlNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="driverName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numberOfAccidents" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numberOfTickets" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ssn" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "driverService", propOrder = {
    "age",
    "creditScore",
    "dlNumber",
    "driverName",
    "numberOfAccidents",
    "numberOfTickets",
    "ssn"
})
@javax.xml.bind.annotation.XmlRootElement(name = "driverService")
public class DriverService implements java.io.Serializable {

    public static final long serialVersionUID = 42L;

    @javax.xml.bind.annotation.XmlElement
    protected Integer age;

    @javax.xml.bind.annotation.XmlElement
    protected Integer creditScore;

    @javax.xml.bind.annotation.XmlElement
    protected String dlNumber;

    @javax.xml.bind.annotation.XmlElement
    protected String driverName;

    @javax.xml.bind.annotation.XmlElement
    protected Integer numberOfAccidents;

    @javax.xml.bind.annotation.XmlElement
    protected Integer numberOfTickets;

    @javax.xml.bind.annotation.XmlElement
    protected Integer ssn;

    public DriverService () {}

    /**
     * Gets the value of the age property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Sets the value of the age property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAge(Integer value) {
        this.age = value;
    }

    /**
     * Gets the value of the creditScore property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCreditScore() {
        return creditScore;
    }

    /**
     * Sets the value of the creditScore property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCreditScore(Integer value) {
        this.creditScore = value;
    }

    /**
     * Gets the value of the dlNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDlNumber() {
        return dlNumber;
    }

    /**
     * Sets the value of the dlNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDlNumber(String value) {
        this.dlNumber = value;
    }

    /**
     * Gets the value of the driverName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * Sets the value of the driverName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDriverName(String value) {
        this.driverName = value;
    }

    /**
     * Gets the value of the numberOfAccidents property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfAccidents() {
        return numberOfAccidents;
    }

    /**
     * Sets the value of the numberOfAccidents property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfAccidents(Integer value) {
        this.numberOfAccidents = value;
    }

    /**
     * Gets the value of the numberOfTickets property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    /**
     * Sets the value of the numberOfTickets property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfTickets(Integer value) {
        this.numberOfTickets = value;
    }

    /**
     * Gets the value of the ssn property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSsn() {
        return ssn;
    }

    /**
     * Sets the value of the ssn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSsn(Integer value) {
        this.ssn = value;
    }

    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("\n\t\tdriverName : "+driverName);
        sBuilder.append("\n\t\tdlNumber : " +dlNumber);
        sBuilder.append("\n\t\tage : "+age);
        sBuilder.append("\n\t\taccidents : " +numberOfAccidents);
        sBuilder.append("\n\t\ttickets : " +numberOfTickets);
        sBuilder.append("\n\t\tSSN : " +ssn);
        sBuilder.append("\n\t\tcreditScore : " +creditScore);
        return sBuilder.toString();
    }

}
