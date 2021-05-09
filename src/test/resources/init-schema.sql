select 1 ;
create schema if not exists taes_key;
use taes_key;

CREATE TABLE IF NOT EXISTS `key_set` (
   `key_id` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
   `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
   `key_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
   `min_length` int DEFAULT NULL,
   `key_generator` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
   `reg_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `chg_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`key_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


