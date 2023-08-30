# Project theme: Task Management System

The goal of the project is to create a convenient task management system that will allow users to effectively organize
and control the execution of their tasks.

## Features of the project implementation and technologies used

- The project is presented in the form of microservices;
- Spring Boot
- Spring Data JPA;
- Hibernate;
- PostgreSQL;
- Spring MVC;
- Spring Security;
- Spring Mail (For sending mail to verify the user);
- Swagger (Open api files are attached to the project for each service);
- Nginx (Nginx is used for interaction between services);
- Feign (To send a request between services, feign is used);
- Docker (The project can be raised in Docker).

## Description of functionality:

The objective is to develop a task management system that will allow users to create, view,
update and delete tasks. The system should provide the following functionality:

1. Basic operations with tasks
2. Task notifications
3. Team and collaboration
4. Priorities and categories of tasks
5. Analytics and reporting

### Basic operations with tasks

1. Create a task:
   - Users should be able to create new tasks.
   - Each task should contain a title, description, completion status and due date.
   - The user should be able to specify the responsible executor for each task.
2. View tasks:
   - Users should be able to view a list of all tasks.
   - Each task should be presented with basic information including title, progress status and due date
     execution.
   - Users should be able to filter and sort tasks by various parameters.
3. Task update:
   - Users should be able to update task information.
   - This may include changing the progress status, due date, or responsible person.
4. Deleting tasks:
   - Users should be able to delete tasks that are no longer required.
5. Additional functionality:
   - Implement the ability to attach files to tasks.

### Task notifications

1. Implement a notification system that will notify users of new tasks, status changes,
   upcoming deadlines and other events.

2. Notifications can be sent via email, SMS or mobile notifications.

### Team and Collaboration

1. Add the ability to create teams or projects where users can collaborate on tasks.
2. Implement the functionality of assigning responsible executors for each task within the team.
3. Provide the ability to share comments and files between team members.

### Priorities and task categories

1. Add a system of priorities and categories for tasks.
2. Implement the ability to set a priority for each task (for example, low, medium, high).
3. Let users create and categorize tasks for easier management and classification.

### Analytics and reporting

1. Implement analytics functionality that will allow users to receive reports on the completion of tasks, time,
   spent on each task and other statistics.
2. Enable the ability to generate graphs and charts for visual presentation of data.

## Instructions for running a project in Docker

To run a project in Docker, you will need:
- apache-maven-3.9.4;
- Docker.

Before creating containers:
- To send mail and verification, you must enter the login and password from your mail in the file
   - TaskManager/user-service/src/main/resources/application.yml
   - For the mail to work correctly, you must enter a password special for applications
   - Implemented sending using google.com
- After, execute the command 'mvn install' in the root directory;
- Run the command 'docker-compose up' to build the project in docker;
- To view urls, open api file is available at http://localhost:81
- To access the database, PGAdmin is available at http://localhost:82
   - Login: admin@admin.com
   - Password: root
   - Password to servers: q1w2e3r4
- A user with the ADMIN role was initially created in the database:
   - Login: admin@admin.com
   - Password: admin!

___

# Тема проекта: Управление задачами (Task Management System)

Цель проекта - создать удобную систему для управления задачами, которая позволит пользователям эффективно организовывать
и контролировать выполнение своих задач.

## Особенности реализации проекта и используемые технологии

- Проект представлен в виде микросервисов;
- Spring Boot;
- Spring Data JPA;
- Hibernate;
- PostgreSQL;
- Spring MVC;
- Spring Security;
- Spring Mail (Для отправки почты с целью верификации пользователя);
- Swagger (К проекту прилагается open api files к каждому сервиису);
- Nginx (Для взаимодействия между сервисами используется nginx);
- Feign (Для отправки запроса между сервисами используется feign);
- Docker (Проект можно поднять в Docker).

## Описание функционала:

Задача состоит в разработке системы управления задачами, которая позволит пользователям создавать, просматривать,
обновлять и удалять задачи. Система должна обеспечивать следующий функционал:

1. Базовые операции с задaчами
2. Уведомления о задачах
3. Команда и совместная работа
4. Приоритеты и категории задач
5. Аналитика и отчетность

### Базовые операции с задачами

1. Создание задачи:
    - Пользователи должны иметь возможность создавать новые задачи.
    - Каждая задача должна содержать заголовок, описание, статус выполнения и срок выполнения.
    - Пользователь должен иметь возможность указать ответственного исполнителя для каждой задачи.
2. Просмотр задач:
    - Пользователи должны иметь возможность просматривать список всех задач.
    - Каждая задача должна быть представлена с основной информацией, включая заголовок, статус выполнения и срок
      выполнения.
    - Пользователи должны иметь возможность фильтровать и сортировать задачи по различным параметрам.
3. Обновление задач:
    - Пользователи должны иметь возможность обновлять информацию о задаче.
    - Это может включать изменение статуса выполнения, срока выполнения или ответственного исполнителя.
4. Удаление задач:
    - Пользователи должны иметь возможность удалять задачи, которые больше не требуются.
5. Дополнительный функционал:
    - Реализуйте возможность прикрепления файлов к задачам.

### Уведомления о задачах

1. Реализуйте систему уведомлений, которая будет оповещать пользователей о новых задачах, изменениях статуса,
   приближающихся сроках выполнения и других событиях.

2. Уведомления могут быть отправлены через электронную почту, SMS или мобильные уведомления.

### Команда и совместная работа

1. Добавьте возможность создания команд или проектов, где пользователи смогут совместно работать над задачами.
2. Реализуйте функционал назначения ответственных исполнителей для каждой задачи внутри команды.
3. Обеспечьте возможность обмена комментариями и файлами между участниками команды.

### Приоритеты и категории задач

1. Добавьте систему приоритетов и категорий для задач.
2. Реализуйте возможность установки приоритета для каждой задачи (например, низкий, средний, высокий).
3. Позвольте пользователям создавать и присваивать категории задачам для более удобного управления и классификации.

### Аналитика и отчетность

1. Реализуйте функционал аналитики, который позволит пользователям получать отчеты о выполнении задач, времени,
   затраченном на каждую задачу и другую статистику.
2. Включите возможность генерации графиков и диаграмм для наглядного представления данных.

## Инструкция по запуску проекта в Docker

Для запуска проекта в Docker потребуется:
- apache-maven-3.9.4;
- Docker.

Перед созданием контейнеров:
- Для отправки почты и верификации необходимо ввести логин и пароль от вашей почты в файле
   - TaskManager/user-service/src/main/resources/application.yml
   - Для корректной работы почты, пароль необходимо ввести специальный для приложений
   - Реализована отправка почти при помощи google.com
- После, выполните команду 'mvn install' в корневой директории;
- Выполните команду 'docker-compose up' для сборки проекта в docker;
- Для просмотра урлов доступен open api file по адресу http://loccalhost:81
- Для доступа к базе данных доступен PGAdmin по адресу http://localhost:82
   - Логин: admin@admin.com
   - Пароль: root
   - Пароль к серверам: q1w2e3r4
- В базе изначально создан пользователь с ролью ADMIN:
   - Логин: admin@admin.com
   - Пароль: admin!

