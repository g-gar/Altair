package com.ggar.altair.model;

import com.ggar.framework.application.MenuItem;

public enum ConsoleMenuItem implements MenuItem {
	
	LIST_ALL_URLS("Show available urls", 0),
	ADD_URL("Save url", 1),
	REMOVE_URL("Remove url",2),
	FIND_URL_BY_TAG("List urls by tags", 3),
	FIND_URL_BY_KEYWORD("List urls by keywords", 4),
	EXIT("Exit",10);

	private final String name;
	private final Integer value;
	
	private ConsoleMenuItem(String name, Integer value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}

}
