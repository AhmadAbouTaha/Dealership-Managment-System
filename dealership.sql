-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 03, 2025 at 06:28 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dealership`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `CID` int(11) NOT NULL,
  `Cname` varchar(20) DEFAULT NULL,
  `Anumber` int(11) DEFAULT NULL,
  `Street` varchar(20) DEFAULT NULL,
  `City` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`CID`, `Cname`, `Anumber`, `Street`, `City`) VALUES
(1001, 'John Doe', 123, 'Elm Street', 'Beirut'),
(1002, 'Jane Smith', 456, 'Pine Street', 'Jounieh'),
(1003, 'Ali Hassan', 789, 'Cedar Street', 'Tripoli'),
(1004, 'Maya Khalil', 101, 'Palm Street', 'Saida'),
(1005, 'Rami Sleiman', 202, 'Oak Avenue', 'Beirut');

-- --------------------------------------------------------

--
-- Table structure for table `maintenance`
--

CREATE TABLE `maintenance` (
  `Mnumber` int(11) NOT NULL,
  `Acost` varchar(80) DEFAULT NULL,
  `Performed` varchar(20) DEFAULT NULL,
  `Servicedate` date DEFAULT NULL,
  `VIN` varchar(18) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `maintenance`
--

INSERT INTO `maintenance` (`Mnumber`, `Acost`, `Performed`, `Servicedate`, `VIN`) VALUES
(1, '300', 'Oil Change', '2025-04-22', '2G4WD582661234567'),
(2, '450', 'Brake Repair', '2025-04-23', '1HGCM82633A123456'),
(3, '250', 'Tire Change', '2025-04-24', '3FAHP0HA6AR123456'),
(4, '500', 'Full Service', '2025-04-25', '5N1AT2MT9LC123456'),
(5, '350', 'Battery Replacement', '2025-04-26', '2T1BURHE5JC123456');

-- --------------------------------------------------------

--
-- Table structure for table `salesperson`
--

CREATE TABLE `salesperson` (
  `SID` int(11) NOT NULL,
  `Sname` varchar(20) DEFAULT NULL,
  `Phonenumber` varchar(20) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `CID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `salesperson`
--

INSERT INTO `salesperson` (`SID`, `Sname`, `Phonenumber`, `Email`, `CID`) VALUES
(5001, 'Mike Johnson', '70123456', 'mike@example.com', 1001),
(5002, 'Sara Lee', '70112233', 'sara@example.com', 1002),
(5003, 'George Hanna', '70114567', 'george@example.com', 1003),
(5004, 'Noura Fares', '70119988', 'noura@example.com', 1004),
(5005, 'Karim Mansour', '70118877', 'karim@example.com', 1005);

-- --------------------------------------------------------

--
-- Table structure for table `salestransaction`
--

CREATE TABLE `salestransaction` (
  `DateOfSale` date NOT NULL,
  `Saleprice` varchar(80) DEFAULT NULL,
  `Discount` varchar(6) DEFAULT NULL,
  `Cash` varchar(80) DEFAULT NULL,
  `Checkk` varchar(80) DEFAULT NULL,
  `VIN` varchar(18) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `salestransaction`
--

INSERT INTO `salestransaction` (`DateOfSale`, `Saleprice`, `Discount`, `Cash`, `Checkk`, `VIN`) VALUES
('2025-04-20', '25000', '0', '25000', '0', '2G4WD582661234567'),
('2025-04-21', '18000', '500', '17500', '500', '1HGCM82633A123456'),
('2025-04-24', '27000', '1000', '26000', '1000', '3FAHP0HA6AR123456'),
('2025-04-25', '22000', '0', '22000', '0', '5N1AT2MT9LC123456'),
('2025-04-26', '30000', '1500', '28500', '1500', '2T1BURHE5JC123456');

-- --------------------------------------------------------

--
-- Table structure for table `vehicle`
--

CREATE TABLE `vehicle` (
  `VIN` varchar(18) NOT NULL,
  `Conndition` varchar(20) DEFAULT NULL,
  `Brand` varchar(20) DEFAULT NULL,
  `Color` varchar(20) DEFAULT NULL,
  `Year` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vehicle`
--

INSERT INTO `vehicle` (`VIN`, `Conndition`, `Brand`, `Color`, `Year`) VALUES
('1HGCM82633A123456', 'Used', 'Honda', 'Black', 2022),
('2G3244FX3423', 'Brand New', 'Mercedes', 'Black', 2022),
('2G4WD582661234567', 'New', 'Toyota', 'White', 2023),
('2T1BURHE5JC123456', 'New', 'Toyota', 'Red', 2025),
('3FAHP0HA6AR123456', 'New', 'Ford', 'Blue', 2023),
('5N1AT2MT9LC123456', 'Used', 'Nissan', 'Gray', 2022);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`CID`);

--
-- Indexes for table `maintenance`
--
ALTER TABLE `maintenance`
  ADD PRIMARY KEY (`Mnumber`),
  ADD KEY `VIN` (`VIN`);

--
-- Indexes for table `salesperson`
--
ALTER TABLE `salesperson`
  ADD PRIMARY KEY (`SID`),
  ADD KEY `CID` (`CID`);

--
-- Indexes for table `salestransaction`
--
ALTER TABLE `salestransaction`
  ADD PRIMARY KEY (`DateOfSale`),
  ADD KEY `VIN` (`VIN`);

--
-- Indexes for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`VIN`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `maintenance`
--
ALTER TABLE `maintenance`
  ADD CONSTRAINT `maintenance_ibfk_1` FOREIGN KEY (`VIN`) REFERENCES `vehicle` (`VIN`) ON DELETE CASCADE;

--
-- Constraints for table `salesperson`
--
ALTER TABLE `salesperson`
  ADD CONSTRAINT `salesperson_ibfk_1` FOREIGN KEY (`CID`) REFERENCES `customer` (`CID`) ON DELETE CASCADE;

--
-- Constraints for table `salestransaction`
--
ALTER TABLE `salestransaction`
  ADD CONSTRAINT `salestransaction_ibfk_1` FOREIGN KEY (`VIN`) REFERENCES `vehicle` (`VIN`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
