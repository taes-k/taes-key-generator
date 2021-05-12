INSERT IGNORE INTO taes_key.key_set(key_id, key_type, key_generator, min_length)
VALUES ('number-mysql-key', 'NUMBER', 'MYSQL', 2);

INSERT IGNORE INTO taes_key.key_set(key_id, key_type, key_generator, min_length)
VALUES ('number-mysql-min-len-1-key', 'NUMBER', 'GENERIC', 1);

INSERT IGNORE INTO taes_key.key_set(key_id, key_type, key_generator, min_length)
VALUES ('number-mysql-key-multi', 'NUMBER', 'MYSQL', 1);