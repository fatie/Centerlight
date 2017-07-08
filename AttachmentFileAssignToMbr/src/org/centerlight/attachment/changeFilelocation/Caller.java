package org.centerlight.attachment.changeFilelocation;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.centerlight.attachment.changeFilelocation.fileIO.FileIntake;
import org.centerlight.attachment.changeFilelocation.fileIO.FileIntakePACE;
import org.centerlight.attachment.changeFilelocation.fileIO.FileOutput;
import org.centerlight.attachment.changeFilelocation.fileIO.FileRecords;

public class Caller {
	private static final Logger logger = LogManager.getLogger(Caller.class);
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
		FileIntake cfl = new FileIntake();
		FileRecords frs =  cfl.openConnectionAndIntakeFile(cfl.getFileFolder());
		frs.writeToLogfile();
		FileOutput fo = new FileOutput(frs);
		fo.transferAllToSublevel();
	}
}
