Для того, чтобы запустить приложение необжодимо:
1) Создать и запустить docker-container, куда будет сохраняться информация о запросах в таблицу translations.
Для это нужно запустить файл docker-compose.yml
2) Запустить преложение через файл KluchinskiyProjectApplication
3) Отправить post-запрос по адресу localhost:3000/api/v1/translate в формате
 {
    "inputString": "text",
    "sourceLang": "en",
    "targetLang": "ru"
 }
Приложение сохраняет информацию в таблицу Translations базы данных Translation, которая имеет следующмй вид:
CREATE TABLE translations (
    id BIGSERIAL PRIMARY KEY,
    ip_address VARCHAR(255),
    input_string TEXT,
    translated_string TEXT
);
можно использовать следующие команды для просмотра записей:
docker exec -it container1 bash
psql -U postgres
\c translation
select * from translations;
был использован https://translate.api.cloud.yandex.net/translate/v2/translate