CREATE TABLE IF NOT EXISTS shop.users (
                                          id INT NOT NULL AUTO_INCREMENT,
                                          email VARCHAR(60) NOT NULL,
    password VARCHAR(200) NOT NULL,
    name VARCHAR(30) NOT NULL,
    surname VARCHAR(60) NOT NULL,
    birthday DATE NULL,
    balance INT NULL,
    address VARCHAR(60) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX IDX_USERS_USER_ID_UNIQUE (ID ASC),
    UNIQUE INDEX IDX_USERS_EMAIL_UNIQUE (EMAIL ASC),
    UNIQUE INDEX IDX_USERS_PASSWORD_UNIQUE (PASSWORD ASC));

CREATE TABLE IF NOT EXISTS shop.categories (
                                               id INT NOT NULL AUTO_INCREMENT,
                                               name VARCHAR(30) NOT NULL,
    image_path VARCHAR(75) NOT NULL,
    rating INT NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX IDX_CATEGORIES_CATEGORY_ID_UNIQUE (ID ASC),
    UNIQUE INDEX IDX_CATEGORIES_NAME_UNIQUE (NAME ASC));

CREATE TABLE IF NOT EXISTS shop.products (
                                             id INT NOT NULL AUTO_INCREMENT,
                                             name VARCHAR(30) NOT NULL,
    description VARCHAR(100) NOT NULL,
    price INT NOT NULL,
    category_id INT NOT NULL,
    image_path  VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX IDX_PRODUCTS_ID_UNIQUE (ID ASC),
    CONSTRAINT FK_PRODUCTS_CATEGORY_ID_CATEGORIES_ID
    FOREIGN KEY (category_id)
    REFERENCES shop.categories (ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS shop.orders (
                                           id INT NOT NULL AUTO_INCREMENT,
                                           order_date DATETIME NOT NULL,
                                           price INT NOT NULL,
                                           user_id INT NOT NULL,
                                           PRIMARY KEY (ID),
    UNIQUE INDEX IDX_ORDERS_ID_UNIQUE (ID ASC),
    CONSTRAINT FK_ORDERS_USERID_USERS_ID
    FOREIGN KEY (user_id)
    REFERENCES shop.users (ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS shop.orders_products (
                                                    order_id INT NOT NULL,
                                                    product_id INT NOT NULL,
                                                    quantity  INT NOT NULL DEFAULT 0,
                                                    PRIMARY KEY (order_id, product_id),
    CONSTRAINT FK_orders_products_order_id_orders_id
    FOREIGN KEY (order_id)
    REFERENCES shop.orders(id),
    CONSTRAINT FK_orders_products_product_id_products_id
    FOREIGN KEY (product_id)
    REFERENCES shop.products(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


INSERT INTO shop.users(email, password, name, surname, birthday, balance, address ) VALUES ('user1@mail.ru', '1111', 'Sveta', 'Kot', '1989-12-29', 10.00, 'Minsk Masherova 7-15');
INSERT INTO shop.users(email, password, name, surname, birthday, balance, address ) VALUES ('user2@mail.ru', '2222', 'Dima', 'Black', '1996-02-25', 2.50,'Minsk Nezavisimisti 5-175');
INSERT INTO shop.users(email, password, name, surname, birthday, balance, address ) VALUES ('user3@mail.ru', '3333', 'Ira', 'Smith','1994-03-08', 0.00,'Minsk Shugaeva 4-25');
INSERT INTO shop.users(email, password, name, surname, birthday, balance, address ) VALUES ('user4@mail.ru', '4444', 'Lena', 'White', '2000-11-15',13.15,'Minsk Kiseleva 25-52');


INSERT INTO shop.categories(name, image_path, rating) VALUES('Капкейки', 'images/categories/cupcakes.png', 2);
INSERT INTO shop.categories(name, image_path, rating) VALUES('Торты', 'images/categories/cakes.png', 5);
INSERT INTO shop.categories(name, image_path, rating) VALUES('Пирожные', 'images/categories/pastries.png',4);
INSERT INTO shop.categories(name, image_path, rating) VALUES('Круассаны', 'images/categories/croissants.png',3);
INSERT INTO shop.categories(name, image_path, rating) VALUES('Киши', 'images/categories/quiche.png',5);


INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Капкейки', 'Лимонный капкейк', 5, 1,'products/lemon.png');
INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Капкейки', 'Шоколадный капкейк', 7, 1,'products/chocolate.png');
INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Капкейки', 'Клубничный капкейк', 10, 1,'products/strawberries.png');
INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Капкейки', 'Сырный капкейк', 8, 1,'products/cheese.jpg');

INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Торты', 'Наполеон', 5, 2,'products/napoleon.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Торты', 'Красный бархат', 7, 2,'products/red.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Торты', 'Медовик', 10, 2,'products/medovik.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Торты', 'Тирамису', 8, 2,'products/tiramisu.jpg');

INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Пирожные', 'Картошка', 5, 3,'products/kartoshka.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Пирожные', 'Классический эклер', 7, 3,'products/ekler.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Пирожные', 'Корзиночка', 10, 3,'products/korzinochka.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Пирожные', 'Павлова', 8, 3,'products/pavlova.jpg');

INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Круассаны', 'Шоколадный круассан', 10, 4,'products/croissant_chocolate.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Круассаны', 'Сырный круассан', 10, 4,'products/chessecroissant.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Круассаны', 'Круассан с беконом', 15, 4,'products/bacon.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Круассаны', 'Круассан с яблочно-грушевой начинкой', 14, 4,'products/apple.jpg');

INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Киши', 'Киш с броколли', 5, 5,'products/brokoli.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Киши', 'Киш с сыром', 7, 5,'products/cheesekish.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Киши', 'Киш с семгой и броколли', 10, 5,'products/kish-s-semgoi-i-brokkoli.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path) VALUES('Киши', 'Киш Лорен с курицей', 8, 5,'products/kish-loren-s-kuricei.jpg');