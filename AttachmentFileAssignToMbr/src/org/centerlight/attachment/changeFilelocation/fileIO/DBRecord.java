package org.centerlight.attachment.changeFilelocation.fileIO;


public abstract class DBRecord {
	
	private String mbrId;
	private String mbrCurrentLOB;
	private String pk;
	private String type;
	private String additionalInformation;
	private String rqstOrPgmLOB;
	
	public DBRecord(String mbrId, String pk){
		this.mbrId = mbrId;
		this.pk = pk;
	}

	public String getMbrId() {
		return mbrId;
	}

	public void setMbrId(String mbrId) {
		this.mbrId = mbrId;
	}
	
	

	public String getRqstOrPgmLOB() {
		return rqstOrPgmLOB;
	}

	public void setRqstOrPgmLOB(String rqstOrPgmLOB) {
		this.rqstOrPgmLOB = rqstOrPgmLOB;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getMbrCurrentLOB() {
		return mbrCurrentLOB;
	}

	public void setMbrCurrentLOB(String mbrCurrentLOB) {
		this.mbrCurrentLOB = mbrCurrentLOB;
	}

	@Override
	public String toString(){
//		return ("Type:"+ this.getType() + ";Primary Key:" + this.getPk() + ";Associated Mbr Id:" + this.getMbrId()+"; Associated Mbr Current LOB:"+ this.getMbrCurrentLOB());
		return ("Type:"+ this.getType() + ";Primary Key:" + this.getPk() + ";Associated Mbr Id:" + this.getMbrId());
	}
	
}
