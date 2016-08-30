package facechamp.api.doc;

import static facechamp.api.doc.AbstractApiDocumentation.NonceDoc.CONSUME_DESCRIPTORS;
import static facechamp.api.doc.AbstractApiDocumentation.NonceDoc.ISSUE_DESCRIPTORS;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.snippet.Attributes.key;

import java.util.UUID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.util.MimeTypeUtils;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import facechamp.api.req.CreateDeviceReq;
import facechamp.api.resp.NonceResp;
import facechamp.domain.entity.ClientTypes;
import facechamp.test.EnumUtils;

public class DeviceApiDocumentation extends AbstractApiDocumentation {
  @Rule
  public final JUnitRestDocumentation jUnitRestDocumentation = new JUnitRestDocumentation(
      AbstractApiDocumentation.DOCUMENTATION_PATH);

  private RequestSpecification        spec;

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    this.spec = new RequestSpecBuilder().addFilter(documentationConfiguration(this.jUnitRestDocumentation)).build();
  }

  @Test
  public void testCreateNonce() {
    // Given
    RequestSpecification specification = RestAssured.given(this.spec)
        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
        .filter(document("device/createNonce", responseFields(this.concat(META_DESCRIPTOR, ISSUE_DESCRIPTORS))));

    // When
    Response response = specification.when().get("/devices/create");

    // Then
    response.then().assertThat()
        .statusCode(is(200));
  }

  @Test
  public void testCreate() throws Exception {
    // Given
    final int type = EnumUtils.random(ClientTypes.class).getId();
    final String identifier = UUID.randomUUID().toString();
    final NonceResp nonce = RestAssured
        .given(new RequestSpecBuilder().addFilter(documentationConfiguration(this.jUnitRestDocumentation)).build())
        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
        .contentType(ContentType.JSON)
        .when().get("/devices/create").as(NonceResp.class);

    ConstraintDescriptions constrats = new ConstraintDescriptions(CreateDeviceReq.class);
    FieldDescriptor[] reqFields = this.concatFieldDescriptors(
        fieldWithPath("type").type(NUMBER).description("클라이언트 OS 종류.")
            .attributes(key("constraints").value(constrats.descriptionsForProperty("type"))),
        fieldWithPath("identifier").type(STRING).description("클라이언트를 설치한 기기 ID. UUID, Vendor ID 등.")
            .attributes(key("identifier").value(constrats.descriptionsForProperty("identifier"))),
        CONSUME_DESCRIPTORS);

    RequestSpecification specification = RestAssured.given(this.spec)
        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
        .contentType(ContentType.JSON)
        .filter(document("device/create", requestFields(reqFields)));

    // When
    CreateDeviceReq req = new CreateDeviceReq(type, identifier);
    req.setNonce(nonce);

    Response response = specification
        .body(req)
        .when()
        .post("/devices");

    // Then
    response.then().assertThat()
        .statusCode(is(200))
        .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
  }
}
