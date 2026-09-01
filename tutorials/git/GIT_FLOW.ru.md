# Git Flow: правила разработки в команде

## Назначение

Этот документ описывает принятый в команде порядок работы с Git Flow:

- какие ветки используются;
- от каких веток создавать новые;
- куда вливать изменения;
- как работать с `feature`, `release`, `hotfix`;
- как работать с зависимыми feature;
- как синхронизировать локальные ветки;
- как безопасно публиковать изменения;
- что делать через Pull Request.

Цель — сделать историю изменений предсказуемой и избежать ситуаций, когда изменения случайно попадают не в ту ветку.

---

## 1. Основная модель веток

В классическом Git Flow используются две основные долгоживущие ветки:

- `main` — код, находящийся в production;
- `develop` — основная ветка текущей разработки.

Временные ветки:

- `feature/*` — разработка новой функциональности;
- `release/*` — подготовка релиза;
- `hotfix/*` — срочное исправление production;
- `bugfix/*` — исправление ошибок в процессе разработки;
- `support/*` — поддержка старых версий, если это необходимо проекту.

Базовая схема:

```text
                          feature/*
                         /        \
                        /          \
                       /            \
main ─────────────────●──────────────●─────────────
                      ↑              ↑
                      │              │
                   release        hotfix
                      │              │
                      └──────┐ ┌─────┘
                             ↓ ↓
develop ────────────────●────●─●──────────────────
                        ↑
                        │
                     feature
```

Главное правило:

```text
feature/*  → develop
release/*  → main
release/*  → develop
hotfix/*   → main
hotfix/*   → develop
```

---

# 2. Что является источником для каждой ветки

## Feature

Feature создаётся от:

```text
develop → feature/*
```

Пример:

```bash
git flow feature start user-profile
```

Получается:

```text
develop
   \
    feature/user-profile
```

Новые feature по умолчанию **не создаются от `main`**.

---

## Release

Release создаётся от:

```text
develop → release/*
```

Пример:

```bash
git flow release start 2.4.0
```

Получается:

```text
develop
   \
    release/2.4.0
```

Release используется для подготовки уже готового набора изменений к production.

В release обычно не добавляют новые большие функциональные возможности.

---

## Hotfix

Hotfix создаётся от:

```text
main → hotfix/*
```

Пример:

```bash
git flow hotfix start 2.4.1
```

Используйте `hotfix`, когда проблема уже находится в production и исправление нельзя ждать до следующего обычного релиза.

---

## Bugfix

`bugfix` используется для исправления ошибок в процессе разработки.

В зависимости от конфигурации `git-flow` такая ветка обычно является веткой разработки и должна в конечном итоге попасть в `develop`.

Пример:

```bash
git flow bugfix start fix-registration
```

---

# 3. Основной рабочий цикл Feature

Обычная разработка выглядит так:

```text
develop
   │
   ├── feature/login
   │
   ├── feature/payment
   │
   └── feature/profile
            │
            └────────→ develop
```

## Шаг 1. Обновить develop

Перед началом работы необходимо синхронизировать локальный `develop`:

```bash
git checkout develop
git pull origin develop
```

## Шаг 2. Создать feature

```bash
git flow feature start user-profile
```

## Шаг 3. Работать и делать коммиты

```bash
git add .
git commit -m "feat: add user profile"
```

Коммиты должны быть небольшими и логически связанными.

## Шаг 4. Опубликовать feature

Если работа ведётся через remote:

```bash
git flow feature publish user-profile
```

или:

```bash
git push -u origin feature/user-profile
```

## Шаг 5. Создать Pull Request

Из:

```text
feature/user-profile
```

в:

```text
develop
```

После ревью и успешного прохождения CI изменения можно вливать в `develop`.

---

# 4. Как вливать Feature в develop

Есть два допустимых варианта.

## Вариант A — через Pull Request

Для командной разработки это предпочтительный способ:

```text
feature/user-profile
        │
        │ Pull Request
        ↓
     develop
```

Плюсы:

- code review;
- автоматические проверки;
- прозрачная история;
- возможность обсуждать изменения до merge.

---

## Вариант B — через git-flow локально

Можно использовать:

```bash
git flow feature finish user-profile
```

Эта команда завершает feature и вливает её в `develop`.

После этого изменения нужно отправить в remote:

```bash
git push origin develop
```

Если проект использует обязательный Pull Request, локальный `feature finish` обычно не используется для финального merge.

---

# 5. Важно: не все Feature должны зависеть друг от друга

Предпочтительная структура:

```text
develop
 ├── feature/A
 ├── feature/B
 └── feature/C
```

Нежелательная структура:

```text
develop
 └── feature/A
       └── feature/B
             └── feature/C
```

Каждая feature по возможности должна начинаться от `develop`.

---

# 6. Что делать, если Feature зависит от другой Feature

Иногда `feature/B` действительно требует незавершённых изменений из `feature/A`.

Например:

```text
develop
   \
    feature/A
          \
           feature/B
```

В этом случае временно допустимо создать `feature/B` от `feature/A`.

Пример:

```bash
git checkout feature/A
git checkout -b feature/B
```

Но нужно понимать, что теперь B зависит от A.

После завершения A желательно вернуть B на обычную базу:

```text
develop
   ├── feature/A
   └── feature/B
```

Например, после merge A в `develop`:

```bash
git checkout feature/B
git rebase develop
```

или выполнить merge:

```bash
git checkout feature/B
git merge develop
```

Выбор между `rebase` и `merge` зависит от правил проекта.

Главное правило:

> Зависимая feature — временное исключение, а не стандартная структура проекта.

Подробнее о зависимостях см. `GIT_BRANCH_DEPENDENCIES.ru.md`.

---

# 7. Как обновлять Feature от develop

Пока feature разрабатывается, в `develop` могут появляться новые изменения.

Периодически feature необходимо синхронизировать.

### Вариант с rebase

```bash
git checkout feature/user-profile
git fetch origin
git rebase origin/develop
```

### Вариант с merge

```bash
git checkout feature/user-profile
git fetch origin
git merge origin/develop
```

Необходимо использовать тот вариант, который принят в правилах конкретного репозитория.

Если используется `rebase` для уже опубликованной ветки, после него может понадобиться:

```bash
git push --force-with-lease
```

Использовать:

```bash
git push --force
```

не рекомендуется.

Подробнее о безопасном использовании `--force-with-lease` см. в:

`GIT_FORCE_WITH_LEASE_RECOVERY.ru.md`

---

# 8. Release Flow

Когда набор изменений готов к релизу:

```text
develop
   │
   └──→ release/2.4.0
```

Создание:

```bash
git flow release start 2.4.0
```

Далее выполняется:

- финальное тестирование;
- исправление найденных ошибок;
- обновление версии;
- подготовка release notes;
- подготовка production-конфигурации.

Когда release готов:

```text
release/2.4.0 → main
release/2.4.0 → develop
```

То есть результат релиза должен оказаться одновременно:

```text
main
```

и

```text
develop
```

Пример:

```bash
git flow release finish 2.4.0
```

После чего изменения необходимо отправить в remote:

```bash
git push origin main
git push origin develop
git push origin --tags
```

Конкретная последовательность зависит от используемого процесса CI/CD.

---

# 9. Почему release вливается и в main, и в develop

Предположим:

```text
develop
   \
    release/2.4.0
```

Во время подготовки релиза нашли bug и исправили его в release.

Если отправить изменения только в `main`:

```text
release → main
```

то исправление может не попасть обратно в будущую разработку.

Поэтому используется:

```text
release → main
release → develop
```

Таким образом, `develop` не теряет исправления, сделанные во время подготовки релиза.

---

# 10. Hotfix Flow

Если production сломан и исправление требуется срочно:

```text
main
  \
   hotfix/2.4.1
```

Создание:

```bash
git flow hotfix start 2.4.1
```

После исправления hotfix должен попасть:

```text
hotfix/2.4.1 → main
hotfix/2.4.1 → develop
```

Пример:

```bash
git flow hotfix finish 2.4.1
```

После этого изменения публикуются:

```bash
git push origin main
git push origin develop
git push origin --tags
```

---

# 11. Bugfix Flow

Если ошибка обнаружена во время разработки и не требует отдельного production hotfix:

```text
develop
   \
    bugfix/registration
```

Создание:

```bash
git flow bugfix start registration
```

После завершения:

```text
bugfix/registration → develop
```

Использование `bugfix` или обычного `feature` для исправления ошибки определяется соглашениями проекта.

Главное — не создавать production hotfix от `develop`.

---

# 12. Support Flow

`support/*` нужен только тогда, когда проект действительно поддерживает старые версии.

Например:

```text
main
 ├── 3.0
 └── support/2.x
```

Такие ветки позволяют поддерживать старую версию отдельно от основной разработки.

Использование `support/*` должно быть явно согласовано командой.

Если проект не требует поддержки нескольких версий одновременно, `support` лучше не использовать без необходимости.

---

# 13. Публикация веток

Для feature:

```bash
git flow feature publish <name>
```

Для остальных типов веток используются соответствующие `publish`/`push` команды согласно конфигурации `git-flow`.

Перед публикацией необходимо убедиться, что ветка имеет правильное имя и базовую ветку.

Проверить ветки можно:

```bash
git branch
git branch -r
```

Полезно также посмотреть историю:

```bash
git log --all --graph --decorate --oneline
```

Это один из самых удобных способов визуально проверить, куда действительно идут изменения.

---

# 14. От какого типа ветки что создаём

| Ветка | Создаётся от | Основное назначение | Куда вливается |
|---|---|---|---|
| `feature/*` | `develop` | Новая функциональность | `develop` |
| `bugfix/*` | `develop` | Исправление ошибки в разработке | `develop` |
| `release/*` | `develop` | Подготовка релиза | `main` и `develop` |
| `hotfix/*` | `main` | Срочное production-исправление | `main` и `develop` |
| `support/*` | зависит от процесса | Поддержка старой версии | зависит от процесса |

---

# 15. Что не следует делать

## Не создавать Feature от main

Плохо:

```text
main
  \
   feature/new-payment
```

Правильно:

```text
develop
   \
    feature/new-payment
```

---

## Не вливать Feature напрямую в main

Обычно:

```text
feature → develop → release → main
```

а не:

```text
feature → main
```

---

## Не создавать Hotfix от develop

Плохо:

```text
develop → hotfix
```

Правильно:

```text
main → hotfix
```

---

## Не держать Feature зависимой от другой Feature без необходимости

Плохо:

```text
feature/A
    \
     feature/B
         \
          feature/C
```

Лучше:

```text
develop
 ├── feature/A
 ├── feature/B
 └── feature/C
```

---

## Не выполнять force push без необходимости

Опасно:

```bash
git push --force
```

Предпочтительно:

```bash
git push --force-with-lease
```

при условии, что force push разрешён правилами репозитория.

---

# 16. Рекомендуемый командный процесс

Для обычной задачи:

```text
1. develop
      ↓
2. feature/*
      ↓
3. commits
      ↓
4. push
      ↓
5. Pull Request
      ↓
6. review + CI
      ↓
7. develop
      ↓
8. release/*
      ↓
9. main
```

В виде схемы:

```text
                           Pull Request
feature/login ─────────────────────────→ develop
                                         │
                                         │ release
                                         ↓
                                      release/2.4.0
                                         │
                              ┌──────────┴──────────┐
                              ↓                     ↓
                            main                 develop
```

---

# 17. Правила Pull Request

Для командной работы рекомендуется:

1. Один PR — одна логическая задача.
2. Целевая ветка PR должна быть заранее определена.
3. PR должен проходить CI до merge.
4. Автор PR не должен самостоятельно обходить обязательные проверки.
5. Конфликты необходимо разрешать в своей рабочей ветке, а не в `develop`/`main`.
6. Не следует смешивать в одном PR feature, рефакторинг и несвязанные исправления.
7. После изменения истории feature-ветки необходимо убедиться, что PR всё ещё содержит только нужные изменения.

Рекомендуемая схема:

```text
feature/* → Pull Request → develop
release/* → Pull Request → main
hotfix/*  → Pull Request → main
```

Если политика проекта допускает отдельный merge release/hotfix в `develop`, это также должно быть явно зафиксировано в правилах репозитория.

---

# 18. Конвенция именования веток

Название ветки должно быть коротким и отражать задачу.

Примеры:

```text
feature/user-profile
feature/payment-api
feature/checkout-flow

bugfix/registration-error
bugfix/incorrect-total

release/2.4.0

hotfix/2.4.1

support/2.x
```

Не рекомендуется:

```text
feature/test
feature/new
feature/fix
feature/ivan
feature/123
```

Если в проекте используется трекер задач, предпочтительно включать идентификатор задачи:

```text
feature/PROJ-123-user-profile
bugfix/PROJ-456-invalid-total
```

Точная схема именования должна быть одинаковой для всей команды.

---

# 19. Коммиты

Коммиты должны быть:

- небольшими;
- законченными;
- логически связанными;
- понятными без чтения всего diff.

Плохо:

```bash
git commit -m "fix"
git commit -m "changes"
git commit -m "test"
```

Лучше:

```bash
git commit -m "feat: add user profile endpoint"
git commit -m "fix: validate empty email"
git commit -m "test: cover invalid payment status"
```

Если проект использует Conventional Commits, придерживаемся его правил:

```text
feat:
fix:
refactor:
test:
docs:
chore:
build:
ci:
```

---

# 20. Синхронизация перед началом работы

Перед созданием новой feature:

```bash
git fetch origin
git checkout develop
git pull --ff-only origin develop
```

После этого:

```bash
git flow feature start my-feature
```

Для `hotfix`:

```bash
git fetch origin
git checkout main
git pull --ff-only origin main
git flow hotfix start 2.4.1
```

Использование `--ff-only` помогает не создавать случайные локальные merge-коммиты во время обычного обновления базовой ветки.

---

# 21. Что делать при конфликтах

Если конфликт возник при `rebase`:

```bash
git status
```

Исправить конфликтующие файлы, затем:

```bash
git add <files>
git rebase --continue
```

Чтобы полностью отменить rebase:

```bash
git rebase --abort
```

Если конфликт возник при merge:

```bash
git status
```

После исправления:

```bash
git add <files>
git commit
```

Если merge необходимо отменить:

```bash
git merge --abort
```

Не следует разрешать конфликты вслепую. После разрешения обязательно проверить diff и тесты.

---

# 22. Удаление завершённых веток

После merge завершённая feature обычно больше не нужна.

Локальную ветку можно удалить:

```bash
git branch -d feature/my-feature
```

Удалённую ветку:

```bash
git push origin --delete feature/my-feature
```

Если репозиторий автоматически удаляет ветку после merge, вручную удалять её не нужно.

Не удаляйте ветку до тех пор, пока не убедились, что нужные изменения действительно попали в целевую ветку.

---

# 23. Как проверить поток коммитов

Для просмотра всех локальных и remote веток:

```bash
git log --all --graph --decorate --oneline --date-order
```

Полезно также:

```bash
git branch -a
```

и:

```bash
git show-branch --all
```

Пример графа:

```text
*   abc1234 (main) Merge release/2.4.0
|\
| * def5678 (release/2.4.0) Prepare release 2.4.0
| * 123abcd Fix release configuration
|/
*   456efgh (develop) Merge feature/payment
|\
| * 789ijkl (feature/payment) Add payment service
| * 012mnop Add payment validation
|/
* 345qrst Previous release
```

Такой граф позволяет быстро проверить:

- от какой ветки была создана ветка;
- какие изменения в неё попали;
- где находится merge;
- не ушла ли feature в неправильную ветку.

---

# 24. Частые ошибки

## Feature создана от старого develop

Симптом:

```text
feature
   \
    старый develop
```

Решение — сначала синхронизировать feature с актуальным `develop`.

---

## Feature случайно создана от main

Не продолжайте разработку, не проверив ситуацию.

Сначала нужно убедиться, какие изменения уже появились в feature, и только после этого выбрать способ переноса на актуальный `develop`.

В зависимости от ситуации это может быть:

```bash
git rebase develop
```

или пересоздание ветки от правильной базы.

---

## В hotfix попали незавершённые изменения из develop

Это обычно означает, что hotfix создавался не от актуального `main`.

Hotfix должен базироваться на том состоянии, которое реально находится в production.

---

## Исправление сделано только в release

Если release содержит важный bugfix, он должен попасть и в `main`, и обратно в `develop`.

Нельзя оставлять важные исправления только внутри временной release-ветки.

---

## Два разработчика одновременно переписали историю одной feature

Не следует выполнять `rebase`/force push общей ветки без согласования.

Для опубликованных веток безопаснее:

```bash
git push --force-with-lease
```

Но даже `--force-with-lease` не отменяет необходимость договориться с остальными участниками.

Подробнее: `GIT_FORCE_WITH_LEASE_RECOVERY.ru.md`.

---

# 25. Полная схема Git Flow

```text
                                      feature/A
                                     /
                                    /
                                   ●
                                  /
                                 /
develop ────────────────●────────●───────────────●────────
                         \                     /
                          \                   /
                           \                 /
                            release/2.4.0
                                   │
                          ┌────────┴────────┐
                          ↓                 ↓
                        main             develop
                          │
                          │
                          └── hotfix/2.4.1
                                  │
                         ┌────────┴────────┐
                         ↓                 ↓
                       main             develop
```

Для обычной разработки поток выглядит так:

```text
develop
   │
   ├── feature/A ──────────────┐
   │                           ↓
   ├── feature/B ─────────→ develop
   │
   └── feature/C ──────────────┘
                                │
                                ↓
                           release/2.4.0
                                │
                          ┌─────┴─────┐
                          ↓           ↓
                        main       develop
```

---

# 26. Краткая памятка

Запоминаем:

```text
feature ← develop
feature → develop

bugfix  ← develop
bugfix  → develop

release ← develop
release → main
release → develop

hotfix  ← main
hotfix  → main
hotfix  → develop
```

Основные команды:

```bash
# Feature
git flow feature start <name>
git flow feature publish <name>
git flow feature finish <name>

# Bugfix
git flow bugfix start <name>
git flow bugfix finish <name>

# Release
git flow release start <version>
git flow release finish <version>

# Hotfix
git flow hotfix start <version>
git flow hotfix finish <version>

# Support
git flow support start <name>
```

Для просмотра графа:

```bash
git log --all --graph --decorate --oneline --date-order
```

---

# 27. Наше командное правило

Перед каждой операцией с ветками нужно ответить на три вопроса:

**1. От какой ветки я создаю текущую ветку?**

**2. В какую ветку должны попасть мои изменения?**

**3. Не содержит ли моя ветка изменений, которые не должны попасть в целевую ветку?**

Если на эти три вопроса есть однозначный ответ, операция с Git почти всегда становится предсказуемой.

---

# 28. Итог

Нормальный Git Flow в команде выглядит так:

```text
                         feature
                       ↗
develop ───────────────●───────────────
   │
   │ release
   ↓
 main
   │
   │ hotfix
   ↓
 develop
```

Ключевая идея проста:

> `develop` — источник обычной разработки, `main` — источник production-правок. Feature идут в `develop`, release — в `main` и обратно в `develop`, hotfix — из `main` в `main` и обратно в `develop`.

Git Flow — это не набор обязательных магических команд. Это договорённость команды о том, **где рождаются изменения, через какие этапы проходят и в какую ветку попадают**.

Перед merge всегда проверяем целевую ветку, актуальность базы, CI и содержимое diff.
