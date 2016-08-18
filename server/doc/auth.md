# 인증

단말기의 하드웨어 ID를 기반으로 한 JWT(Json Web Token) 인증을 사용한다.

인증 헤더(JWT 토큰)를 발급받는 간단한 예제.

1. SERVER : nonce 발급
2. CLIENT : signing
3. CLIENT > SERVER : 인증 요청
4. 시험

## 1. nonce 발급

PATH : `GET /auth/token`

### Request

헤더 :
```
GET http://104.199.190.8/auth/token

[no cookies]

Request Headers:
Connection: keep-alive
Content-Type: application/json;charset=UTF-8
Host: 104.199.190.8
User-Agent: Apache-HttpClient/4.5.2 (Java/1.8.0_102)
```

데이터 : 없음.

### Response

`token` : signing에 사용.
`timestamp` : signing에 사용. `token`의 유효기간을 확인할 때 사용한다.

데이터 :
```json
{
    "token": "70808931bf68d1cfcb02736bbd535d79e0fe58f8",
    "timestamp": 1471497340803
}
```

헤더 :
```
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 17 Aug 2016 23:42:48 GMT
```

## 2. signing

다음 문자열(UTF-8)을 SHA1 해시 함수의 hex 문자열을 `sign`토큰의 값으로 사용한다.

```
SHA1HEX(`type`:`id`:`timestamp`:`token`)
```

예 :
* 해시 전 : `2:cfaf7483-d475-460d-b971-62ad0350ade5:1471497340803:70808931bf68d1cfcb02736bbd535d79e0fe58f8`
* 해시 후 : `57c87200bd5bdfdd7e29e01d6e1c4b1328c73315`

## 3. 인증

### Request

PATH : `POST /auth`

데이터 :
```
{
	"type": 2,
	"id": "cfaf7483-d475-460d-b971-62ad0350ade5",
	"token": "70808931bf68d1cfcb02736bbd535d79e0fe58f8",
	"tokenTimestamp": 1471497340803,
	"sign": "57c87200bd5bdfdd7e29e01d6e1c4b1328c73315"
}
```

헤더 :
```
Request Headers:
Connection: keep-alive
Content-Type: application/json;charset=UTF-8
Content-Length: 208
Host: 104.199.190.8
User-Agent: Apache-HttpClient/4.5.2 (Java/1.8.0_102)
```

### Response

데이터 : 현재 더미데이터로 특별한 의미는 없음. 향후 변경 가능성 높음.
```
{
  "message":"aaaa"
}
```

헤더 : `X-AUTH-TOKEN` 필드(JWT 토큰)가 핵심 내용. 이후의 리퀘스트는 이 헤더를 복사해 사용한다. **리스폰스마다 값이 바뀔 수 있으므로**, 마지막 리스폰스의 필드를 복사해 사용해야 한다.
```
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
X-AUTH-TOKEN: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUQXl1Vmh1cVZYIn0.8mIW2iBk2cyUqmbu-tirHloQGKe69DpEFdWJ6pqOAa8
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Thu, 18 Aug 2016 05:15:40 GMT
```

## 4. 시험

PATH : `POST /index`

### JWT 토큰을 사용할 경우

데이터 : 발급받은 JWT 토큰
```
{
	"jwt":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUQXl1Vmh1cVZYIn0.8mIW2iBk2cyUqmbu-tirHloQGKe69DpEFdWJ6pqOAa8"
}
```

Request 헤더:
```
Connection: keep-alive
X-AUTH-TOKEN: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUQXl1Vmh1cVZYIn0.8mIW2iBk2cyUqmbu-tirHloQGKe69DpEFdWJ6pqOAa8
Content-Type: application/json;charset=UTF-8
Content-Length: 109
Host: 104.199.190.8
User-Agent: Apache-HttpClient/4.5.2 (Java/1.8.0_102)
```

Response 헤더 : **새로운 JWT 토큰 헤더는 추가 예정.**
```
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Thu, 18 Aug 2016 05:15:40 GMT
```

### JWT 토큰 없을 때

Request 헤더 :
```
Connection: keep-alive
Content-Type: application/json;charset=UTF-8
Content-Length: 22
Host: 104.199.190.8
User-Agent: Apache-HttpClient/4.5.2 (Java/1.8.0_102)
```
Response 헤더 :
```
HTTP/1.1 403 Forbidden
Server: Apache-Coyote/1.1
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Thu, 18 Aug 2016 05:27:39 GMT
```
