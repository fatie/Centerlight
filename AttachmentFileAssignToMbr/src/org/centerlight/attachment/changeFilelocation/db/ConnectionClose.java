package org.centerlight.attachment.changeFilelocation.db;

import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionClose {
	private static final Logger logger = LogManager.getLogger(ConnectionClose.class);
	private Connection conn;
	public ConnectionClose(Connection conn){
		this.conn = conn;
	}
	public void closeConnection() {
		logger.debug("Disconnecting....");
		DbUtil.close(this.conn);
		logger.debug("Disconnected");
	}

}
