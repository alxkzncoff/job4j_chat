# Проект - "Чат с помощью REST API"

## Общая информация.

Проект разработан с целью улучшения навыков работы с различными стеками технологий и представляет
собой простой REST сервис который реализует серверную часть онлайн-чата.
При необходимости функционал проекта можно расширить.

## Запуск проекта

Для корректной работы приложения необходимо установить следующие программы:

- [Docker](https://docs.docker.com/engine/install/)
- [Docker-compose](https://docs.docker.com/compose/install/)

1. Запуск проекта с помощью Docker:

Скопировать себе проект:
```
git clone https://github.com/alxkzncoff/job4j_chat.git
```

Перейти в директорию проекта:
```
cd <path>/job4j_chat
```

где ```path``` путь до проекта.

Собрать контейнер и запустить:
```
docker-compose up --build
```

## Команды

После запуска к серверу можно обратиться по адресу: http://localhost:8080

### Пользователи

- `GET /users/all` - получить список всех пользователей.
- `GET /users/currentUser` - вывод информация пользователя, который авторизован в данный момент.
- `GET /users/roomUsers/id/{id}` - выводит список пользователей в комнате, где id - идентификационный номер комнаты.
- `GET /users/roomUsers/id/{name}` - выводит список пользователей в комнате, где name - имя комнаты.
- `POST /users/sign-up` - регистрация пользователя.
- `POST /login` - авторизация пользователя.
- `PUT /users/joinRoom/{name}` - добавляет текущего пользователя в комнату, где name - имя комнаты.

### Комнаты

- `GET /rooms/all` - получить список всех комнат.
- `GET /rooms/id/{id}` - получить комнату по id, где id - идентификационный номер комнаты.
- `GET /rooms/name/{name}` - получить комнату по name, где name - имя комнаты.
- `POST /rooms/` - создать комнату.
- `PUT /rooms/` - обновить данные комнаты.
- `DELETE /rooms/{id}` - удалить комнату по id, где id - идентификационный
номер комнаты. 

### Сообщения

- `GET /messages/` - получить все сообщения.
- `GET /messages/roomId/{id}` - получить все сообщения в комнате по id, где id - идентификационный номер комнаты.
- `GET /messages/roomName/{name}` - получить все сообщения в комнате по name, где name - имя комнаты.
- `POST /messages/` - отправить сообщение. Пользователь должен быть авторизован. Сообщение закрепляется за комнатой,
в которой пользователь находится в данный момент.

### Примеры curl запросов

- Регистрация нового пользователя

```
curl --location --request POST 'http://localhost:8080/users/sign-up' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "newuser",
    "password": "password",
    "role": {
        "id": "2"
    }
}'
```

- Авторизация

```
curl --location --request POST 'http://localhost:8080/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "newuser",
    "password": "password"
}'
```

- Получить список всех пользователей. Необходима авторизация.

```
curl --location --request GET 'http://localhost:8080/users/all' \
--header 'Authorization: Bearer <your bearer token>
```

где ```<your bearer token>``` токен авторизированного пользователя.

## Технологии

[![java](https://img.shields.io/badge/java-17-red)](https://www.java.com/)
[![maven](https://img.shields.io/badge/apache--maven-3.8.3-blue)](https://maven.apache.org/)
[![Spring Boot](https://img.shields.io/badge/spring%20boot-2.7.3-brightgreen)](https://spring.io/projects/spring-boot)
[![PostgresSQL](https://img.shields.io/badge/postgreSQL-14-blue)](https://www.postgresql.org/)

[![Actions Status](https://github.com/alxkzncoff/job4j_chat/workflows/java-ci/badge.svg)](https://github.com/alxkzncoff/job4j_chat/actions)
