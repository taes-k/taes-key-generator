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
    , "type": "NUMBER"
    , "generator": "MYSQL"
    , "min-length" : 10
}

POST /api/key/register
{
    "key": "taes-claim-number"
    , "description":  "taes claim key"
    , "type": "STRING"
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
- 외부 환경과 완전 독립적인 테스트 환경 구축
- 동시성 환경을 테스트 할 수 있는 테스트 코드 구현 
- key별 발급 속도를 측정 할 수 있는 `profile` aop 구축

<img width="200" src="https://raw.githubusercontent.com/taes-k/taes-key-generator/main/images/key_generate_profile.png">

- `Filebeat` + `ELK` 를 통한 `profile` 데이터 분석 (설계만 진행, 해당 프로젝트에는 구현되어 있지 않음)

<img width="200" src="https://raw.githubusercontent.com/taes-k/taes-key-generator/main/images/profile_elk_architecture.png">


---

### 상세 설계내용

`Number type generic generator`  

- `Custom-Sequence` 테이블 설계로 number-key 생성
- 낙관적 락(`Optimisstic Lock`) 사용으로 `scale-out` 환경에서도 unique key를 생성을 보장
- 경쟁에서 탈락할 경우 `Retryable`을 통해 다시 키생성을 시도해 결과 얻을 수 있도록 함
- DB에서 unique를 보장하지만, 너무 많은 충돌이 발생할 경우 서비스 성능 저하가 생기기 때문에 메서드를 동기화 하여 최대한 충돌을 줄이는것이 더 효과적으로 수행됨 (scale-out 환경에서 `sticky-session` LB로 수행된다고 가정시 충돌해소에 큰 도움 될 수 있음)

<img width="200" src="https://raw.githubusercontent.com/taes-k/taes-key-generator/main/images/number_generic_key_without_syncronized.png">

<img width="200" src="https://raw.githubusercontent.com/taes-k/taes-key-generator/main/images/number_generic_key_with_syncronized.png">

`Number type mysql generator`  

- MySQL `myISAM` storage-engine 을 사용해 `Composite-key & Auto-Increment` 테이블을 통한 number-key 생성
- `myISAM` 특성상 `page-lock`으로 처리되어 `scale-out` 환경에서도 동시성 보장
- 경쟁에서 탈락할 경우 `Retryable`을 통해 다시 키생성을 시도해 결과 얻을 수 있도록 함

`String type generator`

- Random key util을 통한 무작위 String key 생성
- DB에서 unique 보장
- 경쟁에서 탈락할 경우 `Retryable`을 통해 다시 키생성을 시도해 결과 얻을 수 있도록 함




