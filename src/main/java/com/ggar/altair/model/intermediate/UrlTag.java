package com.ggar.altair.model.intermediate;

import com.magc.sensecane.framework.model.database.TableEntity;
import com.magc.sensecane.framework.model.database.annotation.Autogenerated;
import com.magc.sensecane.framework.model.database.annotation.Column;
import com.magc.sensecane.framework.model.database.annotation.PrimaryKey;
import com.magc.sensecane.framework.model.database.annotation.Table;

@Table("url_tags")
public class UrlTag extends TableEntity<Integer> {

	@PrimaryKey @Autogenerated @Column("id") private final Integer id;
	@Column("url_id") private final Integer urlId;
	@Column("tag_id") private final Integer tagId;
	
	public UrlTag() {
		this(null, null, null);
	}
	
	public UrlTag(Object id, Object urlId, Object tagId) {
		this((Integer) id, (Integer) urlId, (Integer) tagId);
	}

	public UrlTag(Integer id, Integer urlId, Integer tagId) {
		super();
		this.id = id;
		this.urlId = urlId;
		this.tagId = tagId;
	}

	public Integer getId() {
		return id;
	}

	public Integer getUrlId() {
		return urlId;
	}

	public Integer getTagId() {
		return tagId;
	}
	
}