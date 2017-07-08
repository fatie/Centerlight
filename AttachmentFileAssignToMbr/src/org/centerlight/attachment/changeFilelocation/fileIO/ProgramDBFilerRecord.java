package org.centerlight.attachment.changeFilelocation.fileIO;

public class ProgramDBFilerRecord extends DBRecord {
	private static final String TYPE = "Program";

	public ProgramDBFilerRecord(String mbrId, String pk, String mbrCurrentLOB,
			String additionalInformation, String rqstOrPgmLOB) {
		super(mbrId, pk);
		this.setType(ProgramDBFilerRecord.TYPE);
		this.setAdditionalInformation(additionalInformation);
		this.setMbrCurrentLOB(mbrCurrentLOB);
		this.setRqstOrPgmLOB(rqstOrPgmLOB);
	}
	
	@Override
	public String toString(){
		return super.toString()+ ";Program Screening Name:" + this.getAdditionalInformation() + ";Pgm LOB:" + this.getRqstOrPgmLOB();
	}

}
