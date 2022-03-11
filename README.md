# J-Planning
J에게

# Android Project Repositories
## J들을 위한 계획 설계 어플리케이션

# Project Information
- Name : J-Planning  
- Package name : com.meonjicompany.planning  
- Laguage : Java
- Minimun SDK : API 24 : Android 7.0 (Nougat)  

# 22년 3월 11일 오후 2시 16분 기준 패키지 구조
```console
C:.
└─com
    └─meonjicompany
        └─planning
            ├─activity
            │      IndexPage.java
            │      LoginPage.java
            │
            ├─adapter
            │      PlanningCardViewAdapter.java
            │
            ├─DTO
            │      PlanningItemDTO.java
            │
            ├─fragment
            │      PlanFragment.java
            │      ProfileFragment.java
            │      RepositoryFragment.java
            │
            └─kakao
                    KakaoAPIConnection.java
```

# 브랜치 만들기
```console
git checkout {브랜치} : 브랜치 이동
git checkout -b ex : 브랜치 생성 후 해당 브런치 이동

git branch -r : 원격저장소 브랜치 상태 확인
git branch -a : 로컬저장소 브랜치 상태 확인

git push origin {브랜치 이름} : 생성한 로컬 브랜치를 원격 브랜치에 추가

git branch --set-upstream-to origin/{연동할 브랜치} : 로컬저장소의 브랜치를 깃허브의 브랜치(remote branch)와 연결
```
