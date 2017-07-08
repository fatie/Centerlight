package org.centerlight.attachment.changeFilelocation.fileIO;


public class FileRecord {
	
	private String fileLocation;
	private DBRecord dbRecord;
	
	public FileRecord(String fileLocation, DBRecord dbRecord){
		this.fileLocation = fileLocation;
		this.dbRecord = dbRecord;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
	public DBRecord getDbRecord() {
		return dbRecord;
	}

	public void setDbRecord(DBRecord dbRecord) {
		this.dbRecord = dbRecord;
	}

	@Override
	public String toString(){
		return (this.getDbRecord().toString() + ";File location:" + this.fileLocation);
	}
	
}
