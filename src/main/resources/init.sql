--------------------------------------------------------
--  DDL for schema shop
--------------------------------------------------------
DROP SCHEMA IF EXISTS shop;
CREATE SCHEMA IF NOT EXISTS shop;

--------------------------------------------------------
--  DDL for Table users
--------------------------------------------------------
DROP TABLE IF EXISTS shop.users;
CREATE TABLE IF NOT EXISTS shop.users (
                                                id INT NOT NULL AUTO_INCREMENT,
                                                email VARCHAR(60) NOT NULL,
                                                password VARCHAR(60) NOT NULL,
                                                name VARCHAR(30) NOT NULL,
                                                surname VARCHAR(60) NOT NULL,
                                                birthday DATE NULL,
                                                balance INT NULL,
                                                address VARCHAR(60) NOT NULL,
                                                PRIMARY KEY (`id`),
                                                UNIQUE INDEX IDX_USERS_USER_ID_UNIQUE (ID ASC),
                                                UNIQUE INDEX IDX_USERS_EMAIL_UNIQUE (EMAIL ASC),
                                                UNIQUE INDEX IDX_USERS_PASSWORD_UNIQUE (PASSWORD ASC));
--------------------------------------------------------
--  DDL for Table categories
--------------------------------------------------------
    DROP TABLE IF EXISTS shop.categories;
    CREATE TABLE IF NOT EXISTS shop.categories (
                                                id INT NOT NULL AUTO_INCREMENT,
                                                name VARCHAR(30) NOT NULL,
                                                imagePath VARCHAR(75) NOT NULL,
                                                rating INT NOT NULL,
                                                PRIMARY KEY (`id`),
                                                UNIQUE INDEX IDX_CATEGORIES_CATEGORY_ID_UNIQUE (ID ASC),
                                                UNIQUE INDEX IDX_CATEGORIES_NAME_UNIQUE (NAME ASC));

    --------------------------------------------------------
--  DDL for Table products
--------------------------------------------------------
    DROP TABLE IF EXISTS shop.products;
    CREATE TABLE IF NOT EXISTS shop.products (
                                                    id INT NOT NULL AUTO_INCREMENT,
                                                    name VARCHAR(30) NOT NULL,
                                                    description VARCHAR(100) NOT NULL,
                                                    price INT NOT NULL,
                                                    categoryId INT NOT NULL,
                                                    imagePath  VARCHAR(100) NOT NULL,
                                                    PRIMARY KEY (`id`),
                                                    UNIQUE INDEX IDX_PRODUCTS_ID_UNIQUE (ID ASC),
                                                    CONSTRAINT FK_PRODUCTS_CATEGORY_ID_CATEGORIES_ID
                                                    FOREIGN KEY (categoryId)
                                                    REFERENCES SHOP.CATEGORIES (ID)
                                                    ON DELETE CASCADE
                                                    ON UPDATE CASCADE);
    --------------------------------------------------------
--  DDL for Table ORDERS
    --------------------------------------------------------
    DROP TABLE IF EXISTS shop.orders;
    CREATE TABLE IF NOT EXISTS shop.orders (
                                            id INT NOT NULL AUTO_INCREMENT,
                                            orderDate Timestamp NOT NULL,
                                            price INT NOT NULL,
                                            userId INT NOT NULL,
                                            PRIMARY KEY (ID),
                                            UNIQUE INDEX IDX_ORDERS_ID_UNIQUE (ID ASC),
                                            CONSTRAINT FK_ORDERS_USERID_USERS_ID
                                            FOREIGN KEY (userId)
                                            REFERENCES SHOP.USERS (ID)
                                            ON DELETE CASCADE
                                            ON UPDATE CASCADE);
--------------------------------------------------------
--  DDL for Table ORDERS_PRODUCTS
--------------------------------------------------------
DROP TABLE IF EXISTS shop.orders_products;
CREATE TABLE IF NOT EXISTS shop.orders_products (
                                                    orderId INT NOT NULL,
                                                    productId INT NOT NULL,
                                                    quantity  INT NOT NULL DEFAULT 0,
                                                    PRIMARY KEY (orderId, productId),
                                                    CONSTRAINT FK_orders_products_orderId_orders_id
                                                    FOREIGN KEY (orderId)
                                                    REFERENCES shop.orders(id),
                                                    CONSTRAINT FK_orders_products_productId_products_id
                                                    FOREIGN KEY (productId)
                                                    REFERENCES shop.products(id)
                                                    ON DELETE CASCADE
                                                    ON UPDATE CASCADE);

--------------------------------------------------------
--  DDL for Table IMAGES
--------------------------------------------------------
DROP TABLE IF EXISTS shop.images;
CREATE TABLE IF NOT EXISTS shop.images (
                                            id INT NOT NULL AUTO_INCREMENT,
                                            imagePath VARCHAR(45) NOT NULL,
                                            categoryId INT NULL,
                                            productId INT NULL,
                                            primaryImage  INT   NOT NULL,
                                            UNIQUE INDEX id (id ASC),
                                            PRIMARY KEY (id),
                                            CONSTRAINT FK_images_categoryId_categories_id
                                            FOREIGN KEY (categoryId)
                                            REFERENCES shop.categories (id)
                                            ON DELETE CASCADE
                                            ON UPDATE CASCADE,
                                            CONSTRAINT FK_images_productId_products_id
                                            FOREIGN KEY (productId)
                                            REFERENCES shop.products (id)
                                            ON DELETE CASCADE
                                            ON UPDATE CASCADE
);
--------------------------------------------------------
--  DML for Table users
--------------------------------------------------------
    INSERT INTO shop.users(email, password, name, surname, birthday, balance, address ) VALUES(?, ?, ?, ?, ?, ?,?);

    INSERT INTO shop.users(email, password, name, surname, birthday, balance, address ) VALUES ('user1@mail.ru', '1111', 'Sveta', 'Kot', '1989-12-29', 10.00, 'Minsk Masherova 7-15');
    INSERT INTO shop.users(email, password, name, surname, birthday, balance, address ) VALUES ('user2@mail.ru', '2222', 'Dima', 'Black', '1996-02-25', 2.50,'Minsk Nezavisimisti 5-175');
    INSERT INTO shop.users(email, password, name, surname, birthday, balance, address ) VALUES ('user3@mail.ru', '3333', 'Ira', 'Smith','1994-03-08', 0.00,'Minsk Shugaeva 4-25');
    INSERT INTO shop.users(email, password, name, surname, birthday, balance, address ) VALUES ('user4@mail.ru', '4444', 'Lena', 'White', '2000-11-15',13.15,'Minsk Kiseleva 25-52');
--------------------------------------------------------
--  DML for Table categories
--------------------------------------------------------
    INSERT INTO shop.categories(name, imagePath, rating) VALUES(?, ?, ?);

    INSERT INTO shop.categories(name, imagePath, rating) VALUES('Капкейки', 'images/categories/cupcakes.png', 2);
    INSERT INTO shop.categories(name, imagePath, rating) VALUES('Торты', 'images/categories/cakes.png', 5);
    INSERT INTO shop.categories(name, imagePath, rating) VALUES('Пирожные', 'images/categories/pastries.png',4);
    INSERT INTO shop.categories(name, imagePath, rating) VALUES('Круассаны', 'images/categories/croissants.png',3);
    INSERT INTO shop.categories(name, imagePath, rating) VALUES('Киши', 'images/categories/quiche.png',5);

    --------------------------------------------------------
--  DML for Table products
--------------------------------------------------------
    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES(?, ?, ?, ?, ?);

    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Капкейки', 'Лимонный капкейк', 5, 1,'images/products/lemon.png');
    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Капкейки', 'Шоколадный капкейк', 7, 1,'images/products/chocolate.png');
    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Капкейки', 'Клубничный капкейк', 10, 1,'images/products/strawberries.png');
    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Капкейки', 'Сырный капкейк', 8, 1,'images/products/cheese.jpg');

    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Торты', 'Наполеон', 5, 2,'images/products/napoleon.jpg');
    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Торты', 'Красный бархат', 7, 2,'images/products/red.jpg');
    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Торты', 'Медовик', 10, 2,'images/products/medovik.jpg');
    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Торты', 'Тирамису', 8, 2,'images/products/tiramisu.jpg');

    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Пирожные', 'Картошка', 5, 3,'images/products/kartoshka.jpg');
    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Пирожные', 'Классический эклер', 7, 3,'images/products/ekler.jpg');
    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Пирожные', 'Корзиночка', 10, 3,'images/products/korzinochka.jpg');
    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Пирожные', 'Павлова', 8, 3,'images/products/pavlova.jpg');

    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Круассаны', 'Шоколадный круассан', 10, 4,'images/products/croissant_chocolate.jpg');
    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Круассаны', 'Сырный круассан', 10, 4,'images/products/chessecroissant.jpg');
    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Круассаны', 'Круассан с беконом', 15, 4,'images/products/bacon.jpg');
    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Круассаны', 'Круассан с яблочно-грушевой начинкой', 14, 4,'images/products/apple.jpg');

    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Киши', 'Киш с броколли', 5, 5,'images/products/brokoli.jpg');
    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Киши', 'Киш с сыром', 7, 5,'images/products/cheesekish.jpg');
    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Киши', 'Киш с семгой и броколли', 10, 5,'images/products/kish-s-semgoi-i-brokkoli.jpg');
    INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES('Киши', 'Киш Лорен с курицей', 8, 5,'images/products/kish-loren-s-kuricei.jpg');