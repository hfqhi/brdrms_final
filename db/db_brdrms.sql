-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 24, 2026 at 07:44 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_brdrms`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_document_request`
--

CREATE TABLE `tbl_document_request` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `document_id` int(100) NOT NULL,
  `purpose` varchar(255) NOT NULL,
  `status` enum('PENDING','APPROVED','REJECTED','CANCELLED') NOT NULL DEFAULT 'PENDING',
  `request_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_document_request`
--

INSERT INTO `tbl_document_request` (`id`, `user_id`, `document_id`, `purpose`, `status`, `request_date`) VALUES
(20, 4, 1, 'to get', 'APPROVED', '2026-03-21 07:11:41'),
(21, 4, 2, 'to get', 'APPROVED', '2026-03-21 07:11:47'),
(22, 4, 3, 'to get', 'APPROVED', '2026-03-21 07:12:02'),
(27, 26, 3, 'to be married', 'APPROVED', '2026-03-21 17:19:40'),
(31, 28, 2, 'for DSWD', 'APPROVED', '2026-03-24 04:32:50');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_document_request_payment`
--

CREATE TABLE `tbl_document_request_payment` (
  `id` int(11) NOT NULL,
  `document_request_id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `receipt_number` char(20) NOT NULL,
  `admin_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_document_request_payment`
--

INSERT INTO `tbl_document_request_payment` (`id`, `document_request_id`, `amount`, `receipt_number`, `admin_id`) VALUES
(2, 20, 50, '1231231', 8),
(3, 21, 50, '2131231', 8),
(4, 27, 100, 'OR1002', 22),
(5, 22, 100, '02382', 22),
(7, 31, 100, 'OR1234567', 29);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_document_request_status`
--

CREATE TABLE `tbl_document_request_status` (
  `id` int(11) NOT NULL,
  `document_request_id` int(11) NOT NULL,
  `admin_id` int(11) NOT NULL,
  `remarks` varchar(200) NOT NULL,
  `date_processed` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_document_request_status`
--

INSERT INTO `tbl_document_request_status` (`id`, `document_request_id`, `admin_id`, `remarks`, `date_processed`) VALUES
(7, 20, 8, ' to get chairman', '2026-03-21 07:33:08'),
(8, 21, 8, 'APPROVED PAID', '2026-03-21 08:21:59'),
(9, 27, 22, 'cleared ', '2026-03-21 17:20:59'),
(10, 22, 22, 'good', '2026-03-21 17:21:58'),
(13, 31, 29, 'Paid', '2026-03-24 04:44:53');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_document_type`
--

CREATE TABLE `tbl_document_type` (
  `id` int(11) NOT NULL,
  `document_name` varchar(200) NOT NULL,
  `document_fee` double NOT NULL,
  `is_archived` tinyint(1) NOT NULL DEFAULT 0,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_document_type`
--

INSERT INTO `tbl_document_type` (`id`, `document_name`, `document_fee`, `is_archived`, `updated_at`) VALUES
(1, 'Barangay Clearance', 50, 0, '2026-03-19 06:19:34'),
(2, 'Certificate of Indigency', 50, 0, '2026-03-19 06:19:34'),
(3, 'Certificate of Residency', 50, 0, '2026-03-19 06:19:34');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_payment`
--

CREATE TABLE `tbl_payment` (
  `id` int(11) NOT NULL,
  `document_request_id` int(11) NOT NULL,
  `payment_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `payment_method` varchar(50) NOT NULL,
  `payment_amount` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_residents`
--

CREATE TABLE `tbl_residents` (
  `resident_id` int(11) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `middle_name` varchar(50) NOT NULL,
  `birthdate` varchar(20) NOT NULL,
  `gender` varchar(50) NOT NULL,
  `civil_status` varchar(50) NOT NULL,
  `contact_number` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `date_registered` timestamp NOT NULL DEFAULT current_timestamp(),
  `is_archived` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_residents`
--

INSERT INTO `tbl_residents` (`resident_id`, `first_name`, `last_name`, `middle_name`, `birthdate`, `gender`, `civil_status`, `contact_number`, `email`, `address`, `date_registered`, `is_archived`) VALUES
(17, 'Junnel', 'Ustaris', 'Beckman', '2001-12-31', 'Male', 'Married', '09216598342', 'ustarisj@email.com', 'Laguna', '2026-03-22 21:36:21', 0),
(18, 'Melissa', 'Abadiano', 'Halili', '1992-10-05', 'Female', 'Married', '09357721946', 'melissa@email.com', 'Ortigas', '2026-03-22 21:38:07', 1),
(19, 'Tommy', 'Sawyer', 'Abad', '1974-06-27', 'Male', 'Married', '09482159037', 'sawyert@email.com', 'Pasay', '2026-03-22 21:41:46', 1),
(20, 'John Michael', 'Reyes', 'Santos', '1998-04-24', 'male', 'Single', '09174837261', 'johnm.santos98@gmail.com', 'Calamba', '2026-03-22 21:45:38', 1),
(21, 'Maria Angela', 'Dela Torre', 'Cruz', '1994-12-31', 'Female', 'Single', '09216598342', 'ma.delatorre01@yahoo.com', 'Misamis Occidental', '2026-03-22 21:46:38', 0),
(22, 'Carlo Benjamin', 'Garcua', 'Mendoza', '1995-07-21', 'Male', 'Married', '09357721946', 'cb.mendoza95@gmail.com', 'Batangas City', '2026-03-22 21:48:12', 0),
(23, 'Anne Patricia', 'Flores', 'Villanueva', '2000-03-05', 'Female', 'Married', '09482159037', 'ap.villanueva00@outlook.com', 'Las Pi as City', '2026-03-22 21:49:50', 0),
(24, 'Mark Anthony', 'Bautista', 'Lopez', '1997-01-31', 'Male', 'Married', '09564381279', 'mabautista97@gmail.com', 'San Pablo City', '2026-03-22 21:50:36', 0),
(25, 'Jasmine Nicole', 'Ramos', 'Castillo', '2003-05-21', 'Female', 'Single', '09564381279', 'mabautista97@gmail.com', 'Makati', '2026-03-22 21:51:36', 0),
(26, 'Daniel Joseph', 'Navaroo', 'Aquino', '1993-12-27', 'Male', 'Married', '09752296734', 'dj.aquino94@yahoo.com', 'Tarlac City', '2026-03-22 21:53:05', 0),
(27, 'Joseph', 'Chan', 'Manila', '2025-12-05', 'Male', 'Single', '09083106983', 'joseph@email.com', 'Manila', '2026-03-24 04:49:13', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users`
--

CREATE TABLE `tbl_users` (
  `id` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(100) NOT NULL,
  `full_name` varchar(50) NOT NULL,
  `contact_number` varchar(11) NOT NULL,
  `address` varchar(30) NOT NULL,
  `role` enum('USER','ADMIN') NOT NULL DEFAULT 'USER',
  `is_archived` tinyint(1) NOT NULL DEFAULT 0,
  `status` enum('APPROVED','REJECTED','PENDING') DEFAULT NULL,
  `date_status` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_users`
--

INSERT INTO `tbl_users` (`id`, `username`, `password`, `full_name`, `contact_number`, `address`, `role`, `is_archived`, `status`, `date_status`) VALUES
(3, 'admin', 'admin123', 'Captain', '09864792821', 'Brgy Hall', 'ADMIN', 0, 'APPROVED', NULL),
(4, 'user', '$2a$10$Fq0UaIPwZQcLStghPdT5/efk6OT0zLPMCEXYUN22s1MYpP1bJv2oS', 'Johny Johny ', '09864792821', 'Uknown', 'USER', 0, 'APPROVED', '2026-03-21 06:36:21'),
(5, 'ron', '$2a$10$rRWw8fodpQkfYjtANOmS3.Shd7tzczUTz2U8BH7mAk0OkhquUJmey', 'ronnie', '23232323', 'navotas', 'USER', 0, 'APPROVED', '2026-03-21 05:47:31'),
(7, 'user1', 'user1', 'Bejie Macdon', '09123456789', 'Mandaluyong City', 'USER', 0, 'APPROVED', NULL),
(8, 'bejie', '$2a$10$Fq0UaIPwZQcLStghPdT5/efk6OT0zLPMCEXYUN22s1MYpP1bJv2oS', 'Bejie Macdon', '09123456789', 'Mandaluyong', 'ADMIN', 0, 'APPROVED', '2026-03-21 05:57:16'),
(9, 'bejie1', '$2a$10$Tvpjx39fixqGwUcAkYUCyO6JWmkt0WqiOZUyfQ0wBolakEyL4FuEC', 'bjie', '012312213', 'cavite', 'USER', 0, 'APPROVED', '2026-03-21 08:26:57'),
(11, 'paul', '$2a$10$yGwFBD4wfzY3yH6FfjY7A.8RO/ZB.OePbVHQx9ijp1qXCkjuEKHxm', 'paulhacutina', '09083106985', 'manila', 'USER', 0, 'REJECTED', '2026-03-21 17:00:43'),
(12, 'admin123', '$2a$10$HwX8/soRG80GecaOcuGkHeveWJpt30OZxPYCsDxWRQIIigd1Q.coq', 'paulh', '09083106984', 'calamba', 'USER', 0, 'APPROVED', '2026-03-21 16:57:53'),
(13, 'superadminjoysis', '$2a$10$sFyWSToTx15BJNn2PZs0SeS52TaDpEFfECf4K4B48pVJEXzdxRkGa', 'JOYSIS', '09083106985', 'Caloocan', 'ADMIN', 0, 'APPROVED', '2026-03-21 15:52:33'),
(14, 'admin1', '$2a$10$/ZiUVpcPZU9x.SQKxPBXOOkS18kp/jK7jUgs2PnRoxVSymioRi/na', 'admin1', '09083106983', 'manila', 'ADMIN', 0, 'APPROVED', '2026-03-21 15:51:01'),
(15, 'userjam', '$2a$10$hJCwIHXue5ukatnZ/wYZi.risbtSNiGBgmNV1cxkYTsdWCXeZrGmS', 'jamaicaB', '09083106984', 'calamba', 'USER', 0, 'APPROVED', NULL),
(16, 'userx', '$2a$10$E9Nkv216HKwJas3V9j/JDudEYCXbvo/3k14vgdegLgZAdrFHt6/di', 'userx', '09083106984', 'calamba', 'USER', 0, 'APPROVED', NULL),
(17, 'qwerty', '$2a$10$/vGN/Y9wt.8EBH9cMs01.urFqR9XbDtk49tk1w.EJGTlrF4w8AmnS', 'qwertt', '', '', 'USER', 0, 'APPROVED', NULL),
(18, 'manny', '$2a$10$MxppkRxLQgf0vqjgE7kGgOu4Srg8w1FsUiQHVHLKFeB1WlkBdofDm', 'manny pacquiao', '09083106984', 'calamba', 'USER', 0, 'APPROVED', NULL),
(20, 'test', '$2a$10$QmwBQ3KLupTy2mIGem6UEesdD0CJ6XzGG4xLLae2q4CtV60DLxLdy', 'testing', '09083106984', 'manila', 'USER', 0, 'APPROVED', NULL),
(21, 'testing', '$2a$10$.YcPJ9Ti.9KYDOB3.ceTfO/ARstyRdp8Ih9R/o1krpyCid3UnIyzq', 'testing', '09083106984', 'manila', 'USER', 0, 'APPROVED', NULL),
(22, 'superadmin22', '$2a$10$Rp7fJvJFSHLyOTOWt5gCTOQ6dFSomxqiWKPb28OQ7kFqN/FqOM2q2', 'admin2', '09083106983', 'calamba', 'ADMIN', 0, 'APPROVED', '2026-03-21 16:49:21'),
(23, 'testuser', '$2a$10$P6gtmOZr3Oem79fZt2tiHeXOFhouCU4w36in9.i0eF5ONawRNJ6/O', 'testuser', '09083106984', 'manila', 'USER', 0, 'APPROVED', NULL),
(24, 'testuser1', '$2a$10$83tLu8Lf1nNXFwUkqXyfSOi4ljWS.esSZNp5c0V.G1EG0bf3VW/gO', 'testuser', '09083106983', 'manila', 'USER', 0, 'APPROVED', '2026-03-21 16:56:18'),
(26, 'testing1', '$2a$10$/6Q.0MgXY03NyrZemvhsu.M6aTPn0wqfyUx12YX9kgieOq34jN0pS', '', '', '', 'USER', 0, 'APPROVED', '2026-03-21 17:11:07'),
(27, 'userpaul', '$2a$10$84QmLM/LuXUVNLU8zUPY7O4SNSZtCivEsjuv3zE2BeqbI3CDxF4p6', 'Paul Hacutina', '0908310698', '', 'USER', 0, 'REJECTED', '2026-03-24 04:19:54'),
(28, 'paulhacutina', '$2a$10$5PHDEA7W6OvHj8Wwr0F6r.Tn7iqdEZSTOkIlvcRkJxtHtcQkVKmxm', 'Paul hacutina', '09083106985', 'Manila', 'USER', 0, 'APPROVED', '2026-03-24 04:19:01'),
(29, 'superadmin123', '$2a$10$EOcGnTP85VsL2eciWKzTE.csXVzAHcv07xyoN8wWVCfkFvRrVk3Ly', 'Joysis', '09443106984', 'Caloocan', 'ADMIN', 0, 'APPROVED', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_document_request`
--
ALTER TABLE `tbl_document_request`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_tbldocument_request_tbl_document_type` (`document_id`),
  ADD KEY `FK_tbldocument_request_tblusers` (`user_id`);

--
-- Indexes for table `tbl_document_request_payment`
--
ALTER TABLE `tbl_document_request_payment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_tbldocument_request_tbl_payment` (`document_request_id`);

--
-- Indexes for table `tbl_document_request_status`
--
ALTER TABLE `tbl_document_request_status`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_tbl_document_request_tbl_document_request_status` (`document_request_id`);

--
-- Indexes for table `tbl_document_type`
--
ALTER TABLE `tbl_document_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_payment`
--
ALTER TABLE `tbl_payment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_tbl_document_request_tblpayment` (`document_request_id`);

--
-- Indexes for table `tbl_residents`
--
ALTER TABLE `tbl_residents`
  ADD PRIMARY KEY (`resident_id`);

--
-- Indexes for table `tbl_users`
--
ALTER TABLE `tbl_users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_document_request`
--
ALTER TABLE `tbl_document_request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `tbl_document_request_payment`
--
ALTER TABLE `tbl_document_request_payment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `tbl_document_request_status`
--
ALTER TABLE `tbl_document_request_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `tbl_document_type`
--
ALTER TABLE `tbl_document_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tbl_payment`
--
ALTER TABLE `tbl_payment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_residents`
--
ALTER TABLE `tbl_residents`
  MODIFY `resident_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `tbl_users`
--
ALTER TABLE `tbl_users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_document_request`
--
ALTER TABLE `tbl_document_request`
  ADD CONSTRAINT `FK_tbldocument_request_tbl_document_type` FOREIGN KEY (`document_id`) REFERENCES `tbl_document_type` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_tbldocument_request_tblusers` FOREIGN KEY (`user_id`) REFERENCES `tbl_users` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `tbl_document_request_payment`
--
ALTER TABLE `tbl_document_request_payment`
  ADD CONSTRAINT `FK_tbldocument_request_tbl_payment` FOREIGN KEY (`document_request_id`) REFERENCES `tbl_document_request` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `tbl_document_request_status`
--
ALTER TABLE `tbl_document_request_status`
  ADD CONSTRAINT `FK_tbl_document_request_tbl_document_request_status` FOREIGN KEY (`document_request_id`) REFERENCES `tbl_document_request` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `tbl_payment`
--
ALTER TABLE `tbl_payment`
  ADD CONSTRAINT `FK_tbl_document_request_tblpayment` FOREIGN KEY (`document_request_id`) REFERENCES `tbl_document_request` (`id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
