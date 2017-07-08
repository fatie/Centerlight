package org.centerlight.attachment.changeFilelocation.fileIO;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.swing.JFileChooser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileUtil {
	private static final Logger logger = LogManager.getLogger(FileUtil.class);

	public static boolean close(Writer writer){
		if(writer != null){
			try {
				writer.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		else return false;
	}
	public static boolean close(Reader reader){
		if(reader != null){
			try {
				reader.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		else return false;
	}
	
	public static File chooseFile(String defaultFolder){
		File selectFile;
		JFileChooser fileChooser = new JFileChooser(defaultFolder);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			selectFile = fileChooser.getSelectedFile();
			logger.debug(selectFile.getAbsolutePath()+" is selected");
			return selectFile;
		}
		else {
			logger.debug("No file selected");
			return null;
		}
	}
}
