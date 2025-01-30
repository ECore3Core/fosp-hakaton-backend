
# Spring Project: Hakaton

## Описание проекта

Данный проект представляет собой систему, основанную на архитектуре микросервисов. Он включает в себя несколько независимых компонентов, которые взаимодействуют между собой через REST API. Основная цель проекта — обеспечить модульность, масштабируемость и удобство дальнейшего развития.

## Основные технологии

- **Java**: Основной язык разработки.
- **Spring Boot**: Для создания микросервисов.
- **Spring Security**: Для реализации аутентификации и авторизации.
- **PostgreSQL**: Для работы с базой данных.
- **Docker**: Для контейнеризации и развертывания.
- **Gradle**: Для автоматической сборки.

## Структура проекта

- **`authentication`**: Микросервис для управления пользователями, аутентификации и авторизации.
- **`profile`**: Микросервис для управления профилями пользователей.
- **`.vscode`**: Настройки среды разработки.
- **`.git`**: Репозиторий Git для управления версией кода.

## Развертывание

### Требования

- **Java 17** или выше.
- **Gradle** для сборки проекта.
- **Docker** для контейнеризации.
- **PostgreSQL** для базы данных.

## Описание микросервисов

### 1. Authentication

- **Назначение**: Управление пользователями, регистрация, вход, авторизация.
- **Основные эндпоинты**:
  - `POST /authentication/sign-in`: Авторизация пользователя.
  - `POST /authentication/sign-up`: Регистрация нового пользователя.
  - `GET /authentication/validate-token`: Проверка токена на валидность.
  - `GET /user/`: Получение информации о текущем пользователе.
  - `GET /user/getIdByName/{username}`: Получение id пользователя по username.
  - `GET /user/getUserRole`: Получение роли текущего пользователя.

#### POST /authentication/sign-in

**Описание**: Авторизация пользователя.

**Пример запроса**:
```json
{
    "username": "exampleusername",
    "password": "examplepassword"
}
```

**Пример ответа**:
```json
{
    "token": "sf21rfn9asf......."
}
```

#### POST /authentication/sign-up

**Описание**: Регистрация пользователя.

**Пример запроса**:
```json
{
    "username": "exampleusername",
    "email": "user@example.com",
    "password": "examplepassword",
    "role": "ROLE_EXAMPLE"
}
```
### Поля и их описание

#### `role`

- **Тип**: строка
- **Описание**: Определяет роль пользователя
- **Допустимые значения**:
  - `ROLE_USER`: Обычный пользователь.
  - `ROLE_COMPANY`: Пользователь, который предположительно может добавить компанию.

**Пример ответа**:
```json
{
    "token": "sf21rfn9asf......."
}
```

#### GET /authentication/validate-token

**Описание**: Проверка токена на валидность

**Пример запроса**:
##### Required Headers
- `Authorization`: Bearer token для аутентификации запроса. Пример: `Bearer <your_jwt_token>`.

**Пример ответа**:
```json
{
    "token": "sf21rfn9asf......."
}
```

#### GET /user/

**Описание**: Получение информации о текущем пользователе.

**Пример запроса**:
##### Required Headers
- `Authorization`: Bearer token для аутентификации запроса. Пример: `Bearer <your_jwt_token>`.

**Пример ответа**:
```json
{
    "username": "exampleusername",
    "website": "http://example.website"
}
```

#### GET /user/getIdByName/{username}

**Описание**: Получение id пользователя по username.

**Пример запроса**:
##### Required Headers
- `Authorization`: Bearer token для аутентификации запроса. Пример: `Bearer <your_jwt_token>`.

**Пример ответа**:
```json
{
    "id": "12345"
}
```

#### GET /user/getUserRole

**Описание**: Получение роли текущего пользователя.

**Пример запроса**:
##### Required Headers
- `Authorization`: Bearer token для аутентификации запроса. Пример: `Bearer <your_jwt_token>`.

**Пример ответа**:
```json
{
    "role": "ROLE_EXAMPLE"
}
```

### 2. Profile

- **Назначение**: Управление пользовательскими профилями.
- **Основные эндпоинты**:
- `GET /profile/company/{id}`: Получить колмпанию пользователя по id.
- `POST /profile/create-company`: Создать компанию.
- `GET /profile/get-company/{id}`: Получить компанию по id компании.
- `PUT /profile/update-company/{id}`: Обновить инфорацию о компании.
- `DELETE /profile/delete-company/{id}`: Удалить компанию по id пользователя.
- `GET /profile/service/{id}`: Получить все услуги пользователя по id.
- `POST /profile/create-service/{id}`: Создать услгу по id пользователя.
- `GET /profile/get-service/{id}`: Получить сервис по id сервиса.
- `PUT /profile/update-service/{id}`: Обновить информацию об услуге по id услуги.
- `DELETE /profile/delete-service/{id}`: Удалить услугу по id услуги.

#### GET /profile/company/{id}

**Описание**: Получить колмпанию пользователя по id.

**Пример запроса**:
##### Required Headers
- `Authorization`: Bearer token для аутентификации запроса. Пример: `Bearer <your_jwt_token>`.

**Пример ответа**:
```json
{
    "id": 1,
    "userId": 1,
    "companyName": "exampleCompany",
    "description": "description company",
    "website": "https://company.com",
    "createdAt": "2024-12-06T13:16:49.474892",
    "updatedAt": "2024-12-06T13:16:49.474892",
    "services": []
}
```

#### POST /profile/create-company

**Описание**: Создать компанию.

**Пример запроса**:
##### Required Headers
- `Authorization`: Bearer token для аутентификации запроса. Пример: `Bearer <your_jwt_token>`.

```json
{
    "companyName": "exampleCompany",
    "userId": "1",
    "description": "description company",
    "website": "https://company.com"
}
```

**Пример ответа**:
```json
{
    "id": 1,
    "userId": 1,
    "companyName": "exampleCompany",
    "description": "description company",
    "website": "https://company.com",
    "createdAt": "2024-12-06T13:16:49.474892",
    "updatedAt": "2024-12-06T13:16:49.474892",
    "services": []
}
```


## Дальнейшая работа

- **Profile**: Доделать профиль.
- **Документация API**: Добавить описание всех доступных эндпоинтов.
- **Тестирование**: Настроить автоматические тесты для микросервисов.
- **Мониторинг**: Внедрить мониторинг состояния микросервисов (например, с помощью Prometheus и Grafana).

---

