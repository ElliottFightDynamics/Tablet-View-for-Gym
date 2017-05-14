package com.efd.striketectablet.utilities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.efd.striketectablet.DTO.WebServiceData;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

@SuppressLint("UseSparseArrays")
public class CommonWebServiceAsyncTask extends AsyncTask<WebServiceData, Void, String> {

    private Context applicationContext;
    private ProgressDialog dialog;
    public CommonWebServiceResponseInterface webServiceResponse = null;
    public static final int HTTP_OK = 200;

    /**
     * CommonWebServiceAsyncTask constructor
     *
     * @param applicationContext
     */
    public CommonWebServiceAsyncTask(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * convertStreamToString to convert a web service response message stream to string
     *
     * @param inputStream
     * @return
     */
    private String convertStreamToString(InputStream inputStream) {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {

                sb.append(line + "\n");
            }
        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    @Override
    protected void onPreExecute() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
        dialog = ProgressDialog.show(applicationContext, "Registration", "Processing....", true);
    }

    @Override
    protected String doInBackground(WebServiceData... webServiceData) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> boxer : webServiceData[0].webServiceParams.entrySet()) {
            param.add(new BasicNameValuePair(boxer.getKey(), boxer.getValue()));
        }
        String text = null;
        HttpGet httpGet = null;
        HttpPost httpPost = null;
        HttpResponse response = null;


        if ((webServiceData[0].httpMethodType).equals("GET")) {
            String paramToAppend = "";
            if (!param.isEmpty()) {
                for (int i = 0; i < param.size(); i++) {
                    if (i == 0)
                        paramToAppend = param.get(i).toString();
                    else
                        paramToAppend = paramToAppend + "&" + param.get(i).toString();
                }
            }

            String urlGet = webServiceData[0].webServiceUrl + paramToAppend;
            Log.e(getClass().getSimpleName(), "urlGet = "+urlGet);
            httpGet = new HttpGet(urlGet);
            try {
                response = httpClient.execute(httpGet, localContext);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if ((webServiceData[0].httpMethodType).equals("POST")) {

            String urlPost = webServiceData[0].webServiceUrl;
            Log.e(getClass().getSimpleName(), "urlPost = "+urlPost);

            httpPost = new HttpPost(urlPost);
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(param, HTTP.UTF_8));
                response = httpClient.execute(httpPost, localContext);
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                text = null;
            } else if (entity != null) {

                InputStream instream = entity.getContent();
                text = convertStreamToString(instream);
                instream.close();
            }
        } catch (Exception e) {
            text = null;
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
        return text;
    }

    protected void onPostExecute(String result) {
        Log.d("LoginActivity", "login task post execute");
        try {
            dialog.dismiss();
            webServiceResponse.webServiceProcessFinish(result);
        } catch (Exception e) {
            e.printStackTrace();
            dialog.dismiss();
        }
    }

    /**
     * isNetworkAvailable to check whether connection exist to web service
     * @param context
     * @param webServiceUrl
     * @return
     */
    /*public int isNetworkAvailable(Context context, String webServiceUrl) {
		try{
		    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()){
	            URL url = new URL(webServiceUrl);
	            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
	            urlc.setRequestProperty("Connection", "close");
	            urlc.setConnectTimeout(2000); 
	            urlc.connect();
	            return urlc.getResponseCode();
	        }
		    else{
		    	return -1;
		    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}*/
}
