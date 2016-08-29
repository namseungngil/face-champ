# 기기 정보

인증에 사용할 기기 정보를 등록하거나 등록된 기기인지 확인한다.
새로운 기기일 경우 계정을 만들 수 있는 API를 안내해주고, 계정이 있는 기기일 경우 서비스 요약 정보를 얻을 수 있는 API를 안내한다.

1. nonce 요청.
2. 기기 정보 전송.
3. 응답 내용에 따라 리다이렉트.

## 기기 정보 전송용 nonce 요청

<dl>
<dt>METHOD</dt>
<dd>GET</dt>
<dt>PATH</dt>
<dd>`/device/create`</dd>
<dt>PARAMS</dt>
<dd>없음</dd>
</dl>

REQUEST HEADER:
```
Connection: keep-alive
Content-Type: application/json;charset=UTF-8
Host: localhost:8080
User-Agent: Apache-HttpClient/4.5.2 (Java/1.8.0_102)
```

RESPONSE :
```javascript
{"meta":{},"create":1472356597649,"ttl":300000,"token":"f5101e2d3584cc7749900037f8fe1072f4fc3c06"}
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
Date: Sun, 28 Aug 2016 03:56:37 GMT
```

## 기기 정보 전송(등록 혹은 인증)

<dl>
<dt>METHOD</dt>
<dd>POST</dd>
<dt>PATH</dt>
<dd>`/device`</dd>
<dt>PARAMS</dt>
<dd>`type` : 클라이언트 OS정보.</dd>
<dd>`identifier` : 클라이언트를 설치한 기기(하드웨어)의 ID. UUID 등.</dd>
<dd>`nonceCreate` : nonce 발급 결과의 `create`. nonce 발급 시각. UTC Milliseconds.</dd>
<dd>`nonceTtl` : nonce 발급 결과의 `ttl`. nonce의 유효기간. milliseconds.</dd>
<dd>`nonceToken` : nonce 발급 결과의 `token`. 사용할 nonce 값.</dd>
</dl>

REQUEST :
```json
{
  "type":         0,
  "identifier":   "74e30c40-2689-4aa0-8755-ef9b27d4ba41",
  "nonceCreate":  1472356597649,
  "nonceTtl":     300000,
  "nonceToken":   "f5101e2d3584cc7749900037f8fe1072f4fc3c06"
}
```

REQUEST HEADER:
```
Connection: keep-alive
Content-Type: application/json;charset=UTF-8
Content-Length: 164
Host: localhost:8080
User-Agent: Apache-HttpClient/4.5.2 (Java/1.8.0_102)
```

RESPONSE :
```json
{
    "meta": {},
    "method": "GET",
    "nextApi": "/accounts/create/{deviceKey}",
    "properties": {
        "deviceKey": 5706291757239335000
    }
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
Date: Sun, 28 Aug 2016 03:56:37 GMT
```