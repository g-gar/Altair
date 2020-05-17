package com.ggar.altair;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ggar.altair.dao.TagDao;
import com.ggar.altair.dao.UrlDao;
import com.ggar.altair.dao.UrlTagsDao;
import com.ggar.altair.model.ConsoleMenuItem;
import com.ggar.altair.model.Tag;
import com.ggar.altair.model.Url;
import com.ggar.altair.model.intermediate.UrlTag;
import com.ggar.framework.application.AbstractConsoleApplication;
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

public class Application extends AbstractConsoleApplication<ConsoleMenuItem> implements AltairConsoleApplication<ConsoleMenuItem> {

	public static void main(String[] args) throws IOException {
		
		Application app = new Application(Arrays.asList(new ConsoleMenuItem[] {
			ConsoleMenuItem.FIND_URL_BY_TAG,
			ConsoleMenuItem.FIND_URL_BY_KEYWORD,
			ConsoleMenuItem.ADD_URL,
			ConsoleMenuItem.LIST_ALL_URLS,
			ConsoleMenuItem.REMOVE_URL,
			ConsoleMenuItem.EXIT
		}));
		
		ConsoleMenuItem choice = null;
		boolean stop = false;
		while (!stop) {
			app.clearConsole();
			app.showMenu();
			choice = app.askChoice();
			app.run(choice);
			if (choice.equals(ConsoleMenuItem.EXIT)) {
				stop = true;
			}
		}
	}
	
	public Application(Collection<ConsoleMenuItem> items) {
		super(items);
		
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
				put(UrlTag.class, new UrlTagsDao(pool));
			}
		};
		
		for (Map.Entry<Class, Dao> entry : daos.entrySet()) {
			Dao dao = entry.getValue();
			daocontainer.register(entry.getKey(), dao);
			((CachedDao) dao).reloadCache();
		}
	}

	@Override
	public void run(ConsoleMenuItem choice) {
		DaoContainer container = this.container.get(DaoContainer.class);
		Dao<UrlTag> utdao = container.get(UrlTag.class);
		Dao<Url> urldao = container.get(Url.class);
		Dao<Tag> tagdao = container.get(Tag.class);
		
		Url url;
		List<Tag> tags;
		List<Url> urls;
		switch (choice) {
		case ADD_URL:
			url = new Url(null, super.ask("URL: "), 0);
			tags = Arrays.asList(super.ask("Tags: ").split(",{1}\\s*")).stream()
					.map(e -> {
						Integer id = tagdao.findAll().stream()
								.filter(t -> t.getName().equals(e)).map(Tag::getId)
								.findFirst()
								.orElse(null);
						return tagdao.insertOrUpdate(new Tag(id, e, ""));
					})
					.collect(Collectors.toList());
			addUrl(url, tags);
			break;
		case FIND_URL_BY_TAG:
			tags = Arrays.asList(super.ask("Tags: ").split(",{1}\\s*")).stream()
				.map(e -> {
					Integer id = tagdao.findAll().stream().filter(t -> t.getName().equals(e)).map(Tag::getId).limit(1).findFirst().orElseGet(null);
					return tagdao.insertOrUpdate(new Tag(id, e, ""));
				})
				.collect(Collectors.toList());
			print(findUrlByTags(tags));
			break;
		case FIND_URL_BY_KEYWORD:
			break;
		case LIST_ALL_URLS:
			listAllUrls();
			break;
		case REMOVE_URL:
			break;
		case EXIT:
			exit();
			break;
		default:
			break;
			
		}
	}

	@Override
	public void listAllUrls() {
		print(container.get(DaoContainer.class).get(Url.class).findAll());
	}

	@Override
	public void addUrl(Url url, List<Tag> tags) {
		DaoContainer container = this.container.get(DaoContainer.class);
		
		container.get(Url.class).insertOrUpdate(url);
		tags.stream()
			.map(container.get(Tag.class)::insertOrUpdate)
			.forEach(e -> container.get(UrlTag.class).insertOrUpdate(new UrlTag(null, url.getId(), e.getId())));
	}

	@Override
	public void removeUrl(Url url) {
		DaoContainer container = this.container.get(DaoContainer.class);
	}

	@Override
	public List<Url> findUrlByTags(List<Tag> tags) {
		DaoContainer container = this.container.get(DaoContainer.class);
		return null;
	}

	@Override
	public List<Url> findUrlByKeywords(List<String> keyword) {
		DaoContainer container = this.container.get(DaoContainer.class);
		return null;
	}

	@Override
	public void exit() {
		System.out.println("Exiting...");
	}
	
	public <T> void print(Collection<T> items) {
		System.out.println("Showing items: ");
		items.forEach(System.out::println);
	}
}
