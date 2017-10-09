package selenium.webtestsbase;

import com.mysql.jdbc.PreparedStatement;
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
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            if(sql.isEmpty()) {
                BufferedReader bufReader = new BufferedReader(new FileReader(filePath));
                StringBuffer strBuffer = new StringBuffer();
                String str;
                while ((str = bufReader.readLine()) != null) {
                    strBuffer.append(str);
                    strBuffer.append("\n");
                }
                sql = strBuffer.toString();
            }
            String[] sql1 = sql.split(";");
            for(int i = 0; i < sql1.length -1; i++){
                stmt.addBatch(sql1[i]);
                if((i == sql1.length -2) || (i%5 == 0)){
                    stmt.executeBatch();
                    conn.commit();
                    conn.setAutoCommit(true);
                }
                conn.setAutoCommit(false);
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
        }
    }

    public static void dropAdminTable(){
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL, userName, password);
            stmt = conn.createStatement();
            String sql = "CREATE TABLE `Administrators` (\n" +
                    "  `admin_id`      int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Administrator Id, globally unique, generated',\n" +
                    "\n" +
                    "  `company_id`    int unsigned NOT NULL COMMENT 'Company Id, foreign key',\n" +
                    "  `admin_email`   char(40)     NOT NULL COMMENT 'Admin Email, can change',\n" +
                    "\n" +
                    "  `email_confirmed` boolean   NOT NULL DEFAULT 0 COMMENT 'T: admin_email is confirmed, F: admin did not confirm email',\n" +
                    "  `admin_name`    varchar(60) NOT NULL COMMENT 'Admin Full Name',\n" +
                    "  `pass_hash`     char(32)    NOT NULL COMMENT 'MD5 hash of User password, hex string',\n" +
                    "  `perm_password` boolean     NOT NULL DEFAULT 0 COMMENT 'True: permanent password, F: temp generated password',\n" +
                    "\n" +
                    "  `is_company_admin` boolean  NOT NULL DEFAULT 0 COMMENT 'True: company admin, F: group admin',\n" +
                    "  \n" +
                    "  `email_confirm_hash` char(32) NULL DEFAULT NULL COMMENT 'Field with tmp email + salt hash for email confirmation',\n" +
                    "    \n" +
                    "  `is_active`     boolean     NOT NULL DEFAULT '1',\n" +
                    "  `created_at`    datetime    NOT NULL COMMENT 'Date time when this Admin was created',\n" +
                    "\n" +
                    "  PRIMARY KEY (`admin_id`),\n" +
                    "  UNIQUE KEY  (`admin_email`),\t\t-- Email is globally unique, between companies too\n" +
                    "\n" +
                    "  CONSTRAINT `fk_admins_companies`  \n" +
                    "    FOREIGN KEY (`company_id`)  \n" +
                    "    REFERENCES `Companies` (`company_id`) ON DELETE CASCADE\n" +
                    "\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Administrators table';"
                    + "INSERT INTO `Administrators` (`admin_id`, `company_id`, `admin_email`, `admin_name`, `pass_hash`, `is_company_admin`, `created_at`, `perm_password`)";
            stmt.executeUpdate(sql);
        } catch(Exception ex){
            ex.getMessage();
        }
    }
}
