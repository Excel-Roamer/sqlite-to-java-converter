BEGIN TRANSACTION;

-- Table preferences
CREATE TABLE IF NOT EXISTS `preferences` ( 
    `id` INTEGER NOT NULL PRIMARY KEY,
    `libelle` VARCHAR ( 30 ),
    `value` TEXT
);
-- Content:
INSERT INTO `preferences` VALUES 
    (1, "LANGUAGE", "FR"),
    (2, "LOAD_PATH", ""),
    (3, "DEST_DIR", ""),
    (4, "AUTHOR", "")
;

COMMIT;