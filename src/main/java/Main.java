/**
 * Created by solvie_lee on 12/10/2016.
 */
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Scanner;


import com.sun.org.apache.xerces.internal.impl.xs.SchemaNamespaceSupport;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import pojos.Message;
import pojos.SbUser;


public class Main {
    public static void main(String[] args) throws Exception {

       /* System.out.println(">> Welcome to SubmissionBox!\n>> Please log in: ");
        Scanner sc = new Scanner(System.in);
        System.out.println("username: ");
        String username = sc.nextLine();
        System.out.println("password: ");
        String password = sc.nextLine();*/

        //TODO: attempt to log in. If fail, then try password three times, if that fails, quit.

        //TODO: if login is successful, have the student choose
                //TODO: which assignment to submit for
                //TODO: display assignment details (like the name of it)
                //TODO: display the submission instructions
                //TODO: then have them enter the path to their submissons

        //TODO: send the thing along, and if we aren't returned an error
            //as in, it compiles, and some sort of result was returned back to the student

        //TODO: ask if they want to save that score, showing them their previous score if any.
        //TODO: if they say yes, tell them how it went, then quit. if they say no, quit.



        String user = "nikola_tesla:000000000";
        SbUser sbuser = new SbUser(null, "nikola_tesla", "000000000");
        String encodeduser = new String(Base64.getEncoder().encode(user.getBytes(StandardCharsets.UTF_8)));

        //ASST1 - C unit test

        String filename = "/playground/asst1-260293648.c";
        String mainfilename = "asst1-260293648";
        int asstnum = 1;

        Message response = HTTPMethods.fileUploadPost(filename, mainfilename, asstnum, sbuser );
        System.out.println(response);


        //ASST2 - C output test
/*
        HttpPost httppost = new HttpPost("http://192.168.68.8:8080/upload");

        String filename1 = "/playground/gabriel.c";
        File file1 = new File(filename1);
        httppost.setHeader("Authorization", "Basic " + encodeduser);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("file", new FileBody(file1));
        builder.addTextBody("username", "nikola_tesla");
        builder.addTextBody("mainfilename", "gabriel");
        builder.addTextBody("asstnum", "2");

        httppost.setEntity(builder.build());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);
        System.out.println("executing request " + httppost.getRequestLine());
        System.out.println("Response was: "+response.getStatusLine());
        System.out.println("Response was: "+responseString);*/

        //ASST3 J Unit test
/*
        HttpPost httppost = new HttpPost("http://192.168.68.8:8080/upload");

        String filename1 = "/playground/lucas.zip";
        File file1 = new File(filename1);
        httppost.setHeader("Authorization", "Basic " + encodeduser);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("file", new FileBody(file1));
        builder.addTextBody("username", "nikola_tesla");
        builder.addTextBody("mainfilename", "In2pJ");
        builder.addTextBody("asstnum", "3");

        httppost.setEntity(builder.build());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);
        System.out.println("executing request " + httppost.getRequestLine());
        System.out.println("Response was: "+response.getStatusLine());
        System.out.println("Response was: "+responseString);
*/
        //ASST4 Java output test

/*
        HttpPost httppost = new HttpPost("http://192.168.68.8:8080/upload");

        String filename1 = "/playground/someasstname.zip";
        File file1 = new File(filename1);
        httppost.setHeader("Authorization", "Basic " + encodeduser);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("file", new FileBody(file1));
        builder.addTextBody("username", "nikola_tesla");
        builder.addTextBody("mainfilename", "calc");
        builder.addTextBody("asstnum", "4");

        httppost.setEntity(builder.build());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);
        System.out.println("executing request " + httppost.getRequestLine());
        System.out.println("Response was: "+response.getStatusLine());
        System.out.println("Response was: "+responseString);

*/
    }
}