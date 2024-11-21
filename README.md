# Doosan-Relay
두산 중계서버 API용 웹 프로젝트

- Java 1.8 (Spring Boot)

* 개발내용
프로젝트명 : Doosan-Relay (JAVA1.8, Gradle, SpringBoot)

* 개발 내용
* 초기환경 구축
	- logback
	- profile(local, dev, prod)
	- 3th party lib implementation
	
* 공통 모듈 개발
	- AOP : Req/Res Log 출력 (TODO : Req/Res Report 저장)
	- 중계서버API -> SFDC 요청 모듈 개발

* 중계서버 도메인 오픈 요청
1. PS Org (Sandbox)
 - doosanenerbility--ps.sandbox.my.salesforce.com
 
2. 운영 Org
 - doosanenerbility.my.salesforce.com
 
 
### 중계서버 공인IP 설정 요청
 - IP : 
 - 연동테스트 진행 (중계서버 <-> SFDC)

### 연동 실패했을 경우, SFDC 화면에서 연동프로세스 실행이 가능한 버튼 등을 구현
 - SFDC -> 중계서버 API 쪽으로 Batch 실행 요청
 
===========================================================================
### SFDC -> 중계서버 RestAPI 통신

 * Prod Org
	- doosanenerbility.lightning.force.com
	- doosanenerbility.my.salesforce.com

 * PS Org
	- doosanenerbility--ps.sandbox.my.salesforce.com
	- doosanenerbility--ps.sandbox.lightning.force.com
===========================================================================

기본 패키지 구조 : com.daeu.doosan

1. DoosanRelayApplication.class
	- Spring Boot 메인 클래스 (main 함수 실행 시, 프로젝트 실행)
	
2. aop.ApplicationAop.class
	- AOP 클래스
	- 현재 요청/응답 로그 출력용으로 사용 중
	
3. common.ApplicationInit.class
	- Spring Boot 실행시, 실행되는 클래스 (시작 직후 단 한번)

4. web.*.class
	- API 정의 클래스
===========================================================================
