package com.example.sonamessteetenzin.perfectplate;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kinley on 4/12/2018.
 */

public class DownloadUrl {
    public String readUrl(String myUrl) throws IOException
    {
        String data ="";
        InputStream inputStream= null;
        HttpURLConnection urlConnection=null;
        try {

            URL url= new URL(myUrl);
            //creating a http connection to communicate with url
            urlConnection=(HttpURLConnection)url.openConnection();
            //connecting to url
            urlConnection.connect();
            //reading data from url
            inputStream=urlConnection.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();
            String line = "";//to read each line one by one using while loop
            while ((line=br.readLine())!=null) {//Checking if it is null or not and if it is not null then append it to the string buffer
                sb.append(line);
            }
            data=sb.toString();
            Log.d("downloadUrl",data.toString());
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();

            Log.d("exception in url",e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            inputStream.close();
            urlConnection.disconnect();

        }
        return data;
    }
}