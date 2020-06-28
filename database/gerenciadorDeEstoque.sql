-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema GerenciadorDeEstoque
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema GerenciadorDeEstoque
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `GerenciadorDeEstoque` ;
CREATE SCHEMA IF NOT EXISTS `GerenciadorDeEstoque` DEFAULT CHARACTER SET utf8 ;
USE `GerenciadorDeEstoque` ;

-- -----------------------------------------------------
-- Table `GerenciadorDeEstoque`.`Produto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GerenciadorDeEstoque`.`Produto` (
  `codigo` INT UNSIGNED NOT NULL,
  `nomeProduto` VARCHAR(20) NOT NULL,
  `descricao` LONGTEXT NULL,
  `preco` FLOAT NOT NULL,
  `qtdEstoque` VARCHAR(45) NULL,
  PRIMARY KEY (`codigo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GerenciadorDeEstoque`.`VendaDiária`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GerenciadorDeEstoque`.`VendaDiaria` (
  `idVenda` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `valor` FLOAT NOT NULL,
  `dataVenda` DATE NOT NULL,
  PRIMARY KEY (`idVenda`))
ENGINE = InnoDB;

SELECT * FROM VendaDiaria WHERE MONTH(dataVenda) = 6 AND YEAR(dataVenda) = 2020;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;