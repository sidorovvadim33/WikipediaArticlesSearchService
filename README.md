# WikipediaArticlesSearchService

### Описание приложения

В приложении реализован импорт данных цитат википедии из JSON-файла и поиск статьи по точному совпадению наименования без учета регистра.
Данные сохраняются в БД PostgreSQL, приложение реализовано с помощью фреймворка **Spring**. 
Разбор дампа можно производить из локальной папки предварительно разархивировав.

**JSON-файл необходимо в импортировать папку src/main/resources/static и указать название файла в application.properties. Также для запуска приложения необходимо 
в том же файле указать порт сервера, url для подключения к БД, имя и пароль для пользвателя БД.**
После заполнения файла application.properties необходимо запустить приложение. 

Для начала парсинга необходимо выполнить запрос к сервису: /parseJsonToDB. В случае если парсинг и добавление данных в БД произошли успешно на экран выводится сообщение: 
Парсинг закончился.

После чего можно выполнять запросы по поиску статьи. В приложении предусмотрены два вида вывода JSON в строке и форматированный.

**Примеры запросов к сервису: /wiki/<зазвание статьи> — ответ-строка, /wiki/<название статьи>?pretty — форматированный вывод.**

Ответ выводится в HTML с использованием шаблонизатора **Thymeleaf**. 
 
