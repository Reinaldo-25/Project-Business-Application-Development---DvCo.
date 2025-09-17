-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 11 Jun 2024 pada 09.25
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dvco`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `cart`
--

CREATE TABLE `cart` (
  `UserID` char(5) NOT NULL,
  `DonutID` char(5) NOT NULL,
  `Quantity` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `cart`
--

INSERT INTO `cart` (`UserID`, `DonutID`, `Quantity`) VALUES
('US001', 'DN001', 1),
('US001', 'DN002', 4),
('US001', 'DN004', 4),
('US002', 'DN002', 2),
('US002', 'DN003', 3),
('US003', 'DN002', 2),
('US003', 'DN003', 3),
('US004', 'DN001', 1),
('US004', 'DN004', 4),
('US005', 'DN005', 5);

-- --------------------------------------------------------

--
-- Struktur dari tabel `msdonut`
--

CREATE TABLE `msdonut` (
  `DonutID` char(5) NOT NULL,
  `DonutName` varchar(50) NOT NULL,
  `DonutDescription` varchar(50) NOT NULL,
  `DonutPrice` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `msdonut`
--

INSERT INTO `msdonut` (`DonutID`, `DonutName`, `DonutDescription`, `DonutPrice`) VALUES
('DN001', 'Chocolate Donut', 'Donuts with chocolate sprinkles', 10000),
('DN002', 'Strawberry Donut', 'Donuts with strawberry jam', 12000),
('DN003', 'Cheese Donut', 'Donuts with cheese topping', 15000),
('DN004', 'Peanut Donut', 'Donuts sprinkled with nuts', 13000),
('DN005', 'Vanilla Donut', 'Donuts with vanilla flavor', 11000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `msuser`
--

CREATE TABLE `msuser` (
  `UserID` char(5) NOT NULL,
  `Username` varchar(15) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Age` int(10) NOT NULL,
  `Gender` varchar(10) NOT NULL,
  `Country` varchar(50) NOT NULL,
  `PhoneNumber` varchar(15) NOT NULL,
  `Role` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `msuser`
--

INSERT INTO `msuser` (`UserID`, `Username`, `Email`, `Password`, `Age`, `Gender`, `Country`, `PhoneNumber`, `Role`) VALUES
('US001', 'admin', 'admin@gmail.com', 'admin123', 25, 'Male', 'Indonesia', '081234567890', 'Admin'),
('US002', 'david123', 'david123@gmail.com', 'david123', 18, 'Male', 'Indonesia', '081209876543', 'User'),
('US003', 'christian123', 'christian123@gmail.com', 'christian123', 28, 'Male', 'Singapore', '081234098765', 'User'),
('US004', 'chrizzto', 'chrizzto@gmail.com', 'chrizzto123', 22, 'Male', 'Malaysia', '081234560987', 'User'),
('US005', 'Wati', 'wati@gmail.com', 'wati1234', 35, 'Female', 'Singapore', '081234567980', 'User');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transactiondetail`
--

CREATE TABLE `transactiondetail` (
  `TransactionID` char(5) NOT NULL,
  `DonutID` char(5) NOT NULL,
  `Quantity` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `transactiondetail`
--

INSERT INTO `transactiondetail` (`TransactionID`, `DonutID`, `Quantity`) VALUES
('TR001', 'DN001', 2),
('TR002', 'DN002', 3),
('TR003', 'DN003', 1),
('TR004', 'DN004', 5),
('TR005', 'DN005', 4),
('TR006', 'DN001', 2),
('TR007', 'DN003', 4),
('TR008', 'DN005', 1),
('TR009', 'DN002', 3),
('TR010', 'DN004', 5);

-- --------------------------------------------------------

--
-- Struktur dari tabel `transactionheader`
--

CREATE TABLE `transactionheader` (
  `TransactionID` char(5) NOT NULL,
  `UserID` char(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `transactionheader`
--

INSERT INTO `transactionheader` (`TransactionID`, `UserID`) VALUES
('TR001', 'US001'),
('TR006', 'US001'),
('TR002', 'US002'),
('TR007', 'US002'),
('TR003', 'US003'),
('TR010', 'US003'),
('TR004', 'US004'),
('TR009', 'US004'),
('TR005', 'US005'),
('TR008', 'US005');
--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`UserID`,`DonutID`),
  ADD KEY `DonutID` (`DonutID`);

--
-- Indeks untuk tabel `msdonut`
--
ALTER TABLE `msdonut`
  ADD PRIMARY KEY (`DonutID`);

--
-- Indeks untuk tabel `msuser`
--
ALTER TABLE `msuser`
  ADD PRIMARY KEY (`UserID`);

--
-- Indeks untuk tabel `transactiondetail`
--
ALTER TABLE `transactiondetail`
  ADD PRIMARY KEY (`TransactionID`,`DonutID`),
  ADD KEY `DonutID` (`DonutID`);

--
-- Indeks untuk tabel `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD PRIMARY KEY (`TransactionID`),
  ADD KEY `UserID` (`UserID`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--	
-- Ketidakleluasaan untuk tabel `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `msuser` (`UserID`),
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`DonutID`) REFERENCES `msdonut` (`DonutID`);

--
-- Ketidakleluasaan untuk tabel `transactiondetail`
--
ALTER TABLE `transactiondetail`
  ADD CONSTRAINT `transactiondetail_ibfk_1` FOREIGN KEY (`TransactionID`) REFERENCES `transactionheader` (`TransactionID`),
  ADD CONSTRAINT `transactiondetail_ibfk_2` FOREIGN KEY (`DonutID`) REFERENCES `msdonut` (`DonutID`);

--
-- Ketidakleluasaan untuk tabel `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD CONSTRAINT `transactionheader_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `msuser` (`UserID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
