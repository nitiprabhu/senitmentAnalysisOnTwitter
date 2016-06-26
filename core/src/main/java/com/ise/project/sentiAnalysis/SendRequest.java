package com.ise.project.sentiAnalysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nitish on 25/01/16
 */
public class SendRequest {

    private final static Logger logger = LoggerFactory.getLogger(SendRequest.class);
    public InputStream postURL(HttpURLConnection connection, URL url, String urlParameters, String request) throws
            IOException {

        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("charset", "utf-8");
        connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
        connection.setUseCaches(false);

        DataOutputStream wr = null;


        try(  DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream()))
          {
            dataOutputStream.writeBytes(urlParameters);
            dataOutputStream.flush();
          }catch (Exception e){

            logger.error("DataOutStream Exception {}, {}" , e.getMessage(),e);
            }

        InputStream is = connection.getInputStream();
        return is;
    }
}