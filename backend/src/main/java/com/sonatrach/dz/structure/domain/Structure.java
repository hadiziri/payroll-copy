package com.sonatrach.dz.structure.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name ="payrollstructure")
@NamedQuery(name = "Structure.findByActivity", query = "SELECT p FROM Structure p WHERE IDACTIVITY=?1")
public class Structure {
	@Id
    private Long IDSTRUCTURE;
	private Integer IDACTIVITY;
	private String STRUCTURENAME;
	private String STRUCTURECODELIKE;
	private int STRUCTURECODENOTLIKE;
	private int STATUSSTRUCTURE;
	private String EMAILGROUPMANAGERS;
	
	public Structure() {
		
	}

	public Long getIDSTRUCTURE() {
		return IDSTRUCTURE;
	}

	public void setIDSTRUCTURE(Long iDSTRUCTURE) {
		IDSTRUCTURE = iDSTRUCTURE;
	}

	

	
	public Integer getIDACTIVITY() {
		return IDACTIVITY;
	}

	public void setIDACTIVITY(Integer iDACTIVITY) {
		IDACTIVITY = iDACTIVITY;
	}

	public String getSTRUCTURENAME() {
		return STRUCTURENAME;
	}

	public void setSTRUCTURENAME(String sTRUCTURENAME) {
		STRUCTURENAME = sTRUCTURENAME;
	}



	public int getSTATUSSTRUCTURE() {
		return STATUSSTRUCTURE;
	}

	public void setSTATUSSTRUCTURE(int sTATUSSTRUCTURE) {
		STATUSSTRUCTURE = sTATUSSTRUCTURE;
	}

	public String getEMAILGROUPMANAGERS() {
		return EMAILGROUPMANAGERS;
	}

	public void setEMAILGROUPMANAGERS(String eMAILGROUPMANAGERS) {
		EMAILGROUPMANAGERS = eMAILGROUPMANAGERS;
	}

	public String getSTRUCTURECODELIKE() {
		return STRUCTURECODELIKE;
	}

	public void setSTRUCTURECODELIKE(String sTRUCTURECODELIKE) {
		STRUCTURECODELIKE = sTRUCTURECODELIKE;
	}

	public int getSTRUCTURECODENOTLIKE() {
		return STRUCTURECODENOTLIKE;
	}

	public void setSTRUCTURECODENOTLIKE(int sTRUCTURECODENOTLIKE) {
		STRUCTURECODENOTLIKE = sTRUCTURECODENOTLIKE;
	}

	

	
}
