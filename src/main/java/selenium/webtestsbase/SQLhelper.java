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
    final static String dataBaseURL = "jdbc:mysql://localhost/";

    final static String userName = "root";
    final static String password = "123456";

    public static void cleanDataBase(){
        Connection conn = null;
        Statement stmt = null;
        String filePath = new File("").getAbsolutePath();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dataBaseURL, userName, password);
            stmt = conn.createStatement();
            //String sql = "DROP DATABASE jobserver";
            //stmt.executeUpdate(sql);
            ScriptRunner scriptRunner = new ScriptRunner(conn, false, true);
            scriptRunner.runScript(new BufferedReader(new FileReader(filePath + "/SQLScripts/siberQA-sql-db.sql")));
        } catch(Exception ex) {
            ex.getMessage();
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
