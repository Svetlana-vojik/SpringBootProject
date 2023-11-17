CREATE TABLE IF NOT EXISTS shop.users
(
    id       INT          NOT NULL AUTO_INCREMENT,
    email    VARCHAR(60)  NOT NULL,
    password VARCHAR(200) NOT NULL,
    name     VARCHAR(30)  NOT NULL,
    surname  VARCHAR(60)  NOT NULL,
    birthday DATE         NULL,
    balance  INT          NULL,
    address  VARCHAR(60)  NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX IDX_users_id_UNIQUE (id ASC),
    UNIQUE INDEX IDX_users_email_UNIQUE (email ASC)
);

CREATE TABLE IF NOT EXISTS shop.categories
(
    id         INT         NOT NULL AUTO_INCREMENT,
    name       VARCHAR(30) NOT NULL,
    image_path VARCHAR(75) NOT NULL,
    rating     INT         NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX IDX_categories_id_UNIQUE (id ASC),
    UNIQUE INDEX IDX_categories_name_UNIQUE (name ASC)
);

CREATE TABLE IF NOT EXISTS shop.products
(
    id          INT          NOT NULL AUTO_INCREMENT,
    name        VARCHAR(100)  NOT NULL,
    description VARCHAR(500) NOT NULL,
    price       INT          NOT NULL,
    category_id INT          NOT NULL,
    image_path  VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX IDX_products_id (ID ASC),
    CONSTRAINT FK_products_category_id_categories_id
        FOREIGN KEY (category_id)
            REFERENCES shop.categories (ID)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS shop.orders
(
    id         INT      NOT NULL AUTO_INCREMENT,
    order_date DATETIME NOT NULL,
    price      INT      NOT NULL,
    user_id    INT      NOT NULL,
    PRIMARY KEY (ID),
    UNIQUE INDEX IDX_orders_id_UNIQUE (ID ASC),
    CONSTRAINT FK_orders_user_id_users_id
        FOREIGN KEY (user_id)
            REFERENCES shop.users (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS shop.orders_products
(
    order_id   INT NOT NULL,
    product_id INT NOT NULL,
    quantity   INT NOT NULL DEFAULT 0,
    PRIMARY KEY (order_id, product_id),
    CONSTRAINT FK_orders_products_order_id_orders_id
        FOREIGN KEY (order_id)
            REFERENCES shop.orders (id),
    CONSTRAINT FK_orders_products_product_id_products_id
        FOREIGN KEY (product_id)
            REFERENCES shop.products (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


INSERT INTO shop.users(email, password, name, surname, birthday, balance, address)
VALUES ('user1@mail.ru', '1111', 'Sveta', 'Kot', '1989-12-29', 10.00, 'Minsk Masherova 7-15');
INSERT INTO shop.users(email, password, name, surname, birthday, balance, address)
VALUES ('user2@mail.ru', '2222', 'Dima', 'Black', '1996-02-25', 2.50, 'Minsk Nezavisimisti 5-175');
INSERT INTO shop.users(email, password, name, surname, birthday, balance, address)
VALUES ('user3@mail.ru', '3333', 'Ira', 'Smith', '1994-03-08', 0.00, 'Minsk Shugaeva 4-25');
INSERT INTO shop.users(email, password, name, surname, birthday, balance, address)
VALUES ('user4@mail.ru', '4444', 'Lena', 'White', '2000-11-15', 13.15, 'Minsk Kiseleva 25-52');


INSERT INTO shop.categories(name, image_path, rating)
VALUES ('Капкейки', 'images/categories/cupcakes.png', 2);
INSERT INTO shop.categories(name, image_path, rating)
VALUES ('Торты', 'images/categories/cakes.png', 5);
INSERT INTO shop.categories(name, image_path, rating)
VALUES ('Пирожные', 'images/categories/pastries.png', 4);
INSERT INTO shop.categories(name, image_path, rating)
VALUES ('Круассаны', 'images/categories/croissants.png', 3);
INSERT INTO shop.categories(name, image_path, rating)
VALUES ('Печенье', 'images/categories/cookie.png', 5);


INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Лимонный капкейк', 'Если Вы любите все, что связано с цитрусовыми, тогда наш лимонный капкейк – это то, что Вам нужно. Ванильно-лимонное тесто на основе сметаны в сочетании с лимонным курдом придает непревзойденному вкусу капкейку.', 5, 1, 'products/lemon.png');
INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Шоколадный капкейк', 'Шоколадный капкейк понравится всем любителям шоколада. Это ведь самый шоколадный десерт в нашей онлайн кондитерской. Шоколадная кремовая шапочка имеет легкую и нежную структуру, так как на ее основе крем пломбир. Мы используем только натуральные полностью безопасные ингредиенты, которые покупаем у топовых производителей Франции.', 7, 1, 'products/chocolate.png');
INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Ванильный капкейк', 'Нежный ванильный вкус уже стал классикой среди капкейков, и это не удивительно, ведь ванильный вкус любят абсолютно все. "Шапочка" капкейка изготовлена из крема пломбира, который славится своей особой нежностью. Такие капкейки можно смело заказывать на любое событие, будь то корпоративный праздник компании, открытие магазина или детский день рождения.', 10, 1, 'products/vanilla.png');
INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Ванильно-ягодный капкейк', 'Ванильно-ягодный капкейк не оставит равнодушным никого. Ванильное основание капкейка в сочетании с ягодами – это вкус, который любят абсолютно все. Декор изготовлен из крема пломбира, который просто тает во рту. При изготовлении мы используем только натуральные полностью безопасные ингредиенты от топовых украинских и европейских производителей.', 8, 1, 'products/vanillafruit.png');

INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Наполеон', 'Наполеон', 5, 2, 'products/napoleon.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Красный бархат', 'Красный бархат', 7, 2, 'products/red.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Медовик', 'Медовик', 10, 2, 'products/medovik.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Тирамису', 'Тирамису', 8, 2, 'products/tiramisu.jpg');

INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Картошка', 'Картошка', 5, 3, 'products/kartoshka.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Классический эклер', 'Классический эклер', 7, 3, 'products/ekler.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Корзиночка', 'Корзиночка', 10, 3, 'products/korzinochka.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Павлова', 'Павлова', 8, 3, 'products/pavlova.jpg');

INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Шоколадный круассан', 'Шоколадный круассан', 10, 4, 'products/croissant_chocolate.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Сырный круассан', 'Сырный круассан', 10, 4, 'products/chessecroissant.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Круассан с беконом', 'Круассан с беконом', 15, 4, 'products/bacon.jpg');
INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Круассан с яблочно-грушевой начинкой', 'Круассан с яблочно-грушевой начинкой', 14, 4, 'products/apple.jpg');

INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Печенье Банан', 'Печенье Банан - идеальное дополнение к торту или как подарок для маленьких гостей. Печенье в виде подарка для маленьких гостей рекомендуем заказывать по 2 шт на одного ребенка. Печенье мы упаковываем в прозрачный пакет, который завязывается лентой.', 5, 5, 'products/banan.png');
INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Печенье Щенячий патруль', 'Печенье Щенячий патруль Вы можете заказать, как дополнение к торту или как тематические подарки для маленьких гостей детского праздника. Также такие подарки, в виде печенья с ручной росписью, прекрасная альтернатива сладкого стола в школе или детском саду.', 7, 5, 'products/paw.png');
INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Печенье Три кота', 'Печенье с героями мультсериала "Три кота" покорит маленьких поклонников. Такой вид печенья рекомендуем заказывать как дополнение к праздничному торту или как альтернатива сладкому столу в школе или детском саду.', 10, 5, 'products/cats.png');
INSERT INTO shop.products(name, description, price, category_id, image_path)
VALUES ('Печенье Миньон', 'Печенье в стиле Миньон изготовлено и расписано вручную. Яркое печенье рекомендуем заказывать как дополнение к торту или как подарок для гостей детского праздника.', 8, 5, 'products/minion.png');