package com.ggar.altair.dao;

import com.ggar.altair.model.Url;
import com.magc.sensecane.framework.dao.CachedDao;
import com.magc.sensecane.framework.database.connection.pool.ConnectionPool;

public class UrlDao extends CachedDao<Url> {

	public UrlDao(ConnectionPool pool) {
		super(pool, Url.class);
	}
	
	public UrlDao(ConnectionPool pool, Class<Url> clazz) {
		super(pool, clazz);
	}

	@Override
	public Url createInstance(String... p) {
		return new Url(
			Integer.parseInt(p[0]),
			p[1],
			Long.valueOf(p[2])
		);
	}

}
