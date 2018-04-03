-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema store
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema store
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `store` DEFAULT CHARACTER SET utf8 ;
USE `store` ;
SET FOREIGN_KEY_CHECKS=0;
-- -----------------------------------------------------
-- Table `store`.`cash_flow`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`cash_flow` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `amount` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`supplier`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`supplier` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`delivery`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`delivery` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `supplier_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_delivery_supplier1_idx` (`supplier_id` ASC),
  CONSTRAINT `fk_delivery_supplier1`
    FOREIGN KEY (`supplier_id`)
    REFERENCES `store`.`supplier` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`department`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`department` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`product` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `amount` INT(11) NOT NULL,
  `min_amount` VARCHAR(45) NULL DEFAULT NULL,
  `department_id` INT(11) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_department_idx` (`department_id` ASC),
  CONSTRAINT `fk_product_department`
    FOREIGN KEY (`department_id`)
    REFERENCES `store`.`department` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`delivery_has_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`delivery_has_product` (
  `delivery_id` INT(11) NOT NULL,
  `product_id` INT(11) NOT NULL,
  `price` BIGINT(20) UNSIGNED NOT NULL,
  `amount` INT(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`delivery_id`, `product_id`),
  INDEX `fk_delivery_has_product_product1_idx` (`product_id` ASC),
  INDEX `fk_delivery_has_product_delivery1_idx` (`delivery_id` ASC),
  CONSTRAINT `fk_delivery_has_product_delivery1`
    FOREIGN KEY (`delivery_id`)
    REFERENCES `store`.`delivery` (`id`)
    ON DELETE cascade
    ON UPDATE cascade,
  CONSTRAINT `fk_delivery_has_product_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `store`.`product` (`id`)
    ON DELETE cascade
    ON UPDATE cascade)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`employee` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NOT NULL,
  `department_id` INT(11) NOT NULL,
  `salary` BIGINT(20) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_employee_department1_idx` (`department_id` ASC),
  CONSTRAINT `fk_employee_department1`
    FOREIGN KEY (`department_id`)
    REFERENCES `store`.`department` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`order` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `employee_id` INT(11) NOT NULL,
  `supplier_id` INT(11) NOT NULL,
  `delivery_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Order_employee1_idx` (`employee_id` ASC),
  INDEX `fk_order_supplier1_idx` (`supplier_id` ASC),
  INDEX `fk_order_delivery1_idx` (`delivery_id` ASC),
  CONSTRAINT `fk_Order_employee1`
    FOREIGN KEY (`employee_id`)
    REFERENCES `store`.`employee` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_delivery1`
    FOREIGN KEY (`delivery_id`)
    REFERENCES `store`.`delivery` (`id`)
    ON DELETE cascade
    ON UPDATE cascade,
  CONSTRAINT `fk_order_supplier1`
    FOREIGN KEY (`supplier_id`)
    REFERENCES `store`.`supplier` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`order_has_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`order_has_product` (
  `order_id` INT(11) NOT NULL,
  `product_id` INT(11) NOT NULL,
  `amount` INT(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`order_id`, `product_id`),
  INDEX `fk_order_has_product_product1_idx` (`product_id` ASC),
  INDEX `fk_order_has_product_order1_idx` (`order_id` ASC),
  CONSTRAINT `fk_order_has_product_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `store`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_has_product_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `store`.`product` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`paid_bonus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`paid_bonus` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `size` BIGINT(20) UNSIGNED NOT NULL,
  `date` DATE NOT NULL,
  `employee_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_bonus_employee1_idx` (`employee_id` ASC),
  CONSTRAINT `fk_bonus_employee1`
    FOREIGN KEY (`employee_id`)
    REFERENCES `store`.`employee` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`paid_salary`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`paid_salary` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `salary` BIGINT(20) NOT NULL,
  `date` DATE NOT NULL,
  `employee_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Salary_employee1_idx` (`employee_id` ASC),
  CONSTRAINT `fk_Salary_employee1`
    FOREIGN KEY (`employee_id`)
    REFERENCES `store`.`employee` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`payment` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `amount` BIGINT(20) NOT NULL,
  `delivery_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_payment_delivery1_idx` (`delivery_id` ASC),
  CONSTRAINT `fk_payment_delivery1`
    FOREIGN KEY (`delivery_id`)
    REFERENCES `store`.`delivery` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`price`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`price` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `price` BIGINT(20) UNSIGNED NOT NULL,
  `product_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_price_product1_idx` (`product_id` ASC),
  CONSTRAINT `fk_price_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `store`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`purchase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`purchase` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`product_has_purchase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`product_has_purchase` (
  `product_id` INT(11) NOT NULL,
  `purchase_id` INT(11) NOT NULL,
  `amount` INT(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`product_id`, `purchase_id`),
  INDEX `fk_product_has_purchase_purchase1_idx` (`purchase_id` ASC),
  INDEX `fk_product_has_purchase_product1_idx` (`product_id` ASC),
  CONSTRAINT `fk_product_has_purchase_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `store`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_has_purchase_purchase1`
    FOREIGN KEY (`purchase_id`)
    REFERENCES `store`.`purchase` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
