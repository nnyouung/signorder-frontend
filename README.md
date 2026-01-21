# 농인을 위한 수어 기반 카페 소통 서비스 <span>$\bf{\large{\color{#458EFD}SignOrder}}$</span> - Frontend Part
![image](https://github.com/user-attachments/assets/cb441ce8-455f-43d4-8e0f-5398f50f7209)

##### 작성/담당: 하은영

이전 PR들 (이전하기 전) : https://github.com/kookmin-sw/capstone-2025-30/pulls?q=is%3Apr+is%3Aclosed+author%3Annyouung

---

## 📋 프로젝트 개요
본 프로젝트는 수어를 인식하여 카페에서 주문 및 문의를 할 수 있는 수어 기반 카페 주문 서비스입니다.
<p style="font-size: 1.2em; color: #555; font-weight: bold;">
    <a href="https://kookmin-sw.github.io/capstone-2025-30/" style="color: #3182F6; text-decoration: none; border-bottom: 2px solid #3182F6;">
        ✨ 소개 페이지 ✨
    </a>
</p>

### 주요 기능
- 텍스트가 아닌 수어 아바타 및 이모지 기반 시각적 인터페이스 
- 카메라 기반 수어 인식 앱으로 실시간 양방향 소통 -> 농인 고객과 카페 관리자의 대화 흐름 완성
- 앱 설치 없이 QR 기반 웹 주문 시스템
- 메뉴 웹과 관리자 웹에서 주문 내역 및 진행 상황 실시간 확인
### 구성
- menu-web: 농인 고객용 메뉴 주문 웹
- counter_app: 농인 고객용 수어 인식 문의하기 앱
- admin-web: 관리자용 메뉴 주문 및 문의 확인 웹
### 기술스택

| Category                | Technology                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| ----------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Framework / Library** | ![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge\&logo=react\&logoColor=black) ![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge\&logo=javascript\&logoColor=black) ![Flutter](https://img.shields.io/badge/Flutter-02569B?style=for-the-badge\&logo=flutter\&logoColor=white) ![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge\&logo=kotlin\&logoColor=white)   |
| **Network**             | ![REST API](https://img.shields.io/badge/REST_API-121212?style=for-the-badge\&logo=postman\&logoColor=white) ![gRPC](https://img.shields.io/badge/gRPC-0089D6?style=for-the-badge\&logo=grpc\&logoColor=white) ![WebSocket](https://img.shields.io/badge/WebSocket-4A90E2?style=for-the-badge\&logo=socket.io\&logoColor=white) ![MediaPipe](https://img.shields.io/badge/MediaPipe-0097A7?style=for-the-badge\&logo=google\&logoColor=white) |
---

## 🎥 소개 영상

https://github.com/user-attachments/assets/a3128bb3-c4f2-4088-b45e-1f0ec0306331

---

## 😎 팀원

<table>
  <tr align="center">
    <td width="150">
      <a href="https://github.com/sangkim99">
        <img src="https://github.com/sangkim99.png" width="100" /><br />
        <b>김상민</b>
      </a>
    </td>
    <td width="150">
      <a href="https://github.com/decollzoq">
        <img src="https://github.com/decollzoq.png" width="100" /><br />
        <b>박민선</b>
      </a>
    </td>
    <td width="150">
      <a href="https://github.com/KooSuYeon">
        <img src="https://github.com/KooSuYeon.png" width="100" /><br />
        <b>구수연</b>
      </a>
    </td>
    <td width="150">
      <a href="https://github.com/nnyouung">
        <img src="https://github.com/nnyouung.png" width="100" /><br />
        <b>하은영</b>
      </a>
    </td>
    <td width="150">
      <a href="https://github.com/ghdyd586">
        <img src="https://github.com/ghdyd586.png" width="100" /><br />
        <b>정호용</b>
      </a>
    </td>
  </tr>
  <tr align="center">
    <td>팀장<br/>Backend</td>
    <td>Backend<br/>UI 디자인</td>
    <td>AI<br/>AI 서버 개발</td>
    <td>Frontend<br/>UI 디자인</td>
    <td>아바타 개발</td>
  </tr>
</table>

---

## 📁 폴더 구조
```
signorder-frontend/
│
├── admin-web/                # 관리자 웹 프론트엔드 (React)
│   ├── public/
│   ├── src/
│   │   ├── assets/           # 아이콘 등 정적 리소스
│   │   ├── components/       # 재사용 UI 컴포넌트 (주문 리스트, 채팅 버블 등)
│   │   ├── config/           # API 등 환경설정
│   │   ├── context/          # 전역 상태관리 (WebSocket 등)
│   │   ├── pages/            # 주요 페이지 (주문 확인, 채팅 등)
│   │   ├── styles/           # 전역/커스텀 스타일
│   │   ├── App.js            # 앱 엔트리포인트
│   │   └── index.js          # React DOM 렌더링
│   └── package.json          # 의존성 및 스크립트
│
├── menu-web/                 # 고객용 메뉴 웹 프론트엔드 (React)
│   ├── public/ 
│   ├── src/
│   │   ├── assets/           # 아이콘, 이미지 등 정적 리소스
│   │   ├── components/       # 재사용 UI 컴포넌트 (버튼, 장바구니 등)
│   │   ├── config/           # API 등 환경설정
│   │   ├── context/          # 전역 상태관리 (장바구니 등)
│   │   ├── pages/            # 주요 페이지 (카테고리, 상세, 주문 등)
│   │   ├── styles/           # 전역/커스텀 스타일
│   │   ├── App.js            # 앱 엔트리포인트
│   │   └── index.js          # React DOM 렌더링
│   └── package.json          # 의존성 및 스크립트
│
└── counter_app/              # 수어 인식 및 주문 처리 안드로이드 앱 (Kotlin)
    ├── app/
    │   ├── src/
    │   │   ├── main/
    │   │   │   ├── java/com/google/mediapipe/examples/handlandmarker/
    │   │   │   │   ├── fragment/         # 카메라, 권한 프래그먼트
    │   │   │   │   ├── HandLandmarkerHelper.kt  # 수어 인식 핵심 로직
    │   │   │   │   ├── GrpcClient.kt     # gRPC 통신 클라이언트
    │   │   │   │   ├── HomeActivity.kt   # 홈 액티비티
    │   │   │   │   └── ...               # 기타 액티비티/헬퍼
    │   │   │   ├── assets/               # MediaPipe 모델 등 리소스
    │   │   │   └── res/                  # 레이아웃, 이미지, 값 리소스
    │   │   └── androidTest/
    │   ├── build.gradle                  # 모듈별 gradle 설정
    │   └── ...                           # 기타 설정 파일
    ├── grpc/
    │   └── src/main/proto/               # gRPC 프로토콜 정의(.proto)
    └── build.gradle                      # 프로젝트 gradle 설정

```

---

## 🚀 설치 및 실행 방법

### ✨ 유의사항
- API 서버 주소, WebSocket 주소 등은 .env 및 gradle.properties 파일에서 관리
- 카운터 앱(counter_app)과 관리자 웹(admin-web)이 동시에 실행되어야 시스템이 정상적으로 작동

### admin-web, menu-web
1. cd admin-web (또는 cd menu-web)
2. npm install
3. npm start

### counter_app
1. Android Studio로 counter_app 폴더 열기
2. 실제 디바이스 또는 에뮬레이터에서 실행

---

## 📁 소개 자료
### [중간 발표 자료](https://drive.google.com/file/d/1R-pnw1muGACA_5_bLgEAGD-EcvWx0-XL/view?usp=drive_link )
### [중간 보고서](https://drive.google.com/file/d/1jetP1r_VG7WsAy0ZcKv1CvQkPUQ2O3gr/view?usp=sharing )
### [최종 발표 자료](https://www.canva.com/design/DAGnsdKrB-I/ikGDPPJzdcqZCBxnMFkpww/view?utm_content=DAGnsdKrB-I&utm_campaign=designshare&utm_medium=link2&utm_source=uniquelinks&utlId=h453a9e72fb )
### [결과 보고서](https://github.com/user-attachments/files/24760280/Sign.Order.Team.30.pdf)

 

