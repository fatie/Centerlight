package org.centerlight.attachment.changeFilelocation.DAO;

import java.util.List;

public interface GenericAttachmentFileDAO <T, Pk> {
	
	Pk create(T t);
	T read(Pk pk);
	T read(Pk pk, String fullPath);
	List<T> readAll();
	boolean update(Pk pk, T t);
	boolean delete(T t);

}
