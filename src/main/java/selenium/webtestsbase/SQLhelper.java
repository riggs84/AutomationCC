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
            String sql = "SET foreign_key_checks = 0;\n" +
                    "DROP TABLE IF EXISTS `Users`;\n" +
                    "SET foreign_key_checks = 1;\n" + "CREATE TABLE `Users` (\n" +
                    "  `user_id`    int unsigned NOT NULL AUTO_INCREMENT COMMENT 'User Id, globally unique, generated',\n" +
                    "\n" +
                    "  `company_id` int unsigned NOT NULL COMMENT 'Company Id, foreign key',\n" +
                    "  `user_os_name`   char(40) NOT NULL COMMENT 'User Name(Id) in Windows/Mac/Unix OS',\n" +
                    "\n" +
                    "  `user_email`     char(40)    COMMENT 'User Email',\n" +
                    "  `user_full_name` varchar(64) COMMENT 'Full User Name',\n" +
                    "\n" +
                    "  `is_active`  boolean  NOT NULL DEFAULT '1',\n" +
                    "  `created_at` datetime NOT NULL COMMENT 'date time when user was created',\n" +
                    "\n" +
                    "  PRIMARY KEY (`user_id`),\n" +
                    "  UNIQUE KEY  (`company_id`, `user_os_name`),\n" +
                    "\n" +
                    "  CONSTRAINT `fk_users_companies`    \n" +
                    "    FOREIGN KEY (`company_id`)   \n" +
                    "    REFERENCES `Companies` (`company_id`) ON DELETE CASCADE\n" +
                    "\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Users table';";
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
            String sql = "SET foreign_key_checks = 0;\n" +
                    "DROP TABLE IF EXISTS `Jobs`;\n" +
                    "SET foreign_key_checks = 1;\n" + "CREATE TABLE `Jobs` (\n" +
                    "  `job_id`      int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Job Id, globally unique, generated',\n" +
                    "\n" +
                    "  `company_id`  int unsigned NOT NULL COMMENT 'Company Id, foreign key',\n" +
                    "  `job_name`    char (40)    NOT NULL COMMENT 'Job Name, can change',\n" +
                    "\n" +
                    "  `dir_L_conn`    varchar(1024) DEFAULT NULL COMMENT 'Left Folder CL Options, if not using Accounts, starts with /f1=',\n" +
                    "  `dir_L_acctkey` char (250)    DEFAULT NULL COMMENT 'Left Folder Account Key, references ServerFolders, not empty if using Accounts',\n" +
                    "  `dir_L_folder`  char (250)    DEFAULT NULL COMMENT 'Left Folder URL, references ServerFolders',\n" +
                    "  `dir_R_conn`  varchar(1024) DEFAULT NULL COMMENT 'Right Folder CL Options, if not using Accounts, starts with /f2=',\n" +
                    "  `dir_R_acctkey` char (250)    DEFAULT NULL COMMENT 'Right Folder Account Key, if using Accounts',\n" +
                    "  `dir_R_folder`  char (250)    DEFAULT NULL COMMENT 'Right Folder URL, references ServerFolders',\n" +
                    "  `options`       varchar(2048) DEFAULT NULL COMMENT 'Job Options as command line, not related to left/right Accounts or Connectoids',\n" +
                    "  `description`   varchar(300)  DEFAULT NULL COMMENT 'Job Description',\n" +
                    "  `is_active`     boolean           NOT NULL DEFAULT '1',\n" +
                    "\n" +
                    "  PRIMARY KEY (`job_id`),\n" +
                    "  UNIQUE KEY  (`company_id`, `job_name`),\n" +
                    "\n" +
                    "  CONSTRAINT `fk_jobs_companies`   \n" +
                    "    FOREIGN KEY (`company_id`)  \n" +
                    "    REFERENCES `Companies` (`company_id`) ON DELETE CASCADE\n" +
                    "\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Jobs table';";

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
            String sql = "SET foreign_key_checks = 0;\n" +
                    "DROP TABLE IF EXISTS `Computers`;\n" +
                    "SET foreign_key_checks = 1;\n" + "CREATE TABLE `Computers` (\n" +
                    "  `computer_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Computer Id, globally unique, generated',\n" +
                    "\n" +
                    "  `company_id`   int unsigned NOT NULL COMMENT 'Company Id, foreign key',\n" +
                    "  `computer_os_name` char(40) NOT NULL COMMENT 'Computer OS Name, can change',\n" +
                    "\n" +
                    "  `osinfo` varchar(50) DEFAULT NULL COMMENT ' text OS info from GsGetOsInfo()',\n" +
                    "  `platform` tinyint(4) DEFAULT NULL COMMENT 'win16: 1 (does not happen), windows: 2,macos: 3,iphone: 4,android: 5',\n" +
                    "\n" +
                    "  `is_active`  boolean  NOT NULL DEFAULT '1',\n" +
                    "  `created_at` datetime NOT NULL COMMENT 'date time when computer first appeared',\n" +
                    "\n" +
                    "  PRIMARY KEY (`computer_id`),\n" +
                    "  UNIQUE KEY  (`company_id`, `computer_os_name`),\n" +
                    "\n" +
                    "  CONSTRAINT `fk_computers_companies`  \n" +
                    "    FOREIGN KEY (`company_id`)    \n" +
                    "    REFERENCES `Companies` (`company_id`) ON DELETE CASCADE\n" +
                    "\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Computers table';";

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
            String sql = "SET foreign_key_checks = 0;\n" +
                    "DROP TABLE IF EXISTS `Administrators`;\n" +
                    "SET foreign_key_checks = 1;\n" +
                    "CREATE TABLE `Administrators` (\n" +
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
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Administrators table';\n" +
                    "\n" +
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
