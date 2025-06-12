🎮 GameReview API

GameReview API — это серверное приложение на Spring Boot, реализующее REST API для управления видеоиграми и отзывами к ним. Приложение поддерживает аутентификацию с помощью JWT и имеет юнит-тесты на Mockito.

📆 Возможности

🔐 Регистрация и логин пользователей с JWT-аутентификацией.

🔹 CRUD-операции с играми.

📝 Добавление и управление отзывами к играм.

🧪 Юнит-тесты контроллеров с использованием Mockito и MockMvc.


🛠️ Технологии

Java 17+

Spring Boot

Spring Security

JWT

JPA / Hibernate

PostgreSQL / H2

JUnit 5 + Mockito

Maven

🔐 Аутентификация

После логина вы получаете JWT-токен, который нужно передавать в заголовке:

Authorization: Bearer <ваш_токен>

📋 Примеры запросов

Регистрация

POST /api/auth/register
Content-Type: application/json

{
  "username": "user123",
  "password": "pass123"
}

Логин

POST /api/auth/login
Content-Type: application/json

{
  "username": "user123",
  "password": "pass123"
}
> {%
    client.global.set("token", response.body.accessToken);
%}

Получить список игр

GET /api/game?pageNumber=0&pageSize=10
Authorization: Bearer <token>

Добавить отзыв к игре

POST /api/game/{gameId}/review
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Отличная игра",
  "content": "Мне очень понравился геймплей и графика",
  "rating": 9
}

✅ Тестирование

Для запуска тестов:

./mvnw test

🚀 Запуск

Склонируйте проект:

git clone https://github.com/Bormitus/GameReviewApi.git

Настройте application.properties или application.yml.

Запустите приложение:

./mvnw spring-boot:run
