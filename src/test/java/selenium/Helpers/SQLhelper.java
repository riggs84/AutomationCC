package selenium.Helpers;

import com.mysql.jdbc.PreparedStatement;
import io.qameta.allure.Step;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;

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
            // MySQL does not allow DELIMETER throgh JDBC connection
            stmt.executeUpdate("CREATE FUNCTION `SPLIT_STR`(\n" +
                    "  x VARCHAR(255),\n" +
                    "  delim VARCHAR(12),\n" +
                    "  pos INT\n" +
                    ") RETURNS varchar(255) CHARSET utf8\n" +
                    "RETURN REPLACE(SUBSTRING(SUBSTRING_INDEX(x, delim, pos),\n" +
                    "       LENGTH(SUBSTRING_INDEX(x, delim, pos -1)) + 1),\n" +
                    "       delim, '') ;");
            stmt.executeUpdate("CREATE PROCEDURE `AddUsersToGroup`(IN _users_ids TEXT,IN _group_id int(10) unsigned, IN _company_id char(40))\n" +
                    "BEGIN\n" +
                    "      DECLARE i INT Default 0 ;\n" +
                    "      DECLARE user_id VARCHAR(255);\n" +
                    "      array_loop: LOOP\n" +
                    "         SET i=i+1;\n" +
                    "         SET user_id=SPLIT_STR(_users_ids,\",\",i);\n" +
                    "         IF user_id='' THEN\n" +
                    "            LEAVE array_loop;\n" +
                    "         END IF;\n" +
                    "         INSERT INTO `UsersInGroups` (`ugroup_id`, `user_id`, `company_id`) VALUES (_group_id, user_id, _company_id);\n" +
                    "   END LOOP array_loop;\n" +
                    "END ;");
            stmt.executeUpdate("CREATE PROCEDURE `AddComputersToGroup`(IN _computers_ids TEXT,IN _group_id int(10) unsigned, IN _company_id char(40))\n" +
                    "BEGIN\n" +
                    "      DECLARE i INT Default 0 ;\n" +
                    "      DECLARE computer_id VARCHAR(255);\n" +
                    "      array_loop: LOOP\n" +
                    "         SET i=i+1;\n" +
                    "         SET computer_id=SPLIT_STR(_computers_ids,\",\",i);\n" +
                    "         IF computer_id='' THEN\n" +
                    "            LEAVE array_loop;\n" +
                    "         END IF;\n" +
                    "         INSERT INTO `ComputersInGroups` (`cgroup_id`, `computer_id`, `company_id`) VALUES (_group_id, computer_id, _company_id);\n" +
                    "   END LOOP array_loop;\n" +
                    "END ;");

            stmt.executeUpdate("CREATE PROCEDURE `AddUserToGroups`(IN _groups_ids TEXT,IN _user_id int(10) unsigned, IN _company_id int(10))\n" +
                    "BEGIN\n" +
                    "      DECLARE i INT Default 0 ;\n" +
                    "      DECLARE group_id VARCHAR(255);\n" +
                    "      array_loop: LOOP\n" +
                    "         SET i=i+1;\n" +
                    "         SET group_id=SPLIT_STR(_groups_ids,\",\",i);\n" +
                    "         IF group_id='' THEN\n" +
                    "            LEAVE array_loop;\n" +
                    "         END IF;\n" +
                    "         INSERT INTO `UsersInGroups` (`ugroup_id`, `user_id`, `company_id`) VALUES (group_id, _user_id, _company_id);\n" +
                    "   END LOOP array_loop;\n" +
                    "END ;");
            stmt.executeUpdate("CREATE PROCEDURE `AddComputerToGroups`(IN _groups_ids TEXT,IN _computer_id int(10) unsigned, IN _company_id int(10))\n" +
                    "BEGIN\n" +
                    "      DECLARE i INT Default 0 ;\n" +
                    "      DECLARE group_id VARCHAR(255);\n" +
                    "      array_loop: LOOP\n" +
                    "         SET i=i+1;\n" +
                    "         SET group_id=SPLIT_STR(_groups_ids,\",\",i);\n" +
                    "         IF group_id='' THEN\n" +
                    "            LEAVE array_loop;\n" +
                    "         END IF;\n" +
                    "         INSERT INTO `ComputersInGroups` (`cgroup_id`, `computer_id`, `company_id`) VALUES (group_id, _computer_id, _company_id);\n" +
                    "   END LOOP array_loop;\n" +
                    "END ;");
            stmt.executeUpdate("CREATE PROCEDURE `AssignJobsToUserGroup`(IN _jobs_ids TEXT,IN _group_id int(10) unsigned, IN _company_id char(40))\n" +
                    "BEGIN\n" +
                    "      DECLARE i INT Default 0 ;\n" +
                    "      DECLARE job_id VARCHAR(255);\n" +
                    "      array_loop: LOOP\n" +
                    "         SET i=i+1;\n" +
                    "         SET job_id=SPLIT_STR(_jobs_ids,\",\",i);\n" +
                    "         IF job_id='' THEN\n" +
                    "            LEAVE array_loop;\n" +
                    "         END IF;\n" +
                    "         INSERT INTO `JobsForUserGroups` (`ugroup_id`, `job_id`, `company_id`) VALUES (_group_id, job_id, _company_id);\n" +
                    "   END LOOP array_loop;\n" +
                    "END ;");
            stmt.executeUpdate("CREATE PROCEDURE `AssignJobsToComputerGroup`(IN _jobs_ids TEXT,IN _group_id int(10) unsigned, IN _company_id char(40))\n" +
                    "BEGIN\n" +
                    "      DECLARE i INT Default 0 ;\n" +
                    "      DECLARE job_id VARCHAR(255);\n" +
                    "      array_loop: LOOP\n" +
                    "         SET i=i+1;\n" +
                    "         SET job_id=SPLIT_STR(_jobs_ids,\",\",i);\n" +
                    "         IF job_id='' THEN\n" +
                    "            LEAVE array_loop;\n" +
                    "         END IF;\n" +
                    "         INSERT INTO `JobsForComputerGroups` (`cgroup_id`, `job_id`, `company_id`) VALUES (_group_id, job_id, _company_id);\n" +
                    "   END LOOP array_loop;\n" +
                    "END ;");
            stmt.executeUpdate("CREATE PROCEDURE `AssignJobsToUser`(IN _jobs_ids TEXT,IN _user_id int(10) unsigned, IN _company_id char(40))\n" +
                    "BEGIN\n" +
                    "      DECLARE i INT Default 0 ;\n" +
                    "      DECLARE job_id VARCHAR(255);\n" +
                    "      array_loop: LOOP\n" +
                    "         SET i=i+1;\n" +
                    "         SET job_id=SPLIT_STR(_jobs_ids,\",\",i);\n" +
                    "         IF job_id='' THEN\n" +
                    "            LEAVE array_loop;\n" +
                    "         END IF;\n" +
                    "         INSERT INTO `JobsForUsers` (`user_id`, `job_id`, `company_id`) VALUES (_user_id, job_id, _company_id);\n" +
                    "   END LOOP array_loop;\n" +
                    "END ;");
            stmt.executeUpdate("CREATE PROCEDURE `AssignJobsToComputer`(IN _jobs_ids TEXT,IN _computer_id int(10) unsigned, IN _company_id char(40))\n" +
                    "BEGIN\n" +
                    "      DECLARE i INT Default 0 ;\n" +
                    "      DECLARE job_id VARCHAR(255);\n" +
                    "      array_loop: LOOP\n" +
                    "         SET i=i+1;\n" +
                    "         SET job_id=SPLIT_STR(_jobs_ids,\",\",i);\n" +
                    "         IF job_id='' THEN\n" +
                    "            LEAVE array_loop;\n" +
                    "         END IF;\n" +
                    "         INSERT INTO `JobsForComputers` (`computer_id`, `job_id`, `company_id`) VALUES (_computer_id, job_id, _company_id);\n" +
                    "   END LOOP array_loop;\n" +
                    "END ;");
            stmt.executeUpdate("CREATE PROCEDURE `AssignJobToUsers`(IN _users_ids TEXT,IN _job_id int(10) unsigned, IN _company_id int(10))\n" +
                    "BEGIN\n" +
                    "      DECLARE i INT Default 0 ;\n" +
                    "      DECLARE user_id VARCHAR(255);\n" +
                    "      array_loop: LOOP\n" +
                    "         SET i=i+1;\n" +
                    "         SET user_id=SPLIT_STR(_users_ids,\",\",i);\n" +
                    "         IF user_id='' THEN\n" +
                    "            LEAVE array_loop;\n" +
                    "         END IF;\n" +
                    "         INSERT INTO `JobsForUsers` (`job_id`, `user_id`, `company_id`) VALUES (_job_id, user_id, _company_id);\n" +
                    "   END LOOP array_loop;\n" +
                    "END ;");
            stmt.executeUpdate("CREATE PROCEDURE `AssignJobToUserGroups`(IN _user_groups_ids TEXT,IN _job_id int(10) unsigned, IN _company_id int(10))\n" +
                    "BEGIN\n" +
                    "      DECLARE i INT Default 0 ;\n" +
                    "      DECLARE ugroup_id VARCHAR(255);\n" +
                    "      array_loop: LOOP\n" +
                    "         SET i=i+1;\n" +
                    "         SET ugroup_id=SPLIT_STR(_user_groups_ids,\",\",i);\n" +
                    "         IF ugroup_id='' THEN\n" +
                    "            LEAVE array_loop;\n" +
                    "         END IF;\n" +
                    "         INSERT INTO `JobsForUserGroups` (`job_id`, `ugroup_id`, `company_id`) VALUES (_job_id, ugroup_id, _company_id);\n" +
                    "   END LOOP array_loop;\n" +
                    "END ;");
            stmt.executeUpdate("CREATE PROCEDURE `AssignJobToComputers`(IN _computers_ids TEXT,IN _job_id int(10) unsigned, IN _company_id int(10))\n" +
                    "BEGIN\n" +
                    "      DECLARE i INT Default 0 ;\n" +
                    "      DECLARE computer_id VARCHAR(255);\n" +
                    "      array_loop: LOOP\n" +
                    "         SET i=i+1;\n" +
                    "         SET computer_id=SPLIT_STR(_computers_ids,\",\",i);\n" +
                    "         IF computer_id='' THEN\n" +
                    "            LEAVE array_loop;\n" +
                    "         END IF;\n" +
                    "         INSERT INTO `JobsForComputers` (`job_id`, `computer_id`, `company_id`) VALUES (_job_id, computer_id, _company_id);\n" +
                    "   END LOOP array_loop;\n" +
                    "END ;");
            stmt.executeUpdate("CREATE PROCEDURE `AssignJobToComputerGroups`(IN _computer_groups_ids TEXT,IN _job_id int(10) unsigned, IN _company_id int(10))\n" +
                    "BEGIN\n" +
                    "      DECLARE i INT Default 0 ;\n" +
                    "      DECLARE cgroup_id VARCHAR(255);\n" +
                    "      array_loop: LOOP\n" +
                    "         SET i=i+1;\n" +
                    "         SET cgroup_id=SPLIT_STR(_computer_groups_ids,\",\",i);\n" +
                    "         IF cgroup_id='' THEN\n" +
                    "            LEAVE array_loop;\n" +
                    "         END IF;\n" +
                    "         INSERT INTO `JobsForComputerGroups` (`job_id`, `cgroup_id`, `company_id`) VALUES (_job_id, cgroup_id, _company_id);\n" +
                    "   END LOOP array_loop;\n" +
                    "END ;");
            stmt.executeUpdate("CREATE PROCEDURE `AssignAdministratorToComputerGroups`(IN _computer_groups_ids TEXT,IN _admin_id int(10) unsigned, IN _company_id int(10))\n" +
                    "BEGIN\n" +
                    "      DECLARE i INT Default 0 ;\n" +
                    "      DECLARE cgroup_id VARCHAR(255);\n" +
                    "      array_loop: LOOP\n" +
                    "         SET i=i+1;\n" +
                    "         SET cgroup_id=SPLIT_STR(_computer_groups_ids,\",\",i);\n" +
                    "         IF cgroup_id='' THEN\n" +
                    "            LEAVE array_loop;\n" +
                    "         END IF;\n" +
                    "         INSERT INTO `ComputerGroupAdmins` (`admin_id`, `cgroup_id`, `company_id`, `is_active`, `created_at`) VALUES (_admin_id, cgroup_id, _company_id, 1, NOW());\n" +
                    "   END LOOP array_loop;\n" +
                    "END ;");
            stmt.executeUpdate("CREATE PROCEDURE `AssignAdministratorToUserGroups`(IN _user_groups_ids TEXT,IN _admin_id int(10) unsigned, IN _company_id int(10))\n" +
                    "BEGIN\n" +
                    "      DECLARE i INT Default 0 ;\n" +
                    "      DECLARE ugroup_id VARCHAR(255);\n" +
                    "      array_loop: LOOP\n" +
                    "         SET i=i+1;\n" +
                    "         SET ugroup_id=SPLIT_STR(_user_groups_ids,\",\",i);\n" +
                    "         IF ugroup_id='' THEN\n" +
                    "            LEAVE array_loop;\n" +
                    "         END IF;\n" +
                    "         INSERT INTO `UserGroupAdmins` (`admin_id`, `ugroup_id`, `company_id`, `is_active`, `created_at`) VALUES (_admin_id, ugroup_id, _company_id, 1, NOW());\n" +
                    "   END LOOP array_loop;\n" +
                    "END ;");
            stmt.executeUpdate("CREATE PROCEDURE `AddJobRunRequests`(IN _runner_ids TEXT,IN _job_id int(10) unsigned)\n" +
                    "BEGIN\n" +
                    "      DECLARE i INT Default 0 ;\n" +
                    "      DECLARE runner_id VARCHAR(255);\n" +
                    "      array_loop: LOOP\n" +
                    "         SET i=i+1;\n" +
                    "         SET runner_id=SPLIT_STR(_runner_ids,\",\",i);\n" +
                    "         IF runner_id='' THEN\n" +
                    "            LEAVE array_loop;\n" +
                    "         END IF;\n" +
                    "         INSERT INTO `JobRunRequests` (`job_runner_id`, `job_id`) VALUES (runner_id, _job_id) ON DUPLICATE KEY UPDATE job_runner_id=job_runner_id;\n" +
                    "   END LOOP array_loop;\n" +
                    "END ;");
            stmt.executeUpdate("CREATE PROCEDURE `AddJobRequestsSpOp`(IN _runner_ids TEXT,IN _job_id int(10) unsigned,IN _spop int(10) unsigned)\n" +
                    "BEGIN\n" +
                    "      DECLARE i INT Default 0 ;\n" +
                    "      DECLARE runner_id VARCHAR(255);\n" +
                    "      array_loop: LOOP\n" +
                    "         SET i=i+1;\n" +
                    "         SET runner_id=SPLIT_STR(_runner_ids,\",\",i);\n" +
                    "         IF runner_id='' THEN\n" +
                    "            LEAVE array_loop;\n" +
                    "         END IF;\n" +
                    "         INSERT INTO `JobRunRequests` (`job_runner_id`, `job_id`, `run_oper`) VALUES (runner_id, _job_id, _spop) ON DUPLICATE KEY UPDATE job_runner_id=job_runner_id;\n" +
                    "   END LOOP array_loop;\n" +
                    "END ;");
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

    public static void dropJobsRunnersTable(){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL + "jobserver?allowMultiQueries=true", userName, password);
            stmt = conn.createStatement();
            String sql = "DELETE FROM `JobRunners` ;";
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
                + "VALUES (?, 1, ?, NOW());";
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
                + "VALUES (?, ?, 1, ?, NOW());";
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

    @Step("Assign Job {jobName} to user {userEmail} in MySQL DB")
    public static void assignJobToUser(String jobName, String userFullName){
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "Insert INTO `JobsForUsers` (`company_id`, `user_id`, `job_id`) " +
                "VALUES (1, (SELECT Jobs.job_id FROM `Jobs` WHERE Jobs.job_name=?), (SELECT Users.user_id FROM `Users` WHERE Users.user_full_name=?)) ;";
        //String getJobId = "SELECT Jobs.jobs_id FROM `Jobs` WHERE Jobs.job_name=? ;";
        //String getUserId = "SELECT Users.user_id FROM `Users` WHERE Users.user_email=? ;";
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL +"jobserver?allowMultiQueries=true", userName, password);
            stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, jobName);
            stmt.setString(2, userFullName);
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

    public static void setRunnerBooleanFlags(int isAuthorised, int isActive, String runnerName){
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "UPDATE `JobRunners` SET is_authorized=?, is_active=? " +
                "WHERE JobRunners.user_id =(SELECT Users.user_id FROM Users WHERE Users.user_full_name=? AND Users.company_id=1) " +
                "AND JobRunners.company_id=1 ;";
        try{
            Class.forName(jdbcDriverClass);
            conn = DriverManager.getConnection(dataBaseURL +"jobserver?allowMultiQueries=true", userName, password);
            stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setInt(1, isAuthorised);
            stmt.setInt(2, isActive);
            stmt.setString(3, runnerName);
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
