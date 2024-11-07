
# 🍔 스팡잇츠 (SpangEats) README

## 프로젝트 소개

- 스팡잇츠는 배달의민족과 쿠팡이츠를 벤치마킹하여 개발된 배달 서비스 앱의 백엔드 프로젝트입니다.
- 사용자는 다양한 가게의 메뉴를 검색하고 주문할 수 있으며, 사장님은 가게와 메뉴를 관리할 수 있습니다.
- 주요 기능으로는 회원 관리, 가게 및 메뉴 관리, 주문 관리, 리뷰 기능 등이 포함됩니다.

<br>

---

## 팀원 구성

<div align="center">

| **이한식** | **김신희** | **박지예** | **지창현** | **최서영** |
| :------: |  :------: | :------: | :------: | :------: |
| <img src="images/이한식.png" height="150"> <br/> [@coldrice99](https://github.com/coldrice99) | <img src="images/김신희.png" height="150"> <br/> [@shinheekim](https://github.com/shinheekim) | <img src="images/박지예.png" height="150"> <br/> [@bbobsang](https://github.com/bbobsang) | <img src="images/지창현.png" height="150"> <br/> [@tesbro1194](https://github.com/tesbro1194) | <img src="images/최서영.png" height="150"> <br/> [@seoyeong-4811](https://github.com/seoyeong-4811) |

</div>

<br>

---

## 개발 기간 및 개발 환경

- **전체 개발 기간**: 2024-10-31 ~ 2024-11-07
- **협업 툴**: Slack, Notion
- **Back-end**: Java, Spring Boot, JPA
- **Database**: MySQL
- **버전 및 이슈관리**: GitHub, GitHub Issues

<br>

---

## 2. 채택한 개발 기술과 브랜치 전략

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"><img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"><img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"><img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"><img src="https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white">

### Spring Boot & JPA

- **Spring Boot**를 사용하여 RESTful API 설계와 서버 구축을 진행하였습니다.
- **JPA**로 데이터베이스와의 매핑을 통해 효율적인 데이터 관리 및 트랜잭션을 유지했습니다.

### JWT 인증

- 사용자 인증에 **JWT(Json Web Token)**를 적용하여 회원 로그인과 인증을 처리했습니다.

### 브랜치 전략

- Git-flow 전략을 활용하여 **main**, **develop**, **issue** 브랜치를 관리했습니다.
    - **main**: 배포 버전을 관리하는 브랜치
    - **develop**: 개발 단계에서 통합되는 브랜치
    - **issue**: 기능별로 작업 후 develop 브랜치에 병합

<br>

---

## 프로젝트 설계

### 와이어프레임

#### 1. 회원 관리
<img src="images/와이어프레임/회원기능 와이어프레임.png" alt="회원 기능 와이어프레임" width="600">

#### 2. 가게 관리
<img src="images/와이어프레임/가게 와이어프레임.png" alt="가게 와이어프레임" width="600">

#### 3. 메뉴 관리
<img src="images/와이어프레임/메뉴 와이어프레임.png" alt="메뉴 와이어프레임" width="600">

#### 4. 주문 관리
<img src="images/와이어프레임/주문 와이어프레임.png" alt="주문 와이어프레임" width="600">

#### 5. 리뷰 관리
<img src="images/와이어프레임/리뷰 와이어프레임.png" alt="리뷰 와이어프레임" width="600">

### ERD (Entity Relationship Diagram)

서비스의 데이터베이스 구조는 아래 ERD에 기반합니다. 각 엔터티와 관계를 통해 효율적인 데이터 관리를 고려했습니다.

<img src="images/스팡잇츠 erd.png" alt="스팡잇츠 ERD" width="700">

<br>

---

## 프로젝트 구조

```
├── README.md
├── .gitignore
├── build.gradle
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.sparta.spangeats
│   │   │       ├── common                # 공통 유틸리티 및 기능
│   │   │       ├── domain                # 주요 도메인별 패키지
│   │   │       │   ├── address           # 주소 관련 엔티티 및 로직
│   │   │       │   ├── auth              # 인증 관련 로직
│   │   │       │   ├── basket            # 장바구니 기능 관련
│   │   │       │   ├── cart              # 장바구니 내 아이템 관리
│   │   │       │   ├── member            # 사용자 정보 관리
│   │   │       │   ├── menu              # 메뉴 관리
│   │   │       │   ├── order             # 주문 관리
│   │   │       │   ├── orders            # 주문 상태 관리
│   │   │       │   ├── review            # 리뷰 기능 관련
│   │   │       │   └── store             # 가게 정보 관리
│   │   │       └── security              # 보안 관련 설정
│   │   │           ├── config            # 보안 설정 구성
│   │   │           └── filter            # 인증 및 필터링 기능
│   │   └── resources
│   └── test
│       └── java
│           └── com.sparta.spangeats
│               ├── authtest             # 인증 관련 테스트
│               ├── carttest             # 장바구니 관련 테스트
│               └── review               # 리뷰 관련 테스트
└── SpangEatsApplicationTests.java
```

<br>

---

## 역할 분담

### 🧑‍💻 이한식
- **회원 기능**: 로그인, 회원가입, 회원탈퇴, 회원정보 조회 및 수정
- **카카오 소셜 로그인 기능**

### 👩‍💻 김신희
- **가게 관리**: 가게 등록, 조회, 수정, 폐업 처리
- **가게 조회 기능**: 검색 기능을 통해 가게를 이름 또는 조건별로 조회

### 👩‍💻 박지예
- **메뉴 관리**: 가게 내 메뉴 등록, 수정, 삭제
- **메뉴 상태 관리**: 가게 조회 시 삭제된 메뉴는 표시되지 않도록 처리

### 👨‍💻 지창현
- **주문 기능**: 주문 생성, 주문 상태 변경(요청, 수락, 배달중, 배달 완료)
- **주문 로그 관리**: 주문 상태 변경 시 로그 기록 기능 구현

### 👩‍💻 최서영
- **리뷰 기능**: 리뷰 작성, 수정, 삭제
- **리뷰 필터링**: 별점 기준으로 리뷰를 필터링하여 조회

<br>

---

## 페이지별 기능

### [회원가입]
- 이메일과 비밀번호를 입력하여 유효성 검사를 통과하면 회원가입 완료
- 비밀번호는 암호화 저장

### [로그인]
- 이메일과 비밀번호 입력 시 유효성 검사 후 로그인 처리

### [가게 등록]
- 사장님 권한이 있는 유저가 가게를 등록할 수 있음

### [메뉴 관리]
- 가게 내 메뉴 추가, 수정, 삭제 가능
- 삭제된 메뉴는 조회 시 표시되지 않음

### [주문]
- 사용자는 메뉴를 주문할 수 있으며, 주문 상태 변경 시 로그 기록

### [리뷰 작성]
- 리뷰 작성 시 별점 평가 가능
- 별점 범위에 따른 필터링 기능 제공

<br>

---

## 개선 목표

### 회원 기능
- **검증 로직의 일관성 유지**: 현재 검증 로직이 서비스와 필터에 분산되어 있어 관리와 유지보수가 어렵습니다. 서비스 레이어 또는 필터 중 한 곳에 검증 로직을 일관되게 통합하여, 코드 가독성과 유지보수성을 높일 예정입니다.
- **카카오 소셜 로그인 제한사항 개선**: 카카오 API를 통해 제공되는 정보가 이메일, 닉네임, 카카오 ID에 국한되어 있어, 패스워드 검증이 필요한 API 사용이 불가함. 이를 해결하기 위해, 패스워드가 필요한 서비스에 대하여 대체 인증 방안을 모색하거나, 소셜 로그인 방식에 맞는 별도의 접근 제어 로직을 고려할 예정입니다.

### 가게 관리 기능

### 메뉴 관리 기능

### 주문 관리 기능

### 리뷰 관리 기능

<br>

---
## 프로젝트 후기

### 🧑‍💻 이한식
> 회원 기능 구현을 통해 사용자 관리의 중요성을 실감했습니다. 많은 것을 배울 수 있었던 좋은 경험이었습니다.

### 👩‍💻 김신희
> 가게 관리 및 조회 기능을 통해 데이터베이스와 API 설계의 중요성을 느꼈습니다. 팀원들과 함께 프로젝트를 완성할 수 있어 기쁩니다.

### 👩‍💻 박지예
> 메뉴 관리 기능 구현 시 사용자 경험을 고려한 UI/UX의 필요성을 체감했습니다. 이번 프로젝트를 통해 성장할 수 있었습니다.

### 👨‍💻 지창현
> 주문 관리와 로그 기록을 통해 서비스 안정성을 위한 로깅의 중요성을 깨달았습니다. 모두 수고하셨습니다!

### 👩‍💻 최서영
> 리뷰 기능 구현을 통해 사용자 피드백 관리의 중요성을 느꼈습니다. 함께 해준 팀원들에게 감사드립니다.
