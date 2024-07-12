# Дипломный проект по профессии "Тестировщик"


## Документация




## Инструкция для запуска автотестов

### Перечень необходимого ПО

- IntelliJ IDEA

- Google Chrome

- Docker Desktop

### Порядок запуска автотестов и формирования отчётности

 - клонировать !(репозиторий)[https://github.com/mmpomail/Automation-Diplom](https://github.com/mmpomail/AutomationDiplomWork)
 
 - запустить Docker Desktop, убедиться, что в программе отображается статус "Engine running"
 
 - открыть проект из клонированного репозитория в IntelliJ IDEA
 
 - в терминале IntelliJ IDEA выполнить команду docker compose up
 
 #### Прогон тестов с использованием базы данных MySQL
 
 - открыть дополнительную вкладку в терминале IntelliJ IDEA, ввести команду java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar
 
 - убедиться, что в браузере Google Chrome открывается страница http://localhost:8080
 
 - открыть дополнительную вкладку в терминале IntelliJ IDEA, ввести команду ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"
 
 #### Прогон тестов с использованием базы данных PostgreSQL
 
 - открыть дополнительную вкладку в терминале IntelliJ IDEA, ввести команду java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar
 
 - убедиться, что в браузере Google Chrome открывается страница http://localhost:8080
 
 - открыть дополнительную вкладку в терминале IntelliJ IDEA, ввести команду ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"
 
 #### Формирование отчётности
 
 - после прогона автотестов сгенерировать отчёт Allure, нажав кнопку "Gradle" на правой боковой панели, в появившемся окне зайти в папку verification и дважды кликнуть на allureReport
 
 - открыть файл index.html, находящийся в папке build\reports\allure-report\allureReport
 
 
 
 
