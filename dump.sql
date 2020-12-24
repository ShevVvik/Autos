-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               10.5.6-MariaDB - mariadb.org binary distribution
-- Операционная система:         Win64
-- HeidiSQL Версия:              11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Дамп структуры базы данных autos
CREATE DATABASE IF NOT EXISTS `autos` /*!40100 DEFAULT CHARACTER SET utf16 COLLATE utf16_unicode_ci */;
USE `autos`;

-- Дамп структуры для процедура autos.addCommentToOrder
DELIMITER //
CREATE PROCEDURE `addCommentToOrder`(
	IN `username` VARCHAR(150),
	IN `orderId` INT,
	IN `text` VARCHAR(250)
)
BEGIN

	INSERT INTO comments (offer, USER, message, DATE)
	VALUES (orderId, (SELECT id FROM users WHERE login = username), TEXT, CURDATE());

END//
DELIMITER ;

-- Дамп структуры для процедура autos.assignOrder
DELIMITER //
CREATE PROCEDURE `assignOrder`(
	IN `orderId` INT,
	IN `username` VARCHAR(150)
)
BEGIN

	update offers SET dealer = (SELECT dea.id FROM users us, dealers dea WHERE dea.user_id = us.id AND us.login = username) WHERE id = orderId;

END//
DELIMITER ;

-- Дамп структуры для таблица autos.autos
CREATE TABLE IF NOT EXISTS `autos` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `brand` varchar(50) NOT NULL DEFAULT '',
  `model` varchar(50) NOT NULL DEFAULT '',
  `mileage` int(10) NOT NULL,
  `date` date NOT NULL,
  `vin` char(17) NOT NULL DEFAULT '',
  `car_number` char(17) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- Дамп данных таблицы autos.autos: ~4 rows (приблизительно)
/*!40000 ALTER TABLE `autos` DISABLE KEYS */;
INSERT INTO `autos` (`id`, `brand`, `model`, `mileage`, `date`, `vin`, `car_number`) VALUES
	(7, 'Tesla', 'Model 3', 200000, '1999-01-01', '989898', '232323'),
	(8, 'Tesla', 'Model X', 200000, '1999-01-01', '9898982', '232323'),
	(9, 'VAZ', '2107', 200000, '2000-01-01', '32423443', 'A456KP'),
	(10, 'Tesla', 'Model 3', 2000, '2020-01-01', '3214234', 'a435ff'),
	(11, 'Tesla', 'Model X', 2000, '2020-01-01', '4F2YU08102KM26251', 'A567KE');
/*!40000 ALTER TABLE `autos` ENABLE KEYS */;

-- Дамп структуры для процедура autos.cancelOrder
DELIMITER //
CREATE PROCEDURE `cancelOrder`(
	IN `orderId` INT
)
BEGIN
	
	UPDATE offers SET STATUS = 3 WHERE id = orderId;
	UPDATE offers SET date_end = NOW() WHERE id = orderId;
	
END//
DELIMITER ;

-- Дамп структуры для процедура autos.changeOrderStatus
DELIMITER //
CREATE PROCEDURE `changeOrderStatus`(
	IN `orderId` INT,
	IN `newStatus` INT
)
BEGIN

	UPDATE offers SET STATUS = newStatus WHERE id = orderId;
	
	if (newStatus = 1) then
		UPDATE offers SET date_end = NOW() WHERE id = orderId;
	END if;
END//
DELIMITER ;

-- Дамп структуры для функция autos.checkLogin
DELIMITER //
CREATE FUNCTION `checkLogin`(`username` VARCHAR(150)
) RETURNS int(11)
BEGIN
	DECLARE cnt INT;
	SELECT
		COUNT(*)
	into
		cnt
	FROM
		users us
	WHERE
		us.login = username;
	RETURN cnt;
END//
DELIMITER ;

-- Дамп структуры для таблица autos.cities
CREATE TABLE IF NOT EXISTS `cities` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Дамп данных таблицы autos.cities: ~2 rows (приблизительно)
/*!40000 ALTER TABLE `cities` DISABLE KEYS */;
INSERT INTO `cities` (`id`, `name`) VALUES
	(1, 'Saratov'),
	(2, 'Engels');
/*!40000 ALTER TABLE `cities` ENABLE KEYS */;

-- Дамп структуры для таблица autos.clients
CREATE TABLE IF NOT EXISTS `clients` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `last_name` varchar(60) NOT NULL DEFAULT '',
  `patronymic` varchar(60) NOT NULL DEFAULT '',
  `first_name` varchar(60) NOT NULL DEFAULT '',
  `city` int(10) NOT NULL DEFAULT 0,
  `address` varchar(250) DEFAULT '',
  `phone` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_clients_user_id` (`user_id`),
  KEY `FK_clients_city` (`city`),
  CONSTRAINT `FK_clients_city` FOREIGN KEY (`city`) REFERENCES `cities` (`id`),
  CONSTRAINT `FK_clients_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Дамп данных таблицы autos.clients: ~3 rows (приблизительно)
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` (`id`, `user_id`, `last_name`, `patronymic`, `first_name`, `city`, `address`, `phone`) VALUES
	(1, 3, 'Sheviakov', 'Sergeevich', 'Dmitrii', 1, '123', 0),
	(2, 4, 'Sheviakov', 'Shev', 'Dmitrii', 1, '', 0),
	(3, 7, 'Sheviakov', 'Shev', 'Dmitrii', 2, '', 0),
	(4, 11, 'Sheviakov', 'Shev', 'Dmitrii', 1, '12331', 77777777777);
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;

-- Дамп структуры для таблица autos.comments
CREATE TABLE IF NOT EXISTS `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `offer` int(11) NOT NULL DEFAULT 0,
  `user` int(11) NOT NULL DEFAULT 0,
  `message` varchar(250) NOT NULL DEFAULT '0',
  `date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `FK_comments_offer` (`offer`),
  KEY `FK_comments_user` (`user`),
  CONSTRAINT `FK_comments_offer` FOREIGN KEY (`offer`) REFERENCES `offers` (`id`),
  CONSTRAINT `FK_comments_user` FOREIGN KEY (`user`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Дамп данных таблицы autos.comments: ~2 rows (приблизительно)
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` (`id`, `offer`, `user`, `message`, `date`) VALUES
	(1, 1, 1, 'Test', '2020-12-18 22:21:15'),
	(2, 1, 1, 'Test 2', '2020-12-18 22:21:31'),
	(3, 1, 1, 'Test 3', '2020-12-18 00:00:00');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;

-- Дамп структуры для процедура autos.createOrder
DELIMITER //
CREATE PROCEDURE `createOrder`(
	IN `brand` VARCHAR(150),
	IN `model` VARCHAR(150),
	IN `mileage` INT,
	IN `date` DATE,
	IN `vin` VARCHAR(150),
	IN `carNumber` VARCHAR(6),
	IN `username` VARCHAR(150),
	IN `price` INT
)
BEGIN
	DECLARE auto_id INT;
	INSERT INTO 
		autos
		(brand, model, mileage, DATE, vin, car_number)
	VALUES
		(brand, model, mileage, DATE, vin, carNumber);
	
	SELECT 
		a.id
	INTO
		auto_id
	FROM
		autos a
	WHERE
			a.brand = brand
			AND a.model = model
			AND a.mileage = mileage
			AND a.date = date
			AND a.vin = vin
			AND a.car_number = carNumber
	ORDER BY a.id DESC LIMIT 1;
	
	INSERT INTO
		offers
		(CLIENT, auto, date_start, STATUS, price, dealer_percent)
	VALUES
		(getClientIdByUsername(username), auto_id, NOW(), 2, price, 10);
END//
DELIMITER ;

-- Дамп структуры для таблица autos.dealers
CREATE TABLE IF NOT EXISTS `dealers` (
  `id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  `last_name` varchar(60) NOT NULL DEFAULT '',
  `patronymic` varchar(60) NOT NULL DEFAULT '',
  `first_name` varchar(60) NOT NULL DEFAULT '',
  `photo` varchar(60) NOT NULL DEFAULT '',
  `phone` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `FK_dealers_user_id` (`user_id`),
  CONSTRAINT `FK_dealers_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Дамп данных таблицы autos.dealers: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `dealers` DISABLE KEYS */;
INSERT INTO `dealers` (`id`, `user_id`, `last_name`, `patronymic`, `first_name`, `photo`, `phone`) VALUES
	(1, 1, 'Frilller', 'Seter', 'Grey', '', 88888888888);
/*!40000 ALTER TABLE `dealers` ENABLE KEYS */;

-- Дамп структуры для функция autos.getCancelledOffers
DELIMITER //
CREATE FUNCTION `getCancelledOffers`(`clientId` INT,
	`mode` INT
) RETURNS int(11)
BEGIN
	DECLARE cnt INT;
	IF MODE = 1 THEN	
		SELECT 
			COUNT(*)
		INTO
			cnt
		FROM
			offers off
		WHERE
			off.`client` = clientId
			AND off.`status` = 3;
	ELSE
		SELECT 
			COUNT(*)
		INTO
			cnt
		FROM
			offers off
		WHERE
			off.`dealer` = clientId
			AND off.`status` = 3;
		END if;
	RETURN cnt;
END//
DELIMITER ;

-- Дамп структуры для процедура autos.getCities
DELIMITER //
CREATE PROCEDURE `getCities`()
BEGIN
	SELECT * FROM cities;
END//
DELIMITER ;

-- Дамп структуры для функция autos.getClientIdByUsername
DELIMITER //
CREATE FUNCTION `getClientIdByUsername`(`username` VARCHAR(150)
) RETURNS int(11)
BEGIN
	DECLARE id INT;
	SELECT cl.id INTO id FROM clients cl, users us WHERE us.login = username AND cl.user_id = us.id;
	RETURN id;
END//
DELIMITER ;

-- Дамп структуры для процедура autos.getClientProfileById
DELIMITER //
CREATE PROCEDURE `getClientProfileById`(
	IN `client_id` INT
)
BEGIN
	SELECT
		cl.id,
		cl.first_name,
		cl.patronymic,
		cl.last_name,
		ci.name,
		us.email,
		cl.address,
		cl.phone,
		getCompletedOffers(cl.id, 1),
		getEnteredOffers(cl.id, 1),
		getCancelledOffers(cl.id, 1)
	FROM
		users us,
		clients cl,
		cities ci
	WHERE
		cl.id = client_id
		AND cl.user_id = us.id
		AND ci.id = cl.city;
END//
DELIMITER ;

-- Дамп структуры для процедура autos.getClientProfileByUsername
DELIMITER //
CREATE PROCEDURE `getClientProfileByUsername`(
	IN `username` VARCHAR(50)
)
BEGIN
	SELECT
		cl.id,
		cl.first_name,
		cl.patronymic,
		cl.last_name,
		ci.name,
		us.email,
		cl.address,
		cl.phone,
		getCompletedOffers(cl.id, 1),
		getEnteredOffers(cl.id, 1),
		getCancelledOffers(cl.id, 1)
	FROM
		users us,
		clients cl,
		cities ci
	WHERE
		us.login = username
		AND cl.user_id = us.id
		AND ci.id = cl.city;
END//
DELIMITER ;

-- Дамп структуры для процедура autos.getCommentsByOffer
DELIMITER //
CREATE PROCEDURE `getCommentsByOffer`(
	IN `offerId` INT
)
BEGIN
	
	SELECT
		case when us.role = 1 then (SELECT CONCAT(cl.first_name, ' ', cl.last_name) FROM clients cl WHERE cl.user_id = us.id)
		ELSE (select CONCAT(dea.first_name, ' ', dea.last_name) FROM dealers dea WHERE dea.user_id = us.id) END,
		com.message,
		com.date
	FROM
		comments com,
		users us
	WHERE 
		us.id = com.user
		AND com.offer = offerId;
		
END//
DELIMITER ;

-- Дамп структуры для функция autos.getCompletedOffers
DELIMITER //
CREATE FUNCTION `getCompletedOffers`(`clientId` INT,
	`mode` INT
) RETURNS int(11)
BEGIN
	DECLARE cnt INT;
	IF MODE = 1 THEN	
		SELECT 
			COUNT(*)
		INTO
			cnt
		FROM
			offers off
		WHERE
			off.`client` = clientId
			AND off.`status` = 1;
	ELSE
		SELECT 
			COUNT(*)
		INTO
			cnt
		FROM
			offers off
		WHERE
			off.`dealer` = clientId
			AND off.`status` = 1;
		END if;
	RETURN cnt; 
END//
DELIMITER ;

-- Дамп структуры для процедура autos.getDealerProfileById
DELIMITER //
CREATE PROCEDURE `getDealerProfileById`(
	IN `dealer_id` INT
)
BEGIN

	SELECT
   	cl.id,
      cl.first_name,
      cl.patronymic,
      cl.last_name,
      us.email,
      cl.phone,
      getCompletedOffers(cl.id, 2),
      getEnteredOffers(cl.id, 2),
      getCancelledOffers(cl.id, 2)
   FROM
      users us,
      dealers cl
   WHERE
   	cl.id = dealer_id
      AND cl.user_id = us.id;

END//
DELIMITER ;

-- Дамп структуры для процедура autos.getDealerProfileByUsername
DELIMITER //
CREATE PROCEDURE `getDealerProfileByUsername`(
	IN `username` VARCHAR(150)
)
BEGIN
	
	SELECT
            cl.id,
            cl.first_name,
            cl.patronymic,
            cl.last_name,
            us.email,
            cl.phone,
            getCompletedOffers(cl.id, 2),
            getEnteredOffers(cl.id, 2),
            getCancelledOffers(cl.id, 2)
            FROM
            users us,
            dealers cl
            WHERE
            us.login = username
            AND cl.user_id = us.id;
	
END//
DELIMITER ;

-- Дамп структуры для функция autos.getEnteredOffers
DELIMITER //
CREATE FUNCTION `getEnteredOffers`(`clientId` INT,
	`mode` INT
) RETURNS int(11)
BEGIN
	DECLARE cnt INT;
	IF MODE = 1 THEN	
		SELECT 
			COUNT(*)
		INTO
			cnt
		FROM
			offers off
		WHERE
			off.`client` = clientId
			AND off.`status` = 2;
	ELSE
		SELECT 
			COUNT(*)
		INTO
			cnt
		FROM
			offers off
		WHERE
			off.`dealer` = clientId
			AND off.`status` = 2;
	END if;
	RETURN cnt;
END//
DELIMITER ;

-- Дамп структуры для функция autos.getInProgressOffers
DELIMITER //
CREATE FUNCTION `getInProgressOffers`(`userId` INT,
	`mode` INT
) RETURNS int(11)
BEGIN
	DECLARE cnt INT;
	IF MODE = 1 THEN	
		SELECT 
			COUNT(*)
		INTO
			cnt
		FROM
			offers off
		WHERE
			off.`client` = userId
			AND off.`status` = 4;
	ELSE
		SELECT 
			COUNT(*)
		INTO
			cnt
		FROM
			offers off
		WHERE
			off.`dealer` = userId
			AND off.`status` = 4;
		END if;
	RETURN cnt; 
END//
DELIMITER ;

-- Дамп структуры для процедура autos.getLogEvents
DELIMITER //
CREATE PROCEDURE `getLogEvents`(
	IN `dateStart` DATE,
	IN `dateEnd` DATE
)
BEGIN
	
	SELECT 
		us.login,
		rol.name,
		logt.name,
		l.description,
		l.date,
		logt.id
	FROM 
		logs l,
		users us,
		roles rol,
		log_types logt
	where
		l.user = us.id
		AND l.log_type = logt.id
		AND us.role = rol.id
		AND case when YEAR(dateStart) <> '1970' then l.date >= dateStart ELSE 1 = 1 END 
		AND case when YEAR(dateEnd) <> '1970' then l.date <= dateEnd ELSE 1 = 1 END;

END//
DELIMITER ;

-- Дамп структуры для процедура autos.getOffersByClientId
DELIMITER //
CREATE PROCEDURE `getOffersByClientId`(
	IN `client_id` INT
)
BEGIN
	
	SELECT
		off.id,
		concat(au.brand, ' ', au.model, ' ', YEAR(au.date)),
		off.date_start,
		off.date_end,
		stat.name,
		off.price,
		dea.id,
		concat(dea.first_name, ' ', dea.patronymic, ' ', dea.last_name)
	FROM
		offers off
		LEFT JOIN
			dealers dea 
			ON off.dealer = dea.id,
		autos au,
		statuses stat
	WHERE
		off.`client` = client_id
		AND off.auto = au.id
		AND off.`status` = stat.id;
	
END//
DELIMITER ;

-- Дамп структуры для процедура autos.getOffersByDealerId
DELIMITER //
CREATE PROCEDURE `getOffersByDealerId`(
	IN `dealer_id` INT
)
BEGIN
	SELECT
		off.id,
		concat(au.brand, ' ', au.model, ' ', YEAR(au.date)),
		off.date_start,
		off.date_end,
		stat.name,
		off.price,
		cl.id,
		concat(cl.first_name, ' ', cl.patronymic, ' ', cl.last_name),
		off.dealer_percent
	FROM
		offers off,
		clients cl,
		autos au,
		statuses stat
	WHERE
		off.`dealer` = dealer_id
		AND off.`client` = cl.id
		AND off.auto = au.id
		AND off.`status` = stat.id;
END//
DELIMITER ;

-- Дамп структуры для процедура autos.getOrderProfileById
DELIMITER //
CREATE PROCEDURE `getOrderProfileById`(
	IN `targetId` INT
)
BEGIN


	SELECT 
		off.id,
		CONCAT(cl.first_name, ' ', cl.last_name),
		CONCAT(dea.first_name, ' ', dea.last_name),
		off.date_start,
		off.date_end,
		stat.name,
		
		off.price,
		off.dealer_percent,
		off.price * (1 + off.dealer_percent / 100),
		
		cl.id,
		cl.first_name,
		cl.patronymic,
		cl.last_name,
		cit.name,
		usCl.email,
		cl.phone,
		
		dea.id,
		dea.first_name,
		dea.patronymic,
		dea.last_name,
		usDea.email,
		dea.phone,
		
		au.brand,
		au.model,
		year(au.date),
		au.mileage,
		au.vin,
		au.car_number
	FROM	
		offers off
		left join dealers dea 
			LEFT JOIN users usDea
				ON usDea.id = dea.user_id
			ON dea.id = off.dealer,
		clients cl,
		statuses stat,
		users usCl,
		cities cit,
		autos au
	WHERE
		off.id = targetId
		AND cl.id = off.`client`
		AND stat.id = off.`status`
		AND au.id = off.auto
		AND usCl.id = cl.user_id
		AND cit.id = cl.city;

END//
DELIMITER ;

-- Дамп структуры для функция autos.getOrderStatus
DELIMITER //
CREATE FUNCTION `getOrderStatus`(`orderId` INT
) RETURNS int(11)
BEGIN
	DECLARE stat INT;
	
	SELECT 
		off.status
	INTO
		stat
	FROM 
		offers off
	WHERE
		off.id = orderId;
	
	RETURN stat;
END//
DELIMITER ;

-- Дамп структуры для процедура autos.logEvent
DELIMITER //
CREATE PROCEDURE `logEvent`(
	IN `logEvent` INT,
	IN `message` VARCHAR(250),
	IN `username` VARCHAR(150)
)
BEGIN
	
	DECLARE userId INT;
	
	SELECT us.id INTO userId FROM users us WHERE us.login = username;
	
	INSERT INTO logs(log_type, user, description, DATE) VALUES (logEvent, userId, message, NOW());
	
END//
DELIMITER ;

-- Дамп структуры для таблица autos.logs
CREATE TABLE IF NOT EXISTS `logs` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user` int(10) DEFAULT NULL,
  `log_type` int(10) NOT NULL,
  `date` datetime NOT NULL,
  `description` varchar(250) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `FK_logs_user` (`user`),
  KEY `FK_logs_log_type` (`log_type`),
  CONSTRAINT `FK_logs_log_type` FOREIGN KEY (`log_type`) REFERENCES `log_types` (`id`),
  CONSTRAINT `FK_logs_user` FOREIGN KEY (`user`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=latin1;

-- Дамп данных таблицы autos.logs: ~87 rows (приблизительно)
/*!40000 ALTER TABLE `logs` DISABLE KEYS */;
INSERT INTO `logs` (`id`, `user`, `log_type`, `date`, `description`) VALUES
	(1, 1, 1, '2020-12-22 21:08:16', 'Profile for order (1) was opened'),
	(2, 1, 1, '2020-12-22 21:08:17', 'Profile for client (1) was opened'),
	(3, 9, 1, '2020-12-22 21:08:52', 'Dealer\'s profile was opened'),
	(4, 9, 1, '2020-12-22 21:08:54', 'Dealer\'s profile was opened'),
	(5, 9, 1, '2020-12-22 21:11:10', 'Dealer\'s profile was opened'),
	(6, 9, 1, '2020-12-22 21:13:29', 'Dealer\'s profile was opened'),
	(7, 9, 1, '2020-12-22 21:16:59', 'Dealer\'s profile was opened'),
	(8, 9, 1, '2020-12-22 21:17:03', 'Dealer\'s profile was opened'),
	(9, 9, 1, '2020-12-22 21:17:04', 'Dealer\'s profile was opened'),
	(10, 9, 1, '2020-12-22 21:20:43', 'Dealer\'s profile was opened'),
	(11, 9, 1, '2020-12-22 21:20:51', 'Dealer\'s profile was opened'),
	(12, 9, 1, '2020-12-22 21:24:27', 'Dealer\'s profile was opened'),
	(13, 4, 1, '2020-12-22 22:23:52', 'Profile was opened'),
	(14, 1, 1, '2020-12-24 09:54:26', 'Dealer\'s profile was opened'),
	(15, 3, 1, '2020-12-24 09:54:41', 'Profile was opened'),
	(16, 1, 1, '2020-12-24 09:58:46', 'Dealer\'s profile was opened'),
	(17, 1, 1, '2020-12-24 10:03:28', 'Dealer\'s profile was opened'),
	(18, 1, 1, '2020-12-24 10:04:09', 'Dealer\'s profile was opened'),
	(19, 1, 2, '2020-12-24 10:04:16', 'Search dealers operation'),
	(20, 1, 2, '2020-12-24 10:05:19', 'Search orders operation'),
	(21, 1, 1, '2020-12-24 10:06:55', 'Profile for order (1) was opened'),
	(22, 3, 1, '2020-12-24 10:07:12', 'Profile was opened'),
	(23, 3, 7, '2020-12-24 10:08:17', 'New order was created'),
	(24, 3, 1, '2020-12-24 10:08:17', 'Profile was opened'),
	(25, 1, 1, '2020-12-24 10:08:43', 'Dealer\'s profile was opened'),
	(26, 1, 2, '2020-12-24 10:08:49', 'Search orders operation'),
	(27, 1, 1, '2020-12-24 10:08:50', 'Profile for order (5) was opened'),
	(28, 1, 4, '2020-12-24 10:08:58', 'Order - 5 was assigned to new dealer'),
	(29, 1, 1, '2020-12-24 10:08:58', 'Profile for order (5) was opened'),
	(30, 1, 1, '2020-12-24 10:11:00', 'Dealer\'s profile was opened'),
	(31, 1, 1, '2020-12-24 10:11:02', 'Profile for order (1) was opened'),
	(32, 1, 1, '2020-12-24 22:13:27', 'Dealer\'s profile was opened'),
	(33, 1, 1, '2020-12-24 22:13:29', 'Profile for order (1) was opened'),
	(34, 1, 1, '2020-12-24 22:13:31', 'Dealer\'s profile was opened'),
	(35, 1, 1, '2020-12-24 22:13:33', 'Profile for order (5) was opened'),
	(36, 1, 1, '2020-12-24 22:14:28', 'Profile for order (5) was opened'),
	(37, 1, 1, '2020-12-24 22:14:46', 'Change price to order - 5'),
	(38, 1, 1, '2020-12-24 22:14:46', 'Profile for order (5) was opened'),
	(39, 1, 2, '2020-12-24 22:20:20', 'Search orders operation'),
	(40, 1, 2, '2020-12-24 22:20:28', 'Search orders operation'),
	(41, 1, 2, '2020-12-24 22:20:36', 'Search orders operation'),
	(42, 1, 2, '2020-12-24 22:20:43', 'Search orders operation'),
	(43, 1, 2, '2020-12-24 22:20:46', 'Search orders operation'),
	(44, 1, 2, '2020-12-24 22:20:49', 'Search orders operation'),
	(45, 1, 2, '2020-12-24 22:20:57', 'Search orders operation'),
	(46, 1, 2, '2020-12-24 22:21:04', 'Search orders operation'),
	(47, 1, 2, '2020-12-24 22:21:11', 'Search orders operation'),
	(48, 1, 2, '2020-12-24 22:21:23', 'Search orders operation'),
	(49, 1, 2, '2020-12-24 22:21:35', 'Search orders operation'),
	(50, 1, 2, '2020-12-24 22:21:44', 'Search orders operation'),
	(51, 1, 2, '2020-12-24 22:22:05', 'Search orders operation'),
	(52, 1, 2, '2020-12-24 22:22:11', 'Search orders operation'),
	(53, 1, 2, '2020-12-24 22:22:18', 'Search orders operation'),
	(54, 1, 2, '2020-12-24 22:22:51', 'Search orders operation'),
	(55, 1, 2, '2020-12-24 22:23:13', 'Search orders operation'),
	(56, 1, 2, '2020-12-24 22:23:26', 'Search orders operation'),
	(57, 1, 2, '2020-12-24 22:23:32', 'Search orders operation'),
	(58, 1, 2, '2020-12-24 22:23:40', 'Search orders operation'),
	(59, 1, 2, '2020-12-24 22:23:44', 'Search orders operation'),
	(60, 1, 2, '2020-12-24 22:23:52', 'Search orders operation'),
	(61, 1, 2, '2020-12-24 22:24:11', 'Search orders operation'),
	(62, 1, 2, '2020-12-24 22:24:25', 'Search orders operation'),
	(63, 1, 2, '2020-12-24 22:24:51', 'Search dealers operation'),
	(64, 1, 2, '2020-12-24 22:24:59', 'Search dealers operation'),
	(65, 1, 2, '2020-12-24 22:25:05', 'Search dealers operation'),
	(66, 1, 2, '2020-12-24 22:25:10', 'Search dealers operation'),
	(67, 1, 2, '2020-12-24 22:25:17', 'Search dealers operation'),
	(68, 1, 2, '2020-12-24 22:25:34', 'Search dealers operation'),
	(69, 1, 1, '2020-12-24 22:25:38', 'Profile for dealer (1) was opened'),
	(70, 1, 2, '2020-12-24 22:26:20', 'Search dealers operation'),
	(71, 1, 2, '2020-12-24 22:26:24', 'Search dealers operation'),
	(72, 1, 1, '2020-12-24 22:29:48', 'Dealer\'s profile was opened'),
	(73, 1, 2, '2020-12-24 22:29:57', 'Search dealers operation'),
	(74, 1, 1, '2020-12-24 22:31:23', 'Dealer\'s profile was opened'),
	(75, 1, 1, '2020-12-24 22:35:04', 'Dealer\'s profile was opened'),
	(76, 1, 2, '2020-12-24 22:35:10', 'Search dealers operation'),
	(77, 1, 2, '2020-12-24 22:37:11', 'Search dealers operation'),
	(78, 1, 1, '2020-12-24 22:37:28', 'Dealer\'s profile was opened'),
	(79, 1, 2, '2020-12-24 22:37:32', 'Search dealers operation'),
	(80, 1, 2, '2020-12-24 22:38:07', 'Search dealers operation'),
	(81, 1, 2, '2020-12-24 22:38:18', 'Search dealers operation'),
	(82, 1, 1, '2020-12-24 22:39:35', 'Dealer\'s profile was opened'),
	(83, 1, 2, '2020-12-24 22:39:38', 'Search dealers operation'),
	(84, 1, 2, '2020-12-24 22:41:31', 'Search clients operation'),
	(85, 1, 1, '2020-12-24 22:41:38', 'Dealer\'s profile was opened'),
	(86, 1, 1, '2020-12-24 22:41:43', 'Dealer\'s profile was opened'),
	(87, NULL, 6, '2020-12-24 22:45:44', 'New user with username - shevvvik54 was created'),
	(88, NULL, 6, '2020-12-24 22:48:45', 'New user with username - shevvvik3123 was created'),
	(89, NULL, 6, '2020-12-24 23:03:46', 'New user with username - shevvvik234 was created');
/*!40000 ALTER TABLE `logs` ENABLE KEYS */;

-- Дамп структуры для таблица autos.log_types
CREATE TABLE IF NOT EXISTS `log_types` (
  `id` int(10) NOT NULL,
  `name` varchar(20) NOT NULL DEFAULT '',
  `priority` int(1) NOT NULL DEFAULT 0,
  `description` varchar(150) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Дамп данных таблицы autos.log_types: ~6 rows (приблизительно)
/*!40000 ALTER TABLE `log_types` DISABLE KEYS */;
INSERT INTO `log_types` (`id`, `name`, `priority`, `description`) VALUES
	(1, 'DEFAULT', 0, '0'),
	(2, 'SEARCH', 0, '0'),
	(3, 'CHANGE ORDER STATUS', 0, '0'),
	(4, 'ASSIGN ORDER', 0, '0'),
	(5, 'LOG IN', 0, '0'),
	(6, 'REGISTRATION', 0, '0'),
	(7, 'CREATE ORDER', 0, '0');
/*!40000 ALTER TABLE `log_types` ENABLE KEYS */;

-- Дамп структуры для таблица autos.offers
CREATE TABLE IF NOT EXISTS `offers` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `client` int(10) NOT NULL,
  `dealer` int(10) DEFAULT NULL,
  `auto` int(10) NOT NULL,
  `date_start` datetime NOT NULL,
  `date_end` datetime DEFAULT NULL,
  `status` int(10) NOT NULL DEFAULT 0,
  `price` int(10) NOT NULL DEFAULT 0,
  `dealer_percent` float DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `FK_offers_client` (`client`),
  KEY `FK_offers_status` (`status`),
  KEY `FK_offers_auto` (`auto`),
  KEY `FK_offers_dealer` (`dealer`),
  CONSTRAINT `FK_offers_auto` FOREIGN KEY (`auto`) REFERENCES `autos` (`id`),
  CONSTRAINT `FK_offers_client` FOREIGN KEY (`client`) REFERENCES `clients` (`id`),
  CONSTRAINT `FK_offers_dealer` FOREIGN KEY (`dealer`) REFERENCES `dealers` (`id`),
  CONSTRAINT `FK_offers_status` FOREIGN KEY (`status`) REFERENCES `statuses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Дамп данных таблицы autos.offers: ~5 rows (приблизительно)
/*!40000 ALTER TABLE `offers` DISABLE KEYS */;
INSERT INTO `offers` (`id`, `client`, `dealer`, `auto`, `date_start`, `date_end`, `status`, `price`, `dealer_percent`) VALUES
	(1, 1, 1, 7, '2020-12-11 01:59:23', '2020-12-14 01:40:57', 1, 200000, 10),
	(2, 1, 1, 8, '2020-12-13 21:37:35', '2020-12-16 12:35:46', 3, 200000, 10),
	(3, 1, NULL, 9, '2020-12-16 12:37:31', '2020-12-16 15:27:25', 3, 50000, 10),
	(4, 1, 1, 10, '2020-12-16 15:27:17', '2020-12-16 15:29:11', 1, 2000000, 10),
	(5, 1, 1, 11, '2020-12-24 10:08:17', NULL, 2, 1000000, 1);
/*!40000 ALTER TABLE `offers` ENABLE KEYS */;

-- Дамп структуры для процедура autos.registr
DELIMITER //
CREATE PROCEDURE `registr`(
	IN `login` VARCHAR(50),
	IN `password` VARCHAR(150),
	IN `email` VARCHAR(150),
	IN `first_name` VARCHAR(150),
	IN `patronymic` VARCHAR(150),
	IN `last_name` VARCHAR(150),
	IN `city` INT,
	IN `address` VARCHAR(150),
	IN `phone` BIGINT
)
BEGIN
	INSERT INTO users (login, password, email, role) VALUES (login, password, email, 1);
	INSERT INTO clients (user_id, last_name, patronymic, first_name, city, address, phone) VALUES ((SELECT u.id FROM users u WHERE u.login = login), last_name, patronymic, first_name, city, address, phone);
END//
DELIMITER ;

-- Дамп структуры для таблица autos.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '0',
  `description` varchar(150) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Дамп данных таблицы autos.roles: ~2 rows (приблизительно)
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`, `name`, `description`) VALUES
	(1, 'ROLE_CLIENT', '0'),
	(2, 'ROLE_DEALER', '0'),
	(3, 'ROLE_ADMIN', '0');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

-- Дамп структуры для процедура autos.searchClients
DELIMITER //
CREATE PROCEDURE `searchClients`(
	IN `firstName` VARCHAR(150),
	IN `lastName` VARCHAR(150),
	IN `patronymic` VARCHAR(150),
	IN `city` INT,
	IN `address` VARCHAR(150),
	IN `phone` BIGINT,
	IN `email` VARCHAR(150)
)
BEGIN

	SELECT
		cl.id,
		cl.first_name,
		cl.patronymic,
		cl.last_name,
		cit.name,
		cl.phone,
		us.email
	FROM
		clients cl,
		users us,
		cities cit
	WHERE
		us.id = cl.user_id
		AND cl.city = cit.id
		AND case when email <> '' then us.email = email ELSE 1 = 1 END 
		AND case when firstName <> '' then cl.first_name = firstName ELSE 1 = 1 END
		AND case when lastName <> '' then cl.last_name = lastName ELSE 1 = 1 END
		AND case when patronymic <> '' then cl.patronymic = patronymic ELSE 1 = 1 END 
		AND case when city <> 0 then cl.city = city ELSE 1 = 1 END 
		AND case when address <> '' then cl.address = address ELSE 1 = 1 END 
		AND case when phone <> 0 then cl.phone = phone ELSE 1 = 1 END;
	
END//
DELIMITER ;

-- Дамп структуры для процедура autos.searchDealers
DELIMITER //
CREATE PROCEDURE `searchDealers`(
	IN `firstName` VARCHAR(150),
	IN `lastName` VARCHAR(150),
	IN `patronymic` VARCHAR(150),
	IN `phone` BIGINT,
	IN `ordersType` INT,
	IN `email` VARCHAR(150)
)
BEGIN

	SELECT
		dea.id,
		dea.first_name,
		dea.patronymic,
		dea.last_name,
		dea.phone,
		us.email,
		getEnteredOffers(dea.id, 2),
		getInProgressOffers(dea.id, 2),
		getCompletedOffers(dea.id, 2),
		getCancelledOffers(dea.id, 2)
	FROM
		dealers dea,
		users us
	WHERE
		us.id = dea.user_id
		AND case when email <> '' then us.email = email ELSE 1 = 1 END 
		AND case when firstName <> '' then dea.first_name = firstName ELSE 1 = 1 END
		AND case when lastName <> '' then dea.last_name = lastName ELSE 1 = 1 END
		AND case when patronymic <> '' then dea.patronymic = patronymic ELSE 1 = 1 END 
		AND case when phone <> 0 then dea.phone = phone ELSE 1 = 1 END
		AND case 
			when ordersType = 0 then 1 = 1
			when ordersType = 1 then (getEnteredOffers(dea.id, 2) + getInProgressOffers(dea.id, 2)) <> 0 
			when ordersType = 2 then getEnteredOffers(dea.id, 2) <> 0 
			when ordersType = 3 then getInProgressOffers(dea.id, 2) <> 0 
			when ordersType = 4 then getCompletedOffers(dea.id, 2) <> 0 
			when ordersType = 5 then getCancelledOffers(dea.id, 2) <> 0 
			when ordersType = 6 then (getEnteredOffers(dea.id, 2) + getInProgressOffers(dea.id, 2)) = 0 
			END;

END//
DELIMITER ;

-- Дамп структуры для процедура autos.searchOffers
DELIMITER //
CREATE PROCEDURE `searchOffers`(
	IN `brand` VARCHAR(150),
	IN `model` VARCHAR(150),
	IN `mileageMin` INT,
	IN `mileageMax` INT,
	IN `DATE` INT,
	IN `priceMin` INT,
	IN `priceMax` INT,
	IN `dateStartMin` DATE,
	IN `dateStartMax` DATE,
	IN `status` INT
)
BEGIN
	SELECT
		off.id,
		CONCAT(cl.first_name, ' ', cl.patronymic, ' ', cl.last_name),
		CONCAT(dea.first_name, ' ', dea.patronymic, ' ', dea.last_name),
		CONCAT(au.brand, ' ', au.model, ' ', year(au.date)),
		off.date_start,
		off.date_end,
		st.name,
		off.price * (1 + off.dealer_percent / 100)
	FROM
		offers off,
		clients cl,
		autos au,
		dealers dea,
		statuses st
	WHERE
		off.`client` = cl.id
		AND off.dealer = dea.id
		AND off.auto = au.id
		AND off.`status` = st.id
		
		AND case when brand <> '' then au.brand = brand ELSE 1 = 1 END 
		AND case when model <> '' then au.model = model ELSE 1 = 1 END 
		AND case when mileageMin <> 0 then au.mileage >= mileageMin ELSE 1 = 1 END 
		AND case when mileageMax <> -1 then au.mileage <= mileageMax ELSE 1 = 1 END 
		AND case when DATE <> 0 then YEAR(au.date) = DATE ELSE 1 = 1 END 
		
		AND case when priceMin <> 0 then off.price >= priceMin ELSE 1 = 1 END 
		AND case when priceMax <> 0 then off.price <= priceMax ELSE 1 = 1 END 
		AND case when dateStartMin <> 0 then off.date_start >= dateStartMin ELSE 1 = 1 END 
		AND case when dateStartMax <> 0 then off.date_start <= dateStartMax ELSE 1 = 1 END 
		AND case when status <> 0 then off.status = status ELSE 1 = 1 END;
END//
DELIMITER ;

-- Дамп структуры для процедура autos.searchOffersDetailed
DELIMITER //
CREATE PROCEDURE `searchOffersDetailed`(
	IN `clientFirstName` VARCHAR(150),
	IN `clientLastName` VARCHAR(150),
	IN `clientPatronymic` VARCHAR(150),
	IN `city` INT,
	IN `dealerFirstName` VARCHAR(150),
	IN `dealerLastName` VARCHAR(150),
	IN `dealerPatronymic` VARCHAR(150),
	IN `brand` VARCHAR(150),
	IN `model` VARCHAR(150),
	IN `mileageMin` INT,
	IN `mileageMax` INT,
	IN `DATE` INT,
	IN `priceMin` INT,
	IN `priceMax` INT,
	IN `dateStartMin` DATE,
	IN `dateStartMax` DATE,
	IN `status` INT
)
BEGIN
	SELECT
		off.id,
		cl.id,
		CONCAT(cl.first_name, ' ', cl.patronymic, ' ', cl.last_name),
		dea.id,
		CONCAT(dea.first_name, ' ', dea.patronymic, ' ', dea.last_name),
		CONCAT(au.brand, ' ', au.model, ' ', year(au.date)),
		off.date_start,
		off.date_end,
		st.name,
		(off.price * (1 + off.dealer_percent / 100))
	FROM
		offers off
		LEFT JOIN dealers dea
			ON off.dealer = dea.id,
		clients cl,
		autos au,
		statuses st
	WHERE
		off.`client` = cl.id
		AND off.auto = au.id
		AND off.`status` = st.id
		
		AND case when clientFirstName <> '' then cl.first_name = clientFirstName ELSE 1 = 1 END 
		AND case when clientLastName <> '' then cl.last_name = clientLastName ELSE 1 = 1 END 
		AND case when clientPatronymic <> '' then cl.patronymic = clientPatronymic ELSE 1 = 1 END 
		AND case when city <> 0 then cl.city = city ELSE 1 = 1 END 
		
		AND case when dealerFirstName <> '' then dea.first_name = dealerFirstName ELSE 1 = 1 END 
		AND case when dealerLastName <> '' then dea.last_name = dealerLastName ELSE 1 = 1 END 
		AND case when dealerPatronymic <> '' then dea.patronymic = dealerPatronymic ELSE 1 = 1 END 
		
		AND case when brand <> '' then au.brand = brand ELSE 1 = 1 END 
		AND case when model <> '' then au.model = model ELSE 1 = 1 END 
		AND case when mileageMin <> 0 then au.mileage >= mileageMin ELSE 1 = 1 END 
		AND case when mileageMax <> -1 then au.mileage <= mileageMax ELSE 1 = 1 END 
		AND case when date <> 0 then YEAR(au.date) = date ELSE 1 = 1 END 
		
		AND case when priceMin <> 0 then off.price >= priceMin ELSE 1 = 1 END 
		AND case when priceMax <> 0 then off.price <= priceMax ELSE 1 = 1 END 
		AND case when YEAR(dateStartMin) <> '1970' then off.date_start >= YEAR(dateStartMin) ELSE 1 = 1 END 
		AND case when YEAR(dateStartMax) <> '1970' then off.date_start <= YEAR(dateStartMax) ELSE 1 = 1 END 
		AND case when status <> 0 then st.id = status ELSE 1 = 1 END;
END//
DELIMITER ;

-- Дамп структуры для таблица autos.statuses
CREATE TABLE IF NOT EXISTS `statuses` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '',
  `description` varchar(150) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Дамп данных таблицы autos.statuses: ~4 rows (приблизительно)
/*!40000 ALTER TABLE `statuses` DISABLE KEYS */;
INSERT INTO `statuses` (`id`, `name`, `description`) VALUES
	(1, 'Completed', ''),
	(2, 'Entered', ''),
	(3, 'Cancelled', ''),
	(4, 'In progress', '');
/*!40000 ALTER TABLE `statuses` ENABLE KEYS */;

-- Дамп структуры для таблица autos.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL DEFAULT '0',
  `password` varchar(60) NOT NULL DEFAULT '0',
  `email` varchar(60) NOT NULL DEFAULT '0',
  `role` int(10) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `email` (`email`),
  KEY `FK_users_role` (`role`),
  CONSTRAINT `FK_users_role` FOREIGN KEY (`role`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- Дамп данных таблицы autos.users: ~5 rows (приблизительно)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `login`, `password`, `email`, `role`) VALUES
	(1, 'user', '123', '123', 2),
	(3, 'shevik', '1234567', 'hebog96934@deselling.com', 1),
	(4, 'dima123', '123', 'shevyakov-dima@mail.ru', 1),
	(7, 'shevik123', '123', '', 1),
	(9, 'admin', 'admin', '0', 3),
	(11, 'shevvvik234', '$2a$08$z1sNidnzyBPZugVrNZDriuVL2a20nY9eZJL/DsG2LLA5xJI/YB4nq', 'shevy3333akov-dima@mail.ru', 1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
