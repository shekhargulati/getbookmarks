package com.bookmarks.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;

import com.bookmarks.mongo.DBConnection;
import com.bookmarks.mongo.Story;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Path("/bookmarks")
public class BookmarkRestService {

	@Inject
	DBConnection dbConnection;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response submitStory(Story story){
		DB db = dbConnection.getDB();
		DBCollection bookmarks = db.getCollection("bookmarks");
		BasicDBObject doc = new BasicDBObject("title",story.getTitle()).append("url", story.getUrl()).append("tags", story.getTags());
		bookmarks.insert(doc);
		return Response.created(null).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Story> allStories(){
		DB db = dbConnection.getDB();
		DBCollection bookmarks = db.getCollection("bookmarks");
		DBCursor cursor = bookmarks.find();
		List<Story> stories = new ArrayList<Story>();
		
		while(cursor.hasNext()){
			
			DBObject dbObject = cursor.next();
			stories.add(new Story(dbObject));
		}
		return stories;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Story lookupTodoById(@PathParam("id") String id) {
		DB db = dbConnection.getDB();
		DBCollection bookmarks = db.getCollection("bookmarks");
		DBObject doc = bookmarks.findOne(new BasicDBObject("_id",new ObjectId(id)));
		return new Story(doc);
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") String id){
		DB db = dbConnection.getDB();
		DBCollection bookmarks = db.getCollection("bookmarks");
		bookmarks.remove(new BasicDBObject("_id",new ObjectId(id)));
		return Response.noContent().build();
	}
	
}
