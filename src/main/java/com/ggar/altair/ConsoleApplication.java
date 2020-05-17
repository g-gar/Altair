package com.ggar.altair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ggar.altair.dao.TagDao;
import com.ggar.altair.dao.UrlDao;
import com.ggar.altair.dao.UrlTagsDao;
import com.ggar.altair.model.Tag;
import com.ggar.altair.model.Url;
import com.ggar.altair.model.intermediate.UrlTags;
import com.magc.sensecane.framework.dao.CachedDao;
import com.magc.sensecane.framework.dao.Dao;
import com.magc.sensecane.framework.dao.DaoContainer;
import com.magc.sensecane.framework.database.connection.factory.ConnectionFactory;
import com.magc.sensecane.framework.database.connection.pool.ConnectionPool;
import com.magc.sensecane.framework.database.connection.properties.ConnectionProperties;
import com.magc.sensecane.framework.database.implementation.sqlite.SQLiteConnectionFactory;
import com.magc.sensecane.framework.database.implementation.sqlite.SQLiteConnectionPool;
import com.magc.sensecane.framework.database.implementation.sqlite.SQLiteConnectionProperties;
import com.magc.sensecane.framework.utils.LoadResource;

public class ConsoleApplication extends AbstractApplication {
	
	public ConsoleApplication() {
		
		LoadResource lr = new LoadResource();
		
		ConnectionProperties properties = register(ConnectionProperties.class, new SQLiteConnectionProperties(lr.execute("altair.db").toString()));
		ConnectionFactory<SQLiteConnectionProperties> factory = register(ConnectionFactory.class, new SQLiteConnectionFactory());
		ConnectionPool pool = register(ConnectionPool.class, new SQLiteConnectionPool(this));
		DaoContainer daocontainer = register(DaoContainer.class, new DaoContainer());
		
		pool.configure(10);
		
		Map<Class, Dao> daos = new HashMap<Class, Dao>() {
			{
				put(Url.class, new UrlDao(pool));
				put(Tag.class, new TagDao(pool));
				put(UrlTags.class, new UrlTagsDao(pool));
			}
		};
		
		for (Map.Entry<Class, Dao> entry : daos.entrySet()) {
			Dao dao = entry.getValue();
			daocontainer.register(entry.getKey(), dao);
			((CachedDao) dao).reloadCache();
		}
		
	}

	public static void main(String[] args) {
		
		ConsoleApplication app = new ConsoleApplication();
		
	}
	
}
