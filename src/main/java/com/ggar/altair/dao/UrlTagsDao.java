package com.ggar.altair.dao;

import com.ggar.altair.model.intermediate.UrlTags;
import com.magc.sensecane.framework.dao.CachedDao;
import com.magc.sensecane.framework.database.connection.pool.ConnectionPool;

public class UrlTagsDao extends CachedDao<UrlTags> {

	public UrlTagsDao(ConnectionPool pool) {
		super(pool, UrlTags.class);
	}
	
	public UrlTagsDao(ConnectionPool pool, Class<UrlTags> clazz) {
		super(pool, clazz);
	}

	@Override
	public UrlTags createInstance(String... p) {
		return new UrlTags(
			Integer.parseInt(p[0]), 
			Integer.parseInt(p[1]), 
			Integer.parseInt(p[2])
		);
	}

}
