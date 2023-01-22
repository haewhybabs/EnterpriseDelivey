DROP TABLE IF EXISTS parcel_delivery;

CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(8) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role_id` int(11) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),  
  UNIQUE(`email`),
  FOREIGN KEY (`role_id`) REFERENCES roles(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `delivery_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(40) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `parcel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `user_id` int(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES users(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `driver` (
  `id` int(200) NOT NULL AUTO_INCREMENT,
  `user_id` int(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `address` text NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),  
  FOREIGN KEY (`user_id`) REFERENCES users(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `recipient_address` (
  `id` int(200) NOT NULL AUTO_INCREMENT,
  `user_id` int(200) NOT NULL,
  `address` text NOT NULL,
  `recipient_phone_number` text NOT NULL,
  `recipient_email` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),  
  FOREIGN KEY (`user_id`) REFERENCES users(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `delivery_request` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `user_id` int(255) NOT NULL,
  `sender_address` text NOT NULL,
  `parcel_id` int(255) NOT NULL,
  `delivery_date` datetime DEFAULT NULL,
  `delivery_status_id` int(255) NOT NULL,
  `driver_id` int(255) DEFAULT NULL,
  `recipient_address_id` int(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES users(`id`),  
  FOREIGN KEY (`parcel_id`) REFERENCES parcel(`id`),
  FOREIGN KEY (`delivery_status_id`) REFERENCES delivery_status(`id`),
  FOREIGN KEY (`recipient_address_id`) REFERENCES recipient_address(`id`),
  FOREIGN KEY (`driver_id`) REFERENCES driver(`id`)      
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `rating` (
  `id` int(200) NOT NULL AUTO_INCREMENT,
  `delivery_request_id` int(200) NOT NULL,
  `sender_rating` int(8) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`delivery_request_id`) REFERENCES delivery_request(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO roles (name) VALUES ('Sender');
INSERT INTO roles (name) VALUES ('Driver');
INSERT INTO roles (name) VALUES ('Admin');

INSERT INTO users (name,email,password,role_id)
VALUES ('Ayobami Sender','babalolaisaac@gmail.com','$2a$10$fHL9E/oj9CHDJ5yVg8zJnu4EFqv6rE67pifkN2AAvhKJH8ct.2Wj6','1');

INSERT INTO users (name,email,password,role_id)
VALUES ('Ayobami Admin','babalolaisaac2@gmail.com','$2a$10$fHL9E/oj9CHDJ5yVg8zJnu4EFqv6rE67pifkN2AAvhKJH8ct.2Wj6','3');

INSERT INTO delivery_status (status) VALUES ('Awaiting delivery');
INSERT INTO delivery_status (status) VALUES ('Processing');
INSERT INTO delivery_status (status) VALUES ('In transit');
INSERT INTO delivery_status (status) VALUES ('Delivered');

