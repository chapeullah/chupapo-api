# Session authentication

Пользователь входит по логину и паролю, сервер сохраняет `SecurityContext` в
`JSESSIONID`, а account/role API проверяет permissions его роли.

## Первый запуск

Задайте пароль начального администратора (12-128 символов) и запустите проект:

```powershell
$env:BOOTSTRAP_ADMIN_PASSWORD = "replace-with-a-long-random-password"
docker compose up --build
```

Пользователь `admin` с ролью `ADMIN` создаётся только при его отсутствии.
Пароль не хранится в конфиге и сохраняется в БД только в виде Argon2-хеша. При
запуске без Docker используйте переменную `APP_BOOTSTRAP_ADMIN_PASSWORD`.

В production обязательно включите HTTPS и задайте:

```text
SESSION_COOKIE_SECURE=true
```

## Работа клиента

Все запросы должны отправляться с cookies (`credentials: "include"` в
браузерном `fetch`). Последовательность входа:

1. `GET /api/auth/csrf` — сохранить `token` и `headerName` из ответа.
2. `POST /api/auth/login` — отправить JSON
   `{"username":"admin","password":"..."}` и CSRF-токен в указанном header.
   Ответ: `204` и session cookie.
3. Снова вызвать `GET /api/auth/csrf`: после входа токен специально меняется.
4. Передавать новый CSRF header во всех `POST`, `PUT` и `DELETE` запросах.

Дополнительные endpoints:

- `GET /api/auth/me` — текущий username и выданные authorities;
- `POST /api/auth/logout` — завершить сессию; запрос также требует CSRF header.

Permissions проверяются прямо на controller methods:

- accounts: `ACCOUNTS_CREATE`, `ACCOUNTS_READ`, `ACCOUNTS_UPDATE`,
  `ACCOUNTS_DELETE`;
- roles: `ROLES_CREATE`, `ROLES_READ`, `ROLES_UPDATE`, `ROLES_DELETE`.

Permissions фиксируются в сессии в момент входа. После изменения роли
пользователю нужно войти заново; максимальное окно до автоматического выхода —
30 минут бездействия.
