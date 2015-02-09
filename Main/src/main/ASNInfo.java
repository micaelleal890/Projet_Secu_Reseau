// IP Intelligence REST Web Service
// @author www.quova.com
//Copyright 2010 Quova, Inc.
//This example illustrates how to execute a web service request via HTTP GET. 08
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class ASNInfo {

   public static String service = "http://yourpentesting.com/";


   public static void main(String[] args) throws Exception {

      MessageDigest md = MessageDigest.getInstance("MD5");
      long timeInSeconds = (long)(System.currentTimeMillis()/1000);

      String sig = String.format("%032x", new BigInteger(1, md.digest()));

      String url = service;
      System.out.println("URL=" + url);
      DefaultHttpClient httpclient = new DefaultHttpClient();

      // Create an HTTP GET request
      HttpGet httpget = new HttpGet(url);

      // Execute the request
      httpget.getRequestLine();
      HttpResponse response = null;
      try {
         response = httpclient.execute(httpget);
      } catch (ClientProtocolException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }

      HttpEntity entity = response.getEntity();
      // Print the response
      System.out.println(response.getStatusLine());

      if (entity != null) {
         try {
            InputStream inputStream = entity.getContent();
            // Process the response
            BufferedReader bufferedReader = new BufferedReader(
                  new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
               System.out.println(line);
            }
            bufferedReader.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      // shut down the connection manager to ensure
      // immediate deallocation of all system resources.
      httpclient.getConnectionManager().shutdown();
   }
}