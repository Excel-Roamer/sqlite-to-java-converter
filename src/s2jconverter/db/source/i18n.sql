BEGIN TRANSACTION;

-- Table language
CREATE TABLE IF NOT EXISTS `language` ( 
    `idLang` INTEGER NOT NULL PRIMARY KEY,
    `abbrev` VARCHAR ( 4 ),
    `libelle` VARCHAR ( 64 )
);
-- Content:
INSERT INTO `language` VALUES (1, "AR", "العربية"), (2, "FR", "Français"), (3, "EN", "English");

-- Table i18n
CREATE TABLE IF NOT EXISTS `i18n` ( 
    `idLibelle` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    `libelle` VARCHAR ( 25 )
);
-- Content:
INSERT INTO `i18n` VALUES 
    (1, "APP_TITLE"),
    (2, "WELCOME"),
    (3, "LOAD"),
    (4, "EXPORT"),
    (5, "SUCCESS_TITLE"),
    (6, "SUCCESS_MSG")
;

-- Table translate
CREATE TABLE IF NOT EXISTS `translate` ( 
    `idLibelle` INTEGER,
    `idLang` INTEGER,
    `synonym` VARCHAR ( 255 ),
    PRIMARY KEY(`idLibelle`,`idLang`),
    FOREIGN KEY(`idLibelle`) REFERENCES `i18n`(`idLibelle`),
    FOREIGN KEY(`idLang`) REFERENCES `language`(`idLang`)
);
-- Content:
INSERT INTO `translate` VALUES 
    (1, 1, "SQLite to JAVA Converter"), (1, 2, "SQLite to JAVA Converter"), (1, 3, "SQLite to JAVA Converter"), 
    (2, 1, "مرحبا"), (2, 2, "Bienvenue"), (2, 3, "Welcome"), 
    (3, 1, "تحميل"), (3, 2, "Charger"), (3, 3, "Load"), 
    (4, 1, "تصدير"), (4, 2, "Exporter"), (4, 3, "Export"), 
    (5, 1, "جميل"), (5, 2, "Succès"), (5, 3, "Success"), 
    (6, 1, "تم تصدير الأصناف بنجاح."), (6, 2, "Classes exportées avec succès."), (6, 3, "Export succeded.")
;

COMMIT;