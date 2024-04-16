package bi_project;

import java.time.LocalDateTime;

public class CustomLoggers {
    
    static void sendLog(String title, String Text, String Status){
        String TimeStamp = String.valueOf(LocalDateTime.now());
        String log = String.format("%s | %s | %s | %s", TimeStamp, title, Text, Status);
        System.out.println(log);
    }

    static void sendLog(String title, String Text, boolean Status){
        String TimeStamp = String.valueOf(LocalDateTime.now());
        String log = String.format("%s | %s | %s | %s", TimeStamp, title, Text, Status?"Pass":"Fail");
        System.out.println(log);
    }
}
