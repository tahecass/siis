package com.siis.dto;

public abstract class NavigationPage {
	private String title;
	private String includeUri;
	private String subTitle;
	private Object data;
	private String id;

	public NavigationPage(String title, String subTitle, String includeUri,
			Object data, String id) {
		super();
		this.title = title;
		this.subTitle = subTitle;
		this.includeUri = includeUri;
		this.data = data;
		this.id = id;
	}

	public abstract boolean isSelected();

	public String getTitle() {
		return title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public String getIncludeUri() {
		return includeUri;
	}

	public Object getData() {
		return data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}