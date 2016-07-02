package i5.las2peer.services.tagging;


import java.net.HttpURLConnection;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;

import com.fasterxml.jackson.core.JsonProcessingException;

import i5.las2peer.api.Service;
import i5.las2peer.restMapper.HttpResponse;
import i5.las2peer.restMapper.MediaType;
import i5.las2peer.restMapper.RESTMapper;
import i5.las2peer.restMapper.annotations.ContentParam;
import i5.las2peer.restMapper.annotations.Version;
import i5.las2peer.services.tagging.database.DatabaseManager;
import java.sql.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.jaxrs.Reader;
import io.swagger.models.Swagger;
import io.swagger.util.Json;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.JSONArray; 

/**
 * 
 * Tagging Service
 * 
 * This microservice was generated by the CAE (Community Application Editor). If you edit it, please
 * make sure to keep the general structure of the file and only add the body of the methods provided
 * in this main file. Private methods are also allowed, but any "deeper" functionality should be
 * outsourced to (imported) classes.
 * 
 */
@Path("tagging/")
@Version("0.1") // this annotation is used by the XML mapper
@Api
@SwaggerDefinition(
    info = @Info(title = "Tagging Service", version = "0.1",
        description = "A LAS2peer microservice generated by the CAE.",
        termsOfService = "none",
        contact = @Contact(name = "Thomas Winkler", email = "CAEAddress@gmail.com") ,
        license = @License(name = "BSD",
            url = "https://github.com/cae-development/microservice-Tagging-Service/blob/master/LICENSE.txt") ) )
public class Tagging extends Service {


  /*
   * Database configuration
   */
  private String jdbcDriverClassName;
  private String jdbcLogin;
  private String jdbcPass;
  private String jdbcUrl;
  private String jdbcSchema;
  private DatabaseManager dbm;



  public Tagging() {
    // read and set properties values
    setFieldValues();
    // instantiate a database manager to handle database connection pooling and credentials
    dbm = new DatabaseManager(jdbcDriverClassName, jdbcLogin, jdbcPass, jdbcUrl, jdbcSchema);
  }

  // //////////////////////////////////////////////////////////////////////////////////////
  // Service methods.
  // //////////////////////////////////////////////////////////////////////////////////////


  /**
   * 
   * putTag
   * 
   * @param tag a JSONObject 
   * @param id a String 
   * 
   * @return HttpResponse  
   * 
   */
  @PUT
  @Path("/tags/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiResponses(value = {
       @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "put"),
       @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "error")
  })
  @ApiOperation(value = "putTag", notes = " ")
  public HttpResponse putTag(@ContentParam String tag, @PathParam("id") String id) {
    JSONObject tag_JSON = (JSONObject) JSONValue.parse(tag);
    Connection conn = null; 
    try{ 
        conn = dbm.getConnection();
        PreparedStatement statement = conn.prepareStatement("Insert into tags (name,imgId) Values (?,?);"); 
        statement.setString(1,(String) tag_JSON.get("name")); 
        statement.setInt(2,Integer.parseInt(id));
        statement.executeUpdate();
        conn.close();  
 
        // put
        JSONObject putResponse = new JSONObject(); 
        putResponse.put("status","created");
        HttpResponse put = new HttpResponse(putResponse.toJSONString(), HttpURLConnection.HTTP_CREATED);
        return put; 
    }catch(Exception e){  
       e.printStackTrace();
       HttpResponse error = new HttpResponse("Internal Error: " + e.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR);
      return error;  
    }
    
  }

  /**
   * 
   * getTags
   * 
   * @param id a String 
   * 
   * @return HttpResponse  
   * 
   */
  @GET
  @Path("/tags/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.TEXT_PLAIN)
  @ApiResponses(value = {
       @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "tags")
  })
  @ApiOperation(value = "getTags", notes = " ")
  public HttpResponse getTags(@PathParam("id") String id) {

    // tags
    boolean tags_condition = true;
    if(tags_condition) {
      JSONObject tagsJson = new JSONObject();
      HttpResponse tags = new HttpResponse(tagsJson.toJSONString(), HttpURLConnection.HTTP_OK);
      return tags;
    }
    return null;
  }

  /**
   * 
   * putComment
   * 
   * @param comment a JSONObject 
   * @param id a String 
   * 
   * @return HttpResponse  
   * 
   */
  @PUT
  @Path("/comments/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiResponses(value = {
       @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "putResult")
  })
  @ApiOperation(value = "putComment", notes = " ")
  public HttpResponse putComment(@ContentParam String comment, @PathParam("id") String id) {
    JSONObject comment_JSON = (JSONObject) JSONValue.parse(comment);
    Connection conn = null; 
    try{ 
        conn = dbm.getConnection();
        PreparedStatement statement = conn.prepareStatement("Insert into comments (comment,imgId) Values (?,?);"); 
        statement.setString(1,(String) comment_JSON.get("text")); 
        statement.setInt(2,Integer.parseInt(id));
        statement.executeUpdate();
        conn.close(); 
        // putResult
        JSONObject putResultJson = new JSONObject(); 
        putResultJson.put("status","created");
        HttpResponse putResult = new HttpResponse(putResultJson.toJSONString(), HttpURLConnection.HTTP_CREATED);
        return putResult; 
    }catch(Exception e){  
        e.printStackTrace();
        HttpResponse errorResult = new HttpResponse("Internal Error: " + e.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR);
        return errorResult;  
    }
    
  }

  /**
   * 
   * getComments
   * 
   * @param id a String 
   * 
   * @return HttpResponse  
   * 
   */
  @GET
  @Path("/comments/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.TEXT_PLAIN)
  @ApiResponses(value = {
       @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "commentsResult")
  })
  @ApiOperation(value = "getComments", notes = " ")
  public HttpResponse getComments(@PathParam("id") String id) {
    JSONObject commentsJson = new JSONObject();
    JSONArray array = new JSONArray();
    Connection conn = null;
    try {
        conn = dbm.getConnection();
        PreparedStatement statement = conn.prepareStatement("Select * from comments where imgID='?' limit 10");
        ResultSet result = statement.executeQuery();
        while (result.next()) { 
            JSONObject commentJson = new JSONObject(); 
            commentJson.put("id",result.getInt("id")); 
            commentJson.put("text",result.getString("comment"));
            array.add(commentJson);
        }
        commentsJson.put("comments", array);
        conn.close();
        HttpResponse commentsResult = new HttpResponse(commentsJson.toJSONString(), HttpURLConnection.HTTP_OK);
        return commentsResult;
      } catch (Exception e) {
        e.printStackTrace();
        HttpResponse errorResult = new HttpResponse("Internal Error", HttpURLConnection.HTTP_INTERNAL_ERROR);
        return errorResult;
      }
  }




  // //////////////////////////////////////////////////////////////////////////////////////
  // Methods required by the LAS2peer framework.
  // //////////////////////////////////////////////////////////////////////////////////////

  
  /**
   * 
   * This method is needed for every RESTful application in LAS2peer. Please don't change.
   * 
   * @return the mapping
   * 
   */
  public String getRESTMapping() {
    String result = "";
    try {
      result = RESTMapper.getMethodsAsXML(this.getClass());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }


}
