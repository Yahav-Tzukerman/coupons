CREATE DATABASE  IF NOT EXISTS `coupons2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `coupons2`;
-- MySQL dump 10.13  Distrib 8.0.27, for macos11 (x86_64)
--
-- Host: 127.0.0.1    Database: coupons2
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'CARDS'),(4,'MERCHANDISE'),(5,'WEPONS'),(7,'GAMING'),(9,'DEV');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `companies`
--

DROP TABLE IF EXISTS `companies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `companies` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `foundation_date` date DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_50ygfritln653mnfhxucoy8up` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `companies`
--

LOCK TABLES `companies` WRITE;
/*!40000 ALTER TABLE `companies` DISABLE KEYS */;
INSERT INTO `companies` VALUES (1,'Apple Park','1960-01-01','Pokemon','0546261880','https://i.ibb.co/LxJnP1N/International-Pok-mon-logo-svg.png'),(2,'Tokyo Hightech Park','1968-01-01','Digimon','0546265544','https://i.ibb.co/7pjhYyS/digimon.png'),(13,'Washington st354','1930-01-01','Dc Comics','0545258951','https://i.ibb.co/2d2ZPHs/dc.png'),(14,'hogwarts','1855-01-01','Harry Potter','0546589777','https://i.ibb.co/Sxr7q3T/harry-potter.png'),(15,'Asgard','1750-01-01','Marvel','0458595685','https://i.ibb.co/9HPgWtF/marvel.png'),(16,'Los Angeles','1993-01-01','Power Rangers','045859562','https://i.ibb.co/Yfb4zWN/power-rangers.png'),(17,'Dimenssion X321','2020-01-01','Rick And Morty','0459878956','https://i.ibb.co/KhXYZj0/rickr-and-morty.png'),(18,'Galaxy far far away','1980-01-01','Star Wars','0523698545','https://i.ibb.co/nbqvdw4/starWars.png'),(19,'Japan','1996-01-01','Yu Gi Ho','0258954512','https://i.ibb.co/v38PrQf/yu-gi-ho.png');
/*!40000 ALTER TABLE `companies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupons`
--

DROP TABLE IF EXISTS `coupons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupons` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` int NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `end_date` date NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `price` float NOT NULL,
  `start_date` date NOT NULL,
  `title` varchar(255) NOT NULL,
  `category_id` bigint DEFAULT NULL,
  `company_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_eplt0kkm9yf2of2lnx6c1oy9b` (`code`),
  KEY `FKsywuemw12y5s03cupsj3mjs3b` (`category_id`),
  KEY `FKdcx0ovgcgvc3v9clxn2b9kvg9` (`company_id`),
  CONSTRAINT `FKdcx0ovgcgvc3v9clxn2b9kvg9` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`),
  CONSTRAINT `FKsywuemw12y5s03cupsj3mjs3b` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupons`
--

LOCK TABLES `coupons` WRITE;
/*!40000 ALTER TABLE `coupons` DISABLE KEYS */;
INSERT INTO `coupons` VALUES (51,12,'P1CARD','1999 Pokemon Game Charizard Holo 1st Edition Gem mt 10','2023-03-02','https://i.ibb.co/5nR18rX/char.png',420000,'2022-11-03','1999 Pokemon Game Charizard',1,1),(52,9,'P2CARD','1998 Pocket Monsters Japanese Promo “Illustrator” Pikachu Holo PSA NM 7 ($900,000 USD)','2023-03-02','https://i.ibb.co/qnYmVYF/pik.png',900000,'2022-11-02','1998 Pocket Monsters Pikachu',1,1),(53,13,'P3CARD','1998 Pokémon Blastoise # 009 / 165R Commissioned Presentation Galaxy Star Hologram ','2023-03-02','https://i.ibb.co/PzGLf1N/Pok-mon-Blastoise.png',320000,'2022-11-02','1998 Pokémon Blastoise # 009',1,1),(63,50,'P4CARD','A card so rare it may just be rumour','2023-04-20','https://i.ibb.co/mF4cKY3/pokemon-card-raichu.png',10500,'2022-12-05','Prerelease Raichu',1,1),(64,15,'P5CARD','A legendary Pokémon on a legendarily rare Pokémon card','2023-04-19','https://i.ibb.co/vYBhq78/pokemon-card-logia.png',144300,'2022-12-01','2000 Pokémon Lugia',1,1),(65,13,'P6CARD','The third-rarest Pokémon card of all time','2023-06-13','https://i.ibb.co/RcYctfx/pokemon-card-kangaskhan.png',150100,'2022-12-03','Kangaskhan-Holo #115',1,1),(66,49,'P7CARD','No Rarity Symbol Base Set','2023-09-08','https://i.ibb.co/QFY5Frm/pokemon-card-poliwarth.png',25015,'2022-12-03','Poliwrath-Holo #62',1,1),(67,30,'P8CARD','Gold Star Holo Team Rocket Returns','2023-08-23','https://i.ibb.co/YTMvsst/pokemon-card-torchic.png',25400,'2022-12-03','Torchic Holo #108',1,1),(68,500,'P1BALL','Grupo erik Poke Ball Pokemon Pokeball Replica','2023-05-24','https://i.ibb.co/s3spj1Q/pokemon-pokeball1.png',30,'2022-12-10','Pokemon Pokeball Replica',5,1);
/*!40000 ALTER TABLE `coupons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` bigint NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `amount_of_children` int DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `martial_status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKpog72rpahj62h7nod9wwc28if` FOREIGN KEY (`id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (39,'Nesher Ha Irusim 28',0,'1996-01-31','FEMALE','MARRIED'),(43,'Ramat-Gan Yadidia 24',0,'1991-09-12','MALE','SINGLE'),(46,'Qiryat Motzkin',0,'2002-08-15','MALE','SINGLE'),(47,'Migdal Hamaak',2,'1991-01-01','MALE','MARRIED'),(62,'dthtd dtj',2,'2015-02-04','MALE','UNKNOWN');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchases`
--

DROP TABLE IF EXISTS `purchases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchases` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` int NOT NULL,
  `timestamp` datetime(6) NOT NULL,
  `total_price` float NOT NULL,
  `coupon_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4sqocqt2sv83kxdti1ebdc1aq` (`coupon_id`),
  KEY `FKm0ndjymn9p747pfp4515pio8i` (`user_id`),
  CONSTRAINT `FK4sqocqt2sv83kxdti1ebdc1aq` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`id`),
  CONSTRAINT `FKm0ndjymn9p747pfp4515pio8i` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchases`
--

LOCK TABLES `purchases` WRITE;
/*!40000 ALTER TABLE `purchases` DISABLE KEYS */;
INSERT INTO `purchases` VALUES (123,1,'2022-11-25 22:35:39.001245',420000,51,43),(124,1,'2022-11-25 22:35:39.001245',900000,52,43),(125,2,'2022-11-26 10:44:03.208707',840000,51,39),(126,2,'2022-11-26 10:44:03.208707',1800000,52,39),(127,3,'2022-11-30 16:20:04.106771',1260000,51,43),(128,2,'2022-11-30 16:20:04.071429',640000,53,43),(129,2,'2022-11-30 16:20:04.071475',1800000,52,43),(130,1,'2022-12-01 07:03:08.518071',900000,52,39),(131,2,'2022-12-01 07:03:08.486568',640000,53,39),(132,2,'2022-12-01 07:03:08.521761',840000,51,39),(133,3,'2022-12-01 10:21:06.208673',1260000,51,39),(134,3,'2022-12-01 10:21:06.208334',960000,53,39),(135,5,'2022-12-01 10:21:06.208688',4500000,52,39),(136,1,'2022-12-01 10:22:33.929214',420000,51,39),(137,3,'2022-12-01 10:22:33.937424',2700000,52,39),(138,2,'2022-12-01 10:22:33.920380',640000,53,39),(139,1,'2022-12-03 11:47:31.648626',25015,66,43),(140,2,'2022-12-03 11:47:31.648626',300200,65,43),(141,2,'2022-12-10 12:01:51.134051',1800000,52,43),(142,2,'2022-12-10 12:01:51.134250',840000,51,43);
/*!40000 ALTER TABLE `purchases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount_of_failed_logins` int NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `join_date` date NOT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `suspension_timestamp` datetime(6) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `company_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`),
  KEY `FKin8gn4o1hpiwe6qe4ey7ykwq7` (`company_id`),
  CONSTRAINT `FKin8gn4o1hpiwe6qe4ey7ykwq7` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (39,0,'Orel','2022-07-09','Tzukerman','$2a$10$m9rOpkFyjEYWO6kbnHYjful5JnQTB1c6RuWYMz1dNcmisH8KeBw36','0546261776','CUSTOMER','ACTIVE',NULL,'orel@gmail.com',NULL),(40,0,'Shai','2022-07-12','Gutman','$2a$10$0FGSkRH.6S5l0NbAJZGWdeWNLkKjWm18SPLUybFWg2rqTqw4nCMfq','05462618789','COMPANY','ACTIVE',NULL,'apple@gmail.com',1),(41,0,'Sony','2022-07-12','User','$2a$10$oSN1ZgAMMh/R9RWW7dYzteiJV0BEaTVblJzSBO1FhuOc/tqTrO9te','0546261886','COMPANY','ACTIVE',NULL,'sony@gmail.com',2),(43,0,'Sagi','2022-07-17','Zayger','$2a$10$cbxUwoE592Wf6bY6Zn5KluohcbcHdWskMy6a2/jOYw9QpCBtNUPtG','0546268558','CUSTOMER','ACTIVE',NULL,'sagi@gmail.com',NULL),(46,0,'Nadav','2022-09-24','Edri','$2a$10$JPtyJe/gPVVDKkyF3zej0ObfbjTUtam0MqFekqPrnTzNS.PuVnTU2','0521545785','CUSTOMER','ACTIVE',NULL,'nadav@gmail.com',NULL),(47,0,'Roei','2022-09-24','Aberjil','$2a$10$M9FuOUXBWRiZMcSbecnyXeOwxOsdriprVXgD5dbc5o8OfkCCHxG76','0521545875','CUSTOMER','ACTIVE',NULL,'roei@gmail.com',NULL),(50,0,NULL,'2022-10-29',NULL,'$2a$10$Hkar5ZZFuyoYxGYKqIHqOecVKQjm8kSKiexrX1dpD93t9SXoS2LM6',NULL,'ADMIN','ACTIVE',NULL,'admin@admin.com',NULL),(51,0,'yahav','2022-11-26','tzukerman','$2a$10$SCF7pEmUrOmrYwTSQc8giOqWxtrM2IqcCNBe6rassoqP1wNdG0zLK','0549302333','ADMIN','ACTIVE',NULL,'yahav@gmail.com',NULL),(52,0,'shay','2022-11-26','gutman','$2a$10$K3TKdNvl8ENnPaDBBel/SupEAF7u0Fm1h6Ft5vGLSA63jLv9IL.4G','0546261858','ADMIN','ACTIVE',NULL,'shay@gmail.com',NULL),(53,0,'Poke','2022-11-26','Mon','$2a$10$w9RQImSz70QlGHMkMdt09e8siQHhul3MsHpa7TWTUhmcZjT0wDBIy','0456956626','COMPANY','ACTIVE',NULL,'pokemon@company.com',1),(62,0,'yygk','2022-11-29','fjy','$2a$10$qnRfJi0pLOd3a5gGBGnPk.s3FaFvN3Xw/v380ypBfbi1IrqKV4wtG','0546261880','CUSTOMER','ACTIVE',NULL,'demo@demo.com',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-10 18:15:33
