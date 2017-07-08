package org.centerlight.attachment.changeFilelocation.fileIO;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.centerlight.attachment.changeFilelocation.DAO.AccessDBAttachmentFileDAOMbrImpl;
import org.centerlight.attachment.changeFilelocation.DAO.AccessDBAttachmentFileDAOPgmImpl;
import org.centerlight.attachment.changeFilelocation.DAO.AccessDBAttachmentFileDAORqstImpl;
import org.centerlight.attachment.changeFilelocation.DAO.GenericAttachmentFileDAO;
import org.centerlight.attachment.changeFilelocation.db.ConnectionFactory;
import org.centerlight.attachment.changeFilelocation.db.DbUtil;

public class FileIntake {
	private static final Logger logger = LogManager.getLogger(FileIntake.class);
	protected final static String DEFAULTFOLDER = "C:\\Users\\FGuo\\Documents\\work doucment\\attachment\\attachmentDB";
	protected final static String PROGRAM = "PRGENROLL";
	protected final static String REQUEST_REFERRAL = "REFERRAL";
	protected final static String REQUEST_OUTPATIENT = "OUTPATIENT";
	protected final static String REQUEST_INPATIENT = "INPATIENT";
	protected final static String MEMBER = "MEMBER";
	private File dir;
	private GenericAttachmentFileDAO<DBRecord, String> dao;
	private Connection conn;
	private FileRecords fileRecords;
	private int counter = 0;
	
	public FileIntake() throws IOException{
		this(DEFAULTFOLDER);
	}
	public FileIntake(String folderPath) throws IOException{
		this.dir = FileUtil.chooseFile(folderPath);
	}
	public GenericAttachmentFileDAO<DBRecord, String> getDao() {
		return dao;
	}
	public void setDao(GenericAttachmentFileDAO<DBRecord, String> dao) {
		this.dao = dao;
	}
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	public File getFileFolder(){
		return this.dir;
	}
	
	public File getDir() {
		return dir;
	}
	public void setDir(File dir) {
		this.dir = dir;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public FileRecords getFileRecords() {
		return fileRecords;
	}
	public void setFileRecords(FileRecords fileRecords) {
		this.fileRecords = fileRecords;
	}
	public FileRecords openConnectionAndIntakeFile(File fileFolder) throws IOException{
		this.conn = ConnectionFactory.getConnection();
		this.fileRecords = new FileRecords();
		logger.info("reading files...");
		this.intakeFile(fileFolder);
		logger.info("reading file done");
		logger.info("total " + counter + " files read in....");
		if(conn!=null){
			DbUtil.close(this.conn);
		}
		return this.fileRecords;
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
				System.out.println(++counter);
				String tempName;
				String fullPath = subDir.getAbsolutePath();
				String pk;
				FileRecord fileRecord;
				if (fullPath.contains(tempName = this.dir+"\\"+PROGRAM)){
					pk = "PROG-" + fullPath.substring(tempName.length() + 6,tempName.length() + 21);
					this.dao = new AccessDBAttachmentFileDAOPgmImpl<DBRecord, String>(conn);
					fileRecord = new FileRecord(fullPath, dao.read(pk));
					
				} else if (fullPath.contains(tempName = this.dir+"\\"+REQUEST_OUTPATIENT)){
					pk = fullPath.substring(tempName.length() + 5,tempName.length() + 20);
					if (pk.contains("\\")){
						pk = pk.substring(0,pk.indexOf("\\"));
					}
					this.dao = new AccessDBAttachmentFileDAORqstImpl<DBRecord, String>(conn);
					fileRecord = new FileRecord(fullPath, dao.read(pk));
					
				} else if (fullPath.contains(tempName = this.dir+"\\"+REQUEST_REFERRAL)){
					pk = fullPath.substring(tempName.length() + 5,tempName.length() + 20);
					if (pk.contains("\\")){
						pk = pk.substring(0,pk.indexOf("\\"));
					}
					this.dao = new AccessDBAttachmentFileDAORqstImpl<DBRecord, String>(conn);
					fileRecord = new FileRecord(fullPath, dao.read(pk));
					
				} else if (fullPath.contains(tempName = this.dir+"\\"+REQUEST_INPATIENT)){
					pk = fullPath.substring(tempName.length() + 5,tempName.length() + 20);
					if (pk.contains("\\")){
						pk = pk.substring(0,pk.indexOf("\\"));
					}
					this.dao = new AccessDBAttachmentFileDAORqstImpl<DBRecord, String>(conn);
					fileRecord = new FileRecord(fullPath, dao.read(pk));
				} else {
					tempName = this.dir+"\\"+MEMBER;
					pk = fullPath.substring(tempName.length()+1, tempName.length()+6);
					this.dao = new AccessDBAttachmentFileDAOMbrImpl<DBRecord, String>(conn);
					fileRecord = new FileRecord(fullPath, dao.read(pk, fullPath));
					
				}
//				System.out.println(pk);
				fileRecords.add(fileRecord);
				
			}
		}
		if(fileRecords != null) {
			return fileRecords;
		} else {
			logger.info("No files exist");
			return null;
		}
	}
	
	
	
}
