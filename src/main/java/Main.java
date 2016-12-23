/**
 * Created by solvie_lee on 12/10/2016.
 */
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Encoder;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;


public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Lets do this!! ");
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        String user = "nikola_tesla:000000000";
        String encodeduser = new String(Base64.getEncoder().encode(
                user.getBytes(StandardCharsets.UTF_8)));

        HttpPost httppost = new HttpPost("http://192.168.68.8:8080/upload");

        String filename = "/Users/solvie/config.cnf";
        File file = new File(filename);
        httppost.setHeader("Authorization", "Basic " + encodeduser);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("file", new FileBody(file));
       // builder.addTextBody("userName", userName);
       // builder.addTextBody("password", password);
      //  builder.addTextBody("macAddress", macAddress);
        httppost.setEntity(builder.build());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);
        System.out.println("executing request " + httppost.getRequestLine());
        System.out.println("Response was: "+response.getStatusLine());
        System.out.println("Response was: "+responseString);


        httpclient.close();
    }
}