package selenium.webtestsbase;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class RunnerMock {
    String baseURL; // stores server address from config file
    String responseMessage; // stores response message
    String responseBody;
    int responseCode; // stores response code
    public HashMap<String, String> values;

    public RunnerMock(){
        baseURL = PropertyReaderHelper.getValueFromFileByName("server.name");
        values = new HashMap<>();
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage.trim();
    }

    private String encodeStr(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "UTF-8");
    }

    private void converTextToMap(String respBody){
        String[] tokens = respBody.trim().split("&");
        for (int i = 0; i < tokens.length -1; i++){
            values.put(tokens[i], tokens[i]);
        }
    }

    public String getResponseBody() {
        return responseBody;
    }

    private void sendQueryAndReadResponse(String query, String url) {
        try {
            URL myURL = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) myURL.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-length", String.valueOf(query.length()));
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); //this is for bad cert problem

            DataOutputStream output = new DataOutputStream(conn.getOutputStream());
            output.writeBytes(query);
            output.close();

            DataInputStream input = new DataInputStream( conn.getInputStream() );


            StringBuilder strBld = new StringBuilder();
            for( int c = input.read(); c != -1; c = input.read() )
                //System.out.print( (char)c );
                strBld.append((char)c);
            input.close();

            responseBody = strBld.toString().trim();
            responseCode = conn.getResponseCode();
            responseMessage = conn.getResponseMessage();
            converTextToMap(responseBody);
            System.out.println(values.values());
            //System.out.println(conn.getContentType());

            //System.out.println("Resp Code:"+conn.getResponseCode());
            //System.out.println("Resp Message:"+ conn.getResponseMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void sendFinishJobQuery(String jobStatus, String jobReturnCode, String terminalErr,
                                     String synced_ok, String synced_err, String synced_conflicts,
                                     String elapsedTime, String speedAverage,String byteProcessed,
                                     String jobRunId){
        String url = baseURL + "/api/finish-job-run.html";
        String query = null;
        try {
            query = "jobstatus=" + encodeStr(jobStatus) + "&"
                    + "rc_int=" + encodeStr(jobReturnCode) + "&"
                    + "termerr=" + encodeStr(terminalErr) + "&"
                    + "synced_ok=" + encodeStr(synced_ok) + "&"
                    + "synced_err=" + encodeStr(synced_err) + "&"
                    + "synced_conf=" + encodeStr(synced_conflicts) + "&"
                    + "elapsed=" + encodeStr(elapsedTime) + "&"
                    + "speedave=" + encodeStr(speedAverage) + "&"
                    + "bytesproc=" + encodeStr(byteProcessed) + "&"
                    + "jobrunid=" + encodeStr(jobRunId);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sendQueryAndReadResponse(query, url);
    }

    //this is register new runner query
    public void sendNewUserQuery(String companyId, String userName, String computerName, String platform,
                                 String osInfo, String serverOs){
        String url = baseURL + "/api/new-user.html";
        String query = null;
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("companyid=").append(encodeStr(companyId)).append("&")
                    .append("username=").append(encodeStr(userName)).append("&")
                    .append("computername=").append(encodeStr(computerName)).append("&")
                    .append("platform=").append(encodeStr(platform)).append("&")
                    .append("osinfo=").append(encodeStr(osInfo)).append("&")
                    .append("serveros=").append(encodeStr(serverOs));
            query = builder.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sendQueryAndReadResponse(query, url);
    }

    //TODO it might require force param which is not implemented
    public void sendGetJobsQuery(String serveros, String gsver, String jobrunnerid){
        String url = baseURL + "/api/get-jobs.html";
        String query = null;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("serveros=").append(encodeStr(serveros)).append("&")
                    .append("gsver=").append(encodeStr(gsver)).append("&")
                    .append("jobrunnerid").append(encodeStr(jobrunnerid));
            query = builder.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sendQueryAndReadResponse(query, url);
    }

    public void sendGetRunReqQuery(String jobrunnerid){
        String url = baseURL + "/api/get-run-req.html";
        String query = null;
        try {
            query = query + "jobrunnerid=" + encodeStr(jobrunnerid);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sendQueryAndReadResponse(query, url);
    }

    public void sendNewJobRunQuery(String jobrunnerid, String jobid, String when_started, String gsver){
        String url = baseURL + "/api/new-job-run.html";
        String query = null;

        StringBuilder builder = new StringBuilder();
        try {
            builder.append("jobrunnerid=").append(encodeStr(jobrunnerid)).append("&")
                    .append("jobid=").append(encodeStr(jobid)).append("&")
                    .append("when_started=").append(encodeStr(when_started)).append("&")
                    .append("gsver=").append(encodeStr(gsver));
            query = builder.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sendQueryAndReadResponse(query, url);
    }

    public void sendUpdateJobRunQuery(String jobstatus, String pct_complete, String elapsed, String speedave,
                                      String speedins, String bytesproc, String timeremsec, String jobrunid){
        String url = baseURL + "/api/update-job-run.html";
        String query = null;

        StringBuilder builder = new StringBuilder();
        try {
            builder.append("jobstatus=").append(encodeStr(jobstatus)).append("&")
                    .append("pct_complete=").append(encodeStr(pct_complete)).append("&")
                    .append("elapsed=").append(encodeStr(elapsed)).append("&")
                    .append("speedave=").append(encodeStr(speedave)).append("&")
                    .append("speedins=").append(encodeStr(speedins)).append("&")
                    .append("bytesproc=").append(encodeStr(bytesproc)).append("&")
                    .append("timeremsec=").append(encodeStr(timeremsec)).append("&")
                    .append("jobrunid=").append(encodeStr(jobrunid));
            query = builder.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sendQueryAndReadResponse(query, url);
    }

    public void sendUpdateLog(String logline, String lines, String jobrunid){
        String url = baseURL + "/api/update-lof.html";
        String query = null;

        StringBuilder builder = new StringBuilder();

        try {
            builder.append("logline=").append(encodeStr(logline)).append("&")
                    .append("lines=").append(encodeStr(lines)).append("&")
                    .append("jobrunid=").append(encodeStr(jobrunid));
            query = builder.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sendQueryAndReadResponse(query, url);
    }

    public void sendUploadAccountsQuery(){
        //TODO implement it
    }

}

