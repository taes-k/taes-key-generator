INSERT IGNORE INTO taes_key.key_set(key_id, key_type, key_generator, min_length)
VALUES ('number-generic-key', 'NUMBER', 'GENERIC', 2);

INSERT IGNORE INTO taes_key.key_set(key_id, key_type, key_generator, min_length)
VALUES ('number-generic-min-len-1-key', 'NUMBER', 'GENERIC', 1);

INSERT IGNORE INTO taes_key.key_set(key_id, key_type, key_generator, min_length)
VALUES ('number-generic-key-multi', 'NUMBER', 'GENERIC', 1);
