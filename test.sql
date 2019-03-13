-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 23, 2018 at 08:41 AM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `test`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `test_multi_sets`()
    DETERMINISTIC
begin
        select user() as first_col;
        select user() as first_col, now() as second_col;
        select user() as first_col, now() as second_col, now() as third_col;
        end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `restapi`
--

CREATE TABLE IF NOT EXISTS `restapi` (
  `userid` int(11) NOT NULL,
  `projectid` int(11) NOT NULL,
  `scenarioname` int(11) NOT NULL,
  `method` varchar(11) NOT NULL,
  `wsdl` varchar(500) NOT NULL,
  PRIMARY KEY (`scenarioname`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `restapi`
--

INSERT INTO `restapi` (`userid`, `projectid`, `scenarioname`, `method`, `wsdl`) VALUES
(12, 12, 13, 'GET', 'http://maps.googleapis.com/maps/api/geocode/xml?address=1600 Amphitheatre Parkway, Mountain View, CA');

-- --------------------------------------------------------

--
-- Table structure for table `soapapi`
--

CREATE TABLE IF NOT EXISTS `soapapi` (
  `userid` int(11) NOT NULL,
  `projectid` int(11) NOT NULL,
  `scenarioname` int(11) NOT NULL,
  `wsdl` varchar(200) NOT NULL,
  `portname` varchar(200) NOT NULL,
  `servicename` varchar(200) NOT NULL,
  PRIMARY KEY (`scenarioname`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `soapapi`
--

INSERT INTO `soapapi` (`userid`, `projectid`, `scenarioname`, `wsdl`, `portname`, `servicename`) VALUES
(12, 12, 0, 'http://www.dataaccess.com/webservicesserver/numberconversion.wso?WSDL ', 'NumberConversionSoapBinding ', 'NumberToWords '),
(12, 12, 25, 'http://www.webservicex.com/globalweather.asmx?WSDL ', 'GlobalWeatherSoap ', 'GetWeather ');

-- --------------------------------------------------------

--
-- Table structure for table `unicodeinfo`
--

CREATE TABLE IF NOT EXISTS `unicodeinfo` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(21) COLLATE utf8_unicode_ci NOT NULL,
  `Language` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `Message` varchar(150) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=8 ;

--
-- Dumping data for table `unicodeinfo`
--

INSERT INTO `unicodeinfo` (`id`, `UserName`, `Language`, `Message`) VALUES
(1, 'Yash', 'Hebrew', 'יאש'),
(2, 'Yash', 'Heb', 'יאש'),
(3, 'Yash_777', 'Telugu', '%D7%99%D7%90%D7%A9'),
(4, 'Yash_777', 'Telugu', 'יאש'),

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
