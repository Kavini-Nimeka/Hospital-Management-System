-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema new_schema1
-- -----------------------------------------------------
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`doctor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`doctor` (
                                               `id` INT NOT NULL AUTO_INCREMENT,
                                               `name` TEXT NULL,
                                               `special` TEXT NULL,
                                               `degree` TEXT NULL,
                                               `price` DOUBLE NULL,
                                               PRIMARY KEY (`id`),
                                               UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`patient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`patient` (
                                                `id` INT NOT NULL AUTO_INCREMENT,
                                                `name` TEXT NULL,
                                                `phone` VARCHAR(45) NULL,
                                                `dob` DATETIME NULL,
                                                `address` TEXT NULL,
                                                `username` VARCHAR(45) NULL,
                                                `password` VARCHAR(45) NULL,
                                                PRIMARY KEY (`id`),
                                                UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`appointment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`appointment` (
                                                    `id` INT NOT NULL AUTO_INCREMENT,
                                                    `patient` INT NULL,
                                                    `status` TEXT NULL,
                                                    `reserved_time` DATETIME NULL,
                                                    `doctor` INT NULL,
                                                    `amount` DOUBLE NULL,
                                                    PRIMARY KEY (`id`),
                                                    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
                                                    INDEX `fk_doca_idx` (`doctor` ASC) VISIBLE,
                                                    INDEX `fk_patienttttgdlk8_idx` (`patient` ASC) VISIBLE,
                                                    CONSTRAINT `fk_doca`
                                                        FOREIGN KEY (`doctor`)
                                                            REFERENCES `mydb`.`doctor` (`id`)
                                                            ON DELETE NO ACTION
                                                            ON UPDATE NO ACTION,
                                                    CONSTRAINT `fk_patienttttgdlk8`
                                                        FOREIGN KEY (`patient`)
                                                            REFERENCES `mydb`.`patient` (`id`)
                                                            ON DELETE NO ACTION
                                                            ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`prescription`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`prescription` (
                                                     `id` INT NOT NULL AUTO_INCREMENT,
                                                     `date` DATE NULL,
                                                     `appointment` INT NULL,
                                                     PRIMARY KEY (`id`),
                                                     UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
                                                     INDEX `fk_appointment_idx` (`appointment` ASC) VISIBLE,
                                                     CONSTRAINT `fk_appointment`
                                                         FOREIGN KEY (`appointment`)
                                                             REFERENCES `mydb`.`appointment` (`id`)
                                                             ON DELETE NO ACTION
                                                             ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`item` (
                                             `id` INT NOT NULL AUTO_INCREMENT,
                                             `name` TEXT NULL,
                                             `price` DOUBLE NULL,
                                             PRIMARY KEY (`id`),
                                             UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`prescription_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`prescription_item` (
                                                          `id` INT NOT NULL AUTO_INCREMENT,
                                                          `prescription` INT NULL,
                                                          `item` INT NULL,
                                                          `days` INT NULL,
                                                          `note` TEXT NULL,
                                                          `deleted` TINYINT(1) NULL,
                                                          PRIMARY KEY (`id`),
                                                          UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
                                                          INDEX `fk_pres7h8_idx` (`prescription` ASC) VISIBLE,
                                                          INDEX `fk_itemsgd87k_idx` (`item` ASC) VISIBLE,
                                                          CONSTRAINT `fk_pres7h8`
                                                              FOREIGN KEY (`prescription`)
                                                                  REFERENCES `mydb`.`prescription` (`id`)
                                                                  ON DELETE NO ACTION
                                                                  ON UPDATE NO ACTION,
                                                          CONSTRAINT `fk_itemsgd87k`
                                                              FOREIGN KEY (`item`)
                                                                  REFERENCES `mydb`.`item` (`id`)
                                                                  ON DELETE NO ACTION
                                                                  ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`admin` (
                                              `id` INT NOT NULL AUTO_INCREMENT,
                                              `username` VARCHAR(45) NULL,
                                              `password` VARCHAR(45) NULL,
                                              PRIMARY KEY (`id`))
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
