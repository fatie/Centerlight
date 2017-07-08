package org.centerlight.attachment.changeFilelocation.fileIO;

public class RequestDBFilerRecord extends DBRecord {
	private static final String TYPE = "Request";

	public RequestDBFilerRecord(String mbrId, String pk, String mbrCurrentLOB,
			String additionalInformation, String rqstOrPgmLOB) {
		super(mbrId, pk);
		this.setType(RequestDBFilerRecord.TYPE);
		this.setAdditionalInformation(additionalInformation);
		this.setMbrCurrentLOB(mbrCurrentLOB);
		this.setRqstOrPgmLOB(rqstOrPgmLOB);
	}
	
	@Override
	public String toString(){
		return super.toString()+ ";Request Type:" + this.getAdditionalInformation() + "Rqst LOB:" + this.getRqstOrPgmLOB();
	}

}
