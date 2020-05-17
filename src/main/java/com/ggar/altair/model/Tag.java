package com.ggar.altair.model;

import com.magc.sensecane.framework.model.database.TableEntity;
import com.magc.sensecane.framework.model.database.annotation.Autogenerated;
import com.magc.sensecane.framework.model.database.annotation.Column;
import com.magc.sensecane.framework.model.database.annotation.PrimaryKey;
import com.magc.sensecane.framework.model.database.annotation.Table;
import com.magc.sensecane.framework.model.database.annotation.Unique;

@Table("tags")
public class Tag extends TableEntity<Integer> {

	@PrimaryKey @Autogenerated @Column("id") private final Integer id;
	@Column("name") @Unique private final String name;
	@Column("description") private final String description;
	
	public Tag() {
		this(null, null, null);
	}
	
	public Tag(Object id, Object name, Object description) {
		this((Integer) id, (String) name, (String) description);
	}
	
	public Tag(Integer id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	
}
