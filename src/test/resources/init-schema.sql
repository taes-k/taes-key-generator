select 1 ;
create schema if not exists taes_key;
use taes_key;

CREATE TABLE `key_set` (
    `key_set_seq` int NOT NULL AUTO_INCREMENT,
    `key_id` varchar(300) COLLATE utf8mb4_unicode_ci NOT NULL,
    `description` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `key_type` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `key_generator` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `min_length` int DEFAULT NULL,
    `reg_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `chg_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`key_set_seq`),
    UNIQUE KEY `key_set_UN` (`key_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `key_string` (
    `key_set_seq` int NOT NULL,
    `key_value` char(19) COLLATE utf8mb4_unicode_ci NOT NULL,
    `reg_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`key_set_seq`,`key_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `key_number_mysql` (
    `key_set_seq` int NOT NULL,
    `key_value` bigint NOT NULL AUTO_INCREMENT,
    `reg_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`key_set_seq`,`key_value`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
