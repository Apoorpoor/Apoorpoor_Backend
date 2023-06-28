# 🐳 어푸어푸 🐳

![브로슈어 썸네일](https://github.com/Apoorpoor/Apoorpoor_Backend/assets/97949070/e17530f8-ebad-4a09-a278-a1f65e80d045)

## 🌊 배포 주소
https://www.apoorpoor.com

## 🌊 You Tube 시연 영상
추가예정

## 🌊 프로젝트 개요

### 🐳 프로젝트명
나만의 거지 키우기 - 어푸어푸

### 🐳 프로젝트 한줄 소개
기본적인 가계부 기능에 나만의 거지 캐릭터인 푸어의 포인트를 모아 옷을 입혀 자랑하고, 다른 이용자들과 지출 내용에 대한 오픈 채팅이 가능한 서비스입니다

### 🐳 개발 기간
2023년 5월 19일 ~ 6월 23일 (35일)

### 🐳 팀원 소개
항해99 14기 스프링반
<table>
  <tbody>
    <tr>
      <td align="center"><a href="https://github.com/eivomin"><img src="https://avatars.githubusercontent.com/u/97949070?v=4" width="100px;" alt=""/><br /><sub><b>BE 리더 : 조유민</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/chaeeun0-o"><img src="https://avatars.githubusercontent.com/u/122453216?v=4" width="100px;" alt=""/><br /><sub><b>BE 팀원 : 김채은</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/leesco22"><img src="https://avatars.githubusercontent.com/u/127731995?v=4" width="100px;" alt=""/><br /><sub><b>BE 팀원 : 이세훈</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/OliveLover"><img src="https://avatars.githubusercontent.com/u/118647313?v=4" width="100px;" alt=""/><br /><sub><b>BE 팀원 : 이현규</b></sub></a><br /></td>
    </tr>
  </tbody>
</table>

## 🌊 기술 스택 & 협업
**Back-End**
<div align="start">
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=black">
  <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">
  <img src="https://img.shields.io/badge/websocket-010101?style=for-the-badge&logo=websocket&logoColor=white">
  <img src="https://img.shields.io/badge/AWS S3-569A31?style=for-the-badge&logo=AWS S3&logoColor=white">
  <img src="https://img.shields.io/badge/JSON WEB TOKENS-000000?style=for-the-badge&logo=JSON WEB TOKENS&logoColor=white">
  <img src="https://img.shields.io/badge/AWS EC2-FF920F?style=for-the-badge">
  <img src="https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=black">
  <img src="https://img.shields.io/badge/Github Actions-2088FF?style=for-the-badge&logo=Github Actions&logoColor=white">
  
</div>
<br/>

**Tools**
<div>
  <img src="https://img.shields.io/badge/GITHUB-181717?style=for-the-badge&logo=github&logoColor=white">
  <img src="https://img.shields.io/badge/NOTION-181717?style=for-the-badge&logo=notion&logoColor=white">
  <img src="https://img.shields.io/badge/SLACK-4A154B?style=for-the-badge&logo=slack&logoColor=white">
  <img src="https://img.shields.io/badge/googlesheets-34A853?style=for-the-badge&logo=googlesheets&logoColor=white">
  <img src="https://img.shields.io/badge/discord-5865F2?style=for-the-badge&logo=discord&logoColor=white">
</div>

## 🌊 서비스 아키텍쳐
<img width="1299" alt="image" src="https://github.com/Apoorpoor/Apoorpoor_Backend/assets/97949070/3e7682a1-791c-419c-9b74-76eaacf74220">

## 🌊 기술적 의사결정

| 선택 기술 | 선택이유 및 근거 |
| --- | --- |
| SSE | 사용자가 현재 캐릭터의 상태와 이벤트로 인한 변화를 즉시 인지할 수 있도록 하고자 함. 포인트 획득, 레벨업, 챌린지 등의 불특정한 시간에 발생하는 이벤트에 대해 자동으로 알림을 전송하여 사용자에게 변화된 상태를 알려주는 방법이 필요했음. 이를 위해 Server-Sent Events (SSE)가 가장 적합한 기술이라고 판단하였음. |
| QueryDSL | 가계부 관련 필터링 쿼리가 여러 파라미터를 받아 복잡하고 동적인 쿼리를 작성해야 함, 컴파일 시점에 문법 오류를 확인할 수 있어 유동적인 쿼리 작성이 가능한 QueryDSL을 사용함 |
| Github Action | CI/CD에는 여러가지 툴이 있지만 다른 툴들에 비해 설정 프로세스가 매우 간단하고 public repo 를 사용할 시에 무료라는 점에 사용함 |
| Redis | 채팅 내역을 바로 데이터베이스에 저장하고 불러오는 것은 데이터베이스에 부하를 초래할 수 있어서, 캐싱을 사용하여 임시 저장 함으로써 데이터베이스의 부하를 줄이고 응답 시간을 개선하고 하였음. |
| Websocket | WebSocket은 데이터 송수신을 동시에 할 수 있는 양방향 통신관계이기 때문에 서버와 클라이언트 간의 실시간 업데이트를 해야 하는 채팅에 아주 적합함. |

## 🌊 서비스 핵심기능


## 🌊 트러블 슈팅

## 🌊 API 명세 / 와이어프레임
<details>
<summary>API명세</summary>
<div markdown="1">

https://docs.google.com/spreadsheets/d/1KdPC1GW8KxtWxX5jGaHe6r8tpokIT2xPr7AI5BdSE3M/edit#gid=0

</div>
</details>
<details>
<summary>와이어프레임</summary>
<div markdown="1">

https://www.figma.com/file/a4yKIz6LCPMPVbfaAmPgZg/%EC%96%B4%ED%91%B8%EC%96%B4%ED%91%B8?type=design&node-id=1-1560&mode=design&t=ZJtpxJrY7pYxlWpa-0

</div>
</details>

## 🌊 ERD
![model](https://github.com/Apoorpoor/Apoorpoor_Backend/assets/97949070/5ddca576-c2b7-456e-a017-fb6972dc0f0d)

## 🌊 피드백 개선

