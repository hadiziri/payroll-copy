package com.sonatrach.dz.etatRet.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



@Entity
@Table(name ="PAIE_ETAT_RETIMPOSABLES")
@IdClass(EtatRetId.class)
@NamedQuery(name = "EtatRet.findByPayMonth", query = "SELECT p FROM EtatRet  p  ")
public class EtatRet implements Serializable{
	@Id
	@Column(name="MOIS_EXP")
	String moisexp;
	@Id
	@Column(name="ANNEE_EXP")
	String anneeexp;
	@Id
	@Column(name="AGT_MATRICULE")
	String agtmatricule;
	@Column(name="AGT_NOM")
	String agtnom;
	@Column(name="DIV_OP")
	String divop;
	@Column(name="DIR")
	String dir;
	@Id
	@Column(name="RUB_IMP")
	String rubimp;
	@Column(name="RUB_DESIGNATION")
	String rubdesignation;
	@Id
	@Column(name="DBUL_RAPPEL")
	Integer dbulrappel;
	@Column(name="DBUL_MONTANT")
	BigDecimal dbulmontant;
	@Column(name="DIV_DESIGNATION")
	String divdes;
	@Column(name="DIR_DESIGNATION")
	String dirdes;
	@Column(name="CSTR")
	String cstr;
	@Column(name="STR_DESIGNATION")
	String strdes;
	
	public EtatRet() {
		
	}

	public String getMoisexp() {
		return moisexp;
	}

	public void setMoisexp(String moisexp) {
		this.moisexp = moisexp;
	}

	public String getAnneeexp() {
		return anneeexp;
	}

	public void setAnneeexp(String anneeexp) {
		this.anneeexp = anneeexp;
	}

	public String getAgtmatricule() {
		return agtmatricule;
	}

	public void setAgtmatricule(String agtmatricule) {
		this.agtmatricule = agtmatricule;
	}

	public String getAgtnom() {
		return agtnom;
	}

	public void setAgtnom(String agtnom) {
		this.agtnom = agtnom;
	}

	public String getDivop() {
		return divop;
	}

	public void setDivop(String divop) {
		this.divop = divop;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getRubimp() {
		return rubimp;
	}

	public void setRubimp(String rubimp) {
		this.rubimp = rubimp;
	}

	public String getRubdesignation() {
		return rubdesignation;
	}

	public void setRubdesignation(String rubdesignation) {
		this.rubdesignation = rubdesignation;
	}

	

	public String getCstr() {
		return cstr;
	}

	public void setCstr(String cstr) {
		this.cstr = cstr;
	}

	public String getStrdes() {
		return strdes;
	}

	public void setStrdes(String strdes) {
		this.strdes = strdes;
	}

	public Integer getDbulrappel() {
		return dbulrappel;
	}

	public void setDbulrappel(Integer dbulrappel) {
		this.dbulrappel = dbulrappel;
	}

	public BigDecimal getDbulmontant() {
		return dbulmontant;
	}

	public void setDbulmontant(BigDecimal dbulmontant) {
		this.dbulmontant = dbulmontant;
	}

	public String getDivdes() {
		return divdes;
	}

	public void setDivdes(String divdes) {
		this.divdes = divdes;
	}

	public String getDirdes() {
		return dirdes;
	}

	public void setDirdes(String dirdes) {
		this.dirdes = dirdes;
	}




	
	
}
