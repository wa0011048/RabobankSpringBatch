package com.rabobank.statment.batch.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.rabobank.statment.batch.util.adapter.JaxbBigDecimalAdapter;

/**
 * StatementDTO. java class - a model for the input/output object
 * @author wian0418
 *
 */
@XmlRootElement(name = "record")
public class StatementDTO {
	
	private int reference;	
	private String accountNumber;
	private String description;
	private BigDecimal startBalance;
	private BigDecimal mutation;
	private BigDecimal endBalance;
	private String errorMsg;
    
	public StatementDTO() {
		
	}
	
	public StatementDTO(int reference, String accountNumber, String description, BigDecimal startBalance,
			BigDecimal mutation, BigDecimal endBalance) {
		super();
		this.reference = reference;
		this.accountNumber = accountNumber;
		this.description = description;
		this.startBalance = startBalance;
		this.mutation = mutation;
		this.endBalance = endBalance;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	@XmlAttribute(name = "reference")
	public int getReference() {
		return reference;
	}
	public void setReference(int reference) {
		this.reference = reference;
	}
	
	@XmlElement(name = "accountNumber" )
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	@XmlElement(name = "description" )
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@XmlElement(name = "startBalance" )
	@XmlJavaTypeAdapter(type = BigDecimal.class, value = JaxbBigDecimalAdapter.class)
	public BigDecimal getStartBalance() {
		return startBalance;
	}
	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}
	
	@XmlElement(name = "mutation" )
	@XmlJavaTypeAdapter(type = BigDecimal.class, value = JaxbBigDecimalAdapter.class)
	public BigDecimal getMutation() {
		return mutation;
	}
	public void setMutation(BigDecimal mutation) {
		this.mutation = mutation;
	}
	
	@XmlElement(name = "endBalance" )
	@XmlJavaTypeAdapter(type = BigDecimal.class, value = JaxbBigDecimalAdapter.class)
	public BigDecimal getEndBalance() {
		return endBalance;
	}
	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}
	@Override
	public String toString() {
		return "StatementDTO [reference=" + reference + ", accountNumber=" + accountNumber + ", description="
				+ description + ", startBalance=" + startBalance + ", mutation=" + mutation + ", endBalance="
				+ endBalance + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + reference;
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatementDTO other = (StatementDTO) obj;
		if (reference != other.reference)
			return false;
		return true;
	}   

}
