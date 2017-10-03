-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.1.72-community - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for jobserver
CREATE DATABASE IF NOT EXISTS `jobserver` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `jobserver`;

-- Dumping structure for procedure jobserver.AddComputersToGroup
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddComputersToGroup`(IN _computers_ids TEXT,IN _group_id int(10) unsigned, IN _company_id char(40))
BEGIN
      DECLARE i INT Default 0 ;
      DECLARE computer_id VARCHAR(255);
      array_loop: LOOP
         SET i=i+1;
         SET computer_id=SPLIT_STR(_computers_ids,",",i);
         IF computer_id='' THEN
            LEAVE array_loop;
         END IF;
         INSERT INTO `ComputersInGroups` (`cgroup_id`, `computer_id`, `company_id`) VALUES (_group_id, computer_id, _company_id);
   END LOOP array_loop;
END//
DELIMITER ;

-- Dumping structure for procedure jobserver.AddComputerToGroups
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddComputerToGroups`(IN _groups_ids TEXT,IN _computer_id int(10) unsigned, IN _company_id int(10))
BEGIN
      DECLARE i INT Default 0 ;
      DECLARE group_id VARCHAR(255);
      array_loop: LOOP
         SET i=i+1;
         SET group_id=SPLIT_STR(_groups_ids,",",i);
         IF group_id='' THEN
            LEAVE array_loop;
         END IF;
         INSERT INTO `ComputersInGroups` (`cgroup_id`, `computer_id`, `company_id`) VALUES (group_id, _computer_id, _company_id);
   END LOOP array_loop;
END//
DELIMITER ;

-- Dumping structure for procedure jobserver.AddJobRequestsSpOp
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddJobRequestsSpOp`(IN _runner_ids TEXT,IN _job_id int(10) unsigned,IN _spop int(10) unsigned)
BEGIN
      DECLARE i INT Default 0 ;
      DECLARE runner_id VARCHAR(255);
      array_loop: LOOP
         SET i=i+1;
         SET runner_id=SPLIT_STR(_runner_ids,",",i);
         IF runner_id='' THEN
            LEAVE array_loop;
         END IF;
         INSERT INTO `JobRunRequests` (`job_runner_id`, `job_id`, `run_oper`) VALUES (runner_id, _job_id, _spop) ON DUPLICATE KEY UPDATE job_runner_id=job_runner_id;
   END LOOP array_loop;
END//
DELIMITER ;

-- Dumping structure for procedure jobserver.AddJobRunRequests
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddJobRunRequests`(IN _runner_ids TEXT,IN _job_id int(10) unsigned)
BEGIN
      DECLARE i INT Default 0 ;
      DECLARE runner_id VARCHAR(255);
      array_loop: LOOP
         SET i=i+1;
         SET runner_id=SPLIT_STR(_runner_ids,",",i);
         IF runner_id='' THEN
            LEAVE array_loop;
         END IF;
         INSERT INTO `JobRunRequests` (`job_runner_id`, `job_id`) VALUES (runner_id, _job_id) ON DUPLICATE KEY UPDATE job_runner_id=job_runner_id;
   END LOOP array_loop;
END//
DELIMITER ;

-- Dumping structure for procedure jobserver.AddUsersToGroup
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddUsersToGroup`(IN _users_ids TEXT,IN _group_id int(10) unsigned, IN _company_id char(40))
BEGIN
      DECLARE i INT Default 0 ;
      DECLARE user_id VARCHAR(255);
      array_loop: LOOP
         SET i=i+1;
         SET user_id=SPLIT_STR(_users_ids,",",i);
         IF user_id='' THEN
            LEAVE array_loop;
         END IF;
         INSERT INTO `UsersInGroups` (`ugroup_id`, `user_id`, `company_id`) VALUES (_group_id, user_id, _company_id);
   END LOOP array_loop;
END//
DELIMITER ;

-- Dumping structure for procedure jobserver.AddUserToGroups
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddUserToGroups`(IN _groups_ids TEXT,IN _user_id int(10) unsigned, IN _company_id int(10))
BEGIN
      DECLARE i INT Default 0 ;
      DECLARE group_id VARCHAR(255);
      array_loop: LOOP
         SET i=i+1;
         SET group_id=SPLIT_STR(_groups_ids,",",i);
         IF group_id='' THEN
            LEAVE array_loop;
         END IF;
         INSERT INTO `UsersInGroups` (`ugroup_id`, `user_id`, `company_id`) VALUES (group_id, _user_id, _company_id);
   END LOOP array_loop;
END//
DELIMITER ;

-- Dumping structure for table jobserver.administrators
CREATE TABLE IF NOT EXISTS `administrators` (
  `admin_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Administrator Id, globally unique, generated',
  `company_id` int(10) unsigned NOT NULL COMMENT 'Company Id, foreign key',
  `admin_email` char(40) NOT NULL COMMENT 'Admin Email, can change',
  `email_confirmed` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'T: admin_email is confirmed, F: admin did not confirm email',
  `admin_name` varchar(60) NOT NULL COMMENT 'Admin Full Name',
  `pass_hash` char(32) NOT NULL COMMENT 'MD5 hash of User password, hex string',
  `perm_password` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'True: permanent password, F: temp generated password',
  `is_company_admin` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'True: company admin, F: group admin',
  `email_confirm_hash` char(32) DEFAULT NULL COMMENT 'Field with tmp email + salt hash for email confirmation',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` datetime NOT NULL COMMENT 'Date time when this Admin was created',
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `admin_email` (`admin_email`),
  KEY `fk_admins_companies` (`company_id`),
  CONSTRAINT `fk_admins_companies` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Administrators table';

-- Data exporting was unselected.
-- Dumping structure for procedure jobserver.AssignAdministratorToComputerGroups
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `AssignAdministratorToComputerGroups`(IN _computer_groups_ids TEXT,IN _admin_id int(10) unsigned, IN _company_id int(10))
BEGIN
      DECLARE i INT Default 0 ;
      DECLARE cgroup_id VARCHAR(255);
      array_loop: LOOP
         SET i=i+1;
         SET cgroup_id=SPLIT_STR(_computer_groups_ids,",",i);
         IF cgroup_id='' THEN
            LEAVE array_loop;
         END IF;
         INSERT INTO `ComputerGroupAdmins` (`admin_id`, `cgroup_id`, `company_id`, `is_active`, `created_at`) VALUES (_admin_id, cgroup_id, _company_id, 1, NOW());
   END LOOP array_loop;
END//
DELIMITER ;

-- Dumping structure for procedure jobserver.AssignAdministratorToUserGroups
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `AssignAdministratorToUserGroups`(IN _user_groups_ids TEXT,IN _admin_id int(10) unsigned, IN _company_id int(10))
BEGIN
      DECLARE i INT Default 0 ;
      DECLARE ugroup_id VARCHAR(255);
      array_loop: LOOP
         SET i=i+1;
         SET ugroup_id=SPLIT_STR(_user_groups_ids,",",i);
         IF ugroup_id='' THEN
            LEAVE array_loop;
         END IF;
         INSERT INTO `UserGroupAdmins` (`admin_id`, `ugroup_id`, `company_id`, `is_active`, `created_at`) VALUES (_admin_id, ugroup_id, _company_id, 1, NOW());
   END LOOP array_loop;
END//
DELIMITER ;

-- Dumping structure for procedure jobserver.AssignJobsToComputer
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `AssignJobsToComputer`(IN _jobs_ids TEXT,IN _computer_id int(10) unsigned, IN _company_id char(40))
BEGIN
      DECLARE i INT Default 0 ;
      DECLARE job_id VARCHAR(255);
      array_loop: LOOP
         SET i=i+1;
         SET job_id=SPLIT_STR(_jobs_ids,",",i);
         IF job_id='' THEN
            LEAVE array_loop;
         END IF;
         INSERT INTO `JobsForComputers` (`computer_id`, `job_id`, `company_id`) VALUES (_computer_id, job_id, _company_id);
   END LOOP array_loop;
END//
DELIMITER ;

-- Dumping structure for procedure jobserver.AssignJobsToComputerGroup
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `AssignJobsToComputerGroup`(IN _jobs_ids TEXT,IN _group_id int(10) unsigned, IN _company_id char(40))
BEGIN
      DECLARE i INT Default 0 ;
      DECLARE job_id VARCHAR(255);
      array_loop: LOOP
         SET i=i+1;
         SET job_id=SPLIT_STR(_jobs_ids,",",i);
         IF job_id='' THEN
            LEAVE array_loop;
         END IF;
         INSERT INTO `JobsForComputerGroups` (`cgroup_id`, `job_id`, `company_id`) VALUES (_group_id, job_id, _company_id);
   END LOOP array_loop;
END//
DELIMITER ;

-- Dumping structure for procedure jobserver.AssignJobsToUser
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `AssignJobsToUser`(IN _jobs_ids TEXT,IN _user_id int(10) unsigned, IN _company_id char(40))
BEGIN
      DECLARE i INT Default 0 ;
      DECLARE job_id VARCHAR(255);
      array_loop: LOOP
         SET i=i+1;
         SET job_id=SPLIT_STR(_jobs_ids,",",i);
         IF job_id='' THEN
            LEAVE array_loop;
         END IF;
         INSERT INTO `JobsForUsers` (`user_id`, `job_id`, `company_id`) VALUES (_user_id, job_id, _company_id);
   END LOOP array_loop;
END//
DELIMITER ;

-- Dumping structure for procedure jobserver.AssignJobsToUserGroup
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `AssignJobsToUserGroup`(IN _jobs_ids TEXT,IN _group_id int(10) unsigned, IN _company_id char(40))
BEGIN
      DECLARE i INT Default 0 ;
      DECLARE job_id VARCHAR(255);
      array_loop: LOOP
         SET i=i+1;
         SET job_id=SPLIT_STR(_jobs_ids,",",i);
         IF job_id='' THEN
            LEAVE array_loop;
         END IF;
         INSERT INTO `JobsForUserGroups` (`ugroup_id`, `job_id`, `company_id`) VALUES (_group_id, job_id, _company_id);
   END LOOP array_loop;
END//
DELIMITER ;

-- Dumping structure for procedure jobserver.AssignJobToComputerGroups
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `AssignJobToComputerGroups`(IN _computer_groups_ids TEXT,IN _job_id int(10) unsigned, IN _company_id int(10))
BEGIN
      DECLARE i INT Default 0 ;
      DECLARE cgroup_id VARCHAR(255);
      array_loop: LOOP
         SET i=i+1;
         SET cgroup_id=SPLIT_STR(_computer_groups_ids,",",i);
         IF cgroup_id='' THEN
            LEAVE array_loop;
         END IF;
         INSERT INTO `JobsForComputerGroups` (`job_id`, `cgroup_id`, `company_id`) VALUES (_job_id, cgroup_id, _company_id);
   END LOOP array_loop;
END//
DELIMITER ;

-- Dumping structure for procedure jobserver.AssignJobToComputers
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `AssignJobToComputers`(IN _computers_ids TEXT,IN _job_id int(10) unsigned, IN _company_id int(10))
BEGIN
      DECLARE i INT Default 0 ;
      DECLARE computer_id VARCHAR(255);
      array_loop: LOOP
         SET i=i+1;
         SET computer_id=SPLIT_STR(_computers_ids,",",i);
         IF computer_id='' THEN
            LEAVE array_loop;
         END IF;
         INSERT INTO `JobsForComputers` (`job_id`, `computer_id`, `company_id`) VALUES (_job_id, computer_id, _company_id);
   END LOOP array_loop;
END//
DELIMITER ;

-- Dumping structure for procedure jobserver.AssignJobToUserGroups
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `AssignJobToUserGroups`(IN _user_groups_ids TEXT,IN _job_id int(10) unsigned, IN _company_id int(10))
BEGIN
      DECLARE i INT Default 0 ;
      DECLARE ugroup_id VARCHAR(255);
      array_loop: LOOP
         SET i=i+1;
         SET ugroup_id=SPLIT_STR(_user_groups_ids,",",i);
         IF ugroup_id='' THEN
            LEAVE array_loop;
         END IF;
         INSERT INTO `JobsForUserGroups` (`job_id`, `ugroup_id`, `company_id`) VALUES (_job_id, ugroup_id, _company_id);
   END LOOP array_loop;
END//
DELIMITER ;

-- Dumping structure for procedure jobserver.AssignJobToUsers
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `AssignJobToUsers`(IN _users_ids TEXT,IN _job_id int(10) unsigned, IN _company_id int(10))
BEGIN
      DECLARE i INT Default 0 ;
      DECLARE user_id VARCHAR(255);
      array_loop: LOOP
         SET i=i+1;
         SET user_id=SPLIT_STR(_users_ids,",",i);
         IF user_id='' THEN
            LEAVE array_loop;
         END IF;
         INSERT INTO `JobsForUsers` (`job_id`, `user_id`, `company_id`) VALUES (_job_id, user_id, _company_id);
   END LOOP array_loop;
END//
DELIMITER ;

-- Dumping structure for table jobserver.companies
CREATE TABLE IF NOT EXISTS `companies` (
  `company_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Company Id, globally unique, generated',
  `company_name` varchar(60) NOT NULL COMMENT 'Full Company Name, can change',
  `pre_moderate_users` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Admin pre-approves each user-computer combo that comes in',
  `email_authorization` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'T: user-computer combo is approved by user entering from email received from Admin',
  `global_job_options` varchar(1024) NOT NULL DEFAULT '' COMMENT 'Global Job Options',
  `server_accounts` mediumtext NOT NULL COMMENT 'Server Accounts of Company encoded in TIC, 16 Mb max, may be encrypted',
  `expire_date` datetime DEFAULT NULL COMMENT 'date when license expires',
  `licensed_ws` int(11) DEFAULT '10' COMMENT 'number of licensed Workstation Runners',
  `licensed_s` int(11) DEFAULT '10' COMMENT 'number of licensed Server Runners',
  `pums_customer_id` varchar(12) DEFAULT NULL COMMENT 'CustomerID from PUMS',
  `licenses_info` text COMMENT 'JSON licenses data from PUMS',
  `pums_last_checked` datetime DEFAULT NULL COMMENT 'Last license data update from PUMS date',
  `keep_log_days` int(11) NOT NULL DEFAULT '30' COMMENT 'Clean Older Than Specified Days Count LogLines',
  `only_confirmed_emails` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'True: Allow admin login only with confirmed email',
  `created_at` datetime NOT NULL COMMENT 'Date Time when Company was created',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`company_id`),
  UNIQUE KEY `company_name` (`company_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Companies table';

-- Data exporting was unselected.
-- Dumping structure for table jobserver.computergroupadmins
CREATE TABLE IF NOT EXISTS `computergroupadmins` (
  `cgroup_admin_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Computer Group Admin Id, globally unique, generated',
  `company_id` int(10) unsigned NOT NULL COMMENT 'Company Id, foreign key',
  `cgroup_id` int(10) unsigned NOT NULL COMMENT 'Computer Group Id, foregin key',
  `admin_id` int(10) unsigned NOT NULL COMMENT 'Admin Id, foreign key',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` datetime NOT NULL COMMENT 'date time when this record was created',
  PRIMARY KEY (`cgroup_admin_id`),
  KEY `fk_comp_group_admins_companies` (`company_id`),
  KEY `fk_comp_group_admins_comp_groups` (`cgroup_id`),
  KEY `fk_computer_group_admins_admins` (`admin_id`),
  CONSTRAINT `fk_comp_group_admins_companies` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_comp_group_admins_comp_groups` FOREIGN KEY (`cgroup_id`) REFERENCES `computergroups` (`cgroup_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_computer_group_admins_admins` FOREIGN KEY (`admin_id`) REFERENCES `administrators` (`admin_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Computer Group Administrators table';

-- Data exporting was unselected.
-- Dumping structure for table jobserver.computergroups
CREATE TABLE IF NOT EXISTS `computergroups` (
  `cgroup_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Computer Group Id, globally unique, generated',
  `company_id` int(10) unsigned NOT NULL COMMENT 'Company Id, foreign key',
  `cgroup_name` char(40) NOT NULL COMMENT 'Computer Group Name, can change',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` datetime NOT NULL COMMENT 'date time when group was created',
  PRIMARY KEY (`cgroup_id`),
  UNIQUE KEY `company_id` (`company_id`,`cgroup_name`),
  CONSTRAINT `fk_comp_groups_companies` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Computer Groups table';

-- Data exporting was unselected.
-- Dumping structure for table jobserver.computers
CREATE TABLE IF NOT EXISTS `computers` (
  `computer_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Computer Id, globally unique, generated',
  `company_id` int(10) unsigned NOT NULL COMMENT 'Company Id, foreign key',
  `computer_os_name` char(40) NOT NULL COMMENT 'Computer OS Name, can change',
  `osinfo` varchar(50) DEFAULT NULL COMMENT ' text OS info from GsGetOsInfo()',
  `platform` tinyint(4) DEFAULT NULL COMMENT 'win16: 1 (does not happen), windows: 2,macos: 3,iphone: 4,android: 5',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` datetime NOT NULL COMMENT 'date time when computer first appeared',
  PRIMARY KEY (`computer_id`),
  UNIQUE KEY `company_id` (`company_id`,`computer_os_name`),
  CONSTRAINT `fk_computers_companies` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Computers table';

-- Data exporting was unselected.
-- Dumping structure for table jobserver.computersingroups
CREATE TABLE IF NOT EXISTS `computersingroups` (
  `cig_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Computer In Group Assignment Id, globally unique, generated',
  `company_id` int(10) unsigned NOT NULL COMMENT 'Company Id, foreign key',
  `cgroup_id` int(10) unsigned NOT NULL COMMENT 'Computer Group Id, foreign key',
  `computer_id` int(10) unsigned NOT NULL COMMENT 'Computer Id, foreign key',
  PRIMARY KEY (`cig_id`),
  UNIQUE KEY `company_id` (`company_id`,`cgroup_id`,`computer_id`),
  KEY `fk_comps_in_groups_users` (`computer_id`),
  KEY `fk_comps_in_groups_user_groups` (`cgroup_id`),
  CONSTRAINT `fk_comps_in_groups_companies` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_comps_in_groups_users` FOREIGN KEY (`computer_id`) REFERENCES `computers` (`computer_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_comps_in_groups_user_groups` FOREIGN KEY (`cgroup_id`) REFERENCES `computergroups` (`cgroup_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Computers in Computer Groups table';

-- Data exporting was unselected.
-- Dumping structure for table jobserver.jobrunners
CREATE TABLE IF NOT EXISTS `jobrunners` (
  `job_runner_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Job Runner Id, globally unique, generated',
  `company_id` int(10) unsigned NOT NULL COMMENT 'Company Id, foreign key',
  `computer_id` int(10) unsigned NOT NULL COMMENT 'Computer Id, foreign key',
  `user_id` int(10) unsigned NOT NULL COMMENT 'User Id, foreign key',
  `is_authorized` tinyint(1) NOT NULL COMMENT 'Did Admin authorize this User on this Computer',
  `pass_hash` char(32) NOT NULL COMMENT 'MD5 hash of User On Computer password, hex string',
  `is_server_os` tinyint(4) DEFAULT NULL COMMENT '1: Windows Server, 0: Workstation',
  `goodsync_ver` varchar(64) NOT NULL DEFAULT '' COMMENT 'Version of GoodSync ',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`job_runner_id`),
  UNIQUE KEY `company_id` (`company_id`,`computer_id`,`user_id`),
  KEY `job_runner_id` (`job_runner_id`,`is_authorized`),
  KEY `fk_job_runners_users` (`user_id`),
  KEY `fk_job_runners_computers` (`computer_id`),
  CONSTRAINT `fk_job_runners_companies` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_job_runners_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_job_runners_computers` FOREIGN KEY (`computer_id`) REFERENCES `computers` (`computer_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Registered Job Runners table';

-- Data exporting was unselected.
-- Dumping structure for table jobserver.jobrunrequests
CREATE TABLE IF NOT EXISTS `jobrunrequests` (
  `job_run_req_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Job Run Request Id, foreign key',
  `job_runner_id` int(10) unsigned NOT NULL COMMENT 'Job Runner Id (Company, User, Computer), foreign key',
  `job_id` int(10) unsigned NOT NULL COMMENT 'Job Id, foreign key',
  `run_oper` int(10) unsigned NOT NULL COMMENT 'Run Operation: 0 - Analyze & Sync, 1 to 11 - Special Operation',
  PRIMARY KEY (`job_run_req_id`),
  UNIQUE KEY `job_runner_id` (`job_runner_id`,`job_id`,`run_oper`),
  KEY `fk_jobrunrequests_job` (`job_id`),
  CONSTRAINT `fk_jobrunrequests_runner` FOREIGN KEY (`job_runner_id`) REFERENCES `jobrunners` (`job_runner_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_jobrunrequests_job` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`job_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Job Run requests';

-- Data exporting was unselected.
-- Dumping structure for table jobserver.jobruns
CREATE TABLE IF NOT EXISTS `jobruns` (
  `job_run_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Job Run Id, generated',
  `company_id` int(10) unsigned NOT NULL COMMENT 'Company Id, foreign key',
  `job_runner_id` int(10) unsigned NOT NULL COMMENT 'Job Runner Id (Company, User, Computer), foreign key',
  `job_id` int(10) unsigned NOT NULL COMMENT 'Job Id, foreign key',
  `job_started` datetime NOT NULL COMMENT 'date-time when Job started, cannot change',
  `goodsync_ver` varchar(64) NOT NULL DEFAULT '' COMMENT 'Version of GoodSync that execute this run',
  `console_ver` varchar(64) NOT NULL DEFAULT '' COMMENT 'Dispatcher ver that originated this run',
  `stop_req` smallint(6) DEFAULT '0' COMMENT 'If 1 then Stop of this Job Run has been requested by user',
  `last_active` datetime NOT NULL COMMENT 'date-time for Log Line',
  `result_int` smallint(6) NOT NULL DEFAULT '0' COMMENT 'Utility column. Common result flag: 0 unknown, 1 error, 2 conflict, 3 ok',
  `is_last_run` smallint(6) NOT NULL DEFAULT '0' COMMENT 'Utility column. Is this run last for Job-Runner pair',
  `job_started_server` datetime NOT NULL COMMENT 'Server date-time when Job started, cannot change',
  `last_active_server` datetime NOT NULL COMMENT 'Server date-time for Log Line',
  `job_started_date` date NOT NULL COMMENT 'Utility column. Date when Job started, cannot change',
  `rc_int` smallint(6) DEFAULT '-1' COMMENT 'Job Return Code: -1 running, 0 terminal error, 1 ok see non-term errors',
  `run_state` smallint(6) DEFAULT '0' COMMENT 'Run State: 0 init, 1 waiting to run, 2 analyzing, 3 paused, 4 analyzed, 5 syncing, 6 paused, 7 synced',
  `pct_complete` smallint(6) DEFAULT '0' COMMENT 'Percent Complete',
  `term_err` varchar(2048) DEFAULT NULL COMMENT 'Terminal Error',
  `synced_ok` mediumint(9) DEFAULT '0' COMMENT 'Items Synced OK',
  `synced_err` mediumint(9) DEFAULT '0' COMMENT 'Items Synced with Non-Terminal Errors',
  `synced_conflict` mediumint(9) DEFAULT '0' COMMENT 'Items Synced With Non-Terminal Conflict',
  `elapsed` int(11) DEFAULT NULL COMMENT 'elapsed analyze/sync time in sec',
  `speedave` int(11) DEFAULT NULL COMMENT 'average speed sync',
  `speedins` int(11) DEFAULT NULL COMMENT 'instant speed sync',
  `bytesproc` bigint(20) DEFAULT NULL COMMENT 'bytes synced so far, since sync started',
  `timeremsec` int(11) DEFAULT NULL COMMENT 'time remaining in sec',
  PRIMARY KEY (`job_run_id`),
  UNIQUE KEY `job_runner_id` (`job_runner_id`,`job_id`,`job_started`),
  KEY `fk_jobruns_job` (`job_id`),
  KEY `run_state_company_id` (`company_id`,`run_state`),
  KEY `company_last_run` (`company_id`,`is_last_run`),
  KEY `company_last_result` (`company_id`,`result_int`,`is_last_run`),
  KEY `company_started_date_job` (`company_id`,`job_started_date`,`job_id`),
  KEY `company_started_date_result` (`company_id`,`job_started_date`,`result_int`),
  KEY `company_started_date_last` (`company_id`,`job_started_date`,`is_last_run`),
  KEY `company_started_date_last_result` (`company_id`,`job_started_date`,`result_int`,`is_last_run`),
  CONSTRAINT `fk_jobruns_runner` FOREIGN KEY (`job_runner_id`) REFERENCES `jobrunners` (`job_runner_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_jobruns_job` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`job_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_jobruns_companies` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Job Runs table';

-- Data exporting was unselected.
-- Dumping structure for table jobserver.jobrunsinprogress
CREATE TABLE IF NOT EXISTS `jobrunsinprogress` (
  `job_run_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Job Runner Id (Company, User, Computer), foreign key',
  `job_runner_id` int(10) unsigned NOT NULL COMMENT 'Job Runner Id (Company, User, Computer), foreign key',
  `job_id` int(10) unsigned NOT NULL COMMENT 'Job Id, foreign key',
  `job_started` datetime NOT NULL COMMENT 'date-time when Job started, cannot change',
  `goodsync_ver` varchar(64) NOT NULL DEFAULT '' COMMENT 'Version of GoodSync that execute this run',
  `console_ver` varchar(64) NOT NULL DEFAULT '' COMMENT 'Dispatcher ver that originated this run',
  `run_state` smallint(6) DEFAULT '0' COMMENT 'Run State: 0 init, 1 waiting to run, 2 analyzing, 3 paused, 4 analyzed, 5 syncing, 6 paused, 7 synced',
  `last_active` datetime NOT NULL COMMENT 'date-time for Log Line',
  `pct_complete` smallint(6) DEFAULT '0' COMMENT 'Percent Complete',
  `elapsed` int(11) DEFAULT NULL COMMENT 'elapsed analyze/sync time in sec',
  `speedave` int(11) DEFAULT NULL COMMENT 'average speed sync',
  `speedins` int(11) DEFAULT NULL COMMENT 'instant speed sync',
  `bytesproc` bigint(20) DEFAULT NULL COMMENT 'bytes synced so far, since sync started',
  `timeremsec` int(11) DEFAULT NULL COMMENT 'time remaining in sec',
  `stop_req` smallint(2) DEFAULT '0' COMMENT 'If 1 then Stop of this Job Run has been requested by user',
  `job_started_server` datetime NOT NULL COMMENT 'Server date-time when Job started, cannot change',
  `last_active_server` datetime NOT NULL COMMENT 'Server date-time for Log Line',
  `company_id` int(10) unsigned NOT NULL COMMENT 'Company Id, foreign key',
  PRIMARY KEY (`job_run_id`),
  UNIQUE KEY `job_runner_id` (`job_runner_id`,`job_id`,`job_started`),
  KEY `fk_jobruns_job` (`job_id`),
  KEY `jrip_run_state_company_id` (`company_id`,`run_state`),
  KEY `jrip_job_runner_id_stop_req` (`job_runner_id`,`stop_req`)
) ENGINE=MEMORY DEFAULT CHARSET=utf8 COMMENT='Job Runs In Progress table';

-- Data exporting was unselected.
-- Dumping structure for table jobserver.jobs
CREATE TABLE IF NOT EXISTS `jobs` (
  `job_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Job Id, globally unique, generated',
  `company_id` int(10) unsigned NOT NULL COMMENT 'Company Id, foreign key',
  `job_name` char(40) NOT NULL COMMENT 'Job Name, can change',
  `dir_L_conn` varchar(1024) DEFAULT NULL COMMENT 'Left Folder CL Options, if not using Accounts, starts with /f1=',
  `dir_L_acctkey` char(250) DEFAULT NULL COMMENT 'Left Folder Account Key, references ServerFolders, not empty if using Accounts',
  `dir_L_folder` char(250) DEFAULT NULL COMMENT 'Left Folder URL, references ServerFolders',
  `dir_R_conn` varchar(1024) DEFAULT NULL COMMENT 'Right Folder CL Options, if not using Accounts, starts with /f2=',
  `dir_R_acctkey` char(250) DEFAULT NULL COMMENT 'Right Folder Account Key, if using Accounts',
  `dir_R_folder` char(250) DEFAULT NULL COMMENT 'Right Folder URL, references ServerFolders',
  `options` varchar(2048) DEFAULT NULL COMMENT 'Job Options as command line, not related to left/right Accounts or Connectoids',
  `description` varchar(300) DEFAULT NULL COMMENT 'Job Description',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `company_id` (`company_id`,`job_name`),
  CONSTRAINT `fk_jobs_companies` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Jobs table';

-- Data exporting was unselected.
-- Dumping structure for table jobserver.jobsforcomputergroups
CREATE TABLE IF NOT EXISTS `jobsforcomputergroups` (
  `jfcg_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Job For Computer Group Assignment Id, globally unique, generated',
  `company_id` int(10) unsigned NOT NULL COMMENT 'Company Id, foreign key',
  `cgroup_id` int(10) unsigned NOT NULL COMMENT 'Computer Groop Id, foreign key',
  `job_id` int(10) unsigned NOT NULL COMMENT 'JobId to run, foreign key',
  PRIMARY KEY (`jfcg_id`),
  UNIQUE KEY `company_id` (`company_id`,`cgroup_id`,`job_id`),
  KEY `fk_job_cgs_cgroup` (`cgroup_id`),
  KEY `fk_job_cgs_job` (`job_id`),
  CONSTRAINT `fk_job_cgs_company` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_job_cgs_cgroup` FOREIGN KEY (`cgroup_id`) REFERENCES `computergroups` (`cgroup_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_job_cgs_job` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`job_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Jobs For Computer Groups table';

-- Data exporting was unselected.
-- Dumping structure for table jobserver.jobsforcomputers
CREATE TABLE IF NOT EXISTS `jobsforcomputers` (
  `jfc_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Job For Computer Assignment Id, globally unique, generated',
  `company_id` int(10) unsigned NOT NULL COMMENT 'Company Id, foreign key',
  `computer_id` int(10) unsigned NOT NULL COMMENT 'Computer Id, foreign key',
  `job_id` int(10) unsigned NOT NULL COMMENT 'JobId to run, foreign key',
  PRIMARY KEY (`jfc_id`),
  UNIQUE KEY `company_id` (`company_id`,`computer_id`,`job_id`),
  KEY `fk_job_comps_computer` (`computer_id`),
  KEY `fk_job_comps_job` (`job_id`),
  CONSTRAINT `fk_job_comps_company` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_job_comps_computer` FOREIGN KEY (`computer_id`) REFERENCES `computers` (`computer_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_job_comps_job` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`job_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Jobs For Computers table';

-- Data exporting was unselected.
-- Dumping structure for table jobserver.jobsforusergroups
CREATE TABLE IF NOT EXISTS `jobsforusergroups` (
  `jfug_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Job For User Group Assignment Id, globally unique, generated',
  `company_id` int(10) unsigned NOT NULL COMMENT 'Company Id, foreign key',
  `ugroup_id` int(10) unsigned NOT NULL COMMENT 'User Group Id, foreign key',
  `job_id` int(10) unsigned NOT NULL COMMENT 'JobId to run, foreign key',
  PRIMARY KEY (`jfug_id`),
  UNIQUE KEY `company_id` (`company_id`,`ugroup_id`,`job_id`),
  KEY `fk_job_ugs_ugroup` (`ugroup_id`),
  KEY `fk_job_ugs_job` (`job_id`),
  CONSTRAINT `fk_job_ugs_company` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_job_ugs_ugroup` FOREIGN KEY (`ugroup_id`) REFERENCES `usergroups` (`ugroup_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_job_ugs_job` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`job_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Job For User Groups table';

-- Data exporting was unselected.
-- Dumping structure for table jobserver.jobsforusers
CREATE TABLE IF NOT EXISTS `jobsforusers` (
  `jfu_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Job For User Assignment Id, globally unique, generated',
  `company_id` int(10) unsigned NOT NULL COMMENT 'Company Id, foreign key',
  `user_id` int(10) unsigned NOT NULL COMMENT 'User Id, foreign key',
  `job_id` int(10) unsigned NOT NULL COMMENT 'JobId to run, foreign key',
  PRIMARY KEY (`jfu_id`),
  UNIQUE KEY `company_id` (`company_id`,`user_id`,`job_id`),
  KEY `fk_job_users_user` (`user_id`),
  KEY `fk_job_users_job` (`job_id`),
  CONSTRAINT `fk_job_users_company` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_job_users_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_job_users_job` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`job_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Jobs For Users table';

-- Data exporting was unselected.
-- Dumping structure for table jobserver.serveraccounts
CREATE TABLE IF NOT EXISTS `serveraccounts` (
  `company_id` int(10) unsigned NOT NULL COMMENT 'Company Id, foreign key',
  `account_key` char(250) NOT NULL COMMENT 'Account Key, unique per company',
  `account_name` char(250) NOT NULL COMMENT 'Account Name, may be empty',
  `account_url` char(250) NOT NULL COMMENT 'Account URL -- URL of root folder of this account',
  PRIMARY KEY (`company_id`,`account_key`),
  CONSTRAINT `fk_accounts_companies` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Server Accounts table';

-- Data exporting was unselected.
-- Dumping structure for table jobserver.serverfolders
CREATE TABLE IF NOT EXISTS `serverfolders` (
  `company_id` int(10) unsigned NOT NULL COMMENT 'Company Id, foreign key',
  `account_key` char(250) NOT NULL COMMENT 'Account Key, unique per company',
  `folder_url` char(250) NOT NULL COMMENT 'Folder URL, must be inside URL of Server Account @account_key',
  `folder_opts` char(250) NOT NULL COMMENT 'Folder options for display, contain no passwords',
  PRIMARY KEY (`company_id`,`account_key`,`folder_url`),
  CONSTRAINT `fk_folders_accounts` FOREIGN KEY (`company_id`, `account_key`) REFERENCES `serveraccounts` (`company_id`, `account_key`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Server Folders table';

-- Data exporting was unselected.
-- Dumping structure for function jobserver.SPLIT_STR
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `SPLIT_STR`(
  x VARCHAR(255),
  delim VARCHAR(12),
  pos INT
) RETURNS varchar(255) CHARSET utf8
RETURN REPLACE(SUBSTRING(SUBSTRING_INDEX(x, delim, pos),
       LENGTH(SUBSTRING_INDEX(x, delim, pos -1)) + 1),
       delim, '')//
DELIMITER ;

-- Dumping structure for table jobserver.usergroupadmins
CREATE TABLE IF NOT EXISTS `usergroupadmins` (
  `ugroup_admin_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'User Group Admin Id, globally unique, generated',
  `company_id` int(10) unsigned NOT NULL COMMENT 'Company Id, foreign key',
  `ugroup_id` int(10) unsigned NOT NULL COMMENT 'User Group Id, foregin key',
  `admin_id` int(10) unsigned NOT NULL COMMENT 'Admin Id, foreign key',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` datetime NOT NULL COMMENT 'date time when record was created',
  PRIMARY KEY (`ugroup_admin_id`),
  UNIQUE KEY `company_id` (`company_id`,`ugroup_id`,`admin_id`),
  KEY `fk_user_group_admins_user_groups` (`ugroup_id`),
  KEY `fk_user_group_admins_admins` (`admin_id`),
  CONSTRAINT `fk_users_group_admins_companies` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_user_group_admins_user_groups` FOREIGN KEY (`ugroup_id`) REFERENCES `usergroups` (`ugroup_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_user_group_admins_admins` FOREIGN KEY (`admin_id`) REFERENCES `administrators` (`admin_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='User Group Administrators table';

-- Data exporting was unselected.
-- Dumping structure for table jobserver.usergroups
CREATE TABLE IF NOT EXISTS `usergroups` (
  `ugroup_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'User Group Id, globally unique, generated',
  `company_id` int(10) unsigned NOT NULL COMMENT 'Company Id, foreign key',
  `ugroup_name` char(40) NOT NULL COMMENT 'User Group Name, can change',
  `ugroup_os_name` char(40) NOT NULL COMMENT 'User Group OS Name, can change',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` datetime NOT NULL COMMENT 'date time when group was created',
  PRIMARY KEY (`ugroup_id`),
  UNIQUE KEY `company_id` (`company_id`,`ugroup_name`),
  CONSTRAINT `fk_user_groups_companies` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='User Groups table';

-- Data exporting was unselected.
-- Dumping structure for table jobserver.users
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'User Id, globally unique, generated',
  `company_id` int(10) unsigned NOT NULL COMMENT 'Company Id, foreign key',
  `user_os_name` char(40) NOT NULL COMMENT 'User Name(Id) in Windows/Mac/Unix OS',
  `user_email` char(40) DEFAULT NULL COMMENT 'User Email',
  `user_full_name` varchar(64) DEFAULT NULL COMMENT 'Full User Name',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` datetime NOT NULL COMMENT 'date time when user was created',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `company_id` (`company_id`,`user_os_name`),
  CONSTRAINT `fk_users_companies` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Users table';

-- Data exporting was unselected.
-- Dumping structure for table jobserver.usersingroups
CREATE TABLE IF NOT EXISTS `usersingroups` (
  `uig_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'User In Group Assignment Id, globally unique, generated',
  `company_id` int(10) unsigned NOT NULL COMMENT 'Company Id, foreign key',
  `ugroup_id` int(10) unsigned NOT NULL COMMENT 'User Group Id, foreign key',
  `user_id` int(10) unsigned NOT NULL COMMENT 'User Id, foreign key',
  PRIMARY KEY (`uig_id`),
  UNIQUE KEY `company_id` (`company_id`,`ugroup_id`,`user_id`),
  KEY `fk_users_in_groups_users` (`user_id`),
  KEY `fk_users_in_groups_user_groups` (`ugroup_id`),
  CONSTRAINT `fk_users_in_groups_companies` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_users_in_groups_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_users_in_groups_user_groups` FOREIGN KEY (`ugroup_id`) REFERENCES `usergroups` (`ugroup_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Users in User Groups table';

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
