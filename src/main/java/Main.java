/**
 * Created by solvie_lee on 12/10/2016.
 */

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pojos.Message;
import pojos.SbAssignment;
import pojos.SbUser;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;


public class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        SbUser user = new SbUser();
        SbAssignment asst;

        //LOGIN
        System.out.println(">> Welcome to SubmissionBox!\n>> Please log in: ");
        int tries=0;/*
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println(">> username: ");
            user.setUsername(sc.nextLine());
            System.out.println(">> password: ");
            user.setPassword(sc.nextLine());
            String response = HTTPMethods.postRequest("/login", null, new SbUser(null, user.getUsername(), user.getPassword()));
            if (response.contains("Hello")) {
                System.out.println(">> Hi "+ user.getUsername());
                break;
            }
            else {
                System.out.println("Authentication failed");
                if (tries<3) {
                    tries++;
                    System.out.println(String.format("You have %d tries left", 3 - tries));
                }
                else exit(0);
            }
        }*/
        //TODO: get rid of me
        user.setUsername("nikola_tesla");
        user.setPassword("000000000");

        //CHOOSE ASSIGNMENT
        Scanner sc = new Scanner(System.in);
        System.out.println(">> Choose the number of the assignment you would like to submit: ");
        String response = HTTPMethods.postRequest("/testGetAssignmentDetails", null, false, user);
        List<SbAssignment> list;
        list =mapper.readValue(response, new TypeReference<List<SbAssignment>>(){});
        if (list.size()==0){
            System.out.println("No assignments!");
            exit(0);
        }

        while (true) {
            for (SbAssignment assignment : list)
                System.out.println(String.format(">> %d : %s (%s)", assignment.getAssignmentNum(), assignment.getDescription(), assignment.getLanguage().toString()));
            String selection = sc.nextLine();
            int asstnum;
            try {
                asstnum = Integer.parseInt(selection);
                asst =SbAssignment.findAsst(list, asstnum);
                if (asst!=null)
                    break;
                else {
                    System.out.println("Invalid input, please select an assignment from the list");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number");
                continue;
            }
        }

        // GET PATH
        System.out.println(">> Input the name of your file (.zip, .c, or .java)");
        String filename = sc.nextLine();
        String mainclassname;
        if (filename.contains(".zip")){
            System.out.println(">> Enter the name of your main class");
            mainclassname = sc.nextLine();
        } else {
            mainclassname = filename.split("\\.")[0];
        }

        // RUN
        Message runresults = HTTPMethods.fileUploadPost(filename, mainclassname, asst.getAssignmentNum(), user );
        if (runresults.getMessagetype()== Message.Mtype.SUCCESS){
            System.out.println(">> Your results are: ");
            System.out.println(">> "+ runresults.getDetails());
        } else{
            System.out.println(">> Something went wrong: ");
            System.out.println(">> "+ runresults.getDetails());
            System.out.println(">> Quitting... ");
            exit(0);
        }

        //TODO: ask if they want to save that score, showing them their previous score if any.
        System.out.println(">> Would you like to save your score? (y/n)");

        while (true) {
            String yn = sc.nextLine();
            if (yn.equalsIgnoreCase("y")) {
                String params = String.format("asstnum=%d&mainclassname=%s", asst.getAssignmentNum(), mainclassname);
                String responsentity = HTTPMethods.postRequest("/testSaveScore", params, false, user );
                System.out.println(">> "+ responsentity+"!");
                exit(0);
            } else if (yn.equalsIgnoreCase("n")) {
                System.out.println(">> Quitting...");
            } else {
                System.out.println(">> Please enter 'y' or 'n':");
                continue;
            }
        }



       // SbUser sbuser = new SbUser(null, "nikola_tesla", "000000000");

        //ASST1 - C unit test
/*
        String filename = "/playground/asst1-260293648.c";
        String mainfilename = "asst1-260293648";
        int asstnum = 1;*/


        //ASST2 - C output test
/*
        String filename = "/playground/gabriel.c";
        String mainfilename = "gabriel";
        int asstnum = 2;*/

        //ASST3 J Unit test
        /*

        String filename = "/playground/lucas.zip";
        String mainfilename = "In2pJ";
        int asstnum = 3;*/

        //ASST4 Java output test
/*
        String filename = "/playground/someasstname.zip";
        String mainfilename = "calc";
        int asstnum = 4;*/

        //Message response = HTTPMethods.fileUploadPost(filename, mainfilename, asstnum, sbuser );
        //System.out.println(response);
    }
}