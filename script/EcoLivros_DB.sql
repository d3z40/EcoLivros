-- MySQL Workbench Synchronization
-- Generated: 2010-03-01 23:39
-- Model: New Model
-- Version: 1.0
-- Project: EcoLivros
-- Author: D3z40

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `EcoLivros`;

CREATE SCHEMA IF NOT EXISTS `EcoLivros` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`livro` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `sCodigo` VARCHAR(9) UNIQUE NOT NULL,
  `sAno` VARCHAR(4) NOT NULL,
  `sTitulo` VARCHAR(100) NOT NULL,
  `iEditora` INT(11) NOT NULL,
  `sEdicao` VARCHAR(45) NOT NULL,
  `sISBN` VARCHAR(45) UNIQUE NOT NULL,
  `nNumPaginas` DECIMAL NULL DEFAULT NULL,
  `sSinopse` LONGTEXT NULL DEFAULT NULL,
  `nAltura` DECIMAL NOT NULL,
  `nLargura` DECIMAL NOT NULL,
  `nPeso` DECIMAL NOT NULL,
  `nProfundidade` DECIMAL(2,1) NULL DEFAULT NULL,
  `iGrupoPrecificacao` INT(11) NOT NULL,
  `sCodBarras` VARCHAR(45) UNIQUE NOT NULL,
  `iAtivo` BIT NOT NULL,
  `sMotivo` VARCHAR(100) NOT NULL,
  `dtCadastro` DATE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_livro_grupoprecificacao_idx` (`iGrupoPrecificacao` ASC),
  INDEX `fk_livro_editora_idx` (`iEditora` ASC),
  CONSTRAINT `fk_livro_grupoprecificacao`
    FOREIGN KEY (`iGrupoPrecificacao`)
    REFERENCES `EcoLivros`.`grupoprecificacao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_livro_editora`
    FOREIGN KEY (`iEditora`)
    REFERENCES `EcoLivros`.`editora` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`autor` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `sNomeArtistico` VARCHAR(150) NOT NULL,
  `sNomeCompleto` VARCHAR(150) NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`editora` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `sRazao` VARCHAR(45) NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`grupoprecificacao` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nMargem` DECIMAL NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`categoria` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `sDescricao` VARCHAR(100) NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`livro_categoria` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `iLivro` INT(11) NOT NULL,
  `iCategoria` INT(11) NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`),
  INDEX `fk_livros_categorias_livro_idx` (`iLivro` ASC),
  INDEX `fk_livros_categorias_categoria_idx` (`iCategoria` ASC),
  CONSTRAINT `fk_livros_categorias_livro`
    FOREIGN KEY (`iLivro`)
    REFERENCES `EcoLivros`.`livro` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_livros_categorias_categoria`
    FOREIGN KEY (`iCategoria`)
    REFERENCES `EcoLivros`.`categoria` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`livro_autor` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `iLivro` INT(11) NOT NULL,
  `iAutor` INT(11) NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`),
  INDEX `fk_livros_autores_livro_idx` (`iLivro` ASC),
  INDEX `fk_livros_autores_autor_idx` (`iAutor` ASC),
  CONSTRAINT `fk_livros_autores_livro`
    FOREIGN KEY (`iLivro`)
    REFERENCES `EcoLivros`.`livro` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_livros_autores_autor`
    FOREIGN KEY (`iAutor`)
    REFERENCES `EcoLivros`.`autor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`usuario` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `sNome` VARCHAR(100) NOT NULL,
  `sEmail` VARCHAR(100) UNIQUE NOT NULL,
  `sSenha` VARCHAR(50) NOT NULL,
  `sCPF` VARCHAR(11) NOT NULL UNIQUE,
  `iTipoUsuario` TINYINT NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`endereco` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `iUsuario` INT(11) NOT NULL,
  `sDescricao` VARCHAR(100),
  `sLogradouro` VARCHAR(100),
  `nNumero` INT(11) NOT NULL,
  `sBairro` VARCHAR(100),
  `sCidade` VARCHAR(100),
  `sEstado` VARCHAR(100),
  `sCEP` VARCHAR(8),
  `sUf` VARCHAR(2),
  `dtCadastro` DATE,
  PRIMARY KEY (`id`),
  INDEX `fk_endereco_usuario_idx` (`iUsuario` ASC),
  CONSTRAINT `fk_endereco_usuario`
    FOREIGN KEY (`iUsuario`)
    REFERENCES `EcoLivros`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`cartaodecredito` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `iUsuario` INT(11) NOT NULL,
  `sNomeNoCartao` VARCHAR(100) NOT NULL,
  `sNumeroDoCartao` VARCHAR(100) UNIQUE NOT NULL,
  `nMesValidade` INT(2) NOT NULL,
  `nAnoValidade` INT(2) NOT NULL,
  `nCodigoSeguranca` INT(3) NOT NULL,
  `nBandeira` TINYINT NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`),
  INDEX `fk_cartaodecredito_usuario_idx` (`iUsuario` ASC),
  CONSTRAINT `fk_cartaodecredito_usuario`
    FOREIGN KEY (`iUsuario`)
    REFERENCES `EcoLivros`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`acesso` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `sDescricao` VARCHAR(100) NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`funcionario` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `iUsuario` INT(11) NOT NULL,
  `iAcesso` INT(11) NOT NULL,
  `sNumRegistro` VARCHAR(10) UNIQUE NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`),
  INDEX `fk_funcionario_usuario_idx` (`iUsuario` ASC),
  INDEX `fk_funcionario_acesso_idx` (`iAcesso` ASC),
  CONSTRAINT `fk_funcionario_usuario`
    FOREIGN KEY (`iUsuario`)
    REFERENCES `EcoLivros`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_funcionario_acesso`
    FOREIGN KEY (`iAcesso`)
    REFERENCES `EcoLivros`.`acesso` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`controledeacesso` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `iAcesso` INT(11) NOT NULL,
  `iTipoAcesso` INT(11) NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`),
  INDEX `fk_controledeacesso_acesso_idx` (`iAcesso` ASC),
  CONSTRAINT `fk_controledeacesso_acesso`
    FOREIGN KEY (`iAcesso`)
    REFERENCES `EcoLivros`.`acesso` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`carrinho` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `iLivro` INT(11) NOT NULL,
  `iUsuario` INT(11) NOT NULL,
  `nQtde` INT(3) NOT NULL,
  `iSituacaoEstoque` INT(11) NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`),
  INDEX `fk_carrinho_livro_idx` (`iLivro` ASC),
  INDEX `fk_carrinho_usuario_idx` (`iUsuario` ASC),
  CONSTRAINT `fk_carrinho_livro`
    FOREIGN KEY (`iLivro`)
    REFERENCES `EcoLivros`.`livro` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_carrinho_usuario`
    FOREIGN KEY (`iUsuario`)
    REFERENCES `EcoLivros`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`fornecedor` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `sRazaoSocial` VARCHAR(150) NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`nota` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `iFornecedor` INT(11) NOT NULL,
  `sNumeroNota` VARCHAR(150) UNIQUE NOT NULL,
  `iSituacaoEstoque` INT(11) NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`),
  INDEX `fk_nota_fornecedor_idx` (`iFornecedor` ASC),
  CONSTRAINT `fk_nota_fornecedor`
    FOREIGN KEY (`iFornecedor`)
    REFERENCES `EcoLivros`.`fornecedor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`itemnota` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `iNota` INT(11) NOT NULL,
  `iLivro` INT(11) NOT NULL,
  `iQtde` INT NOT NULL,
  `nValor` DECIMAL NOT NULL,
  `iSituacaoEstoque` INT(11) NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`),
  INDEX `fk_itemnota_nota_idx` (`iNota` ASC),
  INDEX `fk_itemnota_livro_idx` (`iLivro` ASC),
  CONSTRAINT `fk_itemnota_nota`
    FOREIGN KEY (`iNota`)
    REFERENCES `EcoLivros`.`nota` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_itemnota_livro`
    FOREIGN KEY (`iLivro`)
    REFERENCES `EcoLivros`.`livro` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`pedido` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `iUsuario` INT(11) NOT NULL,
  `iFrete` INT(11) NOT NULL,
  `sNumeroPedido` VARCHAR(150) UNIQUE NOT NULL,
  `iEndereco` INT(11) NOT NULL,
  `nTotal` DECIMAL NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`),
  INDEX `fk_pedido_usuario_idx` (`iUsuario` ASC),
  INDEX `fk_pedido_endereco_idx` (`iEndereco` ASC),
  CONSTRAINT `fk_pedido_usuario`
    FOREIGN KEY (`iUsuario`)
    REFERENCES `EcoLivros`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedido_endereco`
    FOREIGN KEY (`iEndereco`)
    REFERENCES `EcoLivros`.`endereco` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`estoque` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `iLivro` INT(11) NOT NULL,
  `nValor` DECIMAL NOT NULL,
  `iSituacaoEstoque` INT(11) NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`),
  INDEX `fk_estoque_livro_idx` (`iLivro` ASC),
  CONSTRAINT `fk_estoque_livro`
    FOREIGN KEY (`iLivro`)
    REFERENCES `EcoLivros`.`livro` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`itempedido` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `iPedido` INT(11) NOT NULL,
  `iEstoque` INT(11) NOT NULL,
  `iLivro` INT(11) NOT NULL,
  `nQtde` INT NOT NULL,
  `nValor` DECIMAL NOT NULL,
  `iSituacaoItem` INT(11) NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`),
  INDEX `fk_itempedido_pedido_idx` (`iPedido` ASC),
  INDEX `fk_itempedido_estoque_idx` (`iEstoque` ASC),
  INDEX `fk_itempedido_livro_idx` (`iLivro` ASC),
  CONSTRAINT `fk_itempedido_pedido`
    FOREIGN KEY (`iPedido`)
    REFERENCES `EcoLivros`.`pedido` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_itempedido_estoque`
    FOREIGN KEY (`iEstoque`)
    REFERENCES `EcoLivros`.`estoque` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_itempedido_livro`
    FOREIGN KEY (`iLivro`)
    REFERENCES `EcoLivros`.`livro` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`formadepagamento` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `iPedido` INT(11) NOT NULL,
  `iPagamento` INT (11) NOT NULL,
  `nValor` DECIMAL NOT NULL,
  `iTipoPagamento` INT(11) NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`),
  INDEX `fk_formadepagamento_pedido_idx` (`iPedido` ASC),
  CONSTRAINT `fk_formadepagamento_pedido`
    FOREIGN KEY (`iPedido`)
    REFERENCES `EcoLivros`.`pedido` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`cupom` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `iUsuario` INT(11) NOT NULL,
  `sIdentificador` VARCHAR(150) NOT NULL,
  `nValorCupom` DECIMAL NOT NULL,
  `nValorUtilizado` DECIMAL NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`),
  INDEX `fk_cupom_usuario_idx` (`iUsuario` ASC),
  CONSTRAINT `fk_cupom_usuario`
    FOREIGN KEY (`iUsuario`)
    REFERENCES `EcoLivros`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`boleto` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `iUsuario` INT(11) NOT NULL,
  `sCodigoBarras` VARCHAR(150) NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`),
  INDEX `fk_boleto_usuario_idx` (`iUsuario` ASC),
  CONSTRAINT `fk_boleto_usuario`
    FOREIGN KEY (`iUsuario`)
    REFERENCES `EcoLivros`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EcoLivros`.`precupom` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `iUsuario` INT(11) NOT NULL,
  `iItemPedido` INT(11) NOT NULL,
  `nValor` DECIMAL NOT NULL,
  `iSituacaoEstoque` INT(11) NOT NULL,
  `dtCadastro` DATE,
  PRIMARY KEY (`id`),
  INDEX `fk_precupom_usuario_idx` (`iUsuario` ASC),
  INDEX `fk_precupom_itempedido_idx` (`iUsuario` ASC),
  CONSTRAINT `fk_precupom_usuario`
    FOREIGN KEY (`iUsuario`)
    REFERENCES `EcoLivros`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_precupom_itempedido`
    FOREIGN KEY (`iItempedido`)
    REFERENCES `EcoLivros`.`itempedido` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

INSERT INTO EcoLivros.autor (autor.sNomeArtistico, autor.sNomeCompleto, dtCadastro)
						VALUES ('William P. Young', 'William P. Young', current_date()),
                               ('Augusto Cury', 'Augusto Cury', current_date()),
                               ('Sarah Cohen-Scali', 'Sarah Cohen-Scali', current_date()),
                               ('Rosane Albert', 'Rosane Albert', current_date()),
                               ('Machado de Assis', 'Machado de Assis', current_date()),
                               ('J. K. Rowling', 'J. K. Rowling', current_date());

INSERT INTO EcoLivros.editora (editora.sRazao, dtCadastro)
						VALUES ('Sextante', current_date()),
                               ('Planeta', current_date()),
                               ('Jangada', current_date()),
                               ('Komedi', current_date()),
                               ('Rocco', current_date());

INSERT INTO EcoLivros.grupoprecificacao (grupoprecificacao.nMargem, dtCadastro)
						VALUES (10, current_date()),
                               (15, current_date()),
                               (20, current_date());

INSERT INTO EcoLivros.categoria (categoria.sDescricao, dtCadastro)
						VALUES ("Ficção", current_date()),
                               ("Romance", current_date()),
                               ("Guerra", current_date()),
                               ("Aventura", current_date()),
                               ("Literatura Infanto-Juvenil", current_date());


INSERT INTO EcoLivros.livro (livro.sCodigo, livro.sAno, livro.sTitulo, livro.iEditora, livro.sEdicao, livro.sISBN, livro.nNumPaginas, livro.sSinopse, livro.nAltura, livro.nLargura, livro.nPeso, livro.nProfundidade, livro.iGrupoPrecificacao, livro.sCodBarras, livro.iAtivo, livro.sMotivo, livro.dtCadastro)
						VALUES ('123456', '2008', 'A Cabana', 1, '1','9788599296363',240, 'O livro aborda a questão recorrente da existência do mal através da história de Mack Allen Phillips, um homem que vive sob o peso da experiência de ter sua filha Missy, de seis anos, raptada durante um acampamento de fim de semana.', 23, 16, 250, 1.5, 1, '98765', 0, 'Novo Livro', current_date()),
							   ('123457', '2016', 'Colecionador de Lagrimas', 2, '6','9788576658085',376, 'Um professor especialista em nazismo e II Guerra Mundial começa a ter insônias e pesadelos, como se estivesse vivendo as atrocidades ocorridas durante o Nazismo. Em um ponto de desatino, diz que os alunos são parceiros de Hitler. Sua intenção é provocar a sensibilidade e a curiosidade de seus alunos. Bem quisto por alguns, mas criticado e processado por outros, sua fama coloca os holofotes sobre ele, ainda mais quando um complô nazista parece persiguí-lo.', 23, 16, 450, 3, 2, '98766', 0, 'Novo Livro', current_date()),
                               ('123458', '2016', 'Max', 3, '1','9788555390395',388, '', 23, 16, 550, 2, 3, '98767', 0, 'Novo Livro', current_date()),
                               ('123459', '2011', 'Helena', 4, '20','9788575826157',238, 'O romance começa com o anúncio da morte do Conselheiro Vale, pai de Estácio e irmão de Dona Úrsula. O Conselheiro é retratado como homem de boas relações, relativa fortuna e certo gosto pela vida boêmia. Em seu testamento, ele reconhecia uma filha natural chamada Helena, pedindo para a família que a recebesse assim que a menina saísse da escola. Já que a existência da moça era até então desconhecida para Estácio e Dona Úrsula, eles a recebem com sentimentos mistos. Estácio rapidamente acolhe a nova irmã e lhe conquista a confiança, enquanto Dona Úrsula resiste por algum tempo à ideia de dividir seus afetos com uma desconhecida.', 21, 14, 150, 1, 1, '98768', 0, 'Novo Livro', current_date()),
                               ('123460', '2000', 'Harry Potter e a Pedra Filosofal', 5, '9','9788532523051',263, 'Harry Potter é um garoto comum que vive num armário debaixo da escada da casa de seus tios. Sua vida muda quando ele é resgatado por uma coruja e levado para a escola de magia e bruxaria de Hogwarts. Lá ele descobre tudo sobre a misteriosa morte de seus pais, aprende a jogar quadribol e enfrenta, num duelo, o cruel Voldemort.', 21, 14, 100, 1.5, 2, '98769', 0, 'Novo Livro', current_date());

INSERT INTO EcoLivros.livro_categoria (livro_categoria.iLivro, livro_categoria.iCategoria, livro_categoria.dtCadastro)
						VALUES (1, 1, current_date()),
                               (2, 1, current_date()),
                               (2, 2, current_date()),
                               (3, 1, current_date()),
                               (3, 3, current_date()),
                               (4, 2, current_date()),
                               (5, 4, current_date()),
                               (5, 5, current_date());

INSERT INTO EcoLivros.livro_autor (livro_autor.iLivro, livro_autor.iAutor, livro_autor.dtCadastro)
						VALUES (1, 1, current_date()),
                               (2, 2, current_date()),
                               (3, 3, current_date()),
                               (3, 4, current_date()),
                               (4, 5, current_date()),
                               (5, 6, current_date());

INSERT INTO EcoLivros.acesso (acesso.sDescricao, dtCadastro)
						VALUES ("Presidente", current_date()),
                               ("Gerente", current_date()),
                               ("Funcionario", current_date());

INSERT INTO EcoLivros.controledeacesso (controledeacesso.iAcesso, controledeacesso.iTipoAcesso, dtCadastro)
						VALUES (1, 1, current_date()),
                               (1, 2, current_date()),
                               (1, 3, current_date()),
                               (1, 4, current_date()),
                               (1, 5, current_date()),
                               (2, 5, current_date()),
                               (3, 1, current_date()),
                               (3, 2, current_date()),
                               (3, 3, current_date()),
                               (3, 4, current_date());
                               
INSERT INTO EcoLivros.usuario (usuario.sNome, usuario.sEmail, usuario.sSenha, usuario.sCPF, usuario.iTipoUsuario, usuario.dtCadastro)
						VALUES ('Fulano Silva', 'fulano.silva@yahoo.com.br', '123456', '35676893423', 2, current_date()),
							   ('Ciclano Souza', 'cilano.souza@hotmail.com', '654321', '31976858798', 1, current_date()),
                               ('Zé Maria', 'ze.maria@gmail.com', '987654', '32054364753', 2, current_date());

INSERT INTO EcoLivros.funcionario (funcionario.iUsuario, funcionario.iAcesso, funcionario.sNumRegistro, funcionario.dtCadastro)
						VALUES (1, 1, '103084', current_date()),
                               (3, 3, '103082', current_date());

INSERT INTO EcoLivros.endereco (endereco.iUsuario, endereco.sDescricao, endereco.sLogradouro, endereco.nNumero, endereco.sBairro, endereco.sCidade, endereco.sEstado, endereco.sCEP, endereco.sUf, endereco.dtCadastro)
						VALUES (2, 'Casa', 'Rua dos Alfineiros', 4, 'Surrey', 'Little Whinging', 'Londres', '12345678', 'SP', current_date()),
							   (2, 'Mãe', 'Largo Grimmauld', 12, 'Furrey', 'Big Whinging', 'Londres', '08695440', 'SP', current_date());

INSERT INTO EcoLivros.cartaodecredito (cartaodecredito.iUsuario, cartaodecredito.sNomeNoCartao, cartaodecredito.sNumeroDoCartao, cartaodecredito.nMesValidade, cartaodecredito.nAnoValidade, cartaodecredito.nCodigoSeguranca, cartaodecredito.nBandeira, cartaodecredito.dtCadastro)
						VALUES (2, 'Ciclano Souza', '4325876556342431', 8, 2024, 123, 2, current_date()),
                               (2, 'Ciclano Souza Silva', '8765096712538754', 12, 2025, 321, 3, current_date());

INSERT INTO EcoLivros.fornecedor (fornecedor.sRazaoSocial, fornecedor.dtCadastro)
						VALUES ('Entrega Quase Tudo', current_date()),
                               ('Trans Vai-Q-Vai', current_date()),
                               ('Esperamos Q Chegue', current_date());

INSERT INTO EcoLivros.nota (nota.iFornecedor, nota.sNumeroNota, nota.iSituacaoEstoque, nota.dtCadastro)
						VALUES (1, '123', 1, current_date()),
                               (3, '456', 1, current_date());

INSERT INTO EcoLivros.itemnota (itemnota.iNota, itemnota.iLivro, itemnota.iQtde, itemnota.nValor, itemnota.iSituacaoEstoque, itemnota.dtCadastro)
						VALUES (1, 1, 5, 15, 1, current_date()),
							   (1, 2, 5, 20, 1, current_date()),
                               (1, 3, 5, 25, 1, current_date()),
                               (2, 4, 5, 30, 1, current_date()),
                               (2, 5, 5, 35, 1, current_date());

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;