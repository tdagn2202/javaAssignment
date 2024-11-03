create database QUANLY_KHOHANG;
use QUANLY_KHOHANG;

create table Category(
	CategoryID int primary key auto_increment,
    CategoryName nvarchar(100) not null
);
create table Products(
	ProductID nvarchar(100) primary key,
    ProductName nvarchar(100) not null,
    Unit nvarchar(50) not null,
    Price decimal(10,2) not null,
    CategoryID int,
    foreign key	(CategoryID) references Category(CategoryID)
);
create table Warehouse(
	ProductID nvarchar(100) primary key,
    ProductName nvarchar(100) not null,
    QuanlityIn int not null,
    QuanlityOut int not null,
	foreign key(ProductID) references Products(ProductID)
);