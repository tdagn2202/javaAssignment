CREATE DATABASE SuperMarket_POS;
USE SuperMarket_POS;

CREATE TABLE `Discount` (
        `discountId` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        `categoryId` INT NOT NULL,
        `discountPercentage` DOUBLE NOT NULL,
        FOREIGN KEY (`categoryId`) REFERENCES `category`(`categoryId`)
);

CREATE TABLE `category` (
        `categoryId` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        `categoryName` NVARCHAR(255) NOT NULL
);

CREATE TABLE `Customer` (
        `customerPhoneNumber` NVARCHAR(255) NOT NULL,
        `customerName` NVARCHAR(255) NOT NULL,
        `points` BIGINT NOT NULL,
        PRIMARY KEY (`customerPhoneNumber`)
);

CREATE TABLE `productDetails` (
      `productBarcode` NVARCHAR(255) NOT NULL,
      `productName` NVARCHAR(255) NOT NULL,
      `productUnit` NVARCHAR(255) NOT NULL,
      `productSupplier` NVARCHAR(255) NOT NULL,
      `productPrice` DECIMAL(10, 2) NOT NULL,
      `productImg` NVARCHAR(255),
      `categoryID` INT NOT NULL,
      PRIMARY KEY (`productBarcode`),
      FOREIGN KEY (`categoryID`) REFERENCES `category`(`categoryId`)
);

CREATE TABLE `Transaction` (
       `transactionId` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
       `customerPhoneNumber` NVARCHAR(255) NOT NULL,
       `totalAmount` DECIMAL(10, 2) NOT NULL,
       `pointEarned` INT NOT NULL,
       `pointUsed` INT NOT NULL,
       FOREIGN KEY (`customerPhoneNumber`) REFERENCES `Customer`(`customerPhoneNumber`)
);

CREATE TABLE `Bill` (
        `billID` VARCHAR(50) NOT NULL,
        `productBarcode` NVARCHAR(255) NOT NULL,
        `billDate` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `billQuantity` INT NOT NULL,
        `billTotalAmount` DECIMAL(10, 2) NOT NULL,
        PRIMARY KEY (`billID`),
        FOREIGN KEY (`productBarcode`) REFERENCES `productDetails`(`productBarcode`)
);

CREATE TABLE `Warehouse` (
     `inventoryId` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
     `productBarcode` NVARCHAR(255) NOT NULL,
     `stock` BIGINT NOT NULL,
     `lastUpdate` DATETIME NOT NULL,
     FOREIGN KEY (`productBarcode`) REFERENCES `productDetails`(`productBarcode`)
);

CREATE TABLE `Users` (
    `uid` varchar(50),
    'pwd' varchar(50)
);
