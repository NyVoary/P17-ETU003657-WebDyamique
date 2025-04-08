-- Creation of the database which is going to manage a system of budget management
CREATE DATABASE budget_management;
USE budget_management;

-- Creation of the table of labels
CREATE TABLE labels (
    label_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Creation of the table of movements
CREATE TABLE movements (
    movement_id INT AUTO_INCREMENT PRIMARY KEY,
    label_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (label_id) REFERENCES labels(label_id)
);

-- Creation of the table of budget
CREATE TABLE budget (
    budget_id INT AUTO_INCREMENT PRIMARY KEY,
    label_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (label_id) REFERENCES labels(label_id)
);

-- Creation of the table login for the users
CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(10)
);