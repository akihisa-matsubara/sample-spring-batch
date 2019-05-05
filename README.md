# sample-spring-batch
Spring-Batch + Spring-Bootを利用したサンプルバッチです。  
in-memory方式のジョブリポジトリーを利用します。  
バッチ実行時にSpring-Boot(Application Server)は起動しません。  

## 動作確認環境
| Platform | Version |
| -------- | ------- |
| IBM Java | 1.8.0_181 |
| DB2 | 11.1.4.4 |

## 利用技術
| FW/OSS | Version |
| ------ | ------- |
| Spring Boot | 2.1.4.RELEASE |
| Spring Batch | 4.1.2.RELEASE |
| mybatis-spring | 2.0.1 |
| mybatis | 3.5.1 |

## 機能
- Rest Service呼び出しジョブ(CallRestService)
- DB to File(DbToFile)
- File to DB(FileToDb)

## 実行方法
1. Javaアプリケーションの実行構成より実行できます  
> ex. callRestServiceJob
