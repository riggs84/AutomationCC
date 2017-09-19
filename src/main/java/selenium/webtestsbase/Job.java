package selenium.webtestsbase;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Job {
    String name;
    String id;
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
        String str = respBody.substring(respBody.indexOf("/"));
        //String str = respBody.split("\\d+\\:\\w+")[1].trim();
        String[] tokens = str.split("/|=");
        for (int i =0; i < tokens.length -1;){
            jobOptions.put(tokens[i++], tokens[i++]);
        }
    }

    public String getJobOptValueByKey(String key){
        return jobOptions.get(key);
    }

}
