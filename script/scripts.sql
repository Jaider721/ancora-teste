INSERT INTO segseven.tb_usuario
(login, senha)
VALUES('jaider', '$2a$10$XW7ALnY58bqC7NWtEwyBHu4RxH.JstHwkjg7BwNA1KnvCzIwTriLi');



CREATE TABLE `tb_usuario_perfil` (
  `cod_usuario` bigint(20) NOT NULL,
  `cod_perfil` bigint(20) NOT NULL,
  UNIQUE KEY `UK_13q6usc4xaufidxfil9sryadt` (`cod_usuario`,`cod_perfil`),
  KEY `FK5yd034kjds0t0pt7o2dd2u395` (`cod_usuario`),
  CONSTRAINT `FK5yd034kjds0t0pt7o2dd2u395` FOREIGN KEY (`cod_usuario`) REFERENCES `tb_usuario` (`cod_usuario`),
  CONSTRAINT `FK70bo6yb83gf62d5l8xmli2413` FOREIGN KEY (`cod_perfil`) REFERENCES `tb_perfil` (`cod_perfil`)
);