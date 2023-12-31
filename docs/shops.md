# Shops 設計

## 概要

ショップの管理を行う

### アクション

- 一覧（検索）
- 照会
- 作成
- 更新
- 削除

### 要件

- ショップは名前、アドレス、コンタクトを持つ
- アドレスには住所か URL
- コンタクトには電話番号かメールアドレス
- 商品検索の項目
  - 名前：部分一致検索、大文字小文字を区別しない
- ショップ照会画面に紐づく商品一覧画面へ遷移する動線を設ける

## モデル

- Shop

| `shops` | Type   | Required | memo                  |
| ------- | ------ | :------: | --------------------- |
| name    | String |    ○     |                       |
| address | String |    ○     | address or URL        |
| contact | String |    ○     | email or phone number |
