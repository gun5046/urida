-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: j8d202.p.ssafy.io    Database: urida
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `board_id` bigint NOT NULL AUTO_INCREMENT,
  `content` mediumtext NOT NULL,
  `view` int DEFAULT NULL,
  `time` varchar(30) DEFAULT NULL,
  `uid` bigint NOT NULL,
  `title` varchar(50) NOT NULL,
  `category_id` int DEFAULT NULL,
  `image` mediumtext,
  PRIMARY KEY (`board_id`),
  KEY `board_user_fk_idx` (`uid`),
  CONSTRAINT `board_user_fk` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `choice`
--

DROP TABLE IF EXISTS `choice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `choice` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pro_id` bigint NOT NULL,
  `word_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `choice_prob_fk_idx` (`pro_id`),
  CONSTRAINT `choice_prob_fk` FOREIGN KEY (`pro_id`) REFERENCES `problem` (`pro_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `comment_id` bigint NOT NULL AUTO_INCREMENT,
  `content` mediumtext,
  `time` varchar(30) DEFAULT NULL,
  `board_id` bigint NOT NULL,
  `uid` bigint NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `comment_user_fk_idx` (`uid`),
  KEY `comment_board_fk_idx` (`board_id`),
  CONSTRAINT `comment_board_fk` FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_user_fk` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `example`
--

DROP TABLE IF EXISTS `example`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `example` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pro_id` bigint NOT NULL,
  `word_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `exam_pro_fk_idx` (`pro_id`),
  CONSTRAINT `exam_pro_fk` FOREIGN KEY (`pro_id`) REFERENCES `problem` (`pro_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `likeboard`
--

DROP TABLE IF EXISTS `likeboard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likeboard` (
  `like_id` int NOT NULL AUTO_INCREMENT,
  `uid` bigint NOT NULL,
  `board_id` bigint NOT NULL,
  `status` tinyint DEFAULT NULL,
  PRIMARY KEY (`like_id`),
  KEY `like_user_fk_idx` (`uid`),
  KEY `like_board_fk_idx` (`board_id`),
  CONSTRAINT `like_board_fk` FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `like_user_fk` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `problem`
--

DROP TABLE IF EXISTS `problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `problem` (
  `pro_id` bigint NOT NULL AUTO_INCREMENT,
  `sentence_id` int DEFAULT NULL,
  `answer_Id` int NOT NULL,
  `type` int NOT NULL,
  `category_id` int NOT NULL,
  `wrong_cnt` int DEFAULT '1',
  `uid` bigint NOT NULL,
  PRIMARY KEY (`pro_id`),
  KEY `prob_user_fk_idx` (`uid`),
  CONSTRAINT `prob_user_fk` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `title`
--

DROP TABLE IF EXISTS `title`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `title` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pro_id` bigint NOT NULL,
  `word_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `title_prob_fk_idx` (`pro_id`),
  CONSTRAINT `title_prob_fk` FOREIGN KEY (`pro_id`) REFERENCES `problem` (`pro_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `uid` bigint NOT NULL AUTO_INCREMENT,
  `nickname` varchar(20) DEFAULT NULL,
  `social_id` varchar(50) NOT NULL,
  `language` int NOT NULL,
  `type` varchar(8) NOT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `social_id` (`social_id`),
  UNIQUE KEY `nickname` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-05 16:00:04
