import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import pojos.Message;
import pojos.SbUser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.IntBinaryOperator;

/**
 * Created by solvie on 2017-01-08.
 */
public class HTTPMethods {

    private static final String hostname = "192.168.68.8";
    //private static final String hostname = "localhost";


    public static String getRequest(String loadpage, String params, SbUser user) throws Exception{
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpHost target = new HttpHost(hostname, 8080, "http"); //TODO: this should be replaced by the actual address later
        String link = loadpage;
        if (params!=null)
            link = loadpage+"?"+params;
        HttpGet request = new HttpGet(link); //fixme hardcoded for test
        request.setHeader("Authorization", "Basic " + encodeUser(user.getUsername(), user.getPassword()));
        HttpResponse httpResponse = httpClient.execute(target, request);
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null)
            return EntityUtils.toString(entity);
        httpClient.close();
        return null;
    }

    public static String postRequest(String loadpage, String params, boolean asjson, SbUser user) throws Exception{
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpHost target = new HttpHost(hostname, 8080, "http"); //TODO: this should be replaced by the actual address later
        ObjectMapper mapper = new ObjectMapper();
        String link= loadpage;
        if (!asjson) {
            if (params!=null)
                link = loadpage+"?"+params;

        }
        HttpPost request = new HttpPost(link);

        if (asjson) {
            StringEntity paramsjson = new StringEntity(mapper.writeValueAsString(params));
            request.addHeader("Content-Type", "application/json");
            request.setEntity(paramsjson);
        }
        request.setHeader("Authorization", "Basic " + encodeUser(user.getUsername(), user.getPassword()));

        HttpResponse httpResponse = httpClient.execute(target, request);
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            return EntityUtils.toString(entity);
        }
        httpClient.close();
        return null;
    }

    public static String encodeUser(String user, String password){
        String userpassword=user+":"+password;
        return new String(Base64.getEncoder().encode(userpassword.getBytes(StandardCharsets.UTF_8)));
    }

    public static Message fileUploadPost(String filename, String mainfilename, int asstnum, SbUser user) throws IOException{
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        HttpPost httppost = new HttpPost("http://192.168.68.8:8080/upload");
        //String filename1 = "/playground/asst1-260293648.c";
        File file = new File(filename);
        httppost.setHeader("Authorization", "Basic " + encodeUser(user.getUsername(), user.getPassword()));

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("file", new FileBody(file));
        builder.addTextBody("username", user.getUsername());
        //builder.addTextBody("mainfilename", "asst1-260293648");
        builder.addTextBody("mainfilename", mainfilename);
        builder.addTextBody("asstnum", Integer.toString(asstnum));

        httppost.setEntity(builder.build());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);
        //System.out.println("executing request " + httppost.getRequestLine());
        System.out.println("Response was: "+response.getStatusLine());
        //System.out.println("Response was: "+responseString);
        httpclient.close();
        return new Message(Message.Mtype.SUCCESS, response.getStatusLine().toString(), responseString);
    }
}
