package com.github.files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletOutputStream;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

@SuppressWarnings("deprecation")
public class FileServletOutputStream {
	
	public static Properties props = new Properties();
	static Mongo mongoClient = null;
	static DB db = null;
	static boolean auth = false;
	static String mongoDB_BucketName, userName, password;
	static {
		try {
			props.load(FileServletOutputStream.class.getClassLoader()
						.getResourceAsStream("mongo.properties"));
			
			mongoClient = new Mongo(props.getProperty("mongoDBHost") , Integer.parseInt(props.getProperty("mongoDBPort")));
			mongoDB_BucketName = props.getProperty("mongoDB_BucketName");
			db = mongoClient.getDB( props.getProperty("mongoDBName") );
			userName = props.getProperty("mongoDBUserName");
			password = props.getProperty("mongoDBPassword");
			auth = db.authenticate(userName, password.toCharArray());
			System.out.println("Static MongoDB Authentication Status « "+auth);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
	}
	
	static void getFileStream( InputStream image, ServletOutputStream sos ) throws IOException {
		BufferedInputStream bin = null;
		BufferedOutputStream bout = null;
		
		try {
			
			bin = new BufferedInputStream( image );
			bout = new BufferedOutputStream( sos );
			int ch =0; ;
			while( (ch = bin.read()) != -1 ) {
				bout.write(ch);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			bin.close();
			bout.close();
			sos.close();
		}
	}
	
	static void getMongoID_FileStream( ServletOutputStream sos, String fileID ) throws IOException {
		if( auth) {
			BasicDBObject query = new BasicDBObject();
			//query.put("_id", new ObjectId( fileID ));
			query.put("_id", fileID );
			
			//Create instance of GridFS implementation
			GridFS gridFs = new GridFS(db, mongoDB_BucketName);
			GridFSDBFile findOne = gridFs.findOne( query );
			InputStream inputStream = findOne.getInputStream();
			
			getFileStream(inputStream, sos);
		}
	}
}
