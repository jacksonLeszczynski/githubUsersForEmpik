DROP TABLE IF EXISTS USER_REST_COUNT_TBL;

CREATE TABLE USER_REST_COUNT_TBL (
    REQUEST_COUNT INT AUTO_INCREMENT PRIMARY KEY,
    LOGIN VARCHAR(250) NOT NULL
);