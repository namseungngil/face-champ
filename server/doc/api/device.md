# 기기 정보

인증에 사용할 기기 정보를 등록하거나 등록된 기기인지 확인한다.

1. nonce 요청.
2. 기기 정보 전송.
3. 응답 내용에 따라 리다이렉트.

## nonce 요청

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

## 기기 정보 등록

<dl>
<dt>METHOD</dt>
<dd>POST</dd>
<dt>PATH</dt>
<dd>`/device`</dd>
<dt>PARAMS</dt>
<dd>type</dd>
<dd>identifier</dd>
<dd>nonceCreate</dd>
<dd>nonceTtl</dd>
<dd>nonceToken</dd>
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
  "meta":     {},
  "method":  "GET",
  "nextApi":  "/account/create"
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