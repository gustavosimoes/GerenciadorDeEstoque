SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
-- -----------------------------------------------------
-- Schema GerenciadorDeEstoque
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `GerenciadorDeEstoque` ;
CREATE SCHEMA IF NOT EXISTS `GerenciadorDeEstoque` DEFAULT CHARACTER SET utf8 ;
USE `GerenciadorDeEstoque` ;
-- -----------------------------------------------------
-- Table `GerenciadorDeEstoque`.`Cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GerenciadorDeEstoque`.`Cliente` (
  `idCliente` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL UNIQUE,
  `cpf` BIGINT NULL DEFAULT NULL,
  `telefone` BIGINT NULL DEFAULT NULL,
  `endereco` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`idCliente`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `GerenciadorDeEstoque`.`Produto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GerenciadorDeEstoque`.`Produto` (
  `codigo` BIGINT UNSIGNED NOT NULL,
  `nomeProduto` VARCHAR(20) NOT NULL,
  `descricao` LONGTEXT NULL DEFAULT NULL,
  `precoDeCusto` FLOAT NULL,
  `preco` FLOAT NOT NULL,
  `qtdEstoque` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`codigo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `GerenciadorDeEstoque`.`VendaDiaria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GerenciadorDeEstoque`.`VendaDiaria` (
  `idVenda` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `valor` FLOAT NOT NULL,
  `dataVenda` DATE NOT NULL,
  `Cliente_idCliente` INT UNSIGNED,
  PRIMARY KEY (`idVenda`),
  INDEX `fk_VendaDiaria_Cliente_idx` (`Cliente_idCliente` ASC),
  CONSTRAINT `fk_VendaDiaria_Cliente`
    FOREIGN KEY (`Cliente_idCliente`)
    REFERENCES `GerenciadorDeEstoque`.`Cliente` (`idCliente`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

select * from Produto;
select * from VendaDiaria;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
