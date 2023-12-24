--Клиенты
INSERT INTO clients (account_number, address, name, patronymic, phone_number, surname, type_of_person)
VALUES ('123456789012345678901', 'Москва', 'Иван', 'Павлович', '88005553535', 'Мискин', 'Физическое');
INSERT INTO clients (account_number, address, name, patronymic, phone_number, surname, type_of_person)
VALUES ('223456789012345678901', 'Нижний Новгород', 'Петр', 'Павлович', '89005553535', 'Карпов', 'Юридическое');
INSERT INTO clients (account_number, address, name, patronymic, phone_number, surname, type_of_person)
VALUES ('323456789012345678907', 'Муром', 'Константин', 'Михайлович', '88205553535', 'Киров', 'Физическое');
INSERT INTO clients (account_number, address, name, patronymic, phone_number, surname, type_of_person)
VALUES ('423456789012345678901', 'Волгоград', 'Денис', 'Иванович', '89006553535', 'Коровьев', 'Юридическое');
INSERT INTO clients (account_number, address, name, patronymic, phone_number, surname, type_of_person)
VALUES ('523456789012345678901', 'Москва', 'Ольга', 'Сергеевна', '89305553535', 'Торопова', 'Физическое');
INSERT INTO clients (account_number, address, name, patronymic, phone_number, surname, type_of_person)
VALUES ('623456789012345678901', 'Москва', 'Иван', 'Иванович', '88005553537', 'Иванов', 'Юридическое');
INSERT INTO clients (account_number, address, name, patronymic, phone_number, surname, type_of_person)
VALUES ('723456789012345678901', 'Меленки', 'Елена', 'Викторовна', '88655553535', 'Минина', 'Физическое');
--Охраняемые объекты
INSERT INTO guarded_objects (address, image, name)
VALUES ('Муром', null, 'Завод');
INSERT INTO guarded_objects (address, image, name)
VALUES ('Москва', null, 'Завод №1');
INSERT INTO guarded_objects (address, image, name)
VALUES ('Москва', null, 'Завод №2');
INSERT INTO guarded_objects (address, image, name)
VALUES ('Москва', null, 'Завод №3');
INSERT INTO guarded_objects (address, image, name)
VALUES ('Москва', null, 'Завод №4');
INSERT INTO guarded_objects (address, image, name)
VALUES ('Москва', null, 'Завод №5');
INSERT INTO guarded_objects (address, image, name)
VALUES ('Москва', null, 'Завод №6');
INSERT INTO guarded_objects (address, image, name)
VALUES ('Москва', null, 'Завод №7');
INSERT INTO guarded_objects (address, image, name)
VALUES ('Где-то в горах', 'Peykan_chaldoran.jpg', 'Машина');
--Менеджеры
INSERT INTO managers (date_of_start, account_number, education, name, patronymic, surname)
VALUES ('2001-01-15', '123456789012345678902', 'Высшее', 'Михаил', 'Никитич', 'Гончаров');
INSERT INTO managers (date_of_start, account_number, education, name, patronymic, surname)
VALUES ('2002-01-15', '223456789012345678902', 'Среднее', 'Максим', 'Андреевич', 'Гончаров');
INSERT INTO managers (date_of_start, account_number, education, name, patronymic, surname)
VALUES ('2003-01-15', '323456789012345678902', 'Среднее', 'Иван', 'Никитич', 'Гончаров');
INSERT INTO managers (date_of_start, account_number, education, name, patronymic, surname)
VALUES ('2004-01-15', '423456789012345678902', 'Среднее', 'Ольга', 'Сергеевна', 'Конева');
INSERT INTO managers (date_of_start, account_number, education, name, patronymic, surname)
VALUES ('2005-01-15', '523456789012345678902', 'Среднее', 'Марина', 'Ивановна', 'Гончарова');
INSERT INTO managers (date_of_start, account_number, education, name, patronymic, surname)
VALUES ('2006-01-15', '623456789012345678902', 'Среднее', 'Светлана', 'Марковна', 'Потапенко');
INSERT INTO managers (date_of_start, account_number, education, name, patronymic, surname)
VALUES ('2007-01-15', '723456789012345678902', 'Среднее', 'Леонид', 'Маркович', 'Потапенко');
--Услуги
INSERT INTO services (period_of_execution, price, name)
VALUES (1, 1000, 'Ликвидация');
INSERT INTO services (period_of_execution, price, name)
VALUES (1, 5000, 'Охрана');
INSERT INTO services (period_of_execution, price, name)
VALUES (1, 2000, 'Пробив');
INSERT INTO services (period_of_execution, price, name)
VALUES (1, 1000, 'Бан');
INSERT INTO services (period_of_execution, price, name)
VALUES (1, 1500, 'Разбан');
INSERT INTO services (period_of_execution, price, name)
VALUES (1, 500, 'Телепорт');
INSERT INTO services (period_of_execution, price, name)
VALUES (1, 10000, 'Похищение');
INSERT INTO services (period_of_execution, price, name)
VALUES (1, 5000, 'Штурм');
--Договоры
INSERT INTO orders (
        date_of_complete,
        date_of_signing,
        price,
        client_id,
        manager_id
    )VALUES('2001-03-12','2001-03-11',1000,1,1);
--Детали договоров
INSERT INTO order_details (
        quantity,
        object_id,
        order_id,
        service_id
    ) VALUES(1,1,1,1);
INSERT INTO order_details (
        quantity,
        object_id,
        order_id,
        service_id
    ) VALUES(1,1,1,2);