# Index 設計

## 概要

トップページとして各種表示を行う

### アクション

- 表示

## 要件

- 各種エンドポイントの一覧表示
- アクセス履歴の URL 一覧を表示

### アクセス履歴

ページを表示した URL をアクセス履歴として一覧で表示する。  
session に保存を行い、ログアウトした時にリセットがされる。  
再起動などでリセットされることは許容するが、同一セッション内であればリセットされないようにする。  
最大で 10 件まで保存し、11 件目以降は古いものから削除する。  
トップページの URL はアクセス履歴に含まない。  
認証ページの URL はアクセス履歴に含まない。  
アセットファイルの URL はアクセス履歴に含まない。
