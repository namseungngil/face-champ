package facechamp.api.doc;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.snippet.Attributes.key;

import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.headers.ResponseHeadersSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.client.RestTemplate;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import facechamp.api.req.CreateAccountReq;
import facechamp.api.req.CreateDeviceReq;
import facechamp.api.resp.ApiGuideResp;
import facechamp.api.resp.NonceResp;
import facechamp.api.security.TokenAuthenticationService;
import facechamp.domain.Account;
import facechamp.domain.entity.ClientTypes;
import facechamp.test.EnumUtils;

public class AccountApiDocumentation extends AbstractApiDocumentation {
  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
  }

  @Test
  public void testCreateNonce() throws Exception {
    // Given
    final NonceResp nonce = new RestTemplate().getForObject("http://localhost:8080/devices/create", NonceResp.class);
    CreateDeviceReq req = new CreateDeviceReq(EnumUtils.random(ClientTypes.class).getId(),
        UUID.randomUUID().toString());
    req.setNonce(nonce);
    final ApiGuideResp redirect = new RestTemplate().postForObject("http://localhost:8080/devices", req,
        ApiGuideResp.class);
    final long deviceKey = (long) redirect.getProperties().get("deviceKey");

    // When
    ResponseFieldsSnippet respFields = responseFields(this.concat(META_DESCRIPTOR, NonceDoc.ISSUE_DESCRIPTORS));
    RequestSpecification specification = RestAssured.given(this.spec)
        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
        .filter(document("accounts/createNonce",
            pathParameters(parameterWithName("deviceKey").description("기기를 등록할 때 발급받은 기기 키.")), respFields));
    Response response = specification.when().get("/accounts/create/{deviceKey}", deviceKey);

    // Then
    response.then().assertThat().statusCode(OK.value());
  }

  @Test
  public void testCreate() throws Exception {
    // Given
    final NonceResp nonce = new RestTemplate().getForObject("http://localhost:8080/devices/create", NonceResp.class);
    CreateDeviceReq createDevice = new CreateDeviceReq(EnumUtils.random(ClientTypes.class).getId(),
        UUID.randomUUID().toString());
    createDevice.setNonce(nonce);
    final ApiGuideResp redirect = new RestTemplate().postForObject("http://localhost:8080/devices", createDevice,
        ApiGuideResp.class);
    final long deviceKey = (long) redirect.getProperties().get("deviceKey");

    final NonceResp nonceResp = new RestTemplate().getForObject("http://localhost:8080/accounts/create/" + deviceKey,
        NonceResp.class);

    // --------

    ConstraintDescriptions requestConstraints = new ConstraintDescriptions(CreateAccountReq.class);

    RequestFieldsSnippet requestFields = requestFields(
        this.concat(
            new FieldDescriptor[] {
                fieldWithPath("deviceKey").type(Long.TYPE).description("계정을 인증할 때 사용할 기기 정보.")
                    .attributes(key("constraints").value(requestConstraints.descriptionsForProperty("deviceKey"))),
                fieldWithPath("name").type(String.class)
                    .description("계정 이름. 중복 불가, 이후 변경 가능.")
                    .attributes(key("regex").value(Account.NAME_PATTERN)),
                // .attributes( key("constraints").value(requestConstraints.descriptionsForProperty("name"))),
                fieldWithPath("bio").optional().type(String.class).description("자기소개")
                    .attributes(key("constraints").value("a"))
            },
            NonceDoc.CONSUME_DESCRIPTORS));

    final String name = "name " + RandomStringUtils.randomAlphanumeric(1 + this.random.nextInt(20));
    final String bio = 1 > this.random.nextInt(20) ? null
        : "bio " + RandomStringUtils.randomAlphanumeric(1 + this.random.nextInt(1000));
    final CreateAccountReq req = new CreateAccountReq(deviceKey, name, bio);
    req.setNonce(nonceResp);

    ResponseHeadersSnippet responseHeaders = responseHeaders(
        headerWithName(TokenAuthenticationService.AUTH_HEADER_NAME)
            .description("JWT 인증 토큰. 인증이 필요한 리퀘스트는 헤더에 이 값을 추가해야 한다. "
                + "인증 이후의 모든 리스폰스에 추가되며, 리퀘스트를 보낼 때는 항상 이전 리스폰스에서 발급받은 JWT 토큰을 추가해서 사용해야 한다."));

    // When
    RequestSpecification specification = RestAssured.given(this.spec)
        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
        .contentType(ContentType.JSON)
        .filter(document("accounts/create", requestFields, responseHeaders, responseFields(REDIRECT_DESCRIPTORS)));
    Response response = specification.when().body(req).post("/accounts");

    // Then
    response.then().assertThat().statusCode(OK.value());
  }
}
