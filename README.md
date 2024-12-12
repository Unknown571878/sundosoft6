# git 협업 테스트 리포지토리
* [유튜브 영상 참고](https://www.youtube.com/watch?v=lxQ-9HQDmcE)
* [블로그 글 참고1](https://velog.io/@ygy0102/GIT-%EA%B9%83%ED%97%88%EB%B8%8C%EB%A1%9C-%ED%98%91%EC%97%85%ED%95%98%EA%B8%B0-Repository-%EA%B3%B5%EC%9C%A0-PR-merge)
* [블로그 글 참고2](https://12716.tistory.com/entry/Git-GitHub-%ED%98%91%EC%97%85%ED%95%98%EA%B8%B0)


## 🚨주의할 점🚨
### 작업물 올리기 전에 최신화 필수
본인의 작업물을 add, commit, push하기 전에 필수로 pull을 해야함
* git pull origin main

## 1. 다른 사람 리포지토리를 본인 계정으로 fork하기

### 1.1 이 리포지토리에 접속해서 fork 드롭다운 클릭 후 본인 계정의 리포지토리를 새로 생성
![image](https://github.com/user-attachments/assets/be2a82d1-3b04-4e62-a494-8cb7a1475533)
### 1.2 본인 계정의 새로운 리포지토리에서 url 복사
![image](https://github.com/user-attachments/assets/a55fd56e-77d4-4b26-ad87-72be3dd74f80)

## 2. 포크한 저장소 clone하기

### 2.1 작업할 폴더에서 git bash 또는 cmd창에서 작업할 위치로 cd 후 아래 명령어 입력(' '는 생략)
* git clone '복사한url'


### 2.2 clone된 폴더로 이동
clone하면 리포지토리 이름으로 폴더가 생김

그 폴더 안에 원래 리포지토리의 파일이 들어있기 때문에 clone한 뒤 리포지토리 이름으로 생성된 폴더로 이동
* cd '리포지토리 이름'
## 3. 브랜치 생성 후 생성한 브랜치에서 작업하기

### 3.1 현재 브랜치 확인
* git branch

현재 branch의 개수와 이름 확인 가능

### 3.2 브랜치 생성 및 변경
두 가지 방법으로 branch생성 및 변경(' '는 생략)
* git branch '새로운 branch 이름' ➡️ git checkout '새로운 branch 이름'

또는
* git checkout -b '새로운 branch 이름'

## 4. 작업한 브랜치 push하기
브랜치에서 본인의 작업을 끝마친 뒤 똑같이 add, commit, push를 진행
* git status, git diff 등으로 깃에 올라갈 항목 확인 가능함

⚠️본인 작업중인 branch 로 push진행하기
   
## 5. Pull Request 보내기
작업 진행 후 본인 계정의 리포지토리를 들어간 뒤 작업한 branch로 변경하면 Pull Request가 가능함
![image](https://github.com/user-attachments/assets/87a5dd1b-489b-4cce-b797-927953ddfaae)

### 5.1 원본 리포지토리 주인에게 작업내용 알려주기
![image](https://github.com/user-attachments/assets/3419296e-7dc0-4cbe-ac4c-ae3c2bfec429)
설명을 작성한 뒤 아래 Create pull request 버튼 클릭

