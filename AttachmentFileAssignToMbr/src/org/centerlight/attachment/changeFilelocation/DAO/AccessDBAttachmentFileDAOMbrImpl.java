package org.centerlight.attachment.changeFilelocation.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.centerlight.attachment.changeFilelocation.db.DbUtil;
import org.centerlight.attachment.changeFilelocation.fileIO.MemberDBFilerRecord;

public class AccessDBAttachmentFileDAOMbrImpl<T, Pk> implements GenericAttachmentFileDAO<T, Pk> {
	private Connection conn;
	private static final Logger logger = LogManager.getLogger(AccessDBAttachmentFileDAOMbrImpl.class);
	
	public AccessDBAttachmentFileDAOMbrImpl(Connection conn){
		this.conn = conn;
	}
	
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Pk create(T t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T read(Pk pk) {
		String query = "SELECT * FROM MbrQueryTable WHERE [Mbr_Member ID] = '" + pk + "'; ";
		Statement stmt = null;
		ResultSet rs = null;
		T result = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			String mbrId = rs.getString(1);
			String mbrRecentLOB = rs.getString(3);
			String mbrRqstOrPgmLOB = "Not available";
			String mbrCurrentLOB = rs.getString(8);
			
			result = (T)new MemberDBFilerRecord(mbrId, mbrId, mbrCurrentLOB, mbrRecentLOB, mbrRqstOrPgmLOB);
			
			
		} catch (SQLException e) {
			logger.error("pk: "+pk);
			e.printStackTrace();
			return null;
		} finally {
			DbUtil.close(rs);
			DbUtil.close(stmt);
			return result;
		}
		
	}
	
	public T read(Pk pk, String fullPath) {
		String query = "SELECT * FROM MbrQueryTable WHERE [Mbr_Member ID] = '" + pk + "'; ";
		Statement stmt = null;
		ResultSet rs = null;
		T result = null;
		try {
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			String mbrId = rs.getString(1);
			String mbrRecentLOB = rs.getString(3);
			Date planStartDate = rs.getDate(5);
			String mbrCurrentLOB = rs.getString(8);
			int startPosition = fullPath.lastIndexOf("_")+1;
			int endPosition = fullPath.lastIndexOf("-");
			String fileDateText = fullPath.substring(startPosition, endPosition);
			int year = Integer.parseInt(fileDateText.substring(0,4));
			int month = Integer.parseInt(fileDateText.substring(5,7));
			int date = Integer.parseInt(fileDateText.substring(8,10));
			GregorianCalendar fileCalendar = new GregorianCalendar(year, month-1, date);
			Date fileDate = new Date(fileCalendar.getTimeInMillis());
			if (fileDate.before(planStartDate)) {
				result = (T)new MemberDBFilerRecord(mbrId, mbrId, mbrCurrentLOB, mbrRecentLOB, "SELECT");
			} else {
				result = (T)new MemberDBFilerRecord(mbrId, mbrId, mbrCurrentLOB, mbrRecentLOB, "PACE");
			}
			
		} catch (SQLException e) {
			logger.error("pk: "+pk);
			e.printStackTrace();
			return null;
		} finally {
			DbUtil.close(rs);
			DbUtil.close(stmt);
			return result;
		}
		
	}

	@Override
	public List<T> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Pk pk, T t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(T t) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
