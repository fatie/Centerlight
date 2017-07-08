package org.centerlight.attachment.changeFilelocation.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class ConnectionFactory {
	public static final Logger logger = LogManager.getLogger(ConnectionFactory.class);
	private static ConnectionFactory instance;
	private Properties properties;
	private InputStream input;
	private String propFileName;
	private String url;
    private String user;
    private String password;
    private String driver; 
	
	private ConnectionFactory(String dbSelection){
		if (dbSelection.equalsIgnoreCase("MSSS")){
			this.setPropFileName("resources/msssDbConfig.properties");
		} else if (dbSelection.equalsIgnoreCase("Oracle")){
			this.setPropFileName("resources/oracleDbConfig.properties");
		} else {
			this.setPropFileName("resources/accessDbConfig.properties");
		}
		try {
			this.setInput(propFileName);
			properties = new Properties();
			properties.load(input);
			this.setDriver(properties.getProperty("DRIVER"));
			this.setPassword(properties.getProperty("PASSWORD"));
			this.setUser(properties.getProperty("USER"));
			this.setUrl(properties.getProperty("URL"));

			
		} catch (FileNotFoundException e) {
			logger.error("ERROR: Could not locate properties file");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("ERROR: Could not open properties file");
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public InputStream getInput() {
		return input;
	}

	public void setInput(String propFileName) throws FileNotFoundException {
		this.input = new FileInputStream(propFileName);
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	private Connection createConnection(){
		Connection connection = null;
		try {
			logger.debug("Driver loading....");
			Class.forName(this.getDriver());
			logger.debug("Driver load----Done");
			logger.debug("Connecting database...");
			connection = DriverManager.getConnection(this.getUrl(), this.getUser(), this.getPassword());
			logger.debug("Connected");	
		} catch (SQLException e) {
			logger.error("ERROR: Unable to Connect to Database.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			logger.error("ERROR: unable to Load Driver");
			e.printStackTrace();
		}
		return connection;
	}
	
	public static Connection getConnection(){
		return getConnection("Access");
	}
	
	public static Connection getConnection(String dbSelection){
		if (instance == null){
			instance = new ConnectionFactory(dbSelection);
		}
		return instance.createConnection();
		
	}

	public void setPropFileName(String propFileName) {
		this.propFileName = propFileName;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public Properties getProperties() {
		return properties;
	}

	public String getPropFileName() {
		return propFileName;
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getDriver() {
		return driver;
	}
	
}
