package selenium.webtestsbase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class  SQLhelper {

    final static String jdbcDriverClass = "com.mysql.jdbc.Driver";
    final static String dataBaseURL = "jdbc:mysql://localhost/?allowMultiQueries=true";

    final static String userName = "root";
    final static String password = "123456";

    public static void cleanAndRecreateDataBase(){
        Connection conn = null;
        Statement stmt = null;
        String filePath = new File("").getAbsolutePath();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dataBaseURL, userName, password);
            stmt = conn.createStatement();
            BufferedReader bufReader = new BufferedReader(new FileReader(filePath + "/SQLScripts/job-server-data-model1.sql"));
            StringBuffer strBuffer = new StringBuffer();
            String str;
            while ((str = bufReader.readLine()) != null) {
                strBuffer.append(str);
                strBuffer.append("\n");
            }
            String sql = strBuffer.toString();
            stmt.execute(sql);
            //String sql = "DROP DATABASE jobserver";
            //stmt.executeUpdate(sql);
            //ScriptRunner scriptRunner = new ScriptRunner(conn, false, true);
            //scriptRunner.runScript(new BufferedReader(new FileReader(filePath + "/SQLScripts/job-server-data-model.sql")));
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
