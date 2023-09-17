-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: elecciones
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
-- Table structure for table `electores`
--

DROP TABLE IF EXISTS `electores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `electores` (
  `idVoto` int NOT NULL,
  `dni` varchar(10) NOT NULL,
  `cuil` varchar(13) NOT NULL,
  `isAdmin` tinyint(1) DEFAULT '0',
  `haVotado` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`idVoto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `electores`
--

LOCK TABLES `electores` WRITE;
/*!40000 ALTER TABLE `electores` DISABLE KEYS */;
INSERT INTO `electores` VALUES (221,'45678901','20-45678901-4',1,1),(1836,'34567890','20-34567890-5',0,0),(2374,'23456789','20-23456789-6',0,0),(4237,'56789012','20-56789012-3',0,0),(4563,'12345678','20-12345678-7',0,0);
/*!40000 ALTER TABLE `electores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `votos`
--

DROP TABLE IF EXISTS `votos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `votos` (
  `idVoto` int NOT NULL AUTO_INCREMENT,
  `eleccion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idVoto`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `votos`
--

LOCK TABLES `votos` WRITE;
/*!40000 ALTER TABLE `votos` DISABLE KEYS */;
INSERT INTO `votos` VALUES (1,'Javier Milei'),(2,'Javier Milei'),(3,'Damian Olaso');
/*!40000 ALTER TABLE `votos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-17 12:17:23
