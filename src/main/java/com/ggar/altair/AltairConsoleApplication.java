package com.ggar.altair;

import java.util.List;

import com.ggar.altair.model.Tag;
import com.ggar.altair.model.Url;
import com.ggar.framework.application.MenuItem;

public interface AltairConsoleApplication<T extends MenuItem> {

	void listAllUrls();
	void addUrl(Url url, List<Tag> tags);
	void removeUrl(Url url);
	List<Url> findUrlByTags(List<Tag> tags);
	List<Url> findUrlByKeywords(List<String> keyword);
	void exit();
}
