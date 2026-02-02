# Mini Shop Backend

REST API для мини интернет-магазина (Backend only), реализованный на **Spring Boot**.  
Проект демонстрирует работу с **JWT-аутентификацией, ролями, PostgreSQL, Redis и Elasticsearch**.

---

## Функциональность

### Авторизация и роли
- Регистрация и логин пользователей
- JWT (Bearer Token)
- Роли:
    - `USER`
    - `ADMIN`

### Каталог товаров
- CRUD товаров (только ADMIN)
- Категории товаров
- Поиск и фильтрация через **Elasticsearch**
- Переиндексация каталога (ADMIN)

### Корзина (Redis)
- Server-side корзина
- Хранение в Redis (`cart:{userId}`)
- TTL корзины (по умолчанию 7 дней)
- Ограничения количества товара
- Очистка корзины после checkout

### Заказы
- Оформление заказа из корзины
- Хранение заказов в PostgreSQL
- Snapshot цены и названия товара
- Просмотр заказов пользователя
- Контроль доступа (USER видит только свои заказы, ADMIN — любые)

---

## Архитектура

| Компонент | Назначение |
|----------|------------|
| PostgreSQL | Основное хранилище (source of truth) |
| Redis | Корзина и временные данные |
| Elasticsearch | Поиск и фильтрация товаров |
| Flyway | Миграции БД |
| Spring Security | JWT + Role-based access |
| Logback | Логирование |

---

## Технологический стек

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA (Hibernate)
- Spring Data Redis
- Spring Data Elasticsearch
- PostgreSQL
- Redis
- Elasticsearch
- Flyway
- JWT (jjwt)
- Maven
- Docker Compose
- Logback

---

## Запуск проекта

### Запуск инфраструктуры
```bash
docker-compose up -d
```

### Поднимаются:
- PostgreSQL
- Redis
- Elasticsearch

## Запуск приложения
```bash 
./mvnw spring-boot:run
```
### Flyway автоматически:
- создаёт схему БД
- применяет миграции
- создаёт admin-пользователя (один раз)


## Admin пользователь (seed)
### Создаётся автоматически через Flyway:
```
email:    admin@minishop.com
password: admin123
role:     ADMIN
```
## API
### Базовый префикс:
```
/api/v1
```
## Auth

| Метод | Endpoint       | Описание             |
| ----- | -------------- | -------------------- |
| POST  | /auth/register | Регистрация          |
| POST  | /auth/login    | Логин                |
| GET   | /auth/me       | Текущий пользователь |

## Products

| Метод  | Endpoint                | Роль  |
| ------ | ----------------------- | ----- |
| GET    | /products               | USER  |
| GET    | /products/search        | USER  |
| POST   | /admin/products         | ADMIN |
| PATCH  | /admin/products/{id}    | ADMIN |
| DELETE | /admin/products/{id}    | ADMIN |
| POST   | /admin/products/reindex | ADMIN |

## Cart

| Метод  | Endpoint                |
| ------ | ----------------------- |
| GET    | /cart                   |
| POST   | /cart/items             |
| PATCH  | /cart/items/{productId} |
| DELETE | /cart/items/{productId} |
| DELETE | /cart/clear             |

## Orders

| Метод | Endpoint         |
| ----- | ---------------- |
| POST  | /orders/checkout |
| GET   | /orders          |
| GET   | /orders/{id}     |

## Безопасность
- JWT Bearer Authentication
- BCrypt для хранения паролей
- Role-based access control
- DTO validation
- Global Exception Handling
- Server-side cart

## Логирование
### Используется Logback:

- INFO — общее
- WARN — security
- DEBUG — SQL (для разработки)
