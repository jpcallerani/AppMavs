CREATE TABLE `mundofac_1`.`slineup` (
  `id` INT NOT NULL COMMENT '',
  `id_usuario` INT NOT NULL COMMENT '',
  `data` DATE NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `FK_USUARIO_LINEUP_idx` (`id_usuario` ASC)  COMMENT '',
  CONSTRAINT `FK_USUARIO_LINEUP`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `mundofac_1`.`slogin_users` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
