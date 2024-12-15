## 🚀 팀원 
| **이동영** | **이현지** |
| :------: |  :------: |
| [<img src="https://avatars.githubusercontent.com/u/43364585?v=4" height=150 width=150> <br/> @leedy3838](https://github.com/leedy3838) | [<img src="https://avatars.githubusercontent.com/u/110108243?v=4" height=150 width=150> <br/> @Amepistheo](https://github.com/Amepistheo) |

## 🔧 Tech Stack
- **Language**: Java 17
- **Library & Framework** : Spring Boot 3.3.3
- **Database** : NCP MySQL, NCP MongoDB, Object Storage
- **Deploy**: NCP Server, Docker, Docker Compose, Gihub Action, Nginx
- **Dependency**: Lombok, Swagger, Spring Security, JWT, FCM, OpenCSV, OpenAI, QueryDSL
- **Monitoring**: Prometheus, Grafana, Loki

### 🌱 ERD
![image](https://github.com/user-attachments/assets/f64cc8f3-83d5-4ba1-9856-01a726d3fcb7)

### 🛠️ Infra Structure
![shwimping 인프라 drawio](https://github.com/user-attachments/assets/097f9408-da28-489d-a674-219306f10a47)

## 🗂️ Project Structure

```markdown
src
├── main
│   ├── auth
│   ├── bookmark
│   ├── cardnews
│   ├── global
│   ├── place
│   ├── review
│   └── user
│       ├── application
│       ├── domain
│       ├── dto
│       |    ├── request
│       |    └── response
│       ├── exception
│       ├── presentation
│       ├── repository
│       └── util
└── resources
    ├── data
    |   ├── 기후동행쉼터.csv
    |   ├── 도서관 쉼터.csv
    |   ├── 서울시 무더위쉼터.csv
    |   ├── 서울시 한파쉼터.csv
    |   └── 스마트쉼터 현황.csv
    ├── firebase
    ├── logs
    |   ├── common
    |   └── error
    |   |   ├── file-error-appender.xml
    |   |   ├── file-info-appender.xml
    |   |   └── file-warn-appender.xml
    |   └── console-appender.xml
    ├── logback-spring.xml
    ├── application-local.yml
    └── application-prod.yml
```
