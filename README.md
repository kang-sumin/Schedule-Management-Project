# Schedule-Management-Project
일정 관리 앱 서버 만들기
### **요구사항**

### 1단계

기능: 일정 작성

### 조건

-   할일, 담당자명, 비밀번호, 작성/수정일을 저장할 수 있습니다.
    -   기간 정보는 날짜와 시간을 모두 포함한 형태 입니다.
-   각 일정의 고유 식별자(ID)를 자동으로 생성하여 관리합니다.
-   최초 입력간에는 수정일은 작성일과 동일합니다.
-   등록된 일정의 정보를 반환 받아 확인할 수 있습니다.

### 2단계

기능: 선택한 일정 조회

### 조건

-   선택한 일정 단건의 정보를 조회할 수 있습니다.
-   일정의 고유 식별자(ID)를 사용하여 조회합니다.

### 3단계

기능: 일정 목록 조회

### 조건

-   다음 조건을 바탕으로 등록된 일정 목록을 전부 조회할 수 있습니다.
    -   수정일 (형식 : YYYY-MM-DD)
    -   담당자명
-   조건 중 한 가지만을 충족하거나, 둘 다 충족을 하지 않을 수도, 두 가지를 모두 충족할 수도 있습니다.
-   수정일 기준 내림차순으로 정렬하여 조회합니다.

### 4단계

기능: 선택한 일정 수정

### 조건

-   선택한 일정 내용 중 할일, 담당자명 만 수정 가능합니다.
    -   서버에 일정 수정을 요청할 때 비밀번호를 함께 전달합니다.
    -   작성일 은 변경 안되며, 수정일 은 수정 시점으로 변경합니다.
-   수정된 일정의 정보를 반환 받아 확인할 수 있습니다.

### 5단계

기능: 선택한 일정 삭제

### 조건

-   선택한 일정을 삭제할 수 있습니다.
    -   서버에 일정 수정을 요청할 때 비밀번호를 함께 전달합니다.

---

### **Class Diagram**

[##_Image|kage@x5sGh/btsI5BCw5gP/zCurGkDoai3bANgA3qe5o0/img.png|CDM|1.3|{"originWidth":3378,"originHeight":1664,"style":"alignCenter"}_##]

---

### **API 명세서**

[https://documenter.getpostman.com/view/37567342/2sA3s7iUQn](https://documenter.getpostman.com/view/37567342/2sA3s7iUQn)

[Schedule Management API document

The Postman Documenter generates and maintains beautiful, live documentation for your collections. Never worry about maintaining API documentation again.

documenter.getpostman.com](https://documenter.getpostman.com/view/37567342/2sA3s7iUQn)

[##_Image|kage@cvpXXx/btsI4bymn85/JcKxh8HMfkEcVg7cRoKJyK/img.png|CDM|1.3|{"originWidth":1920,"originHeight":5018,"style":"alignCenter"}_##]

---

### **ERD**

[##_Image|kage@rrRhX/btsI5cwu06t/acZ48dKcyFSq5KLt7mDUe1/img.png|CDM|1.3|{"originWidth":866,"originHeight":495,"style":"alignCenter","filename":"Blank diagram - Page 1.png"}_##]

---

### **SQL**

```
create table schedule(
                         id int not null auto_increment,
                         todo varchar(100) not null,
                         charge varchar(20) not null,
                         password varchar(10) not null,
                         createDate datetime not null,
                         updateDate datetime not null,
                         primary key (id)
);
```