-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema lldb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `lldb` ;

-- -----------------------------------------------------
-- Schema lldb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `lldb` DEFAULT CHARACTER SET utf8 ;
USE `lldb` ;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `cohort` VARCHAR(255) NOT NULL,
  `enabled` BIT(1) NOT NULL DEFAULT b'1',
  `created_at` DATETIME NULL DEFAULT NULL,
  `role` VARCHAR(100) NULL DEFAULT NULL,
  `first_name` VARCHAR(100) NULL DEFAULT NULL,
  `last_name` VARCHAR(100) NULL DEFAULT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `user_unique` (`username` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `question` ;

CREATE TABLE IF NOT EXISTS `question` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `question` TEXT NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  `enabled` BIT(1) NULL DEFAULT b'1',
  `hint` TEXT NULL DEFAULT NULL,
  `explanation` TEXT NULL DEFAULT NULL,
  `user_id` INT(11) NOT NULL,
  `answer_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_question_user_idx` (`user_id` ASC),
  INDEX `fk_question_choice1_idx` (`answer_id` ASC),
  CONSTRAINT `fk_question_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_question_choice1`
    FOREIGN KEY (`answer_id`)
    REFERENCES `choice` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `choice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `choice` ;

CREATE TABLE IF NOT EXISTS `choice` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(255) NULL DEFAULT NULL,
  `position` INT(11) NULL DEFAULT '0',
  `correct` BIT(1) NULL DEFAULT NULL,
  `learn_more` VARCHAR(255) NULL DEFAULT NULL,
  `question_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_choice_question1_idx` (`question_id` ASC),
  CONSTRAINT `fk_choice_question1`
    FOREIGN KEY (`question_id`)
    REFERENCES `question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cohort_question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cohort_question` ;

CREATE TABLE IF NOT EXISTS `cohort_question` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `open_datetime` DATETIME NULL DEFAULT NULL,
  `close_datetime` DATETIME NULL DEFAULT NULL,
  `minutes` INT(11) NULL DEFAULT NULL,
  `question_id` INT(11) NOT NULL,
  `instructor_user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_cohort_question_question1_idx` (`question_id` ASC),
  INDEX `fk_cohort_question_user1_idx` (`instructor_user_id` ASC),
  CONSTRAINT `fk_cohort_question_question1`
    FOREIGN KEY (`question_id`)
    REFERENCES `question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cohort_question_user1`
    FOREIGN KEY (`instructor_user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `quiz`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quiz` ;

CREATE TABLE IF NOT EXISTS `quiz` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NULL DEFAULT NULL,
  `enabled` BIT(1) NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  `instructor_user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_quiz_user1_idx` (`instructor_user_id` ASC),
  CONSTRAINT `fk_quiz_user1`
    FOREIGN KEY (`instructor_user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `quiz_question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quiz_question` ;

CREATE TABLE IF NOT EXISTS `quiz_question` (
  `id` INT(11) NOT NULL,
  `quiz_id` INT(11) NOT NULL,
  `question_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_quiz_question_quiz1_idx` (`quiz_id` ASC),
  INDEX `fk_quiz_question_question1_idx` (`question_id` ASC),
  CONSTRAINT `fk_quiz_question_quiz1`
    FOREIGN KEY (`quiz_id`)
    REFERENCES `quiz` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_quiz_question_question1`
    FOREIGN KEY (`question_id`)
    REFERENCES `question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tag` ;

CREATE TABLE IF NOT EXISTS `tag` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `question_has_tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `question_has_tag` ;

CREATE TABLE IF NOT EXISTS `question_has_tag` (
  `question_id` INT(11) NOT NULL,
  `tag_id` INT(11) NOT NULL,
  PRIMARY KEY (`question_id`, `tag_id`),
  INDEX `fk_question_has_tag_tag1_idx` (`tag_id` ASC),
  INDEX `fk_question_has_tag_question1_idx` (`question_id` ASC),
  CONSTRAINT `fk_question_has_tag_question1`
    FOREIGN KEY (`question_id`)
    REFERENCES `question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_question_has_tag_tag1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `tag` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `student_guess`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `student_guess` ;

CREATE TABLE IF NOT EXISTS `student_guess` (
  `user_id` INT(11) NOT NULL,
  `cohort_question_id` INT(11) NOT NULL,
  `guess_choice_datetime` DATETIME NULL,
  `choice_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `cohort_question_id`),
  INDEX `fk_user_has_cohort_question_cohort_question1_idx` (`cohort_question_id` ASC),
  INDEX `fk_user_has_cohort_question_user1_idx` (`user_id` ASC),
  INDEX `fk_student_guess_choice1_idx` (`choice_id` ASC),
  CONSTRAINT `fk_user_has_cohort_question_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_cohort_question_cohort_question1`
    FOREIGN KEY (`cohort_question_id`)
    REFERENCES `cohort_question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_guess_choice1`
    FOREIGN KEY (`choice_id`)
    REFERENCES `choice` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `quiz_guess`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quiz_guess` ;

CREATE TABLE IF NOT EXISTS `quiz_guess` (
  `user_id` INT(11) NOT NULL,
  `quiz_question_id` INT(11) NOT NULL,
  `choice_datetime` DATETIME NULL,
  `choice_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `quiz_question_id`),
  INDEX `fk_user_has_quiz_question_quiz_question1_idx` (`quiz_question_id` ASC),
  INDEX `fk_user_has_quiz_question_user1_idx` (`user_id` ASC),
  INDEX `fk_quiz_guess_choice1_idx` (`choice_id` ASC),
  CONSTRAINT `fk_user_has_quiz_question_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_quiz_question_quiz_question1`
    FOREIGN KEY (`quiz_question_id`)
    REFERENCES `quiz_question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_quiz_guess_choice1`
    FOREIGN KEY (`choice_id`)
    REFERENCES `choice` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

SET SQL_MODE = '';
DROP USER IF EXISTS instructor@localhost;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'instructor'@'localhost' IDENTIFIED BY 'instructor';

GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE * TO 'instructor'@'localhost';
GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE * TO 'instructor'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `user`
-- -----------------------------------------------------
START TRANSACTION;
USE `lldb`;
INSERT INTO `user` (`id`, `username`, `password`, `cohort`, `enabled`, `created_at`, `role`, `first_name`, `last_name`, `updated_at`) VALUES (DEFAULT, 'sheldon', 'test', 'c43', 1, NULL, 'admin', 'sheldon', 'pasciak', NULL);

COMMIT;

