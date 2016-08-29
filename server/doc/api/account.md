# 계정

서비스 콘텐츠에 접근할 때의 권한 제어 단위.
기기 정보를 사용해 인증을 받아서 생성, 사용한다.

## 계정 등록용 nonce 발급

<dl>
<dt>METHOD</dt>
<dd>GET</dt>
<dt>PATH</dt>
<dd>`/accounts/create/{deviceKey}`</dd>
<dt>PARAMS</dt>
<dd>`deviceKey` : [기기 정보 등록 결과](./device.md#%EA%B8%B0%EA%B8%B0-%EC%A0%95%EB%B3%B4-%EB%93%B1%EB%A1%9D )의 `properties.deviceKey`. 전송한 기기 정보의 고유값.</dd>
</dl>

REQUEST HEADER : `GET http://localhost:8080/accounts/create/5706291757239334971`
```
Connection: keep-alive
Content-Type: application/json;charset=UTF-8
Host: localhost:8080
User-Agent: Apache-HttpClient/4.5.2 (Java/1.8.0_102)
```

RESPONSE :
```javascript
{
    "meta": {},
    "create": 1472449145946,
    "ttl": 300000,
    "token": "8f1ed81cd76208f5b916536b5114f74d29756cef"
}
```

RESPONSE HEADER :
```
HTTP/1.1 200 
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Mon, 29 Aug 2016 05:39:05 GMT
```


## 계정 정보 등록

신규 사용자는 계정을 등록할 때 인증이 완료된다. 이때 응답 헤더의 `X-AUTH-TOKEN`를 다음 API 접근시에 요청 헤더에 추가하면 된다.

<dl>
<dt>METHOD</dt>
<dd>POST</dt>
<dt>PATH</dt>
<dd>`/accounts`</dd>
<dt>PARAMS</dt>
<dd>`deviceKey` : 계정을 인증할 때 사용할 기기 키.</dd>
<dd>`name` : 계정 이름. `\S.{0,43}\S`</dd>
<dd>`bio` : 자기소개.</dd>
<dd>`nonceXXX` : 생략.</dd>
</dl>

REQUEST :
```javascript
{
  "deviceKey": 5706291757239335000,
  "name": "account name #0 @1472449145953",
  "bio": "bio @1472449145953",
  "nonceCreate": 1472449145946,
  "nonceTtl": 300000,
  "nonceToken": "8f1ed81cd76208f5b916536b5114f74d29756cef"
}
```

REQUEST HEADER : `POST http://localhost:8080/accounts`
```
Connection: keep-alive
Content-Type: application/json;charset=UTF-8
Content-Length: 202
Host: localhost:8080
User-Agent: Apache-HttpClient/4.5.2 (Java/1.8.0_102)
```

RESPONSE :
```javascript
{
    "meta": {},
    "method": "GET",
    "nextApi": "/accounts/my",
    "properties": null
}
```

RESPONSE HEADER :
```
HTTP/1.1 200 
X-AUTH-TOKEN: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI4NiIsImlhdCI6MTQ3MjQ0OTE0Nn0.W4N36hR2drv56na6ILh3h_Z9YQ_XRbHjMiPhvqZGQYg
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Mon, 29 Aug 2016 05:39:05 GMT
```
