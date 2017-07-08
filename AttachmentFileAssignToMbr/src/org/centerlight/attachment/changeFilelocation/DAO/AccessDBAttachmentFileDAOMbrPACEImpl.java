package org.centerlight.attachment.changeFilelocation.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.centerlight.attachment.changeFilelocation.db.DbUtil;
import org.centerlight.attachment.changeFilelocation.fileIO.MemberDBFilerRecord;

public class AccessDBAttachmentFileDAOMbrPACEImpl<T, Pk> extends AccessDBAttachmentFileDAOMbrImpl<T, Pk> {
	
	public AccessDBAttachmentFileDAOMbrPACEImpl(Connection conn){
		super(conn);
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
			stmt = this.getConn().createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			String mbrId = rs.getString(1);
			String mbrRecentLOB = rs.getString(3);
			String mbrCurrentLOB = rs.getString(8);
			String mbrRqstOrPgmLOB = "Not available";
			if(mbrCurrentLOB != null){
				if ((!mbrId.equals("T1")) &&(mbrCurrentLOB.equalsIgnoreCase("PACE"))){
					result = (T)new MemberDBFilerRecord(mbrId, mbrId, mbrCurrentLOB, mbrRecentLOB, mbrRqstOrPgmLOB);
				} else {
					result = null;
				} 
			} else {
					result = null;
			}
			
			
		} catch (SQLException e) {
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
