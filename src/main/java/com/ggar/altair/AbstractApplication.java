package com.ggar.altair;

import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.container.DefaultContainer;

public class AbstractApplication implements Container {

	protected final Container container;
	
	public AbstractApplication() {
		this(new DefaultContainer() {});
	}

	public AbstractApplication(Container container) {
		this.container = container;
	}

	@Override
	public <T> T register(T entity) {
		container.register(entity);
		return entity;
	}

	@Override
	public <T> T register(Class<T> clazz, T entity) {
		container.register(clazz, entity);
		return entity;
	}

	@Override
	public <T> T unregister(Class<T> clazz) {
		return container.unregister(clazz);
	}

	@Override
	public <T> T unregister(Class<T> clazz, T entity) {
		return container.unregister(clazz, entity);
	}

	@Override
	public Boolean containsClass(Class clazz) {
		return container.containsClass(clazz);
	}

	@Override
	public <T> T get(Class<T> clazz) {
		return container.get(clazz);
	}

	
	
}
