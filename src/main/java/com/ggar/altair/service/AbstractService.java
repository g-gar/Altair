package com.ggar.altair.service;

import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.dao.Dao;
import com.magc.sensecane.framework.dao.DaoContainer;
import com.magc.sensecane.framework.model.BaseEntity;

public class AbstractService {

	private final Container container;

	public AbstractService(Container container) {
		this.container = container;
	}
	
	public <T extends BaseEntity> Dao<T> getDao(Class<T> daoClass) {
		return container.get(DaoContainer.class).get(daoClass);
	}
	
}
