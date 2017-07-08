package org.centerlight.attachment.changeFilelocation.fileIO;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileOutput {
	private static final Logger logger = LogManager.getLogger(FileOutput.class);
	private final static String TARGETFOLDER = "C:\\toILS";
	private File fileFolder;
	private FileRecords fileRecords;
	private int counter = 0;
		
	public FileOutput(FileRecords fileRecords) throws IOException{
		this(TARGETFOLDER, fileRecords);
		
	}
	public FileOutput(String folderPath, FileRecords fileRecords) throws IOException{
		logger.info("initiating file transferring by file output class...");
		this.fileFolder = new File(folderPath);
		this.fileRecords = fileRecords;
	}
	
	public void transferAllToSublevel(){
		logger.info("moving...");
		for (FileRecord fileRecord: this.fileRecords){
			if(fileRecord.getDbRecord().getMbrCurrentLOB() !=null){
				if(fileRecord.getDbRecord().getMbrCurrentLOB().equals("PACE")){
					if((!fileRecord.getDbRecord().getAdditionalInformation().contains("SELECT Comp")) && (!fileRecord.getDbRecord().getAdditionalInformation().contains("FIDA Comp")) && (!fileRecord.getDbRecord().getAdditionalInformation().contains("FIDA Comp"))){
						if(!fileRecord.getDbRecord().getRqstOrPgmLOB().contains("SELECT")&& (!fileRecord.getDbRecord().getRqstOrPgmLOB().contains("FIDA")) &&(!fileRecord.getDbRecord().getRqstOrPgmLOB().contains("DIRECT"))){	
							String target;
							String fileRecordType = fileRecord.getDbRecord().getType();
							String fileLocation = fileRecord.getFileLocation();
							String fileRecordTypeInName;
							if (fileRecordType.equals("Member")){
								fileRecordType = "MBER";
								fileRecordTypeInName = "MBR";
							} else if (fileRecordType.equals("Program")){
								fileRecordType="PROG";
								fileRecordTypeInName = fileRecordType;
							} else {
								fileRecordType="RQST";
								fileRecordTypeInName = fileRecordType;
							}
							target = this.fileFolder.getAbsolutePath()+"\\"+fileRecord.getDbRecord().getMbrId()+"\\"+fileRecordType+"\\"+fileRecordTypeInName+"_"+fileRecord.getDbRecord().getPk()+"_"+fileLocation.substring(fileLocation.lastIndexOf("\\")+1);
							transferFileRecord(fileRecord.getFileLocation(),target);
							System.out.println(++counter);
						}
					}
				}
			}
		}
		logger.info("total " + counter + " eligible files are transfered...");
		logger.info("moving done");
	}
	public void transferFileRecord(String original, String target){
		File targetFile = new File(target);
		File parentFile = targetFile.getParentFile();
		if(!parentFile.exists()){
			parentFile.mkdirs();
		}
		File originalFile = new File(original);
		logger.trace(original+":->"+target);
		originalFile.renameTo(targetFile);
	}
	public File getFileFolder(){
		return this.fileFolder;
	}

}
