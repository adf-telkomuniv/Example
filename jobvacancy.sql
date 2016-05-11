-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 11, 2016 at 03:23 AM
-- Server version: 5.6.26
-- PHP Version: 5.6.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jobvacancy`
--

-- --------------------------------------------------------

--
-- Table structure for table `applicants`
--

CREATE TABLE IF NOT EXISTS `applicants` (
  `email` varchar(255) NOT NULL,
  `gender` char(1) DEFAULT NULL,
  `lastEducation` varchar(200) DEFAULT NULL,
  `expertise` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `applicationfiles`
--

CREATE TABLE IF NOT EXISTS `applicationfiles` (
  `applicationId` int(11) NOT NULL,
  `date_created` date NOT NULL,
  `name` varchar(50) NOT NULL,
  `resume` text NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `submittedfiles`
--

CREATE TABLE IF NOT EXISTS `submittedfiles` (
  `vacancyId` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `applicationId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `email` varchar(255) NOT NULL,
  `password` varchar(200) NOT NULL,
  `name` varchar(50) NOT NULL,
  `address` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `vacancy`
--

CREATE TABLE IF NOT EXISTS `vacancy` (
  `vacancyId` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `detail` varchar(200) NOT NULL,
  `deadline` date NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '0',
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `applicants`
--
ALTER TABLE `applicants`
  ADD PRIMARY KEY (`email`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `applicationfiles`
--
ALTER TABLE `applicationfiles`
  ADD PRIMARY KEY (`applicationId`),
  ADD KEY `applicationfiles_applicant` (`email`);

--
-- Indexes for table `submittedfiles`
--
ALTER TABLE `submittedfiles`
  ADD PRIMARY KEY (`vacancyId`,`email`),
  ADD KEY `applicant_submitted` (`email`),
  ADD KEY `submitted_files` (`applicationId`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`email`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `vacancy`
--
ALTER TABLE `vacancy`
  ADD PRIMARY KEY (`vacancyId`),
  ADD KEY `vacancy_user` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `applicationfiles`
--
ALTER TABLE `applicationfiles`
  MODIFY `applicationId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `vacancy`
--
ALTER TABLE `vacancy`
  MODIFY `vacancyId` int(11) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `applicants`
--
ALTER TABLE `applicants`
  ADD CONSTRAINT `applicant_user` FOREIGN KEY (`email`) REFERENCES `users` (`email`) ON DELETE CASCADE;

--
-- Constraints for table `applicationfiles`
--
ALTER TABLE `applicationfiles`
  ADD CONSTRAINT `applicationfiles_applicant` FOREIGN KEY (`email`) REFERENCES `applicants` (`email`) ON DELETE CASCADE;

--
-- Constraints for table `submittedfiles`
--
ALTER TABLE `submittedfiles`
  ADD CONSTRAINT `applicant_submitted` FOREIGN KEY (`email`) REFERENCES `applicants` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `submitted_files` FOREIGN KEY (`applicationId`) REFERENCES `applicationfiles` (`applicationId`) ON DELETE CASCADE,
  ADD CONSTRAINT `vacancy_submitted` FOREIGN KEY (`vacancyId`) REFERENCES `vacancy` (`vacancyId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `vacancy`
--
ALTER TABLE `vacancy`
  ADD CONSTRAINT `vacancy_user` FOREIGN KEY (`email`) REFERENCES `users` (`email`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
