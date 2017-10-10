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
    final static String dataBaseURL = "jdbc:mysql://localhost/";

    final static String userName = "root";
    final static String password = "123456";

    static String sql = "";

    public static void cleanAndRecreateDataBase(){
        Connection conn = null;
        Statement stmt = null;
        String filePath = new File("").getAbsolutePath() + "/SQLScripts/job-server-data-model1.sql";
        try {
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL +"?allowMultiQueries=true", userName, password);
            //conn.setAutoCommit(false);
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
            stmt.executeUpdate(sql);
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

    public static void dropUsersTable(){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL + "jobserver" +"?allowMultiQueries=true", userName, password);
            stmt = conn.createStatement();
            String sql = /*"SET foreign_key_checks = 0;\n" +
                    "DROP TABLE IF EXISTS `Users`;\n" +
                    "SET foreign_key_checks = 1*/"DELETE FROM `users` ;";
            stmt.executeUpdate(sql);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            if(stmt!=null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void dropUserGroupsTable(){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL + "jobserver" +"?allowMultiQueries=true", userName, password);
            stmt = conn.createStatement();
            String sql = "SET foreign_key_checks = 0;\n" +
                    "DROP TABLE IF EXISTS `Jobs`;\n" +
                    "SET foreign_key_checks = 1;\n" + "CREATE TABLE `UserGroups` (\n" +
                    "  `ugroup_id`  int unsigned NOT NULL AUTO_INCREMENT COMMENT 'User Group Id, globally unique, generated',\n" +
                    "\n" +
                    "  `company_id`     int unsigned NOT NULL COMMENT 'Company Id, foreign key',\n" +
                    "  `ugroup_name`    char(40) NOT NULL COMMENT 'User Group Name, can change',\n" +
                    "\n" +
                    "  `ugroup_os_name` char(40) NOT NULL COMMENT 'User Group OS Name, can change',\n" +
                    "\n" +
                    "  `is_active`   boolean  NOT NULL DEFAULT '1',\n" +
                    "  `created_at`  datetime NOT NULL COMMENT 'date time when group was created',\n" +
                    "\n" +
                    "  PRIMARY KEY (`ugroup_id`),\n" +
                    "  UNIQUE KEY  (`company_id`, `ugroup_name`),\n" +
                    "  CONSTRAINT `fk_user_groups_companies`   \n" +
                    "    FOREIGN KEY (`company_id`)  \n" +
                    "    REFERENCES `Companies` (`company_id`) ON DELETE CASCADE\n" +
                    "\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='User Groups table';\n" +
                    "\n" +
                    "CREATE TABLE `UsersInGroups` (\n" +
                    "  `uig_id`  int unsigned NOT NULL AUTO_INCREMENT COMMENT 'User In Group Assignment Id, globally unique, generated',\n" +
                    "\n" +
                    "  `company_id` int unsigned NOT NULL COMMENT 'Company Id, foreign key',\n" +
                    "  `ugroup_id`  int unsigned NOT NULL COMMENT 'User Group Id, foreign key',\n" +
                    "  `user_id`    int unsigned NOT NULL COMMENT 'User Id, foreign key',\n" +
                    "\n" +
                    "  PRIMARY KEY (`uig_id`),\n" +
                    "  UNIQUE  KEY (`company_id`, `ugroup_id`, `user_id`),\n" +
                    "\n" +
                    "  CONSTRAINT `fk_users_in_groups_companies`   \n" +
                    "    FOREIGN KEY (`company_id`)   \n" +
                    "    REFERENCES `Companies` (`company_id`) ON DELETE CASCADE,\n" +
                    "  CONSTRAINT `fk_users_in_groups_users`       \n" +
                    "    FOREIGN KEY (`user_id` )     \n" +
                    "    REFERENCES `Users` (`user_id`) ON DELETE CASCADE,\n" +
                    "  CONSTRAINT `fk_users_in_groups_user_groups` \n" +
                    "    FOREIGN KEY (`ugroup_id`)    \n" +
                    "    REFERENCES `UserGroups` (`ugroup_id`) ON DELETE CASCADE\n" +
                    "\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Users in User Groups table';";
            stmt.executeUpdate(sql);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            if(stmt!=null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void dropJobsTable(){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL + "jobserver" +"?allowMultiQueries=true", userName, password);
            stmt = conn.createStatement();
            String sql = "DELETE FROM `Jobs` ;";

            stmt.executeUpdate(sql);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            if(stmt!=null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void dropComputersTable(){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL + "jobserver" +"?allowMultiQueries=true", userName, password);
            stmt = conn.createStatement();
            String sql = "DELETE FROM `Computers` ;";

            stmt.executeUpdate(sql);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally {
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
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL + "jobserver" +"?allowMultiQueries=true", userName, password);
            stmt = conn.createStatement();
            String sql = "DELETE FROM `Administrators` ;" +
                    "INSERT INTO `Administrators` (`admin_id`, `company_id`, `admin_email`, `admin_name`, `pass_hash`, `is_company_admin`, `created_at`, `perm_password`) \n" +
                    "VALUES (1, 1, 'viktor.iurkov@yandex.ru', 'viktor iurkov', '11350bfad87b880df7f90b89ef1bddd5', 1, NOW(), true);";
            stmt.executeUpdate(sql);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally {
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
