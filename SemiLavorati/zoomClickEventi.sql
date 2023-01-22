-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: zoomclickeventi
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `artista`
--

DROP TABLE IF EXISTS `artista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artista` (
  `idArtista` int NOT NULL,
  `tipoArtista` varchar(255) NOT NULL,
  PRIMARY KEY (`idArtista`),
  CONSTRAINT `utente.id` FOREIGN KEY (`idArtista`) REFERENCES `utente` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artista`
--

LOCK TABLES `artista` WRITE;
/*!40000 ALTER TABLE `artista` DISABLE KEYS */;
/*!40000 ALTER TABLE `artista` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `idCliente` int NOT NULL,
  PRIMARY KEY (`idCliente`),
  CONSTRAINT `utente.idUtente` FOREIGN KEY (`idCliente`) REFERENCES `utente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1),(2);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contabile`
--

DROP TABLE IF EXISTS `contabile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contabile` (
  `idcontabile` int NOT NULL,
  PRIMARY KEY (`idcontabile`),
  CONSTRAINT `utente.idC` FOREIGN KEY (`idcontabile`) REFERENCES `gestore` (`idgestore`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contabile`
--

LOCK TABLES `contabile` WRITE;
/*!40000 ALTER TABLE `contabile` DISABLE KEYS */;
/*!40000 ALTER TABLE `contabile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fornitori`
--

DROP TABLE IF EXISTS `fornitori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fornitori` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nomeAzienda` varchar(255) NOT NULL,
  `proprietario` varchar(255) NOT NULL,
  `telefono` char(10) NOT NULL,
  `tipoFornitore` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornitori`
--

LOCK TABLES `fornitori` WRITE;
/*!40000 ALTER TABLE `fornitori` DISABLE KEYS */;
/*!40000 ALTER TABLE `fornitori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gestore`
--

DROP TABLE IF EXISTS `gestore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gestore` (
  `idgestore` int NOT NULL,
  PRIMARY KEY (`idgestore`),
  CONSTRAINT `utente.idUt` FOREIGN KEY (`idgestore`) REFERENCES `utente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gestore`
--

LOCK TABLES `gestore` WRITE;
/*!40000 ALTER TABLE `gestore` DISABLE KEYS */;
/*!40000 ALTER TABLE `gestore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gestoreimpiegati`
--

DROP TABLE IF EXISTS `gestoreimpiegati`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gestoreimpiegati` (
  `idgestoreImpiegati` int NOT NULL,
  PRIMARY KEY (`idgestoreImpiegati`),
  CONSTRAINT `gestore.idI` FOREIGN KEY (`idgestoreImpiegati`) REFERENCES `gestore` (`idgestore`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gestoreimpiegati`
--

LOCK TABLES `gestoreimpiegati` WRITE;
/*!40000 ALTER TABLE `gestoreimpiegati` DISABLE KEYS */;
/*!40000 ALTER TABLE `gestoreimpiegati` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gestorepacchetti`
--

DROP TABLE IF EXISTS `gestorepacchetti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gestorepacchetti` (
  `idGestorePacchetti` int NOT NULL,
  PRIMARY KEY (`idGestorePacchetti`),
  CONSTRAINT `gestore.idP` FOREIGN KEY (`idGestorePacchetti`) REFERENCES `gestore` (`idgestore`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gestorepacchetti`
--

LOCK TABLES `gestorepacchetti` WRITE;
/*!40000 ALTER TABLE `gestorepacchetti` DISABLE KEYS */;
/*!40000 ALTER TABLE `gestorepacchetti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gestoreparty`
--

DROP TABLE IF EXISTS `gestoreparty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gestoreparty` (
  `idgestoreParty` int NOT NULL,
  PRIMARY KEY (`idgestoreParty`),
  CONSTRAINT `gestore.idPa` FOREIGN KEY (`idgestoreParty`) REFERENCES `gestore` (`idgestore`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gestoreparty`
--

LOCK TABLES `gestoreparty` WRITE;
/*!40000 ALTER TABLE `gestoreparty` DISABLE KEYS */;
/*!40000 ALTER TABLE `gestoreparty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingaggio`
--

DROP TABLE IF EXISTS `ingaggio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingaggio` (
  `idArtista` int NOT NULL,
  `idParty` int NOT NULL,
  `prezzo` double DEFAULT NULL,
  PRIMARY KEY (`idArtista`,`idParty`),
  KEY `party.id_idx` (`idParty`),
  CONSTRAINT `artista.id` FOREIGN KEY (`idArtista`) REFERENCES `artista` (`idArtista`),
  CONSTRAINT `party.id` FOREIGN KEY (`idParty`) REFERENCES `party` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingaggio`
--

LOCK TABLES `ingaggio` WRITE;
/*!40000 ALTER TABLE `ingaggio` DISABLE KEYS */;
/*!40000 ALTER TABLE `ingaggio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pacchetti`
--

DROP TABLE IF EXISTS `pacchetti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pacchetti` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titolo` varchar(255) NOT NULL,
  `eventiConsigliati` varchar(255) DEFAULT NULL,
  `prezzo` double NOT NULL,
  `flag` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pacchetti`
--

LOCK TABLES `pacchetti` WRITE;
/*!40000 ALTER TABLE `pacchetti` DISABLE KEYS */;
/*!40000 ALTER TABLE `pacchetti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `party`
--

DROP TABLE IF EXISTS `party`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `party` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` varchar(255) NOT NULL,
  `data` date NOT NULL,
  `dataPrenotazione` date NOT NULL,
  `nomeLocale` varchar(255) NOT NULL,
  `servizi` varchar(255) NOT NULL,
  `stato` varchar(45) NOT NULL,
  `idPacchetto` int NOT NULL,
  `idCliente` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `pacchetto.id_idx` (`idPacchetto`),
  KEY `cliente.id_idx` (`idCliente`),
  CONSTRAINT `cliente.id` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`),
  CONSTRAINT `pacchetto.id` FOREIGN KEY (`idPacchetto`) REFERENCES `pacchetti` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `party`
--

LOCK TABLES `party` WRITE;
/*!40000 ALTER TABLE `party` DISABLE KEYS */;
/*!40000 ALTER TABLE `party` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `richiede`
--

DROP TABLE IF EXISTS `richiede`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `richiede` (
  `idFornitore` int NOT NULL,
  `idParty` int NOT NULL,
  `prezzo` double NOT NULL,
  PRIMARY KEY (`idFornitore`,`idParty`),
  KEY `party.idParty_idx` (`idParty`),
  CONSTRAINT `fornitori.id` FOREIGN KEY (`idFornitore`) REFERENCES `fornitori` (`id`),
  CONSTRAINT `party.idParty` FOREIGN KEY (`idParty`) REFERENCES `party` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `richiede`
--

LOCK TABLES `richiede` WRITE;
/*!40000 ALTER TABLE `richiede` DISABLE KEYS */;
/*!40000 ALTER TABLE `richiede` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `cognome` varchar(45) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `telefono` char(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES (1,'Angelo','Santangelo','asantangelo11@gmail.com','Djangelo19.','3312834046'),(2,'Dario','De Maio','dariodemaio@gmail.com','Dario12@','3336664585');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-22 17:03:15
