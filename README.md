# 게시판 프로젝트

## 개발 기간
2024-01-10 ~ 2024-02-08 (4주, 개인)

## ERD
![image](https://github.com/user-attachments/assets/8151b4ce-1331-4eb6-a1d7-a1a962a45e68)

## 사용 기술 및 도구
- Language: Java 11
- Framework: Spring Boot 2.7.16
- ORM: JPA
- Template Engine: Thymeleaf
- Frontend: HTML5, CSS3
- Database: MariaDB

## 구현 기능
- 회원 기능: 회원가입, 로그인(Session), 로그아웃, 회원정보 수정, 비밀번호 수정
- 게시판 기능: 게시글 작성, 수정, 삭제, 상세조회
- 댓글 기능: 댓글 작성
- 검색 기능: 작성자, 내용, 제목으로 검색, 검색 결과 Paging 처리

## 성능 개선
- 오류 메시지 화면 출력: BindingResult 객체를 활용하여 오류 메시지를 화면에 보여줌으로써 사용자 경험을 개선하였습니다.
- Session 방식 도입: 권한에 따른 접근이 필요하여 보안과 관리 측면에서 안전하게 관리할 수 있는 Session 방식을 사용하였습니다.
- 직접 구현한 PasswordEncoder: 규모가 작은 개인 프로젝트여서 Spring Security를 사용하지 않고, 평문을 암호화하는 과정이 필요하여 SHA-256 해싱 알고리즘을 직접 구현해 사용하였습니다.
