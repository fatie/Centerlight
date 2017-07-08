package org.centerlight.attachment.changeFilelocation.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.centerlight.attachment.changeFilelocation.db.DbUtil;
import org.centerlight.attachment.changeFilelocation.fileIO.RequestDBFilerRecord;

public class AccessDBAttachmentFileDAORqstPACEImpl<T, Pk> extends AccessDBAttachmentFileDAORqstImpl<T, Pk> {
	public AccessDBAttachmentFileDAORqstPACEImpl(Connection conn){
		super(conn);
	}
	
	@Override
	public Pk create(T t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T read(Pk pk) {
		String query = "SELECT * FROM RqstQueryTable WHERE [Rqst ID] = '" + pk + "'; ";
		Statement stmt = null;
		ResultSet rs = null;
		T result = null;
		try {
			stmt = this.getConn().createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			String mbrId= rs.getString(1);
			String rqstId = rs.getString(2);
			String rqstType = rs.getString(4);
			String rqstLOB = rs.getString(5);
			String mbrCurrentLOB = rs.getString(7);
			
			if(mbrCurrentLOB != null){
				if ((!mbrId.equals("T1")) &&(mbrCurrentLOB.equalsIgnoreCase("PACE"))){
					result = (T)new RequestDBFilerRecord(mbrId, rqstId, mbrCurrentLOB, rqstType, rqstType);
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
