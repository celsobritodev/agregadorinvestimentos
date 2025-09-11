-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: db_example
-- ------------------------------------------------------
-- Server version	8.3.0

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
-- Table structure for table `tb_accounts_stocks`
--

DROP TABLE IF EXISTS `tb_accounts_stocks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_accounts_stocks` (
  `quantity` int DEFAULT NULL,
  `account_id` binary(16) NOT NULL,
  `stock_id` varchar(255) NOT NULL,
  PRIMARY KEY (`account_id`,`stock_id`),
  KEY `FKa4o389u2cjd7778axl5rrraa5` (`stock_id`),
  CONSTRAINT `FK4ergru1ndhjiwdhaq4u1j21yx` FOREIGN KEY (`account_id`) REFERENCES `tb_accounts` (`account_id`),
  CONSTRAINT `FKa4o389u2cjd7778axl5rrraa5` FOREIGN KEY (`stock_id`) REFERENCES `tb_stocks` (`stock_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_accounts_stocks`
--

LOCK TABLES `tb_accounts_stocks` WRITE;
/*!40000 ALTER TABLE `tb_accounts_stocks` DISABLE KEYS */;
INSERT INTO `tb_accounts_stocks` VALUES (50,_binary '/\çJöI¹Ÿû/wŠoB','MGLU3'),(100,_binary '/\çJöI¹Ÿû/wŠoB','PETR4');
/*!40000 ALTER TABLE `tb_accounts_stocks` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-11  9:24:41
