package com.ggar.altair.dao;

import com.ggar.altair.model.intermediate.UrlTag;
import com.magc.sensecane.framework.dao.CachedDao;
import com.magc.sensecane.framework.database.connection.pool.ConnectionPool;

public class UrlTagsDao extends CachedDao<UrlTag> {

	public UrlTagsDao(ConnectionPool pool) {
		super(pool, UrlTag.class);
	}
	
	public UrlTagsDao(ConnectionPool pool, Class<UrlTag> clazz) {
		super(pool, clazz);
	}

	@Override
	public UrlTag createInstance(String... p) {
		return new UrlTag(
			Integer.parseInt(p[0]), 
			Integer.parseInt(p[1]), 
			Integer.parseInt(p[2])
		);
	}

}
