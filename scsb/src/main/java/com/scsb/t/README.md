https://www.developerfiles.com/using-uuid-as-primary-key-in-mysqlmariadb-databases/

## `templates_registration` 表格

| 欄位名        | 類型                  | 是否允許為空 | 預設值                        | 說明                           |
| ------------- | --------------------- | ------------ | ----------------------------- | ------------------------------ |
| `template_id` | `BINARY(16)`          | 否           | 无                            | 表單模板的唯一标识              |
| `template_name`| `VARCHAR(255)`       | 否           | 无                            | 表單模板的名称                  |
| `created_at`  | `DATETIME`           | 是           | 当前时间                      | 記錄建立的日期時間              |
| `updated_at`  | `DATETIME`           | 是           | 当前时间                      | 記錄最後更新的日期時間          |
| `created_by`  | `VARCHAR(255)`       | 否           | 无                            | 記錄建立者的使用者名稱或ID      |
| `status`      | `ENUM('草稿', '啟用', '禁用')` | 否    | 无                            | 表單模板的狀態               |

- 主鍵：`template_id`

INSERT INTO templates_registration (template_id, template_name, created_by, status)
VALUES
(UNHEX(REPLACE(UUID(),'-','')), '無線網路申請單', 'E001', 'Enabled'),
(UNHEX(REPLACE(UUID(),'-','')), '門禁進出申請單', 'E001', 'Enabled');
**  DELIMITER //

    CREATE FUNCTION UuidToBin(_uuid BINARY(36))
        RETURNS BINARY(16)
        LANGUAGE SQL  DETERMINISTIC  CONTAINS SQL  SQL SECURITY INVOKER
    RETURN
        UNHEX(CONCAT(
            SUBSTR(_uuid, 15, 4),
            SUBSTR(_uuid, 10, 4),
            SUBSTR(_uuid,  1, 8),
            SUBSTR(_uuid, 20, 4),
            SUBSTR(_uuid, 25) ));
    //
    CREATE FUNCTION UuidFromBin(_bin BINARY(16))
        RETURNS BINARY(36)
        LANGUAGE SQL  DETERMINISTIC  CONTAINS SQL  SQL SECURITY INVOKER
    RETURN
        LCASE(CONCAT_WS('-',
            HEX(SUBSTR(_bin,  5, 4)),
            HEX(SUBSTR(_bin,  3, 2)),
            HEX(SUBSTR(_bin,  1, 2)),
            HEX(SUBSTR(_bin,  9, 2)),
            HEX(SUBSTR(_bin, 11))
                 ));

    //
    DELIMITER ;

## 記得UUID一定要經過雙向轉換成能正常運作