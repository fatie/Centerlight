package org.centerlight.attachment.changeFilelocation.fileIO;

public class MemberDBFilerRecord extends DBRecord {
	private static final String TYPE = "Member";

	public MemberDBFilerRecord(String mbrId, String pk, String mbrCurrentLOB,
			String additionalInformation, String rqstOrPgmLOB) {
		super(mbrId, pk);
		this.setType(MemberDBFilerRecord.TYPE);
		this.setAdditionalInformation(additionalInformation);
		this.setMbrCurrentLOB(mbrCurrentLOB);
		this.setRqstOrPgmLOB(rqstOrPgmLOB);
	}
	
	@Override
	public String toString(){
		return super.toString()+ ";Mbr Associated Lastest Plan:" + this.getAdditionalInformation() + ";Rqst or Pgm LOB:" + this.getRqstOrPgmLOB();
	}

}
