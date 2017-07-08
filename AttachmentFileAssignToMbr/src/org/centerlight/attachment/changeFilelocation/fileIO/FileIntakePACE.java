package org.centerlight.attachment.changeFilelocation.fileIO;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.centerlight.attachment.changeFilelocation.DAO.AccessDBAttachmentFileDAOMbrPACEImpl;
import org.centerlight.attachment.changeFilelocation.DAO.AccessDBAttachmentFileDAOPgmPACEImpl;
import org.centerlight.attachment.changeFilelocation.DAO.AccessDBAttachmentFileDAORqstPACEImpl;
import org.centerlight.attachment.changeFilelocation.db.ConnectionFactory;
import org.centerlight.attachment.changeFilelocation.db.DbUtil;

public class FileIntakePACE extends FileIntake {
	private static final Logger logger = LogManager.getLogger(FileIntakePACE.class);
	
	public FileIntakePACE() throws IOException{
		this(FileIntake.DEFAULTFOLDER);
	}
	public FileIntakePACE(String folderPath) throws IOException{
		super(folderPath);
	}
	
	@Override
	public FileRecords openConnectionAndIntakeFile(File fileFolder) throws IOException{
		this.setConn(ConnectionFactory.getConnection());
		this.setFileRecords(new FileRecords());
		logger.info("reading files...");
		this.intakeFile(fileFolder);
		logger.info("reading file done");
		if(this.getConn()!=null){
			DbUtil.close(this.getConn());
		}
		return this.getFileRecords();
	}
	
	private FileRecords intakeFile(File fileFolder) throws IOException{
		File[] subDirs = fileFolder.listFiles(new FileFilter() {
		    public boolean accept(File pathname) {
		        return true;//pathname.isDirectory();
		    }
		});
		for (File subDir : subDirs) {
			if (subDir.isDirectory()) {
				intakeFile(subDir);
			} else {
				this.setCounter((this.getCounter())+1);
				System.out.println(this.getCounter());
				String tempName;
				String fullPath = subDir.getAbsolutePath();
				String pk;
				FileRecord fileRecord = null;
				DBRecord dbRecord = null;
				if (fullPath.contains(tempName = this.getDir()+"\\"+PROGRAM)){
					pk = "PROG-" + fullPath.substring(tempName.length() + 6,tempName.length() + 21);
					this.setDao(new AccessDBAttachmentFileDAOPgmPACEImpl<DBRecord, String>(this.getConn()));
					dbRecord = this.getDao().read(pk);
					if (dbRecord!=null){
						fileRecord = new FileRecord(fullPath, dbRecord);
					}
				} else if (fullPath.contains(tempName = this.getDir()+"\\"+REQUEST_OUTPATIENT)){
					pk = fullPath.substring(tempName.length() + 5,tempName.length() + 20);
					if (pk.contains("\\")){
						pk = pk.substring(0,pk.indexOf("\\"));
					}
					this.setDao(new AccessDBAttachmentFileDAORqstPACEImpl<DBRecord, String>(this.getConn()));
					dbRecord = this.getDao().read(pk);
					if (dbRecord!=null){
						fileRecord = new FileRecord(fullPath, dbRecord);
					}
					
				} else if (fullPath.contains(tempName = this.getDir()+"\\"+REQUEST_REFERRAL)){
					pk = fullPath.substring(tempName.length() + 5,tempName.length() + 20);
					if (pk.contains("\\")){
						pk = pk.substring(0,pk.indexOf("\\"));
					}
					this.setDao(new AccessDBAttachmentFileDAORqstPACEImpl<DBRecord, String>(this.getConn()));
					dbRecord = this.getDao().read(pk);
					if (dbRecord!=null){
						fileRecord = new FileRecord(fullPath, dbRecord);
					}
					
				} else if (fullPath.contains(tempName = this.getDir()+"\\"+REQUEST_INPATIENT)){
					pk = fullPath.substring(tempName.length() + 5,tempName.length() + 20);
					if (pk.contains("\\")){
						pk = pk.substring(0,pk.indexOf("\\"));
					}
					this.setDao(new AccessDBAttachmentFileDAORqstPACEImpl<DBRecord, String>(this.getConn()));
					dbRecord = this.getDao().read(pk);
					if (dbRecord!=null){
						fileRecord = new FileRecord(fullPath, dbRecord);
					}
				} else {
					tempName = this.getDir()+"\\"+MEMBER;
					pk = fullPath.substring(tempName.length()+1, tempName.length()+6);
					this.setDao(new AccessDBAttachmentFileDAOMbrPACEImpl<DBRecord, String>(this.getConn()));
					dbRecord = this.getDao().read(pk);
					if (dbRecord!=null){
						fileRecord = new FileRecord(fullPath, dbRecord);
					}
					
				}
				if (fileRecord!=null){
					this.getFileRecords().add(fileRecord);
				}
			}
		}
		if(this.getFileRecords() != null) {
			return this.getFileRecords();
		} else {
			logger.info("No files exist");
			return null;
		}
	}
}
