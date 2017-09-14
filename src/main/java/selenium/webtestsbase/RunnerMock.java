package selenium.webtestsbase;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

public class RunnerMock {
    String baseURL; // stores server address from config file
    String responseMessage; // stores response message
    String responseBody;
    int responseCode; // stores response code
    HashMap<String, String> values;

    public RunnerMock(){
        baseURL = PropertyReaderHelper.getValueFromFileByName("server.name");
        values = new HashMap<>();
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    private String encodeStr(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "UTF-8");
    }

    private void storeTextToMap(String respBody){
        String[] tokens = respBody.trim().split("&|=");
        for (int i = 0; i < tokens.length -1;){
            values.put(tokens[i++], tokens[i++]);
        }
    }

    public String getResponseBody() {
        return responseBody;
    }

    public String getValueByKey(String key){
        return values.get(key);
    }

    private void sendQueryAndReadResponseDigestAuth(String query, String url) {
        // stackoverflow development - some magic is coming below   http://literatejava.com/networks/ignore-ssl-certificate-errors-apache-httpclient-4-4/
        HttpClientBuilder b = HttpClientBuilder.create();

        // setup a Trust Strategy that allows all certificates.
        //
        SSLContext sslContext = null;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    return true;
                }
            }).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        b.setSslcontext(sslContext);

        // don't check Hostnames, either.
        //      -- use SSLConnectionSocketFactory.getDefaultHostnameVerifier(), if you don't want to weaken
        HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

        // here's the special part:
        //      -- need to create an SSL Socket Factory, to use our weakened "trust strategy";
        //      -- and create a Registry, to register it.
        //
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory)
                .build();

        // now, we create connection-manager using our Registry.
        //      -- allows multi-threaded use
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        b.setConnectionManager(connMgr);
        /*SSLConnectionSocketFactory sslsf = null;
        SSLContextBuilder builder = new SSLContextBuilder();
        try {
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        try {
            sslsf = new SSLConnectionSocketFactory(
                    builder.build());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }*/
        CloseableHttpClient httpClient = b.build();
        //CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("jobrunnerid", "617"));
        nvps.add(new BasicNameValuePair("serveros", "0"));
        nvps.add(new BasicNameValuePair("gsver", "10.5.5.3"));
        DigestScheme digestAuth = new DigestScheme();
        digestAuth.overrideParamter("algorithm", "MD5");
        digestAuth.overrideParamter("realm", "JobServer");
        digestAuth.overrideParamter("nonce", "4017397a3051e9d385ec218831e11a17");
        digestAuth.overrideParamter("qop", "auth");
        digestAuth.overrideParamter("opaque", "3188f230b037557f2e6d68fde5c0e561");

        Header auth = null;
        try {
            auth = digestAuth.authenticate(new
                    UsernamePasswordCredentials(getValueByKey("jobrunnerid"), getValueByKey("password")), httpPost);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        System.out.println(auth.getName());
        System.out.println(auth.getValue());
        httpPost.setHeader(auth);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        CloseableHttpResponse response2 = null;
        try {
            response2 = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(response2.getStatusLine());
            HttpEntity entity2 = response2.getEntity();
            InputStream input = entity2.getContent();
            BufferedReader br;
            StringBuilder sb = new StringBuilder();
            String line;
            br = new BufferedReader(new InputStreamReader(input));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            System.out.print(sb.toString());
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity2);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void sendQueryAndReadResponse(String query, String url) {
        try {
            /*String credentials = values.get("jobrunnerid") + ":" + values.get("password");
            String encodedCreds = Base64.getEncoder().encodeToString(credentials.getBytes("UTF-8"));*/

            URL myURL = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) myURL.openConnection();
            //conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-length", String.valueOf(query.length()));
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            //conn.setRequestProperty("Authorization", "Basic " + encodedCreds);
            conn.setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); //this is for bad cert problem

            DataOutputStream output = new DataOutputStream(conn.getOutputStream());
            DataInputStream input = null;
            output.writeBytes(query);
            output.close();
            conn.connect();

            input = new DataInputStream(conn.getInputStream());
            StringBuilder strBld = new StringBuilder();
            for(int c = input.read(); c != -1; c = input.read())
                //System.out.print( (char)c );
                strBld.append((char)c);
            input.close();

            // save response data
            responseBody = strBld.toString().trim();
            responseCode = conn.getResponseCode();
            responseMessage = conn.getResponseMessage().trim();

            // place response params into hash map
            storeTextToMap(responseBody);
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
                                 String osInfo, String serverOs, String gsver){
        String url = baseURL + "/api/new-user.html";
        String query = null;
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("companyid=").append(encodeStr(companyId)).append("&")
                    .append("username=").append(encodeStr(userName)).append("&")
                    .append("computername=").append(encodeStr(computerName)).append("&")
                    .append("platform=").append(encodeStr(platform)).append("&")
                    .append("osinfo=").append(encodeStr(osInfo)).append("&")
                    .append("serveros=").append(encodeStr(serverOs)).append("&")
                    .append("gsver=").append(encodeStr(gsver));
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
                    .append("jobrunnerid=").append(encodeStr(jobrunnerid));
            query = builder.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sendQueryAndReadResponseDigestAuth(query, url);
        //sendQueryAndReadResponse(query, url);
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

