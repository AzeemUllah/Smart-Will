-- phpMyAdmin SQL Dump
-- version 4.0.10.20
-- https://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 04, 2018 at 08:57 AM
-- Server version: 5.7.18
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `smart_will`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_accounts`
--

CREATE TABLE IF NOT EXISTS `admin_accounts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `email` varchar(150) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `admin_accounts`
--

INSERT INTO `admin_accounts` (`id`, `username`, `email`, `password`) VALUES
(1, 'azeemullah', 'theazeemullah@gmail.com', 'admin123');

-- --------------------------------------------------------

--
-- Table structure for table `executor`
--

CREATE TABLE IF NOT EXISTS `executor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `mid_name` varchar(50) NOT NULL,
  `sur_name` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `post_code` varchar(15) NOT NULL,
  `date_of_birth` date NOT NULL,
  `relationship` varchar(50) NOT NULL,
  `u_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `u_id` (`u_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=139 ;

--
-- Dumping data for table `executor`
--

INSERT INTO `executor` (`id`, `first_name`, `mid_name`, `sur_name`, `address`, `post_code`, `date_of_birth`, `relationship`, `u_id`, `created_at`, `updated_at`) VALUES
(130, 'Executer 1', '', 'Test', 'asdad', '123123', '1981-11-24', 'asdasd', 124, '2017-11-24 10:18:20', NULL),
(131, 'Executer 2', '', 'Test', 'dasdad', 'asdsad', '1987-11-24', 'dasdasd', 124, '2017-11-24 10:18:49', NULL),
(132, 'Executer 3', '', 'Test', 'addad', 'dasda', '1986-11-24', 'dsdasd', 124, '2017-11-24 10:23:57', NULL),
(133, 'asdasd', '', 'asdsad', 'dasd', 'dasda', '1991-12-02', 'asdsad', 125, '2017-12-02 08:53:53', NULL),
(134, 'sdasda', '', 'dsasdad', 'asd', 'asdasd', '1985-12-02', 'dasda', 126, '2017-12-02 08:56:37', NULL),
(135, 'adada', '', 'dadads', 'ad', 'dasd', '1992-12-02', 'adasd', 127, '2017-12-02 08:59:43', NULL),
(136, 'sdasda', '', 'dsasdad', 'asd', 'asdasd', '1985-12-02', 'dasda', 126, '2017-12-02 09:51:41', NULL),
(137, 'sdasd', '', 'adasd', 'sadsad', 'adasd', '1991-12-02', 'asdasd', 128, '2017-12-02 09:55:22', NULL),
(138, 'dasdsad', '', 'sdsad', 'sdasd', 'adasd', '1982-12-14', 'asdasds', 129, '2017-12-14 12:52:57', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `gifts`
--

CREATE TABLE IF NOT EXISTS `gifts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `mid_name` varchar(50) NOT NULL,
  `sur_name` varchar(50) NOT NULL,
  `relationship` varchar(50) NOT NULL,
  `gifts` varchar(50) NOT NULL,
  `u_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `u_id` (`u_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=139 ;

--
-- Dumping data for table `gifts`
--

INSERT INTO `gifts` (`id`, `first_name`, `mid_name`, `sur_name`, `relationship`, `gifts`, `u_id`, `created_at`, `updated_at`) VALUES
(136, 'adasd', '', 'sadasd', 'dasdasd', 'dsadasd', 128, '2017-12-02 10:52:28', NULL),
(137, 'asdasd', '', 'dasdasd', 'asdasd', 'dasdasd', 128, '2017-12-04 10:15:33', NULL),
(138, 'sdasd', '', 'sdsa', 'asdsad', 'dasd', 129, '2017-12-15 06:47:29', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `gifts_legacy`
--

CREATE TABLE IF NOT EXISTS `gifts_legacy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `foundation1` varchar(50) NOT NULL,
  `charity_no_1` varchar(250) NOT NULL,
  `gift_1` varchar(250) NOT NULL,
  `foundation2` varchar(50) DEFAULT NULL,
  `charity_no_2` varchar(250) NOT NULL,
  `gift_2` varchar(250) NOT NULL,
  `foundation3` varchar(50) DEFAULT NULL,
  `charity_no_3` varchar(250) NOT NULL,
  `gift_3` varchar(250) NOT NULL,
  `u_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `u_id` (`u_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=40 ;

--
-- Dumping data for table `gifts_legacy`
--

INSERT INTO `gifts_legacy` (`id`, `foundation1`, `charity_no_1`, `gift_1`, `foundation2`, `charity_no_2`, `gift_2`, `foundation3`, `charity_no_3`, `gift_3`, `u_id`, `created_at`, `updated_at`) VALUES
(37, 'asdas', 'sdasda', 'dasd', '', '', '', '', '', '', 124, '2017-12-02 08:54:03', NULL),
(38, 'adasd', 'adasd', 'dasd', '', '', '', '', '', '', 126, '2017-12-02 08:56:54', '2017-12-02 09:51:48'),
(39, 'asdasd', 'dasda', 'sdasd', '', '', '', '', '', '', 127, '2017-12-02 08:59:51', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `guardians`
--

CREATE TABLE IF NOT EXISTS `guardians` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `mid_name` varchar(50) NOT NULL,
  `sur_name` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `post_code` varchar(15) NOT NULL,
  `date_of_birth` date NOT NULL,
  `relationship` varchar(50) NOT NULL,
  `u_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `u_id` (`u_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=43 ;

--
-- Dumping data for table `guardians`
--

INSERT INTO `guardians` (`id`, `first_name`, `mid_name`, `sur_name`, `address`, `post_code`, `date_of_birth`, `relationship`, `u_id`, `created_at`, `updated_at`) VALUES
(41, 'Guardian 1', '', 'Test', 'asdasd', 'asdsad', '1991-11-24', 'asdsad', 124, '2017-11-24 10:24:21', NULL),
(42, 'Guardian 2', '', 'test', 'sdasd', 'aasdsd', '1987-11-24', 'sadsad', 124, '2017-11-24 10:24:44', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `resedue_of_estate`
--

CREATE TABLE IF NOT EXISTS `resedue_of_estate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) DEFAULT NULL,
  `mid_name` varchar(50) DEFAULT NULL,
  `sur_name` varchar(50) DEFAULT NULL,
  `relationship` varchar(50) NOT NULL,
  `estate` int(11) NOT NULL,
  `type` varchar(50) NOT NULL,
  `u_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `u_id` (`u_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=72 ;

--
-- Dumping data for table `resedue_of_estate`
--

INSERT INTO `resedue_of_estate` (`id`, `first_name`, `mid_name`, `sur_name`, `relationship`, `estate`, `type`, `u_id`, `created_at`, `updated_at`) VALUES
(63, 'Residue 1', '', 'test', 'asdasd', 100, 'beneficiary', 124, '2017-11-24 10:36:08', NULL),
(65, NULL, NULL, NULL, 'childrens', 100, 'childrens', 127, '2017-12-02 09:27:38', NULL),
(70, 'addasd', '', 'asdsad', 'dasdad', 100, 'beneficiary', 128, '2017-12-04 10:15:43', NULL),
(71, NULL, NULL, NULL, 'childrens', 100, 'childrens', 129, '2017-12-15 06:48:19', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `special_request`
--

CREATE TABLE IF NOT EXISTS `special_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `buried_cremated` varchar(50) NOT NULL,
  `home_owner` varchar(250) NOT NULL,
  `music` varchar(250) NOT NULL,
  `video_storage` varchar(250) NOT NULL,
  `prepaid_plan` varchar(10) NOT NULL,
  `request` varchar(50) NOT NULL,
  `u_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_id` (`u_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=28 ;

--
-- Dumping data for table `special_request`
--

INSERT INTO `special_request` (`id`, `buried_cremated`, `home_owner`, `music`, `video_storage`, `prepaid_plan`, `request`, `u_id`, `created_at`, `updated_at`) VALUES
(26, 'Buried', 'No', 'dDsd', '', 'No', '', 124, '2017-11-24 10:37:53', NULL),
(27, 'Cremated', 'No', '', '', 'No', '', 128, '2017-12-04 10:15:49', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `spouse`
--

CREATE TABLE IF NOT EXISTS `spouse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `mid_name` varchar(50) NOT NULL,
  `sur_name` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `post_code` varchar(15) NOT NULL,
  `telephone` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `date_of_birth` date NOT NULL,
  `gender` varchar(10) NOT NULL,
  `u_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `u_id` (`u_id`),
  KEY `u_id_2` (`u_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=26 ;

--
-- Dumping data for table `spouse`
--

INSERT INTO `spouse` (`id`, `first_name`, `mid_name`, `sur_name`, `address`, `post_code`, `telephone`, `email`, `date_of_birth`, `gender`, `u_id`, `created_at`, `updated_at`) VALUES
(25, 'Spouse', '', 'Test 1', 'ddasd', '3123123', '3123123', 'asdad@gmail.com', '2017-11-24', 'Male', 124, '2017-11-24 10:13:04', '2017-11-24 10:13:04');

-- --------------------------------------------------------

--
-- Table structure for table `step_children`
--

CREATE TABLE IF NOT EXISTS `step_children` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `mid_name` varchar(50) NOT NULL,
  `sur_name` varchar(50) NOT NULL,
  `relationship` varchar(50) NOT NULL,
  `estate` varchar(50) NOT NULL,
  `u_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `u_id` (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `support_desission`
--

CREATE TABLE IF NOT EXISTS `support_desission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `option1` varchar(50) NOT NULL,
  `option2` varchar(50) NOT NULL,
  `option3` varchar(50) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `support_desission`
--

INSERT INTO `support_desission` (`id`, `option1`, `option2`, `option3`, `user_id`) VALUES
(2, 'Yes', 'Yes', 'Yes', 124),
(7, 'No', 'Yes', 'Yes', 127),
(11, 'No', 'No', 'No', 126),
(13, 'No', 'No', 'No', 128),
(16, 'Yes', 'Yes', 'Yes', 129);

-- --------------------------------------------------------

--
-- Table structure for table `testator`
--

CREATE TABLE IF NOT EXISTS `testator` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `mid_name` varchar(50) NOT NULL,
  `sur_name` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `post_code` varchar(15) NOT NULL,
  `telephone` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `date_of_birth` date NOT NULL,
  `gender` varchar(10) NOT NULL,
  `u_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `u_id` (`u_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=93 ;

--
-- Dumping data for table `testator`
--

INSERT INTO `testator` (`id`, `first_name`, `mid_name`, `sur_name`, `address`, `post_code`, `telephone`, `email`, `date_of_birth`, `gender`, `u_id`, `created_at`, `updated_at`) VALUES
(87, 'Azeem', '', 'Ullah', '264, 21st Jump Street, Ohio', '1234576', '12345678', '', '1982-11-24', 'Male', 124, '2017-11-24 09:59:06', '2017-11-24 10:17:49'),
(88, 'asdasd', '', 'dasd', 'dasd', 'asdsda', '3213123', 'a@a.com', '1987-12-02', 'Male', 125, '2017-12-02 08:53:41', NULL),
(89, 'sadasd', '', 'dasd', 'dsad', 'adasd', '312312', 'b@b.com', '1988-12-02', 'Male', 126, '2017-12-02 08:56:25', '2017-12-02 09:51:40'),
(90, 'dasda', '', 'dsasd', 'dasd', 'ads', '123123', 'c@c.com', '1988-12-02', 'Male', 127, '2017-12-02 08:59:33', NULL),
(91, 'aadad', '', 'dasd', 'asdsad', 'dasd', '1323', 'd@d.com', '1987-12-02', 'Male', 128, '2017-12-02 09:55:11', NULL),
(92, 'dasdasd', '', 'asdsad', 'dasdsad', '23213213', '1123123', 'abc@abc.com', '1972-12-14', 'Male', 129, '2017-12-14 12:52:39', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(150) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(500) NOT NULL,
  `token_generat` varchar(50) DEFAULT NULL,
  `token_status` tinyint(10) DEFAULT NULL,
  `current_working_class` varchar(50) DEFAULT NULL,
  `solicitor` varchar(10) NOT NULL DEFAULT 'No',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=130 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `user_name`, `email`, `password`, `token_generat`, `token_status`, `current_working_class`, `solicitor`, `created_at`, `updated_at`) VALUES
(124, 'azeem ull', 'theazeemullah@gmail.com', '1234', NULL, NULL, 'submission', 'No', '2017-11-24 10:37:53', '2017-11-24 09:57:52'),
(125, 'adasd', 'a@a.com', '1234', NULL, NULL, 'residue', 'No', '2017-12-02 08:54:11', '2017-12-02 08:53:21'),
(126, 'sadsad', 'b@b.com', '1234', NULL, NULL, 'residue', 'No', '2017-12-02 09:52:39', '2017-12-02 08:56:08'),
(127, 'asda', 'c@c.com', '1234', NULL, NULL, 'special_req', 'No', '2017-12-02 09:07:56', '2017-12-02 08:59:12'),
(128, 'adasd', 'd@d.com', '1234', NULL, NULL, 'submission', 'No', '2017-12-04 10:15:50', '2017-12-02 09:54:52'),
(129, 'Azeem', 'abc@abc.com', '1234', NULL, NULL, 'special_req', 'No', '2017-12-15 06:48:19', '2017-12-14 12:52:00');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `executor`
--
ALTER TABLE `executor`
  ADD CONSTRAINT `executor_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `gifts`
--
ALTER TABLE `gifts`
  ADD CONSTRAINT `gifts_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `gifts_legacy`
--
ALTER TABLE `gifts_legacy`
  ADD CONSTRAINT `gifts_legacy_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `guardians`
--
ALTER TABLE `guardians`
  ADD CONSTRAINT `guardians_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `resedue_of_estate`
--
ALTER TABLE `resedue_of_estate`
  ADD CONSTRAINT `resedue_of_estate_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `special_request`
--
ALTER TABLE `special_request`
  ADD CONSTRAINT `special_request_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `spouse`
--
ALTER TABLE `spouse`
  ADD CONSTRAINT `spouse_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `step_children`
--
ALTER TABLE `step_children`
  ADD CONSTRAINT `step_children_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `support_desission`
--
ALTER TABLE `support_desission`
  ADD CONSTRAINT `support_desission_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `testator`
--
ALTER TABLE `testator`
  ADD CONSTRAINT `fg_userid_testator` FOREIGN KEY (`u_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
