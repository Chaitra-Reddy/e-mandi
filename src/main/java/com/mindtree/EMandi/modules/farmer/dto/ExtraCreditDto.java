package com.mindtree.EMandi.modules.farmer.dto;

import org.springframework.stereotype.Component;

@Component
public class ExtraCreditDto 
{
	private int transactionId;
	private String cropClass;
	
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public String getCropClass() {
		return cropClass;
	}
	public void setCropClass(String cropClass) {
		this.cropClass = cropClass;
	}
}
