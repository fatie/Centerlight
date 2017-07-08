package org.centerlight.attachment.changeFilelocation;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.centerlight.attachment.changeFilelocation.fileIO.FileIntake;
import org.centerlight.attachment.changeFilelocation.fileIO.FileIntakeForEChamp;
import org.centerlight.attachment.changeFilelocation.fileIO.FileOutput;
import org.centerlight.attachment.changeFilelocation.fileIO.FileOutputForEChamp;
import org.centerlight.attachment.changeFilelocation.fileIO.FileRecords;

public class CallerForEChamp {
	private static final Logger logger = LogManager.getLogger(CallerForEChamp.class);
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
		FileIntake cfl = new FileIntakeForEChamp();
		FileRecords frs =  cfl.openConnectionAndIntakeFile(cfl.getFileFolder());
		frs.writeToLogfile();
		FileOutputForEChamp fo = new FileOutputForEChamp(frs);
		fo.transferAllToSublevel();
		
	}
}
