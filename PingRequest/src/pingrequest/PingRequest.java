/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package pingrequest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author edwardyun
 */
public class PingRequest {
    
    private final String USER_TOKEN = "R6S4OYQLAPWB7WKIID3D";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        PingRequest http = new PingRequest();
        
        System.out.println("Test 1 - Send HTTP GET request");
        http.sendGet();
        
        System.out.println("\n Test 2 - Send HTTP POST request");
        http.sendPost();
    }
    
    // HTTP GET request
    private void sendGet() throws Exception {
        String url = "https://www.eventbriteapi.com/v3/events/search/";
        
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        
        // parameters
        String query = "music";
        String city = "nashville";
        
        // optional default is GET
        connection.setRequestMethod("GET");
        
        // add request header
        connection.setRequestProperty("Authorization", "bearer +" + USER_TOKEN);
        connection.setRequestProperty("q", query);
        connection.setRequestProperty("venue.city", city);
        connection.setRequestProperty("start_date.range_start=", 
                dateFormatter(true));
        connection.setRequestProperty("start_date.range_end=", 
                dateFormatter(false));
        
        int responseCode = connection.getResponseCode();
        System.out.println("\n Sending 'GET' request to URL: " + url);
        System.out.println("Response Code : " + responseCode);
        
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        // print result
        System.out.println(response.toString());
    }
    
    // HTTP POST request
    private void sendPost() throws Exception {
        
        String url = "https://selfsolve.apple.com/wcResults.do";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        
        //add request header
        con.setRequestMethod("POST");
        String user = "Mozilla/5.0";
        con.setRequestProperty("User-Agent", user);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        
        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
        
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);
        
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        //print result
        System.out.println(response.toString());
    }
    
    // returns query string for HTTP requests
    // start and end dates are in UTC format
    private String formatParams(String city, String token) {
//        return "?venue.city=" + city + 
//                "&start_date.range_start=" + dateFormatter(true) + 
//                "&start_date.range_end" + dateFormatter(false) + 
//                "&token=" + token;
        return "?venue.city=Nashville&start_date.range_start=2015-03-21T00%3"
                + "A00%3A15Z&start_date.range_end=2015-03-22T23%3A00%3A58Z&"
                + "token=R6S4OYQLAPWB7WKIID3D";
    }
    
    // format: DD MM YYYY
    private String dateFormatter(Boolean st) {
        String start = "T00%3A00%3A15Z";
        String end = "T23%3A59%3A58Z";
        String time = "";
        if (st){
            time = start;
        }
        else {
            time = end;
        }
        return getDate() + time;
    }
    
    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");    
        Date date = new Date(); 
        return dateFormat.format(date); 
    }
    
}
