-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: project_android
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `Id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Id`),
  CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`Id`) REFERENCES `user` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (8);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Total` double DEFAULT NULL,
  `Active` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Payment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Id_fk_donhang` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `Id_fk_donhang` (`Id_fk_donhang`),
  CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`Id_fk_donhang`) REFERENCES `selling_order` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (14,139000,'Chua thanh toan','Tien mat','HD101'),(15,37800,'Chua thanh toan','Tien mat','HD102');
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Price` double DEFAULT NULL,
  `Quantity` int DEFAULT NULL,
  `Total` double DEFAULT NULL,
  `Id_fk_donhang` varchar(255) DEFAULT NULL,
  `Id_fk_dish` int DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `Id_fk_donhang` (`Id_fk_donhang`),
  KEY `Id_fk_dish` (`Id_fk_dish`),
  CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`Id_fk_donhang`) REFERENCES `selling_order` (`Id`),
  CONSTRAINT `order_detail_ibfk_2` FOREIGN KEY (`Id_fk_dish`) REFERENCES `dish` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (22,65000,2,130000,'HD101',48),(23,30000,3,90000,'HD102',1),(24,8000,2,16000,'HD102',4);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `Id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Id`),
  CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`Id`) REFERENCES `user` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1),(9),(10),(11),(16);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish`
--

DROP TABLE IF EXISTS `dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dish` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Price` double DEFAULT NULL,
  `Quantity` int DEFAULT NULL,
  `Describe_dish` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Id_fk` int DEFAULT NULL,
  `Name_fk` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `time_up` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `Name_fk` (`Name_fk`),
  KEY `Id_fk` (`Id_fk`),
  CONSTRAINT `dish_ibfk_1` FOREIGN KEY (`Name_fk`) REFERENCES `group_dish` (`Name`),
  CONSTRAINT `dish_ibfk_2` FOREIGN KEY (`Id_fk`) REFERENCES `restaurant` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish`
--

LOCK TABLES `dish` WRITE;
/*!40000 ALTER TABLE `dish` DISABLE KEYS */;
INSERT INTO `dish` VALUES (1,'Bánh Mì Thịt Xíu Mại',30000,34,'Bánh mì + xíu mại',2,'Ăn trưa','/images/banhmixiumai.jpg','2024-11-10 22:26:08'),(2,'Combo Sườn Cốt Lết Đặc Biệt',89000,100,'Sườn cốt lết + bì + chả + canh rong biển',17,'Ăn sáng','/images/suoncotlet.jpg','2024-11-25 10:33:01'),(3,'Combo Sườn Cây Đặc Biệt',99000,100,'Sườn cây + bì + chả + canh rong biển',17,'Bún','/images/suoncay.jpg','2024-11-25 22:33:07'),(4,'Bánh Mì Que Pate',8000,98,'Bánh mì que + pate',2,'Ăn sáng','/images/banhmique.jpg','2024-11-27 09:30:37'),(5,'Bánh Mì Que Đặt Biệt ',30000,100,'Bánh mì que + thịt nguội + chà bông + pate',2,'Ăn sáng','/images/quedacbiet.jpg','2024-11-27 09:31:35'),(6,'Bánh Mì Kẹp Xúc Xích Đức',45000,100,'Bánh mì + xúc xích Đức + phô mai',2,'Ăn trưa','/images/kepxx.jpg','2024-11-27 09:33:07'),(7,'Xúc Xích Đức',25000,100,'Xúc xích Đức',2,'Ăn trưa','/images/xucxich.jpg','2024-11-27 09:33:54'),(8,'Trà Chanh ',23000,100,'Trà + chanh',2,'Tráng miệng','/images/trachanh.jpg','2024-11-27 09:34:53'),(9,'Cơm Sườn, Chả',60000,100,'Cơm + sườn + chả + dưa leo',24,'Ăn sáng','/images/comsuoncha.jpg','2024-11-27 09:46:36'),(10,'Cơm Sườn, Trứng',60000,100,'Cơm + sườn + trứng + dưa leo',24,'Ăn sáng','/images/comsuontrung.jpg','2024-11-27 09:47:05'),(11,'Bún Thịt Nướng, Chả Giò Đặc Biệt',50000,100,'Bún + thịt nướng + chả giò siêu to',24,'Bún','/images/bunthitnuong.jpg','2024-11-27 09:47:29'),(12,'Cơm Sườn',50000,100,'Cơm + sườn + dưa leo',24,'Ăn sáng','/images/comsuon.jpg','2024-11-27 09:47:55'),(13,'Cơm Thịt Nướng Kim Tiền',45000,100,'Cơm + thịt nướng kim tiền + dưa leo',24,'Ăn sáng','/images/kimtien.jpg','2024-11-27 09:48:20'),(14,'Bún Thịt Nướng',40000,100,'Bún + thịt nướng + chả giò nhỏ',24,'Bún','/images/bunthitnuong1.jpg','2024-11-27 09:48:47'),(15,'Cơm Sườn, Bì, Chả',70000,100,'Cơm + sườn + chả + trứng + dưa leo',24,'Ăn sáng','/images/suonbicha.jpg','2024-11-27 09:49:09'),(16,'Hủ Tiếu Nam vang',67000,100,'Hủ tiếu + thịt + tôm + trứng cút',25,'Hủ tiếu','/images/namvang.jpg','2024-11-27 09:57:36'),(17,'Hủ Tiếu Sườn Sụn',67000,100,'Hủ tiếu + sườn sụn',25,'Hủ tiếu','/images/suonsun.jpg','2024-11-27 09:58:18'),(18,'Hủ Tiếu Bò Viên',67000,100,'Hủ tiếu + bò viên',25,'Hủ tiếu','/images/bovien.jpg','2024-11-27 09:58:40'),(19,'Mì Gói Thập Cẩm',67000,100,'Mì gói + thịt bằm + trứng cút + thịt',25,'Mì','/images/mithapcam.jpg','2024-11-27 09:59:17'),(20,'Mì Tươi Nam Vang',67000,100,'Mì gói + thịt + tôm + trứng cút',25,'Hủ tiếu','/images/mituoi.jpg','2024-11-27 09:59:38'),(21,'Hoành Thánh',67000,100,'Chỉ có nước lèo thịt bằm và hoành thánh',25,'Ăn sáng','/images/hoanhthanh.jpg','2024-11-27 09:59:57'),(22,'Hủ Tiếu Nạt ',60000,100,'Hủ tiếu + thịt nạt heo',25,'Hủ tiếu','/images/hutieunat.jpg','2024-11-27 10:00:17'),(23,'Bánh Tráng Trộn Hột Gà',29000,100,'Bánh tráng + hột gà',26,'Tráng miệng','/images/banhtranghotga.jpg','2024-11-27 10:10:14'),(24,'Bánh Tráng Chấm',26000,100,'Bánh tráng + nước chấm',26,'Tráng miệng','/images/banhtrangcham.jpg','2024-11-27 10:10:36'),(25,'Bánh Tráng Trộn Trứng Cút',29000,100,'Bánh tráng + trứng cút',26,'Tráng miệng','/images/banhtrangtrungcut.jpg','2024-11-27 10:10:55'),(26,'Gỏi Xoài Khô Bò',33000,100,'Gỏi xoài + khô bò',26,'Tráng miệng','/images/goixoaikhobo.jpg','2024-11-27 10:11:19'),(27,'Xoài Lắc',27000,100,'Xoài lắc',26,'Tráng miệng','/images/xoailac.jpg','2024-11-27 10:11:42'),(28,'Gỏi Xoài Khô Mực',33000,100,'Gỏi xoài + khô mực',26,'Tráng miệng','/images/goixoaikhomuc.jpg','2024-11-27 10:12:01'),(29,'Bánh Tráng Cuộn Bơ',14000,100,'',26,'Tráng miệng','/images/banhtrangcuonbo.jpg','2024-11-27 10:12:19'),(30,'Mì Kim Chi Đặc Biệt',65000,100,'Mì + bò cuộn + cá viên + tôm + xúc xích + cá hồi',27,'Mì','/images/micaydacbiet.jpg','2024-11-27 10:18:45'),(31,'Mì Kim Chi Hải Sản',59000,100,'Mì + tôm + bạch tuột',27,'Mì','/images/micayhaisan.jpg','2024-11-27 10:19:03'),(32,'Mì Kim Chi Bò Mỹ',59000,100,'Mì + bò cuộn',27,'Mì','/images/mibomy.jpg','2024-11-27 10:19:20'),(33,'Mì Kim Chi Cá Hồi',59000,100,'Mì + cá hồi ',27,'Mì','/images/cahoi.jpg','2024-11-27 10:19:40'),(34,'Mì Lẩu thái Bò Mỹ ',59000,100,'Mì + bò cuộn',27,'Mì','/images/lauthaibomy.jpg','2024-11-27 10:20:04'),(35,'Mì Lẩu Thái Hải Sản',59000,100,'Mì + tôm + bạch tuột',27,'Mì','/images/lauthaihaisan.jpg','2024-11-27 10:20:20'),(36,'Mì Kim Chi Đùi Gà',59000,100,'Mì + đùi gà',27,'Mì','/images/duiga.jpg','2024-11-27 10:20:39'),(37,'Mì Lẩu Thái Đùi Gà',59000,100,'Mì + đùi gà',27,'Mì','/images/duiga1.jpg','2024-11-27 10:21:03'),(38,'Phở Bò',66000,99,'Phở bò siêu hấp dẫn',28,'Ăn trưa','/images/phobo.jpg','2024-11-27 10:29:35'),(39,'Phở Tái',66000,98,'Đảm bảo ngon từ lần ăn đầu tiên',28,'Ăn trưa','/images/photai.jpg','2024-11-27 10:30:04'),(40,'Miến Gà',66000,100,'Ngon tuyệt vời',28,'Ăn trưa','/images/mienga.jpg','2024-11-27 10:30:20'),(41,'Miến Bò',66000,99,'Ngon tuyệt vời',28,'Ăn trưa','/images/mienbo.jpg','2024-11-27 10:30:43'),(42,'Phở Tái Gầu',80000,100,'Siêu hấp dẫn',28,'Ăn trưa','/images/photaigau.jpg','2024-11-27 10:31:03'),(43,'Phở Tái Bò Viên',80000,100,'Siêu ngon',28,'Ăn trưa','/images/phobovien.jpg','2024-11-27 10:31:29'),(44,'Phở Tái Gân',80000,100,'Siêu ngon',28,'Ăn trưa','/images/taigan.jpg',NULL),(45,'Cơm Sườn Cây',75000,100,'Sườn sụn giòn',17,'Ăn sáng','/images/suoncay1.jpg','2024-11-27 10:45:11'),(46,'Cơm Sườn Cây - Chả',85000,100,'Sườn sụn giòn + chả vàng ươm',17,'Ăn trưa','/images/suoncaycha.jpg','2024-11-27 10:45:30'),(47,'Cơm Sườn Cây - Bì Chả',95000,100,'Sườn sụn giòn + bì thịt chiên + chả vàng ươm',17,'Ăn sáng','/images/caybicha.jpg','2024-11-27 10:45:52'),(48,'Cơm Sườn Cốt Lết',65000,98,'Sườn màu caremeil + vừa nạc vừa mỡ',17,'Ăn sáng','/images/suoncotlet1.jpg','2024-11-27 10:46:24'),(49,'abc',1000,20,'aaaa',2,'Ăn sáng','/images/1732690101482_top-15-hinh-anh-mon-an-ngon-viet-nam-khien-ban-khong-the-roi-mat-3.jpg','2024-11-27 13:48:22');
/*!40000 ALTER TABLE `dish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `selling_order`
--

DROP TABLE IF EXISTS `selling_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `selling_order` (
  `Id` varchar(255) NOT NULL,
  `Quantity` int DEFAULT NULL,
  `Total_dish` double DEFAULT NULL,
  `Delivery_fee` double DEFAULT NULL,
  `VoucherS` int DEFAULT NULL,
  `VoucherR` int DEFAULT NULL,
  `Total` double DEFAULT NULL,
  `Note` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Process` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Send_order` datetime DEFAULT NULL,
  `Receive_order` datetime DEFAULT NULL,
  `Id_fk_customer` int DEFAULT NULL,
  `Id_fk_shiper` int DEFAULT NULL,
  `Id_fk_restaurant` int DEFAULT NULL,
  `Id_voucher_system` int DEFAULT NULL,
  `Id_voucher_restaurant` int DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `Id_fk_customer` (`Id_fk_customer`),
  KEY `Id_fk_restaurant` (`Id_fk_restaurant`),
  KEY `Id_fk_shiper` (`Id_fk_shiper`),
  KEY `FK_system` (`Id_voucher_system`),
  KEY `FK_restaurant` (`Id_voucher_restaurant`),
  CONSTRAINT `selling_order_ibfk_1` FOREIGN KEY (`Id_fk_customer`) REFERENCES `customer` (`Id`),
  CONSTRAINT `selling_order_ibfk_2` FOREIGN KEY (`Id_fk_restaurant`) REFERENCES `restaurant` (`Id`),
  CONSTRAINT `selling_order_ibfk_3` FOREIGN KEY (`Id_fk_shiper`) REFERENCES `shiper` (`Id`),
  CONSTRAINT `FK_restaurant` FOREIGN KEY (`Id_voucher_restaurant`) REFERENCES `voucher_restaurant` (`Id`),
  CONSTRAINT `FK_system` FOREIGN KEY (`Id_voucher_system`) REFERENCES `voucher_system` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `selling_order`
--

LOCK TABLES `selling_order` WRITE;
/*!40000 ALTER TABLE `selling_order` DISABLE KEYS */;
INSERT INTO `selling_order` VALUES ('HD101',2,130000,0,30000,0,139000,NULL,'Dang gui den nha hang','2024-11-27 13:37:12',NULL,1,NULL,17,4,NULL,37.4220936,-122.083922),('HD102',5,106000,0,100000,0,37800,NULL,'Xong','2024-11-27 13:43:13',NULL,1,29,2,5,NULL,37.4220936,-122.083922);
/*!40000 ALTER TABLE `selling_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `Id` varchar(255) NOT NULL,
  `RatingShiper` double DEFAULT NULL,
  `RatingRestaurant` double DEFAULT NULL,
  `Id_fk_shiper` int DEFAULT NULL,
  `Id_fk_restaurant` int DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `Id_fk_restaurant` (`Id_fk_restaurant`),
  KEY `Id_fk_shiper` (`Id_fk_shiper`),
  CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`Id`) REFERENCES `selling_order` (`Id`),
  CONSTRAINT `feedback_ibfk_2` FOREIGN KEY (`Id_fk_restaurant`) REFERENCES `restaurant` (`Id`),
  CONSTRAINT `feedback_ibfk_3` FOREIGN KEY (`Id_fk_shiper`) REFERENCES `shiper` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES ('HD102',4,4.5,29,2);
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `get_voucher_restaurant`
--

DROP TABLE IF EXISTS `get_voucher_restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `get_voucher_restaurant` (
  `Id_customer` int NOT NULL,
  `Id_voucher_restaurant` int NOT NULL,
  `Quantity` int DEFAULT NULL,
  `History` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`Id_customer`,`Id_voucher_restaurant`),
  KEY `Id_voucher_restaurant` (`Id_voucher_restaurant`),
  CONSTRAINT `get_voucher_restaurant_ibfk_1` FOREIGN KEY (`Id_customer`) REFERENCES `customer` (`Id`),
  CONSTRAINT `get_voucher_restaurant_ibfk_2` FOREIGN KEY (`Id_voucher_restaurant`) REFERENCES `voucher_restaurant` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `get_voucher_restaurant`
--

LOCK TABLES `get_voucher_restaurant` WRITE;
/*!40000 ALTER TABLE `get_voucher_restaurant` DISABLE KEYS */;
INSERT INTO `get_voucher_restaurant` VALUES (1,5,1,'Valid'),(1,6,1,'Valid'),(1,9,1,'Valid'),(1,11,1,'Valid'),(1,14,1,'Valid');
/*!40000 ALTER TABLE `get_voucher_restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `get_voucher_system`
--

DROP TABLE IF EXISTS `get_voucher_system`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `get_voucher_system` (
  `Id_customer` int NOT NULL,
  `Id_voucher_system` int NOT NULL,
  `Quantity` int DEFAULT NULL,
  `History` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`Id_customer`,`Id_voucher_system`),
  KEY `Id_voucher_system` (`Id_voucher_system`),
  CONSTRAINT `get_voucher_system_ibfk_1` FOREIGN KEY (`Id_customer`) REFERENCES `customer` (`Id`),
  CONSTRAINT `get_voucher_system_ibfk_2` FOREIGN KEY (`Id_voucher_system`) REFERENCES `voucher_system` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `get_voucher_system`
--

LOCK TABLES `get_voucher_system` WRITE;
/*!40000 ALTER TABLE `get_voucher_system` DISABLE KEYS */;
INSERT INTO `get_voucher_system` VALUES (1,4,0,'Valid'),(1,5,0,'Valid');
/*!40000 ALTER TABLE `get_voucher_system` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_dish`
--

DROP TABLE IF EXISTS `group_dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_dish` (
  `Name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_dish`
--

LOCK TABLES `group_dish` WRITE;
/*!40000 ALTER TABLE `group_dish` DISABLE KEYS */;
INSERT INTO `group_dish` VALUES ('Ăn sáng','/images/banhmi.png'),('Ăn trưa','/images/pho.png'),('Bún','/images/bun.png'),('est','/images/top-15-hinh-anh-mon-an-ngon-viet-nam-khien-ban-khong-the-roi-mat-3.jpg'),('Hủ tiếu','/images/hutieu.png'),('Mì','/images/mi.png'),('Tráng miệng','/images/banh.png');
/*!40000 ALTER TABLE `group_dish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `FullName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Gender` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Birthday` date DEFAULT NULL,
  `Address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `PhoneNumber` varchar(15) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `UserName` varchar(255) DEFAULT NULL,
  `PassW` varchar(255) DEFAULT NULL,
  `Active` int DEFAULT NULL,
  `Id_role` int DEFAULT NULL,
  `Otp` varchar(10) DEFAULT NULL,
  `Latitude` double DEFAULT NULL,
  `Longitude` double DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `PhoneNumber` (`PhoneNumber`),
  KEY `Id_role` (`Id_role`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`Id_role`) REFERENCES `role` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'ABC','Nữ','2024-11-29','Bình Thạnh, Hồ Chí Minh ANC','123','52200077@student.tdtu.edu.vn',NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,4,NULL,37.4219983,-122.084,'/images/avatar1.jpg'),(2,'MinhLuan',NULL,NULL,'Quận 3, Hồ Chí Minh','000',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,2,'928752',37.4219983,-122.084,'/images/avatar2.jpg'),(6,'Nguyen Minh Luan',NULL,NULL,'Quận 2, Hồ Chí Minh','+32432422',NULL,NULL,'03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4',1,3,'749081',NULL,NULL,'/images/avatar3.jpg'),(7,'minhluan',NULL,NULL,'Quận 1, Hồ Chí Minh','+313213213',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',0,1,'568999',NULL,NULL,'/images/avatar4.jpg'),(8,'Minh Luan','Nam','2024-11-09','Quận 8, Hồ Chí Minh','1234',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,1,'981926',NULL,NULL,'/images/avatar5.jpg'),(9,'khang',NULL,NULL,'Quận Tân Bình, Hồ Chí Minh','0948523010',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,3,'708685',NULL,NULL,'/images/avatar6.jpg'),(10,'Trần Bình Khang','Nữ','2004-11-07','Quận Bình Tân, Hồ Chí Minh','113','52200115@student.tdtu.edu.vn',NULL,NULL,1,4,NULL,10.744428021700298,106.70175276696682,'/images/avatar7.jpg'),(11,'yrdhd',NULL,NULL,'Quận Gò Vấp, Hồ Chí Minh','0123',NULL,NULL,'0ffe1abd1a08215353c233d6e009613e95eec4253832a761af28ff37ac5a150c',1,3,'597199',NULL,NULL,'/images/avatar8.jpg'),(13,'luan',NULL,NULL,'Quận 7, Hồ Chí Minh','1235',NULL,NULL,'0ffe1abd1a08215353c233d6e009613e95eec4253832a761af28ff37ac5a150c',1,3,'914703',NULL,NULL,'/images/avatar14.jpg'),(16,'NCK',NULL,NULL,'Nhà Bè, Hồ Chí Minh','111',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,4,'323449',37.4220936,-122.083922,'/images/avatar13.jpg'),(17,'Com tam Sa Bi Chuong',NULL,NULL,'Hóc Môn, Hồ Chí Minh','8386',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,2,'936372',37.4220936,-122.083922,'/images/sabichuong.jpg'),(18,'Bao Sai Gon',NULL,NULL,'18, Lê Văn Lương, Tân Phong, Quận 7, Hồ Chí Minh ','3979',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,1,'249154',NULL,NULL,'/images/avatar9.jpg'),(19,'Tai Xe Dep Trai',NULL,NULL,'82, Huỳnh Tấn Phát, Bình Thuận, Quận 7, Hồ Chí Minh','2161',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,3,'176294',NULL,NULL,'/images/avatar11.jpg'),(20,'Cao Ky 1',NULL,NULL,'35, Nguyễn Văn Linh, Tân Thuận Tây, Quận 7, Hồ Chí Minh','9999',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,3,'077978',NULL,NULL,'/images/avatar12.jpg'),(22,'Binh Khang',NULL,NULL,'99, Nguyễn Tất Thành, Phường 13, Quận 1, Hồ Chí Minh','555',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,3,'948489',NULL,NULL,'/images/avatar16.jpg'),(23,'Misa',NULL,NULL,'2, Số 38, Phường 3, Quận 4, Hồ Chí Minh','2205',NULL,NULL,'0ffe1abd1a08215353c233d6e009613e95eec4253832a761af28ff37ac5a150c',1,3,'005829',NULL,NULL,'/images/avatar17.jpg'),(24,'Tan My',NULL,NULL,'18, Phường 4, Quận 8, Hồ Chí Minh','111111',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,2,'617552',37.4230936,-122.083922,'/images/comtam.jpg'),(25,'Hoa',NULL,NULL,'102, Cao Lỗ, Phường 4, Quận 8, Hồ Chí Minh','222222',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,2,'856422',37.5220936,-122.083922,'/images/hutieu.jpg'),(26,'Bong',NULL,NULL,'19, Phạm Hùng, Bình Hưng, Bình Chánh, Hồ Chí Minh','333333',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,2,'362077',37.427,-122.083922,'/images/banhtrang.jpg'),(27,'Seoul',NULL,NULL,'81, Hưng Phú, Phường 8, Quận 8, Hồ Chí Minh','444444',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,2,'763459',37.4120936,-122.083922,'/images/seoul.png'),(28,'Pho',NULL,NULL,'242, Trần Xuân Soạn, Tân Hưng, Quận 7, Hồ Chí Minh','555555',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,2,'217759',36.4220936,-122.083922,'/images/pho.jpg'),(29,'Nguyen Van A',NULL,NULL,NULL,'+15551234567',NULL,NULL,'0ffe1abd1a08215353c233d6e009613e95eec4253832a761af28ff37ac5a150c',1,3,'383962',NULL,NULL,'/images/avatar2.jpg');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Balance` double DEFAULT NULL,
  `Working` int DEFAULT NULL,
  `Rating` double DEFAULT NULL,
  PRIMARY KEY (`Id`),
  CONSTRAINT `restaurant_ibfk_1` FOREIGN KEY (`Id`) REFERENCES `user` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
INSERT INTO `restaurant` VALUES (2,'Bánh Mì Pate Cột Đèn Sài Gòn',NULL,NULL,4.5),(17,'Quán cơm FapTV',NULL,NULL,0),(24,'Cơm Thất Gia Truyền',NULL,NULL,0),(25,'Tiệm ăn hương quê',NULL,NULL,0),(26,'Quán ăn bình dân',NULL,NULL,0),(27,'Quán ăn Bé Tí',NULL,NULL,0),(28,'Quán bì nhẹ nhàng',NULL,NULL,0);
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `Id` int NOT NULL,
  `RoleName` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Admin'),(2,'Restaurant'),(3,'Shipper'),(4,'Customer');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shiper`
--

DROP TABLE IF EXISTS `shiper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shiper` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Balance` double DEFAULT NULL,
  `Working` int DEFAULT NULL,
  `Rating` double DEFAULT NULL,
  `bienso` varchar(20) DEFAULT NULL,
  `status_request` int DEFAULT NULL,
  PRIMARY KEY (`Id`),
  CONSTRAINT `shiper_ibfk_1` FOREIGN KEY (`Id`) REFERENCES `user` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shiper`
--

LOCK TABLES `shiper` WRITE;
/*!40000 ALTER TABLE `shiper` DISABLE KEYS */;
INSERT INTO `shiper` VALUES (6,9000010,NULL,2.5,NULL,NULL),(13,39000000,NULL,3.75,NULL,NULL),(19,NULL,NULL,0,NULL,NULL),(20,NULL,NULL,0,NULL,NULL),(22,0,NULL,0,NULL,NULL),(23,517000,NULL,0,NULL,1),(29,273540,NULL,4,NULL,1);
/*!40000 ALTER TABLE `shiper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voucher_restaurant`
--

DROP TABLE IF EXISTS `voucher_restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voucher_restaurant` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Quantity` int DEFAULT NULL,
  `Price` double DEFAULT NULL,
  `Expiry` datetime DEFAULT NULL,
  `Id_fk` int DEFAULT NULL,
  `Active` int DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `Id_fk` (`Id_fk`),
  CONSTRAINT `voucher_restaurant_ibfk_1` FOREIGN KEY (`Id_fk`) REFERENCES `restaurant` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucher_restaurant`
--

LOCK TABLES `voucher_restaurant` WRITE;
/*!40000 ALTER TABLE `voucher_restaurant` DISABLE KEYS */;
INSERT INTO `voucher_restaurant` VALUES (2,'Khao bạn mới',1,10000,'2024-11-27 10:33:00',17,1),(3,'Khao ngay 20K',9,20000,'2024-11-10 05:30:00',17,1),(4,'Miễn phí ship tối đa 50K',0,50000,'2024-11-28 14:31:00',17,1),(5,'Khao deal hời',8,50000,'2024-11-30 10:35:00',28,1),(6,'Phở - 20K',18,20000,'2024-12-16 05:35:00',28,1),(7,'Giảm đa tầng',9,30000,'2024-11-30 10:35:00',28,1),(8,'Seoul - Siêu giảm giá',9,50000,'2024-12-24 10:39:00',27,1),(9,'Seoul - Miễn phí ship tối đa 30K',18,30000,'2024-12-18 10:39:00',27,1),(10,'Seoul - Ưu đãi',14,20000,'2024-12-10 10:39:00',27,1),(11,'Giảm siêu sâu',8,30000,'2024-12-30 10:41:00',25,1),(12,'Siêu hấp dẫn',14,10000,'2024-12-30 10:42:00',25,1),(14,'Khai truong',9,15000,'2024-11-30 04:48:00',2,1);
/*!40000 ALTER TABLE `voucher_restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voucher_system`
--

DROP TABLE IF EXISTS `voucher_system`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voucher_system` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Quantity` int DEFAULT NULL,
  `Price` double DEFAULT NULL,
  `Expiry` datetime DEFAULT NULL,
  `Id_fk` int DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `Id_fk` (`Id_fk`),
  CONSTRAINT `voucher_system_ibfk_1` FOREIGN KEY (`Id_fk`) REFERENCES `admin` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucher_system`
--

LOCK TABLES `voucher_system` WRITE;
/*!40000 ALTER TABLE `voucher_system` DISABLE KEYS */;
INSERT INTO `voucher_system` VALUES (4,'Người dùng mới',9,30000,'2024-11-29 13:33:00',8),(5,'Ưu đãi khách hàng',29,100000,'2024-11-30 05:34:00',8),(6,'Mung ngay 20/11',10,20000,'2024-11-29 16:51:00',8);
/*!40000 ALTER TABLE `voucher_system` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'project_android'
--
/*!50003 DROP PROCEDURE IF EXISTS `AddMoreToCart` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddMoreToCart`(
    IN p_OrderID VARCHAR(50),
    IN p_MonAnID INT
)
BEGIN

    DECLARE v_PriceDish DOUBLE;
    DECLARE v_Quantity INT;
    DECLARE v_Total_donhang DOUBLE;


    SELECT Price INTO v_PriceDish FROM dish WHERE Id = p_MonAnID;
    
    UPDATE order_detail
    SET Quantity = Quantity + 1, Total = Total + v_PriceDish
    WHERE Id_fk_donhang = p_OrderID AND Id_fk_dish = p_MonAnID;


    SELECT sum(Quantity) INTO v_Quantity from order_detail
    WHERE Id_fk_donhang = p_OrderID
    GROUP BY Id_fk_donhang ;
    
    SELECT sum(Total) INTO v_Total_donhang from order_detail
    WHERE Id_fk_donhang = p_OrderID
    GROUP BY Id_fk_donhang ;

    

    UPDATE selling_order
        SET Quantity = v_Quantity,Total_dish = v_Total_donhang
        WHERE Id = p_OrderID;


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `AddToCart` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddToCart`(
    IN p_CustomerID INT,
    IN p_RestaurantID INT,
    IN p_MonAnID INT,
    IN p_SoLuong INT
)
BEGIN
    DECLARE v_OrderID VARCHAR(50);
    DECLARE v_OrderIDTemp VARCHAR(50);
    DECLARE v_RestaurantIDTemp INT;

    DECLARE v_Stt INT;
    DECLARE v_PriceDish DOUBLE;
    DECLARE v_Quantity INT;
    DECLARE v_Total_donhang DOUBLE;

    DECLARE v_Total DOUBLE;

    SELECT Price INTO v_PriceDish FROM dish WHERE Id = p_MonAnID;
    SET v_Total = p_SoLuong * v_PriceDish;
    
    IF (SELECT COUNT(*) FROM selling_order WHERE id_fk_customer = p_CustomerID AND Process = 'Gio hang') = 0 THEN
        SELECT Id INTO v_OrderIDTemp FROM selling_order WHERE id_fk_customer = p_CustomerID ORDER BY Id DESC LIMIT 1;
        
        IF v_OrderIDTemp IS NULL THEN
            SET v_OrderID = CONCAT('HD', CAST(p_CustomerID AS CHAR), '01');
        ELSE
            SET v_Stt = CAST(RIGHT(v_OrderIDTemp, 2) AS UNSIGNED);
            SET v_Stt = v_Stt + 1;

            IF v_Stt < 10 THEN
                SET v_OrderID = CONCAT('HD', CAST(p_CustomerID AS CHAR), '0', CAST(v_Stt AS CHAR));
            ELSE
                SET v_OrderID = CONCAT('HD', CAST(p_CustomerID AS CHAR), CAST(v_Stt AS CHAR));
            END IF;
        END IF;

        INSERT INTO selling_order(Id, Quantity, Total_dish, Delivery_fee, VoucherS, VoucherR, Total, Note, Process, Send_order, Receive_order, Id_fk_customer, Id_fk_shiper, Id_fk_restaurant)
        VALUES (v_OrderID, p_SoLuong, v_Total, 0, 0, 0, 0, NULL, 'Gio hang', NULL, NULL, p_CustomerID, NULL, p_RestaurantID);

        INSERT INTO order_detail(Price, Quantity, Total, Id_fk_donhang, Id_fk_dish)
        VALUES (v_PriceDish, p_SoLuong, v_Total, v_OrderID, p_MonAnID);
        
    ELSE
        SELECT Id INTO v_OrderID FROM selling_order WHERE id_fk_customer = p_CustomerID AND Process = 'Gio hang' LIMIT 1;

        SELECT Id_fk_restaurant INTO v_RestaurantIDTemp FROM selling_order WHERE id_fk_customer = p_CustomerID AND Process = 'Gio hang' LIMIT 1;
        IF v_RestaurantIDTemp != p_RestaurantID THEN
            UPDATE selling_order
            SET Id_fk_restaurant = p_RestaurantID
            WHERE Id = v_OrderID;

            DELETE FROM order_detail
            WHERE Id_fk_donhang = v_OrderID;

            INSERT INTO order_detail(Price, Quantity, Total, Id_fk_donhang, Id_fk_dish)
            VALUES (v_PriceDish, p_SoLuong, v_Total, v_OrderID, p_MonAnID);

        ELSE
            IF (SELECT COUNT(*) FROM order_detail WHERE Id_fk_donhang = v_OrderID AND Id_fk_dish = p_MonAnID) > 0 THEN
                UPDATE order_detail
                SET Quantity = Quantity + p_SoLuong, Total = Total + v_Total
                WHERE Id_fk_donhang = v_OrderID AND Id_fk_dish = p_MonAnID;
            ELSE
                INSERT INTO order_detail(Price, Quantity, Total, Id_fk_donhang, Id_fk_dish)
                VALUES (v_PriceDish, p_SoLuong, v_Total, v_OrderID, p_MonAnID);
            END IF;
        END IF;

        SELECT SUM(Quantity) INTO v_Quantity FROM order_detail
        WHERE Id_fk_donhang = v_OrderID
        GROUP BY Id_fk_donhang;

        SELECT SUM(Total) INTO v_Total_donhang FROM order_detail
        WHERE Id_fk_donhang = v_OrderID
        GROUP BY Id_fk_donhang;

        UPDATE selling_order
        SET Quantity = v_Quantity, Total_dish = v_Total_donhang
        WHERE Id = v_OrderID;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `AddVoucherRestaurantToCart` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddVoucherRestaurantToCart`(
    IN p_OrderID VARCHAR(50),
    IN p_VoucerID INT
)
BEGIN

    DECLARE v_GetVoucher DOUBLE;
    DECLARE v_CustomerID int;
    DECLARE v_RestaurantID int;

    SELECT Id_fk_customer INTO v_CustomerID from selling_order WHERE Id = p_OrderID;
    SELECT Id_fk_restaurant INTO v_RestaurantID from selling_order WHERE Id = p_OrderID;
    
    IF p_VoucerID = -1 THEN
        UPDATE selling_order
        SET VoucherR = 0, Id_voucher_restaurant = NULL
        WHERE Id = p_OrderID;
    ELSE
        SELECT v.Price INTO v_GetVoucher FROM selling_order d,get_voucher_restaurant g,voucher_restaurant v WHERE d.Id_fk_customer = v_CustomerID and d.Id_fk_customer = g.Id_customer and g.Id_voucher_restaurant = v.Id and v.Id = p_VoucerID LIMIT 1 ;
        IF v_GetVoucher IS NULL THEN
            SET v_GetVoucher = 0;
        END IF;
        UPDATE selling_order
        SET VoucherR = v_GetVoucher,Id_voucher_restaurant = p_VoucerID
        WHERE Id = p_OrderID;
    END IF;
    

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `AddVoucherSystemToCart` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddVoucherSystemToCart`(
    IN p_OrderID VARCHAR(50),
    IN p_SystemID INT
)
BEGIN

    DECLARE v_GetVoucher DOUBLE;
    DECLARE v_CustomerID INT;
    
    
    SELECT Id_fk_customer INTO v_CustomerID from selling_order WHERE Id = p_OrderID;
    
    IF p_SystemID = -1 THEN
        UPDATE selling_order
        SET VoucherS = 0, Id_voucher_system = NULL  -- Hoặc một giá trị khác nếu cần
        WHERE Id = p_OrderID;

    ELSE
        SELECT v.Price INTO v_GetVoucher FROM selling_order d,get_voucher_system g,voucher_system v WHERE d.Id_fk_customer = v_CustomerID and d.Id_fk_customer = g.Id_customer and g.Id_voucher_system =  p_SystemID and g.Id_voucher_system = v.Id LIMIT 1;
        IF v_GetVoucher IS NULL THEN
    	    SET v_GetVoucher = 0;
        END IF;    

        UPDATE selling_order
        SET VoucherS = v_GetVoucher,Id_voucher_system = p_SystemID
        WHERE Id = p_OrderID;
    END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `CalculateDistance` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `CalculateDistance`(
  	IN p_CustomerID INT,
    IN p_RestaurantID INT,
    IN p_OrderID VARCHAR(50)
)
BEGIN

	DECLARE v_LatitudeCustomer double;
	DECLARE v_LongtitudeCustomer double;
	DECLARE v_LatitudeRestaurant double;
	DECLARE v_LongtitudeRestaurant double;
	DECLARE distance double;
    
    
    SELECT Latitude INTO v_LatitudeCustomer FROM user WHERE Id = p_CustomerID;
    SELECT Longitude INTO v_LongtitudeCustomer FROM user WHERE Id = p_CustomerID;
    SELECT Latitude INTO v_LatitudeRestaurant FROM user WHERE Id = p_RestaurantID;
    SELECT Longitude INTO v_LongtitudeRestaurant FROM user WHERE Id = p_RestaurantID;
    
    
    
    
    -- Haversine Formula
    SET @earth_radius = 6371; 

    SET @dlat = RADIANS(v_LatitudeRestaurant - v_LatitudeCustomer);
    SET @dlon = RADIANS(v_LongtitudeRestaurant - v_LongtitudeCustomer);
    
    SET @a = SIN(@dlat / 2) * SIN(@dlat / 2) +
             COS(RADIANS(v_LatitudeCustomer)) * COS(RADIANS(v_LatitudeRestaurant)) *
             SIN(@dlon / 2) * SIN(@dlon / 2);
             
    SET @c = 2 * ATAN2(SQRT(@a), SQRT(1 - @a));
    
    -- Calculate the distance
    SET distance = @earth_radius * @c;
    SELECT distance;
    UPDATE selling_order
    SET Delivery_fee = distance * 17000
    WHERE Id = p_OrderID;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `Cancel` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `Cancel`(
    IN p_OrderID VARCHAR(50)    
)
BEGIN

	DECLARE v_Process VARCHAR(255) CHARACTER Set utf8;
	DECLARE v_customerId int;
	DECLARE v_voucher_restaurantId int;
	DECLARE v_voucher_systemId int;
	SELECT Process INTO v_Process FROM selling_order
        WHERE Id = p_OrderID;
    IF v_Process = 'Dang gui den nha hang' THEN
        SELECT Id_fk_customer INTO v_customerId FROM selling_order
        WHERE Id = p_OrderID;

        SELECT Id_voucher_system INTO v_voucher_systemId FROM selling_order
        WHERE Id = p_OrderID;

        SELECT Id_voucher_restaurant INTO v_voucher_restaurantId FROM selling_order
        WHERE Id = p_OrderID;

        UPDATE get_voucher_system
        SET Quantity = Quantity + 1
        WHERE Id_customer = v_customerId and Id_voucher_system = v_voucher_systemId;

        UPDATE get_voucher_restaurant
        SET Quantity = Quantity + 1
        WHERE Id_customer = v_customerId and Id_voucher_restaurant = v_voucher_restaurantId;

  UPDATE dish
    SET Quantity = Quantity + (
        SELECT quantity
        FROM order_detail
        WHERE order_detail.Id_fk_dish = dish.Id AND order_detail.Id_fk_donhang = p_OrderID
    )
    WHERE Id IN (
        SELECT Id_fk_dish
        FROM order_detail
        WHERE Id_fk_donhang = p_OrderID
    );
    

        UPDATE selling_order
        SET Process  = 'Da huy'
        WHERE Id = p_OrderID;

        UPDATE bill
        SET Active = 'Da huy'
        WHERE Id_fk_donhang = p_OrderID;

    
  END IF;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `Confirm` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `Confirm`(
    IN p_OrderID VARCHAR(50),
    IN p_Payment VARCHAR(50) CHARACTER Set utf8,
    IN p_Latitude DOUBLE,
    IN p_Longitude DOUBLE

    
)
BEGIN

	DECLARE v_Total double;
    
	DECLARE v_Total_dish double;
	DECLARE v_Delivery_fee double;
	DECLARE v_VoucherS double;
	DECLARE v_VoucherR double;
    
	DECLARE v_customerId int;
	DECLARE v_voucher_restaurantId int;
	DECLARE v_voucher_systemId int;
    
    
    SELECT Id_fk_customer INTO v_customerId FROM selling_order WHERE Id = p_OrderID;
    SELECT Id_voucher_restaurant INTO v_voucher_restaurantId FROM selling_order WHERE Id = p_OrderID;
    SELECT Id_voucher_system INTO v_voucher_systemId FROM selling_order WHERE Id = p_OrderID;
    
    
    
   UPDATE dish
    SET Quantity = Quantity - (
        SELECT quantity
        FROM order_detail
        WHERE order_detail.Id_fk_dish = dish.Id AND order_detail.Id_fk_donhang = p_OrderID
    )
    WHERE Id IN (
        SELECT Id_fk_dish
        FROM order_detail
        WHERE Id_fk_donhang = p_OrderID
    );
    
   

   UPDATE get_voucher_system
    SET Quantity = Quantity -1
    WHERE Id_customer = v_customerId and Id_voucher_system = v_voucher_systemId;
    
    UPDATE get_voucher_restaurant
    SET Quantity = Quantity -1
    WHERE Id_customer = v_customerId and Id_voucher_restaurant = v_voucher_restaurantId;
    
    
    SELECT Total_dish INTO v_Total_dish FROM selling_order WHERE Id = p_OrderID;
    SELECT Delivery_fee INTO v_Delivery_fee FROM selling_order WHERE Id = p_OrderID;
    SELECT VoucherS INTO v_VoucherS FROM selling_order WHERE Id = p_OrderID;
    SELECT VoucherR INTO v_VoucherR FROM selling_order WHERE Id = p_OrderID;
    
    SET v_Total = v_Total_dish + v_Total_dish*0.3 - v_VoucherS - v_VoucherR;
    UPDATE selling_order
            SET Total = v_Total,Process = 'Dang gui den nha hang', latitude = p_Latitude, longitude = p_Longitude,  Send_order = NOW()
            WHERE Id = p_OrderID;
    IF p_Payment = 'Tien mat' THEN
    	INSERT INTO `bill`(`Total`, `Active`, `Payment`, `Id_fk_donhang`) VALUES (v_Total,'Chua thanh toan',p_Payment,p_OrderID);
    END IF;

    IF p_Payment = 'Chuyen khoan' THEN
    	INSERT INTO `bill`(`Total`, `Active`, `Payment`, `Id_fk_donhang`) VALUES (v_Total,'Da thanh toan',p_Payment,p_OrderID);
    END IF;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `createCustomer` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `createCustomer`(
    IN p_id INT
)
BEGIN
	INSERT INTO customer(Id) VALUES(p_id)    ;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `createRestaurant` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `createRestaurant`(
    IN p_id INT
)
BEGIN
	INSERT INTO restaurant(Id,Rating) VALUES(p_id,0)    ;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `createShiper` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `createShiper`(
    IN p_id INT
)
BEGIN
	INSERT INTO shiper(Id,Rating, Balance, status_request) VALUES(p_id,0, 0, 0)    ;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `CustomerRating` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `CustomerRating`(
    IN p_orderId VARCHAR(255),
    IN p_id_res INT,
    IN p_ratingRes DOUBLE,
    IN p_id_shiper INT,
    IN p_ratingShiper DOUBLE
    
)
BEGIN
	DECLARE v_avgRes DOUBLE;
	DECLARE v_avgShiper DOUBLE;
   
    INSERT INTO feedback VALUES(p_orderId,p_ratingShiper,p_ratingRes,p_id_shiper,p_id_res);
    
    SELECT AVG(RatingRestaurant) INTO v_avgRes 
    FROM feedback 
    WHERE Id_fk_restaurant = p_id_res;

    -- Tính trung bình cộng của RatingShiper cho shipper
    SELECT AVG(RatingShiper) INTO v_avgShiper 
    FROM feedback 
    WHERE Id_fk_shiper = p_id_shiper;

    UPDATE restaurant
    SET Rating = v_avgRes
    WHERE Id = p_id_res;
    
      UPDATE shiper
    SET Rating = v_avgShiper
    WHERE Id = p_id_shiper;
    
    UPDATE selling_order
    SET Process = 'Da danh gia'
    WHERE Id = p_orderId;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DeleteToCart` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `DeleteToCart`(
    IN p_id INT
)
BEGIN
	DECLARE v_Quantity INT;
	DECLARE v_Total_dish DOUBLE;
    DECLARE v_OrderID VARCHAR(50);

    SELECT Id_fk_donhang INTO v_OrderID FROM order_detail
    WHERE Id = p_id;
   
   
   
   DELETE FROM order_detail
   WHERE Id = p_id;
   
   
   	SELECT sum(Quantity) INTO v_Quantity from order_detail
        WHERE Id_fk_donhang = v_OrderID
        GROUP BY Id_fk_donhang ;
  	SELECT sum(Total) INTO v_Total_dish from order_detail
        WHERE Id_fk_donhang = v_OrderID
        GROUP BY Id_fk_donhang ;

        

 	UPDATE selling_order
            SET Quantity = v_Quantity,Total_dish = v_Total_dish
            WHERE Id = v_OrderID;
    

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetVoucherRes` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetVoucherRes`(
    IN p_CustomerID INT,
    IN p_VoucerID INT
)
BEGIN
	INSERT INTO get_voucher_restaurant VALUES(p_CustomerID,p_VoucerID,1,'Valid');
    UPDATE voucher_restaurant
    SET Quantity = Quantity -1
    WHERE Id = p_VoucerID;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetVoucherSys` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetVoucherSys`(
    IN p_CustomerID INT,
    IN p_VoucerID INT
)
BEGIN
	INSERT INTO get_voucher_system VALUES(p_CustomerID,p_VoucerID,1,'Valid');
     UPDATE voucher_system
    SET Quantity = Quantity -1
    WHERE Id = p_VoucerID;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GoiYNhaHang` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `GoiYNhaHang`(
  	IN p_CustomerID INT,
    IN p_RestaurantID INT
)
BEGIN

	DECLARE v_LatitudeCustomer double;
	DECLARE v_LongtitudeCustomer double;
	DECLARE v_LatitudeRestaurant double;
	DECLARE v_LongtitudeRestaurant double;
	DECLARE distance double;
    
    
    SELECT Latitude INTO v_LatitudeCustomer FROM user WHERE Id = p_CustomerID;
    SELECT Longitude INTO v_LongtitudeCustomer FROM user WHERE Id = p_CustomerID;
    SELECT Latitude INTO v_LatitudeRestaurant FROM user WHERE Id = p_RestaurantID;
    SELECT Longitude INTO v_LongtitudeRestaurant FROM user WHERE Id = p_RestaurantID;
    
    
    
    
    -- Haversine Formula
    SET @earth_radius = 6371; 

    SET @dlat = RADIANS(v_LatitudeRestaurant - v_LatitudeCustomer);
    SET @dlon = RADIANS(v_LongtitudeRestaurant - v_LongtitudeCustomer);
    
    SET @a = SIN(@dlat / 2) * SIN(@dlat / 2) +
             COS(RADIANS(v_LatitudeCustomer)) * COS(RADIANS(v_LatitudeRestaurant)) *
             SIN(@dlon / 2) * SIN(@dlon / 2);
             
    SET @c = 2 * ATAN2(SQRT(@a), SQRT(1 - @a));
    
    -- Calculate the distance
    SET distance = @earth_radius * @c;

    SELECT distance;
    
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `LoginByEmail` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `LoginByEmail`(
    IN p_Email VARCHAR(255),
    IN p_Fullname VARCHAR(255) CHARACTER SET utf8,
    IN p_Role int
)
BEGIN
    DECLARE v_Id INT;
	INSERT INTO `user`(`FullName`,`Email`, `Active`, `Id_role`) VALUES (p_Fullname,p_Email,1,p_Role);
    SELECT Id INTO v_Id from `user` where Email = p_Email;
    If (select count(*) from customer where Id = v_Id ) = 0 THEN
        INSERT INTO customer VALUES(v_Id);
    END IF;
    
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MinusToCart` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `MinusToCart`(
    IN p_OrderID VARCHAR(50),
    IN p_MonAnID INT
)
BEGIN

    DECLARE v_PriceDish DOUBLE;
    DECLARE v_Quantity INT;
    DECLARE v_Total_donhang DOUBLE;


    SELECT Price INTO v_PriceDish FROM dish WHERE Id = p_MonAnID;
    
    UPDATE order_detail
    SET Quantity = Quantity - 1, Total = Total - v_PriceDish
    WHERE Id_fk_donhang = p_OrderID AND Id_fk_dish = p_MonAnID;


    SELECT sum(Quantity) INTO v_Quantity from order_detail
    WHERE Id_fk_donhang = p_OrderID
    GROUP BY Id_fk_donhang ;
    
    SELECT sum(Total) INTO v_Total_donhang from order_detail
    WHERE Id_fk_donhang = p_OrderID
    GROUP BY Id_fk_donhang ;

    

    UPDATE selling_order
        SET Quantity = v_Quantity,Total_dish = v_Total_donhang
        WHERE Id = p_OrderID;


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `Signup` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `Signup`(
    IN p_Fullname VARCHAR(255) CHARACTER SET utf8,
    IN p_Phonenumber VARCHAR(15),
    IN p_Username VARCHAR(255),
    IN p_PassW VARCHAR(255),
    IN p_Role int,
    IN p_Otp VARCHAR(10)
)
BEGIN
	INSERT INTO `user`(`FullName`,`PhoneNumber`, `UserName`, `PassW`, `Active`, `Id_role`, `Otp`) VALUES (p_Fullname,p_Phonenumber,p_Username,p_PassW,0,p_Role,p_Otp);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SumVoucherDiscount` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `SumVoucherDiscount`(
    IN p_OrderID VARCHAR(50)
)
BEGIN
    SELECT VoucherS  + VoucherR  as Tong from selling_order WHERE Id = p_OrderID
    GROUP BY Id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `test` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `test`()
BEGIN
    DECLARE v_OrderID VARCHAR(50);
    DECLARE v_OrderIDTemp VARCHAR(50);
    DECLARE v_Stt INT;
    DECLARE v_PriceDish DOUBLE;
    DECLARE v_Quantity INT;
    DECLARE v_Total DOUBLE;
  
    IF (SELECT COUNT(*) FROM selling_order WHERE id_fk_customer = 4 AND Process != 'Gio hang') > 0 THEN
        SELECT Id INTO v_OrderIDTemp FROM selling_order WHERE id_fk_customer = 4 ORDER BY Id DESC LIMIT 1;
        SET v_Stt = CAST(RIGHT(v_OrderIDTemp, 2) AS UNSIGNED);
        SET v_Stt = v_Stt + 1;

        IF v_Stt < 10 THEN
            SET v_OrderID = CONCAT('HD', CAST(4 AS CHAR), '0', CAST(v_Stt AS CHAR));
        ELSE 
            SET v_OrderID = CONCAT('HD', CAST(4 AS CHAR), CAST(v_Stt AS CHAR));
        END IF;
        
        SELECT v_OrderID;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-28 22:14:09
