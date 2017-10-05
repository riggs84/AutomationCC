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

        try {
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL, userName, password);
            stmt = conn.createStatement();
            if(sql.isEmpty()){
                BufferedReader bufReader = new BufferedReader(new FileReader(filePath));
                StringBuffer strBuffer = new StringBuffer();
                String str;
                while ((str = bufReader.readLine()) != null) {
                    strBuffer.append(str);
                    strBuffer.append("\n");
                }
                sql = strBuffer.toString();
            }
            stmt.execute(sql);
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
        }
    }
}
