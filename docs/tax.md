# Taxes 設計

## 概要

税額の種類を DB 化する

### アクション

- 参照
- 登録
- 編集
- 削除

### 要件

- 削除する際はその ID を他で使用している場合は削除できない様にしてください。

## モデル

- Tax

| `taxes`      | Type    | memo 　 　　    |
| ------------ | ------- | --------------- |
| id           | Integer | 　 　　         |
| rate         | Integer | 　 　　         |
| tax_included | TinyInt | 0:税抜　 1:税込 |
| rounding     | String  | 　 　　         |

## 定数

TODO: データベース化

- TaxType

| ID  | Rate(%) | Tax included | Rounding |
| --- | ------- | ------------ | -------- |
| 1   | 0       | No           | Floor    |
| 2   | 0       | No           | Round    |
| 3   | 0       | No           | Ceil     |
| 4   | 0       | Yes          | Floor    |
| 5   | 0       | Yes          | Round    |
| 6   | 0       | Yes          | Ceil     |
| 7   | 8       | No           | Floor    |
| 8   | 8       | No           | Round    |
| 9   | 8       | No           | Ceil     |
| 10  | 8       | Yes          | Floor    |
| 11  | 8       | Yes          | Round    |
| 12  | 8       | Yes          | Ceil     |
| 13  | 10      | No           | Floor    |
| 14  | 10      | No           | Round    |
| 15  | 10      | No           | Ceil     |
| 16  | 10      | Yes          | Floor    |
| 17  | 10      | Yes          | Round    |
| 18  | 10      | Yes          | Ceil     |
