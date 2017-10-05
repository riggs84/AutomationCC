package selenium.webtestsbase;

import org.apache.xalan.xslt.Process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class  SQLhelper {

    final static String jdbcDriverClass = "com.mysql.jdbc.Driver";
    final static String dataBaseURL = "jdbc:mysql://localhost/?allowMultiQueries=true";

    final static String userName = "root";
    final static String password = "123456";

    static String sql = "";

    public static void cleanAndRecreateDataBase(){
        Connection conn = null;
        Statement stmt = null;
        String filePath = new File("").getAbsolutePath() + "/SQLScripts/job-server-data-model1.sql";
        ProcessBuilder pb = new ProcessBuilder("C:\\Programm Files\\MySQL\\MySQL Server 5.1\\bin\\mysql.exe"
                , "--user=" + userName + "--password=" + password + "-e ./ " + filePath);
        try {
            java.lang.Process p = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*String[] sql1 = null;
        try {
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL, userName, password);
            stmt = conn.createStatement();
            //if(sql.isEmpty()){
            if(sql1.length == 0){
                BufferedReader bufReader = new BufferedReader(new FileReader(filePath + "/SQLScripts/job-server-data-model1.sql"));
                StringBuffer strBuffer = new StringBuffer();
                String str;
                while ((str = bufReader.readLine()) != null) {
                    strBuffer.append(str);
                    strBuffer.append("\n");
                }
                sql1 = strBuffer.toString().split(";");
                sql = strBuffer.toString();
            }
            for(int i = 0; i < sql1.length; i++){
                stmt.execute(sql1[i]);
            }
            //stmt.execute(sql);
        } catch(Exception ex) {
            ex.getMessage();
            System.out.println(ex.getMessage());
        } finally {
            if(stmt!=null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }
}
