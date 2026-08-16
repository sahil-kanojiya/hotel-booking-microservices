CREATE DATABASE IF NOT EXISTS microservices_db;
USE microservices_db;

CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    about VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS hotels (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    location VARCHAR(255),
    about VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS ratings (
    rating_id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255),
    hotel_id VARCHAR(255),
    rating INT,
    feedback VARCHAR(255)
);

INSERT INTO users (id, name, email, about) VALUES 
('u1', 'Alice', 'alice@example.com', 'Traveler'),
('u2', 'Bob', 'bob@example.com', 'Business Man')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO hotels (id, name, location, about) VALUES 
('h1', 'Grand Plaza', 'New York', 'Luxury Hotel'),
('h2', 'Ocean View', 'Miami', 'Beachfront Resort')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO ratings (rating_id, user_id, hotel_id, rating, feedback) VALUES 
('r1', 'u1', 'h1', 5, 'Excellent stay!'),
('r2', 'u2', 'h2', 4, 'Very good, nice view.')
ON DUPLICATE KEY UPDATE rating=VALUES(rating);
