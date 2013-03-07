package com.bookmarks.mongo;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

public class Story {

	private String id;
	
	private String title;
	
	private String url;
	
	private String[] tags;
	
	public Story() {
		// TODO Auto-generated constructor stub
	}
	
	public Story(String title, String url, String[] tags) {
		this.title = title;
		this.url = url;
		this.tags = tags;
	}
	
	public Story(DBObject doc) {
		this.title = (String)doc.get("title");
		this.url = (String)doc.get("url");
		BasicDBList tagzz = (BasicDBList)doc.get("tags");
		tags = new String[tagzz.size()];
		int index = 0;
		for (Object object : tagzz) {
			tags[index] = (String)object;
			index++;
		}
		this.id = ((ObjectId)doc.get("_id")).toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}
	
	
}
