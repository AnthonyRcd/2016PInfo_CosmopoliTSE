package TSE.P_INFO.CosmopoliTse;

import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DaveHttp {

	//Uncompress the received dates
	public static String uncompress(GZIPInputStream in) throws IOException {   
		 if (in == null) {   
			 return "";   
		 }   
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
         byte[] buffer = new byte[256];
		 int n;   
		 while ((n = in.read(buffer))>= 0) {   
		 	out.write(buffer, 0, n);   
		 }   
		    // ToString () using the platform default encoding, can also be explicitly specified as toString ()   
		 return out.toString("utf-8");
	}   
	
    // A request to send a GET method to the specified URL
    // realUrl: The specified URL
	public static String sendGet(String url) throws IOException {
        String result = "";
        GZIPInputStream in = null;
        
        try {
        	URL realUrl = new URL(url);
            // Open the connection to servers with one URL
            URLConnection connection = realUrl.openConnection();
            // Set the generic request properties
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            // Establish the actual connection
            connection.connect();
            // Define GZIPInputStream and uncompress responsive dates from servers
            in = new GZIPInputStream(connection.getInputStream());    
   		    result = uncompress(in);
   		    
   		    // Return
   		 	return result;
            
        } catch (Exception e) {
            System.out.println("error in http get!" + e);
            e.printStackTrace();
        }
        // Use the 'finally' block to close the input stream
        finally {
            in.close();
        }
        // Return
        return result;
    }
    public static String secondStoryDave(String Tag) throws IOException{
    	String url = "https://api.stackexchange.com/2.2/tags/" + Tag + "/top-answerers/all_time?page=1&pagesize=1&site=stackoverflow";
    	String jsonString = DaveHttp.sendGet(url);
    	JSONObject obj= new JSONObject(jsonString);  
    	String tagTopAnswerer=obj.getJSONArray("items").getJSONObject(0).getJSONObject("user").getString("display_name");
    	return tagTopAnswerer;
    }
    
    public static void main(String[] args) throws IOException {
        System.out.println(DaveHttp.secondStoryDave("C++"));
    }
}