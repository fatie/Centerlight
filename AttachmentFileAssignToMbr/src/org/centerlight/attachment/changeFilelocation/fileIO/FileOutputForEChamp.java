package org.centerlight.attachment.changeFilelocation.fileIO;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileOutputForEChamp {
	private static final Logger logger = LogManager
			.getLogger(FileOutputForEChamp.class);
	private final static String TARGETFOLDER = "C:\\toILS";
	private File fileFolder;
	private FileRecords fileRecords;
	private int counter = 0;

	public FileOutputForEChamp(FileRecords fileRecords) throws IOException {
		this(TARGETFOLDER, fileRecords);

	}

	public FileOutputForEChamp(String folderPath, FileRecords fileRecords)
			throws IOException {
		logger.info("initiating file transferring by file output class...");
		//this.fileFolder = FileUtil.chooseFile(folderPath);
		this.fileFolder = new File(folderPath);
		this.fileRecords = fileRecords;
	}

	public void transferAllToSublevel() {
		logger.info("moving...");
		for (FileRecord fileRecord : this.fileRecords) {
			if (fileRecord.getDbRecord().getMbrCurrentLOB() != null) {
				if (fileRecord.getDbRecord().getMbrCurrentLOB().equals("PACE")) {
					if (!fileRecord.getDbRecord().getAdditionalInformation()
							.contains("SELECT Comp")) {
						String target;
						String fileRecordType = fileRecord.getDbRecord()
								.getType();
						String fileLocation = fileRecord.getFileLocation();
						String fileRecordTypeInName;
						if (fileRecordType.equals("Member")) {
							fileRecordType = "MBER";
							fileRecordTypeInName = "MBR";
						} else if (fileRecordType.equals("Program")) {
							fileRecordType = "PROG";
							fileRecordTypeInName = fileRecordType;
						} else {
							fileRecordType = "RQST";
							fileRecordTypeInName = fileRecordType;
						}
						String fileName = fileLocation.substring(fileLocation
								.lastIndexOf("\\") + 1);
						String fileRestName = fileName.substring(fileName
								.indexOf("_") + 1);
						String time = fileRestName.substring(
								fileRestName.lastIndexOf(".") - 10,
								fileRestName.lastIndexOf("."));
						String fileRestNameNoTime = fileRestName.substring(0,
								fileRestName.lastIndexOf(".") - 11);

						String year = time.substring(time.length() - 4);
						String month = time.substring(0, 2);
						String day = time.substring(3, 5);

						String timeStamp = year + "-" + month + "-" + day
								+ "-00.00.00.000";

						target = this.fileFolder.getAbsolutePath() + "\\"
								+ fileRecord.getDbRecord().getMbrId() + "\\"
								+ fileRecordType + "\\" + "ECHAMP_" + fileRecordTypeInName
								+ "_" + fileRecord.getDbRecord().getPk() + "_"
								+ fileRestNameNoTime + "_" + timeStamp + ".pdf";
						transferFileRecord(fileRecord.getFileLocation(),target);
						System.out.println(++counter);
					}
				}
			}
		}
		logger.info("total " + counter + " eligible files are transfered...");
		logger.info("moving done");
	}

	public void transferFileRecord(String original, String target) {
		File targetFile = new File(target);
		File parentFile = targetFile.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		File originalFile = new File(original);
		logger.trace(original + ":->" + target);
		originalFile.renameTo(targetFile);
	}

	public File getFileFolder() {
		return this.fileFolder;
	}

}
