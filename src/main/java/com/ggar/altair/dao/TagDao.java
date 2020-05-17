package com.ggar.altair.dao;

import com.ggar.altair.model.Tag;
import com.magc.sensecane.framework.dao.CachedDao;
import com.magc.sensecane.framework.database.connection.pool.ConnectionPool;

public class TagDao extends CachedDao<Tag> {

	public TagDao(ConnectionPool pool) {
		super(pool, Tag.class);
	}
	
	public TagDao(ConnectionPool pool, Class<Tag> clazz) {
		super(pool, clazz);
	}

	@Override
	public Tag createInstance(String... p) {
		return new Tag(
			Integer.parseInt(p[0]),
			p[1],
			p[2]
		);
	}

}
