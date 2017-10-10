package selenium.Helpers;

import com.mysql.jdbc.PreparedStatement;
import io.qameta.allure.Step;

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
            conn = DriverManager.getConnection(dataBaseURL + "jobserver?allowMultiQueries=true", userName, password);
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
            conn = DriverManager.getConnection(dataBaseURL + "jobserver?allowMultiQueries=true", userName, password);
            stmt = conn.createStatement();
            String sql = "DELETE FROM `UserGroups` ;";
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
            conn = DriverManager.getConnection(dataBaseURL + "jobserver?allowMultiQueries=true", userName, password);
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
            conn = DriverManager.getConnection(dataBaseURL + "jobserver?allowMultiQueries=true", userName, password);
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
            conn = DriverManager.getConnection(dataBaseURL + "jobserver?allowMultiQueries=true", userName, password);
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


    @Step("Create admin in MySQL DB with {email} and {name}")
    public static void createAdministrator(String email, String name, boolean isCompanyAdmin){
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "INSERT INTO `Administrators` (`company_id`, `admin_email`, `admin_name`, `pass_hash`, `is_company_admin`, `created_at`, `perm_password`) "
                + "VALUES (?, ?, ?, '11350bfad87b880df7f90b89ef1bddd5', ?, NOW(), true);";
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL +"jobserver?allowMultiQueries=true", userName, password);
            stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setInt(1, 1);
            stmt.setString(2, email);
            stmt.setString(3, name);
            if (isCompanyAdmin){
                stmt.setInt(4, 1);
            } else {
                stmt.setInt(4, 0);
            }
            stmt.executeUpdate();
        } catch (Exception ex){
            System.out.print(ex.getMessage());
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

    @Step("Create computer in MySQL DB with data: {OSname}")
    public static void createComputer(String OSname){
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "INSERT INTO `Computers` (`computer_os_name`, `company_id`, `created_at`) "
                + "VALUES (?, 1, NOW());";
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL +"jobserver?allowMultiQueries=true", userName, password);
            stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, OSname);
            stmt.executeUpdate();
        } catch (Exception ex){
            System.out.print(ex.getMessage());
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

    @Step("Create user group in MySQL DB with {userGroupName} and {userGroupOSname}")
    public static void createUserGroup(String userGroupName, String userGroupOSname){
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "INSERT INTO `UserGroups` (`ugroup_name`, `company_id`, `ugroup_os_name`, `created_at`) "
                + "VALUES (?, 1, ? NOW());";
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL +"jobserver?allowMultiQueries=true", userName, password);
            stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, userGroupName);
            stmt.setString(2, userGroupOSname);
            stmt.executeUpdate();
        } catch (Exception ex){
            System.out.print(ex.getMessage());
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

    @Step("Create user in MYSQL DB with {osName}, {fullName}, {email}")
    public static void createUser(String osName, String fullName, String email){
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "INSERT INTO `Users` (`user_os_name`, `user_email`, `company_id`, `user_full_name`, `created_at`) "
                + "VALUES (?, ?, 1, ? NOW());";
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL +"jobserver?allowMultiQueries=true", userName, password);
            stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, osName);
            stmt.setString(2, email);
            stmt.setString(3, fullName);
            stmt.executeUpdate();
        } catch (Exception ex){
            System.out.print(ex.getMessage());
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
