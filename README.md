# taes-key-generator

### 개요

등록된 서비스별로 Unique하게 사용 가능한 `Key`를 만들어주는 Key-generator 서비스  

- 제공되는 키는 `Unique` 합니다.
- 제공되는 키는 `문자형`, `숫자형` 으로 타입 설정이 가능합니다.
- `숫자형` 키는 `mySql` 로직을 사용한 타입과, `generic` 타입중 선택이 가능합니다.

---

### 사용예제

- key 등록

```json
POST /api/key/register
{
    "key": "taes-policy-number"
    , "description":  "taes policy key"
    , "type": "number"
    , "generator": "mysql"
    , "min-length" : 10
}

POST /api/key/register
{
    "key": "taes-claim-number"
    , "description":  "taes claim key"
    , "type": "string"
}

```

- key 발급

```json
GET /api/key/taes-policy-number 
> { "value": "2755371233" }


GET /api/key/taes-claim-number
> { "value": "UCAA-E22A-OOKP-0021" }
```

---

### 개발 환경

- Java8
- Spring boot 2.4
- MySQL

---

### 고려사항

- 멀티쓰레드 환경에서 동시성을 고려하되 Key 값의 중복은 피해야함
- 스케일 아웃된 환경에서도 중복 Key 발생을 피해야함
- 키충돌을 완벽히 피할수는 없기에, 충돌시 키를 재생성 하는 로직이 필요함
- 동시성 환경을 고려해, 특히나 `generic` number 키 생성시 키 충돌을 최대한 피할수 있는 설계가 필요함


