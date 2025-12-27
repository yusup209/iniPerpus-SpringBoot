-- Schema for iniPerpus application (MariaDB)
-- Run: CREATE DATABASE iniperpus; USE iniperpus;

SET NAMES utf8mb4;

CREATE TABLE IF NOT EXISTS `role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) DEFAULT NULL,
  `full_name` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `user_roles` (
  `user_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_user_roles_user_idx` (`user_id`),
  KEY `fk_user_roles_role_idx` (`role_id`),
  CONSTRAINT `fk_user_roles_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_roles_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `book` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `author` VARCHAR(255) NOT NULL,
  `isbn` VARCHAR(100) DEFAULT NULL,
  `publisher` VARCHAR(255) DEFAULT NULL,
  `year_published` INT DEFAULT NULL,
  `copies_total` INT NOT NULL DEFAULT 1,
  `copies_available` INT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `idx_book_isbn` (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `student` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `student_id` VARCHAR(100) DEFAULT NULL,
  `name` VARCHAR(255) DEFAULT NULL,
  `class_name` VARCHAR(255) DEFAULT NULL,
  `photo_filename` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_student_studentId` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `lending` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `book_id` BIGINT NOT NULL,
  `borrower_id` BIGINT NOT NULL,
  `lend_date` DATE DEFAULT NULL,
  `due_date` DATE DEFAULT NULL,
  `return_date` DATE DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_lending_book_idx` (`book_id`),
  KEY `fk_lending_borrower_idx` (`borrower_id`),
  CONSTRAINT `fk_lending_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_lending_borrower` FOREIGN KEY (`borrower_id`) REFERENCES `student` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `presence_record` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `student_id` BIGINT NOT NULL,
  `timestamp` DATETIME DEFAULT NULL,
  `matched` TINYINT(1) DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `fk_presence_student_idx` (`student_id`),
  CONSTRAINT `fk_presence_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- End of schema
