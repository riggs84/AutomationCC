package selenium.Helpers;

import com.mysql.jdbc.PreparedStatement;
import io.qameta.allure.Step;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;

public class  SQLhelper {

    final static String jdbcDriverClass = "com.mysql.jdbc.Driver";
    final static String dataBaseURL = "jdbc:mysql://192.168.1.74/";

    final static String userName = "root";
    final static String password = "Pa$$w0rd";

    static String sql = "";
    private static String companyId = "";

    public static String getCompanyId(){
        if(companyId.isEmpty()){
            throw new Error();
        } else {
            return companyId;
        }
    }


    public static void cleanAndRecreateDataBase(){
        Connection conn = null;
        Statement stmt = null;
        // delete test company and all related
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL + "JobServer?allowMultiQueries=true", userName, password);
            stmt = conn.createStatement();
            String sql = "DELETE FROM `Companies` WHERE Companies.company_name='SiberQA' ;";
            stmt.executeUpdate(sql);
        } catch(Exception ex){
            // do nothing in case of exception
        }
        // create test company and get it's ID in DB
        try {
            String sql = "INSERT INTO `Companies` (`company_name`, `server_accounts`, `created_at`, `licensed_ws`, `licensed_s`) VALUES ('SiberQA', '', NOW(), '1', '1');";
            stmt.executeUpdate(sql);
            String sqlGetCompanyId = "SELECT Companies.company_id FROM `Companies` WHERE Companies.company_name='SiberQA' ;";
            ResultSet rs = stmt.executeQuery(sqlGetCompanyId);
            // companyId = rs.getString(1) without cycle returns nothing and i don't know why
            while(rs.next()){
                companyId = rs.getString(1);
            }
        } catch (Exception ex){
            // do nothing
        }
        // create admin
        try{
            String sql = "INSERT INTO `Administrators` (`admin_id`, `company_id`, `admin_email`, `admin_name`, `pass_hash`, `is_company_admin`, `created_at`, `perm_password`) \n" +
                    "VALUES (1," + companyId + ", 'viktor.iurkov@yandex.ru', 'viktor iurkov', '11350bfad87b880df7f90b89ef1bddd5', 1, NOW(), true);";
            stmt.executeUpdate(sql);
        } catch(Exception ex){
            // do nothing in case of exception
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

    public static void dropUsersTable(){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL + "JobServer?allowMultiQueries=true", userName, password);
            stmt = conn.createStatement();
            String sql = /*"SET foreign_key_checks = 0;\n" +
                    "DROP TABLE IF EXISTS `Users`;\n" +
                    "SET foreign_key_checks = 1*/"DELETE FROM `users` ;";
            stmt.executeUpdate(sql);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            System.out.println("dropUsers");
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
            conn = DriverManager.getConnection(dataBaseURL + "JobServer?allowMultiQueries=true", userName, password);
            stmt = conn.createStatement();
            String sql = "DELETE FROM `UserGroups` ;";
            stmt.executeUpdate(sql);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            System.out.println("drop user group");
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
            conn = DriverManager.getConnection(dataBaseURL + "JobServer?allowMultiQueries=true", userName, password);
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

    public static void dropJobsForComputerGroupTable(){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL + "jobserver?allowMultiQueries=true", userName, password);
            stmt = conn.createStatement();
            String sql = "DELETE FROM `JobsForComputerGroups` ;";

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

    public static void dropJobsForUserGroupTable(){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL + "JobServer?allowMultiQueries=true", userName, password);
            stmt = conn.createStatement();
            String sql = "DELETE FROM `JobsForUserGroups` ;";

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

    public static void dropJobsForComputerTable(){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL + "JobServer?allowMultiQueries=true", userName, password);
            stmt = conn.createStatement();
            String sql = "DELETE FROM `JobsForComputers` ;";

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

    public static void dropJobsForUsersTable(){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL + "JobServer?allowMultiQueries=true", userName, password);
            stmt = conn.createStatement();
            String sql = "DELETE FROM `JobsForUsers` ;";

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

    /*public static void dropAllTables(){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL + "JobServer?allowMultiQueries=true", userName, password);
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables("JobServer", null, "%", null);
            stmt = conn.createStatement();
            while(rs.next()){
                String sql = "DELETE FROM `"+ rs.getString(3) + "` ;";
                stmt.executeUpdate(sql);
            }
            stmt.executeUpdate("INSERT INTO `Companies` (`company_id`, `company_name`, `server_accounts`, `created_at`) VALUES (1, 'SiberQA', 'null', NOW());");
            stmt.executeUpdate("INSERT INTO `Administrators` (`admin_id`, `company_id`, `admin_email`, `admin_name`, `pass_hash`, `is_company_admin`, `created_at`, `perm_password`) \n" +
                    "VALUES (1, 1, 'viktor.iurkov@yandex.ru', 'viktor iurkov', '11350bfad87b880df7f90b89ef1bddd5', 1, NOW(), true);");
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            System.out.println("drop all tables");
        }finally {
            if(stmt!=null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    @Step("Clean computers table in MySQL DB")
    public static void dropComputersTable(){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL + "JobServer?allowMultiQueries=true", userName, password);
            stmt = conn.createStatement();
            String sql = "DELETE FROM `Computers` WHERE Computers.company_id=" + companyId + " ;";

            stmt.executeUpdate(sql);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            System.out.println("drop computers table");
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

    public static void dropJobsRunnersTable(){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL + "JobServer?allowMultiQueries=true", userName, password);
            stmt = conn.createStatement();
            String sql = "DELETE FROM `JobRunners` WHERE JobRunners.company_id=`" + companyId + " ;";
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
            conn = DriverManager.getConnection(dataBaseURL + "JobServer?allowMultiQueries=true", userName, password);
            stmt = conn.createStatement();
            String sql = "DELETE FROM `Administrators` WHERE Administrators.company_id=" + companyId + " ;";
            stmt.executeUpdate(sql);
            String sqlInsertAdmin = "INSERT INTO `Administrators` (`admin_id`, `company_id`, `admin_email`, `admin_name`, `pass_hash`, `is_company_admin`, `created_at`, `perm_password`) \n" +
                    "VALUES (1, " + companyId + ", 'viktor.iurkov@yandex.ru', 'viktor iurkov', '11350bfad87b880df7f90b89ef1bddd5', 1, NOW(), true);";
            stmt.executeUpdate(sqlInsertAdmin);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            System.out.println("drop admin table");
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

    public static void dropComputerGroupsTable(){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL + "JobServer?allowMultiQueries=true", userName, password);
            stmt = conn.createStatement();
            String sql = "DELETE FROM `ComputerGroups` WHERE ComputerGroups.company_id=" + companyId + " ;";
            stmt.executeUpdate(sql);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            System.out.println(" -drop computer groups table");
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

    public static void createAdministrator(String email, String name, boolean isCompanyAdmin){
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "INSERT INTO `Administrators` (`company_id`, `admin_email`, `admin_name`, `pass_hash`, `is_company_admin`, `created_at`, `perm_password`) "
                + "VALUES (?, ?, ?, '11350bfad87b880df7f90b89ef1bddd5', ?, NOW(), true);";
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL +"JobServer?allowMultiQueries=true", userName, password);
            stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, companyId);
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
            System.out.println("create admin");
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

    public static void createComputer(String OSname){
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "INSERT INTO `Computers` (`computer_os_name`, `company_id`, `created_at`) "
                + "VALUES (?, ?, NOW());";
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL +"JobServer?allowMultiQueries=true", userName, password);
            stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, OSname);
            stmt.setString(2, companyId);
            stmt.executeUpdate();
        } catch (Exception ex){
            System.out.print(ex.getMessage());
            System.out.println("create computer");
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

    public static void createUserGroup(String userGroupName, String userGroupOSname){
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "INSERT INTO `UserGroups` (`ugroup_name`, `company_id`, `ugroup_os_name`, `created_at`) "
                + "VALUES (?, ?, ?, NOW());";
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL +"JobServer?allowMultiQueries=true", userName, password);
            stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, userGroupName);
            stmt.setString(2, companyId);
            stmt.setString(3, userGroupOSname);
            stmt.executeUpdate();
        } catch (Exception ex){
            System.out.print(ex.getMessage());
            System.out.println("create user group");
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
                + "VALUES (?, ?, ?, ?, NOW());";
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL +"JobServer?allowMultiQueries=true", userName, password);
            stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, osName);
            stmt.setString(2, email);
            stmt.setString(3, companyId);
            stmt.setString(4, fullName);
            stmt.executeUpdate();
        } catch (Exception ex){
            System.out.print(ex.getMessage());
            System.out.println("create user");
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

    @Step("Create computer group {computerGroupName} in MySQL DB")
    public static void createComputerGroup(String computerGroupName, int isGroupActive){
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "INSERT INTO `ComputerGroups` (`cgroup_name`, `company_id`, `is_active`, `created_at`) "
                + "VALUES (?, ?, ?, NOW());";
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL +"JobServer?allowMultiQueries=true", userName, password);
            stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, computerGroupName);
            stmt.setString(2, companyId);
            stmt.setInt(3, isGroupActive);
            stmt.executeUpdate();
        } catch (Exception ex){
            System.out.print(ex.getMessage());
            System.out.println("create computer group");
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

    @Step("Assign Job {jobName} to user {userFullName} in MySQL DB")
    public static void assignJobToUser(String jobName, String userFullName){
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "Insert INTO `JobsForUsers` (`company_id`, `job_id`, `user_id`) " +
                "VALUES (" + companyId + ", (SELECT Jobs.job_id FROM `Jobs` WHERE Jobs.job_name=?), " +
                "(SELECT Users.user_id FROM `Users` WHERE Users.user_full_name=?)) ;";
        //String getJobId = "SELECT Jobs.jobs_id FROM `Jobs` WHERE Jobs.job_name=? ;";
        //String getUserId = "SELECT Users.user_id FROM `Users` WHERE Users.user_email=? ;";
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL +"JobServer?allowMultiQueries=true", userName, password);
            stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, jobName);
            stmt.setString(2, userFullName);
            stmt.executeUpdate();
        } catch (Exception ex){
            System.out.print(ex.getMessage());
            System.out.println(" -assign job to user");
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

    @Step("Assign job {jobName} to computer {compName} in MySQL DB")
    public static void assignJobToComputer(String jobName, String compName){
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "Insert INTO `JobsForComputers` (`company_id`, `job_id`, `computer_id`) " +
                "VALUES (" + companyId + ", (SELECT Jobs.job_id FROM `Jobs` WHERE Jobs.job_name=?), " +
                "(SELECT Computers.computer_id FROM `Computers` WHERE Computers.computer_os_name=?)) ;";
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL +"JobServer?allowMultiQueries=true", userName, password);
            stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, jobName);
            stmt.setString(2, compName);
            stmt.executeUpdate();
        } catch (Exception ex){
            System.out.print(ex.getMessage());
            System.out.println("assign job to comp");
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

    @Step("Assign job {jobName} to user group {userGroupName} in MySQL DB")
    public static void assignJobToUserGroup(String jobName, String userGroupName){
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "Insert INTO `JobsForUserGroups` (`company_id`, `job_id`, `ugroup_id`) " +
                "VALUES (" + companyId + ", (SELECT Jobs.job_id FROM `Jobs` WHERE Jobs.job_name=?), " +
                "(SELECT UserGroups.ugroup_id FROM `UserGroups` WHERE UserGroups.ugroup_name=?)) ;";
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL +"JobServer?allowMultiQueries=true", userName, password);
            stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, jobName);
            stmt.setString(2, userGroupName);
            stmt.executeUpdate();
        } catch (Exception ex){
            System.out.print(ex.getMessage());
            System.out.println("assign job to user group");
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

    @Step("Assign job {jobName}  to computer group {computerGroupName} in MySQL DB")
    public static void assignJobToComputerGroup(String jobName, String computerGroupName){
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "Insert INTO `JobsForComputerGroups` (`company_id`, `job_id`, `cgroup_id`) " +
                "VALUES (" + companyId + ", (SELECT Jobs.job_id FROM `Jobs` WHERE Jobs.job_name=?), " +
                "(SELECT ComputerGroups.cgroup_id FROM `ComputerGroups` WHERE ComputerGroups.cgroup_name=? AND ComputerGroups.company_id='" + companyId + "')) ;";
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL +"JobServer?allowMultiQueries=true", userName, password);
            stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, jobName);
            stmt.setString(2, computerGroupName);
            stmt.executeUpdate();
        } catch (Exception ex){
            System.out.print(ex.getMessage());
            System.out.println(" -assign job to comp group");
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

    @Step("Add user {userFullName} to users group {usersGroupName} in MySQL DB")
    public static void addUserToUsersGroup(String userFullName, String usersGroupName){
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "Insert INTO `UsersInGroups` (`company_id`, `ugroup_id`, `user_id`) " +
                "VALUES (" + companyId + ", (SELECT UserGroups.ugroup_id FROM `UserGroups` WHERE UserGroups.ugroup_name=?), " +
                "(SELECT Users.user_id FROM `Users` WHERE Users.user_full_name=?)) ;";
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL +"JobServer?allowMultiQueries=true", userName, password);
            stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, usersGroupName);
            stmt.setString(2, userFullName);
            stmt.executeUpdate();
        } catch (Exception ex){
            System.out.print(ex.getMessage());
            System.out.println("add user to user group");
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

    @Step("Add computer {computerOSname} to computer group {computerGroup} in MySQL DB")
    public static void addComputerToComputerGroup(String computerGroup, String computerOSname){
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "Insert INTO `ComputersInGroups` (`company_id`, `cgroup_id`, `computer_id`) " +
                "VALUES (" + companyId + ", (SELECT ComputerGroups.cgroup_id FROM `ComputerGroups` WHERE ComputerGroups.cgroup_name=? AND ComputerGroups.company_id=" +
                "'" + companyId + "'), " +
                "(SELECT Computers.computer_id FROM `Computers` WHERE Computers.computer_os_name=? AND Computers.company_id='" + companyId + "')) ;";
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL +"JobServer?allowMultiQueries=true", userName, password);
            stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, computerGroup);
            stmt.setString(2, computerOSname);
            stmt.executeUpdate();
        } catch (Exception ex){
            System.out.print(ex.getMessage());
            System.out.println(" -add comp to comp group");
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

    public static void setRunnerBooleanFlags(int isAuthorised, int isActive, String runnerName){
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "UPDATE `JobRunners` SET is_authorized=?, is_active=? " +
                "WHERE JobRunners.user_id =(SELECT Users.user_id FROM Users WHERE Users.user_full_name=? AND Users.company_id=" + companyId + ") " +
                "AND JobRunners.company_id=" + companyId + " ;";
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL +"JobServer?allowMultiQueries=true", userName, password);
            stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setInt(1, isAuthorised);
            stmt.setInt(2, isActive);
            stmt.setString(3, runnerName);
            stmt.executeUpdate();
        } catch (Exception ex){
            System.out.print(ex.getMessage());
            System.out.println("set runner flags");
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
