DROP DATABASE `bodb`;
CREATE DATABASE `bodb`;

USE `bodb` 

DROP TABLE IF EXISTS `organizations`;
CREATE TABLE `organizations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_credentials`;
CREATE TABLE `user_credentials` (
  `account_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `organization_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_props`;
CREATE TABLE `user_props` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_id` bigint(20) NOT NULL,
  `prop_key` varchar(50) NOT NULL,
  `prop_value` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `organization` bigint(20) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `role_type` bigint(2) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `role_module_permissions`;
CREATE TABLE `role_module_permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `module` bigint(2) DEFAULT NULL,
  `permissions` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `organizations` VALUES (1,0,0,'root'),(2,1,1,'trimark');

INSERT INTO `user_credentials` VALUES (1,2,2,'superuser','password');

INSERT INTO `user_props` VALUES (1,1,'name','Charley Brewster'),(2,1,'phone','+171111111'),(3,1,'mobile','+171000001'),(4,1,'email','charley@frightnight.com');

INSERT INTO `roles` VALUES (1,1,'Supplier',1,'Supplier'),(2,2,'Superuser',2,'Superuser');

INSERT INTO `role_module_permissions` VALUES (1,1,1,1),(2,1,2,1),(3,1,3,1),(4,1,4,127),(5,1,5,127),(6,1,6,31),(7,1,7,191),(8,1,8,31),(9,1,9,959);

