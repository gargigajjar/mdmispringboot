package org.mdmi.rt.service;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DiffReport implements Diffable<DiffReport> {

	private String title;

	private String content;

	private String excerpt;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	@Override
	public DiffResult diff(DiffReport obj) {
		return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE).append(
			"title", this.title, obj.title).append("content", this.content, obj.content).append(
				"excerpt", this.excerpt, obj.excerpt).build();
	}

}
