-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 09, 2024 at 05:49 AM
-- Server version: 8.0.39
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `supermarket_pos`
--

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

CREATE TABLE `bill` (
  `billId` int NOT NULL,
  `billDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `bill`
--

INSERT INTO `bill` (`billId`, `billDate`) VALUES
(1, '2024-11-09'),
(2, '2024-11-09');

-- --------------------------------------------------------

--
-- Table structure for table `bill_details`
--

CREATE TABLE `bill_details` (
  `Detail_ID` int NOT NULL,
  `billId` int DEFAULT NULL,
  `productBarcode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `detailQuantity` int NOT NULL,
  `detailAmount` decimal(9,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `bill_details`
--

INSERT INTO `bill_details` (`Detail_ID`, `billId`, `productBarcode`, `detailQuantity`, `detailAmount`) VALUES
(1, 1, '999', 1, 10000.00),
(2, 1, '123', 1, 100000.00),
(3, 1, '1', 1, 1.00),
(4, 2, '999', 1, 10000.00),
(5, 2, '1', 1, 1.00),
(6, 2, '123', 1, 100000.00);

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `categoryId` int NOT NULL,
  `categoryName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`categoryId`, `categoryName`) VALUES
(1, 'Đồ ăn'),
(2, 'Đồ uống'),
(3, 'Đồ đóng gói/ hộp'),
(4, 'Đồ tươi/ sống'),
(5, 'Đồ gia dụng'),
(6, 'Nhu yếu phẩm');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customerPhoneNumber` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `customerName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `points` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customerPhoneNumber`, `customerName`, `points`) VALUES
('0392271697', 'Nguyễn Thanh Ngân', 15000),
('0666666', 'Tên măc định 4', 0),
('0777777777', 'Tên mặc định 3', 0),
('0823974998', 'Trần Hải Đăng', 775);

-- --------------------------------------------------------

--
-- Table structure for table `discount`
--

CREATE TABLE `discount` (
  `discountId` varchar(20) NOT NULL,
  `categoryId` int NOT NULL,
  `discountPercentage` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `discount`
--

INSERT INTO `discount` (`discountId`, `categoryId`, `discountPercentage`) VALUES
('1', 1, 29),
('P01', 1, 25),
('P02', 1, 25),
('P03', 3, 25),
('P04', 3, 35),
('P05', 1, 11),
('P06', 4, 20),
('P09', 1, 15),
('P10', 5, 20);

-- --------------------------------------------------------

--
-- Table structure for table `productdetails`
--

CREATE TABLE `productdetails` (
  `productBarcode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `productName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `productUnit` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `productSupplier` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `productPrice` decimal(10,2) NOT NULL,
  `categoryID` int NOT NULL,
  `productImage` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `productdetails`
--

INSERT INTO `productdetails` (`productBarcode`, `productName`, `productUnit`, `productSupplier`, `productPrice`, `categoryID`, `productImage`) VALUES
('1', '1', '1', '1', 1.00, 1, '318783079_1503365453491923_2356383309814998612_n.jpg'),
('10', '10', '10', '10', 10.00, 1, '318783079_1503365453491923_2356383309814998612_n.jpg'),
('11', '11', '11', '11', 11.00, 1, '0e2514fdf008daa5407b28311d03d82b.jpg'),
('123', 'abc', 'acb', 'acb', 100000.00, 1, '0e73ff5a-7c7b-4f2c-a71c-8c4ddffd11eb.jpg'),
('1234', 'a', 'a', 'a', 100000.00, 1, '04f359b986bc40e219ad.jpg'),
('2', '2', '2', '2', 2.00, 2, 'Picture1.png'),
('3', '3', '3', '3', 3.00, 3, '318783079_1503365453491923_2356383309814998612_n.jpg'),
('4', '4', '4', '4', 4.00, 4, 'React.ico'),
('5', '5', '5', '5', 5.00, 5, '318783079_1503365453491923_2356383309814998612_n.jpg'),
('529876543000', 'a', 'Chai', 'Xmen', 100000.00, 1, '1053126.jpg'),
('5298765432108', 'Hàng thử nghiệm', 'Auto', 'Auto', 1000.00, 1, '...'),
('6', '6', '6', '6', 6.00, 6, '0e2514fdf008daa5407b28311d03d82b.jpg'),
('666', '1', '1', '1', 1.00, 1, 'duahau.png'),
('7', '6', '6', '6', 6.00, 6, '0e2514fdf008daa5407b28311d03d82b.jpg'),
('777', '1', '1', '1', 1.00, 1, 'kikicat.png'),
('8930987654328', 'Dầu gội X-Men Amazon Hunt dưỡng tóc và giảm ngứa hương nước hoa 150g', 'Chai', 'X-men', 49000.00, 6, 'xmen.png'),
('8931234568979', 'Nước tăng lực Sting dâu 330ml', 'Chai', 'CocaCola', 1000000.00, 1, 'sting.jpg'),
('8932345678908', 'Sữa tắm cho bé Johnson\'s chứa sữa và gạo 500ml', 'Chai', 'Jonhson', 60000.00, 6, 'jonhson.jpg'),
('8935678901231', 'Bánh trứng tươi chà bông Karo Richy túi 156g', 'Bịch', 'Richy', 20000.00, 2, 'karo.jpg'),
('8938765432106', 'Vỉ 4 viên pin AA Panasonic Manganese R6NT/4B-V', 'Vỉ', 'Panasonic', 245000.00, 6, 'pinpana.jpg'),
('9', '9', '9', '9', 9.00, 1, '123-3-e1656079791658.webp'),
('999', 'ADC', 'Person', 'idk', 10000.00, 1, '428679345_122122877330173407_810285915387112153_n.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `settings`
--

CREATE TABLE `settings` (
  `settingName` varchar(50) NOT NULL,
  `settingValue` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `settings`
--

INSERT INTO `settings` (`settingName`, `settingValue`) VALUES
('conversionRate', '0.5');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `transactionId` bigint UNSIGNED NOT NULL,
  `customerPhoneNumber` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `totalAmount` decimal(10,2) NOT NULL,
  `pointEarned` int NOT NULL,
  `pointUsed` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`transactionId`, `customerPhoneNumber`, `totalAmount`, `pointEarned`, `pointUsed`) VALUES
(1, '0823974998', 60000.00, 6000, 0);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `uid` varchar(50) DEFAULT NULL,
  `pwd` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`uid`, `pwd`) VALUES
('staff', 'staff'),
('admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `warehouse`
--

CREATE TABLE `warehouse` (
  `inventoryId` bigint UNSIGNED NOT NULL,
  `productBarcode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `stock` bigint NOT NULL,
  `lastUpdate` datetime NOT NULL,
  `quantityIn` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `warehousegeneral`
--

CREATE TABLE `warehousegeneral` (
  `warehouseId` varchar(50) DEFAULT NULL,
  `warehouseDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`billId`);

--
-- Indexes for table `bill_details`
--
ALTER TABLE `bill_details`
  ADD PRIMARY KEY (`Detail_ID`),
  ADD KEY `billId` (`billId`),
  ADD KEY `productBarcode` (`productBarcode`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`categoryId`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customerPhoneNumber`);

--
-- Indexes for table `discount`
--
ALTER TABLE `discount`
  ADD PRIMARY KEY (`discountId`),
  ADD KEY `categoryId` (`categoryId`);

--
-- Indexes for table `productdetails`
--
ALTER TABLE `productdetails`
  ADD PRIMARY KEY (`productBarcode`),
  ADD KEY `categoryID` (`categoryID`);

--
-- Indexes for table `settings`
--
ALTER TABLE `settings`
  ADD PRIMARY KEY (`settingName`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`transactionId`),
  ADD KEY `customerPhoneNumber` (`customerPhoneNumber`);

--
-- Indexes for table `warehouse`
--
ALTER TABLE `warehouse`
  ADD PRIMARY KEY (`inventoryId`),
  ADD KEY `productBarcode` (`productBarcode`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bill`
--
ALTER TABLE `bill`
  MODIFY `billId` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `bill_details`
--
ALTER TABLE `bill_details`
  MODIFY `Detail_ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `transactionId` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `warehouse`
--
ALTER TABLE `warehouse`
  MODIFY `inventoryId` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bill_details`
--
ALTER TABLE `bill_details`
  ADD CONSTRAINT `bill_details_ibfk_1` FOREIGN KEY (`billId`) REFERENCES `bill` (`billId`),
  ADD CONSTRAINT `bill_details_ibfk_2` FOREIGN KEY (`productBarcode`) REFERENCES `productdetails` (`productBarcode`);

--
-- Constraints for table `discount`
--
ALTER TABLE `discount`
  ADD CONSTRAINT `discount_ibfk_1` FOREIGN KEY (`categoryId`) REFERENCES `category` (`categoryId`);

--
-- Constraints for table `productdetails`
--
ALTER TABLE `productdetails`
  ADD CONSTRAINT `productdetails_ibfk_1` FOREIGN KEY (`categoryID`) REFERENCES `category` (`categoryId`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`customerPhoneNumber`) REFERENCES `customer` (`customerPhoneNumber`);

--
-- Constraints for table `warehouse`
--
ALTER TABLE `warehouse`
  ADD CONSTRAINT `warehouse_ibfk_1` FOREIGN KEY (`productBarcode`) REFERENCES `productdetails` (`productBarcode`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
