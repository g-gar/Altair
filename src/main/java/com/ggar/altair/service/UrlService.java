package com.ggar.altair.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.ggar.altair.model.Tag;
import com.ggar.altair.model.Url;
import com.ggar.altair.model.intermediate.UrlTag;
import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.exception.InstanceNotFoundException;

public class UrlService extends AbstractService {

	public UrlService(Container container) {
		super(container);
	}

	public Boolean addUrl(String url) {
		Long time = Instant.now().getEpochSecond();
		return super.getDao(Url.class).insert(new Url(null, url, time)) != null;
	}
	
	public Boolean registerTagsToUrl(Url url, Collection<Tag> tags) {
		Boolean result = false;
		for (Tag tag : tags) {
			result |= registerTagToUrl(url, tag);
		}
		return result;
	}
	
	public Boolean registerTagToUrl(Url url, Tag tag) {
		UrlTag ut = hasTag(url, tag);
		return super.getDao(UrlTag.class).insertOrUpdate(new UrlTag(ut.getId(), url.getId(), tag.getId())) != null;
	}
	
	public UrlTag hasTag(Url url, Tag tag) {
		return super.getDao(UrlTag.class).findAll().stream()
			.filter(ut -> ut.getUrlId().equals(url.getId()) && ut.getTagId().equals(tag.getId()))
			.findFirst().orElse(null);
	}
	
	public List<Tag> getRelatedTags(Url url){
		return super.getDao(UrlTag.class).findAll().stream()
			.filter(ut -> ut.getUrlId().equals(url.getId()))
			.map(ut -> {
				Tag tag = null;
				try {
					tag = super.getDao(Tag.class).find(ut.getTagId());
				} catch (InstanceNotFoundException e) {
					e.printStackTrace();
				}
				return tag;
			})
			.collect(Collectors.toList());
	}
	
	public List<Url> findUrlsByTag(Tag tag) {
		return super.getDao(UrlTag.class).findAll().stream()
			.filter(ut -> ut.getTagId().equals(tag.getId()))
			.map(ut -> {
				Url url = null;
				try {
					url = super.getDao(Url.class).find(ut.getUrlId());
				} catch (InstanceNotFoundException e) {
					e.printStackTrace();
				}
				return url;
			})
			.collect(Collectors.toList());
	}
	
	public List<Url> findUrlsByKeyword(String keyword) {
		return super.getDao(Url.class).findAll().stream()
			.filter(e -> e.getValue().contains(keyword))
			.collect(Collectors.toList());
	}
}
