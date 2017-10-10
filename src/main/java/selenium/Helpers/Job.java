package selenium.Helpers;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Job {
    private String name;
    private String id;
    HashMap<String, String> jobOptions;

    public Job(String responseBody){
        jobOptions = new HashMap<>();
        setJobNameAndId(responseBody);
        parseJobSettings(responseBody);
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }

    private void setJobNameAndId(String respBody){
        Pattern p = Pattern.compile("\\d+\\:\\w+");
        Matcher m = p.matcher(respBody);
        String string = "";
        if ( m.find() ){
            string = string + m.group();
        }
        else {
            throw new IllegalStateException("no job id and name were found in response body");
        }
        String[] str = string.split(":");
        id = str[0];
        name = str[1];
    }

    private void parseJobSettings(String respBody){
        String str = respBody.substring(respBody.indexOf("/")-1);
        //String str = respBody.split("  /")[1];
        String[] tokens = str.split(" /|=");
        for (int i = 1; i < tokens.length -1;){
            jobOptions.put(tokens[i++].trim(), tokens[i++].trim());
        }
    }

    public String getJobOptValueByKey(String key){
        return jobOptions.get(key);
    }


}
