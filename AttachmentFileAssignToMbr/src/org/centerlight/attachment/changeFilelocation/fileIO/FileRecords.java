package org.centerlight.attachment.changeFilelocation.fileIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileRecords implements Iterable<FileRecord> {
	private static final Logger logger = LogManager.getLogger(FileRecords.class);
	private List<FileRecord> list;
	
	public FileRecords(List<FileRecord> list){
		this.list = list;
	}
	public FileRecords(){
		this.list = new ArrayList<FileRecord>();
	}

	public List<FileRecord> getList() {
		return list;
	}

	public void setList(List<FileRecord> list) {
		this.list = list;
	}
	public void add(FileRecord fileRecord){
		list.add(fileRecord);
	}
	
	public boolean writeToLogfile(){
		File file = new File("target/logs/readingEChampFileLog.txt");	
		logger.debug("Writing all files information to log file");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for (FileRecord fr : this){
				bw.append(fr.getDbRecord().getType()+";");
				bw.append(fr.getDbRecord().getPk()+";");
				bw.append(fr.getDbRecord().getMbrId()+";");
				bw.append(fr.getDbRecord().getMbrCurrentLOB()+";");
				bw.append(fr.getDbRecord().getAdditionalInformation()+";");
				bw.append(fr.getDbRecord().getRqstOrPgmLOB()+";");
				bw.append(fr.getFileLocation());
				bw.append("\n");
			}
			if (bw != null){
				FileUtil.close(bw);
			}
			logger.debug("Writing done.");
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public Iterator<FileRecord> iterator() {
		return list.iterator();
	}
}
