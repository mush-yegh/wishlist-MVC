--'user'
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `mail` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL DEFAULT 'qwerty',
  `birth_date` date NOT NULL,
  `active` tinyint(4) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `role` varchar(45) NOT NULL DEFAULT 'USER',
  `state` varchar(45) NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mail` (`mail`))
  ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8

--'wish'
CREATE TABLE `wish` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `owner_id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL,
  `link` varchar(255) DEFAULT NULL,
  `description` text,
  `price` decimal(10,2) NOT NULL,
  `created` date NOT NULL,
  `updated` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkqi4lso34o5xjkhiw71uadwvu` (`owner_id`),
  CONSTRAINT `FKkqi4lso34o5xjkhiw71uadwvu` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`))
  ENGINE=InnoDB  DEFAULT CHARSET=utf8

--'request'
CREATE TABLE `request` (
  `request_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sender_id` bigint(20) NOT NULL,
  `recipient_id` bigint(20) NOT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'PENDING',
  `request_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `response_date` date DEFAULT NULL,
  PRIMARY KEY (`request_id`),
  UNIQUE KEY `index4` (`sender_id`,`recipient_id`),
  KEY `fk_request_1_idx` (`sender_id`),
  KEY `fk_request_2_idx` (`recipient_id`),
  CONSTRAINT `fk_request_1` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_request_2` FOREIGN KEY (`recipient_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION)
  ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE DEFINER=`root`@`localhost` TRIGGER `wishlist_test`.`request_AFTER_UPDATE` AFTER UPDATE ON `request` FOR EACH ROW
BEGIN
	If NEW.status = 'ACCEPTED'	THEN
	insert into friend(user_one_id, user_two_id, is_deleted)
	values (NEW.sender_id, NEW.recipient_id, 0);

	insert into friend(user_one_id, user_two_id, is_deleted)
	values (NEW.recipient_id, NEW.sender_id, 0);
	END IF;
END

--'friend',
CREATE TABLE `friend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_one_id` bigint(20) NOT NULL,
  `user_two_id` bigint(20) NOT NULL,
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `id_one_two_UNIQUE` (`user_one_id`,`user_two_id`),
  UNIQUE KEY `id_two_one_UNIQUE` (`user_two_id`,`user_one_id`),
  KEY `id_one_two_INDEX` (`user_one_id`,`user_two_id`),
  KEY `id_two_one_INDEX` (`user_two_id`,`user_one_id`),
  CONSTRAINT `FK5l3ullgl1vop7yesd2eaprd93` FOREIGN KEY (`user_two_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKq2pntl76r1p2sv7yae2e5opqb` FOREIGN KEY (`user_one_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_friend_user_one_id` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_friend_user_two_id` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION)
  ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8
