package facechamp.api.doc;

import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.restdocs.payload.FieldDescriptor;

public abstract class AbstractApiDocumentation {
  public static final String          DOCUMENTATION_PATH = "target/generated-snippets";
  public static final String          JWT_HEADER         = "X-AUTH-TOKEN";

  public static final String          RESP_META          = "meta";
  public static final String          RESP_DESC_META     = "디버깅 정보를 담든 공통 필드.";

  public static final FieldDescriptor META_DESCRIPTOR    = fieldWithPath(RESP_META).type(OBJECT)
      .description(RESP_DESC_META);

  /**
   * nonce 발급과 관련된 필드 정보 모음.
   *
   * @author Just Burrow
   * @since 2016. 8. 29.
   */
  public static class NonceDoc {
    public static final String            RESP_CREATE         = "create";
    public static final String            RESP_CREATE_DESC    = "nonce 토큰을 생성한 시각. UTC Milliseconds.";

    public static final String            RESP_TTL            = "ttl";
    public static final String            RESP_TTL_DESC       = "nonce 토큰의 유효기간. Milliseconds.";

    public static final String            RESP_TOKEN          = "token";
    public static final String            RESP_TOKEN_DESC     = "nonce 문자열. 생성 시각, TTL, 사용할 API 등의 정보로 만든 해시값.";

    /**
     * nonce 생성 API의 속성 정보.
     *
     * @author Just Burrow
     * @since 2016. 9. 4.
     */
    public static final FieldDescriptor[] ISSUE_DESCRIPTORS   = {
        fieldWithPath(RESP_CREATE).type(NUMBER).description(RESP_CREATE_DESC),
        fieldWithPath(RESP_TTL).type(NUMBER).description(RESP_TTL_DESC),
        fieldWithPath(RESP_TOKEN).type(STRING).description(RESP_TOKEN_DESC) };

    public static final String            REQ_CREATE          = "nonceCreate";
    public static final String            REQ_CREATE_DESC     = "발급받은 nonce의 create 필드.";

    public static final String            REQ_TTL             = "nonceTtl";
    public static final String            REQ_TTL_DESC        = "발급받은 nonce의 ttl 필드.";

    public static final String            REQ_TOKEN           = "nonceToken";
    public static final String            REQ_TOKEN_DESC      = "발급받은 nonce의 token 필드.";

    /**
     * nonce를 사용하는 API에 nonce 정보를 전송하는 공통 속성.
     *
     * @author Just Burrow
     * @since 2016. 9. 4.
     */
    public static final FieldDescriptor[] CONSUME_DESCRIPTORS = {
        fieldWithPath(REQ_CREATE).type(NUMBER).description(REQ_CREATE_DESC),
        fieldWithPath(REQ_TTL).type(NUMBER).description(REQ_TTL_DESC),
        fieldWithPath(REQ_TOKEN).type(STRING).description(REQ_TOKEN_DESC) };
  }

  protected Instant before;

  protected void setUp() throws Exception {
    this.before = Instant.now();
  }

  /**
   * @param fd
   * @param descriptors
   * @return
   * @author Just Burrow
   * @since 2016. 8. 30.
   */
  protected FieldDescriptor[] concat(FieldDescriptor fd, FieldDescriptor... descriptors) {
    List<FieldDescriptor> list = new ArrayList<>();
    list.add(fd);
    for (FieldDescriptor fieldDescriptor : descriptors) {
      list.add(fieldDescriptor);
    }

    return list.toArray(new FieldDescriptor[list.size()]);
  }

  /**
   * @param fd1
   * @param fd2
   * @return
   * @author Just Burrow
   * @since 2016. 8. 30.
   */
  protected FieldDescriptor[] concat(FieldDescriptor[] fd1, FieldDescriptor[] fd2) {
    List<FieldDescriptor> list = new ArrayList<>();

    for (FieldDescriptor fieldDescriptor : fd1) {
      list.add(fieldDescriptor);
    }
    for (FieldDescriptor fieldDescriptor : fd2) {
      list.add(fieldDescriptor);
    }

    return list.toArray(new FieldDescriptor[list.size()]);
  }

  /**
   * @param fdArray
   * @param fd
   * @param descriptors
   * @return
   * @author Just Burrow
   * @since 2016. 8. 30.
   */
  protected FieldDescriptor[] concat(FieldDescriptor[] fdArray, FieldDescriptor fd, FieldDescriptor... descriptors) {
    List<FieldDescriptor> list = new ArrayList<>();
    for (FieldDescriptor fieldDescriptor : fdArray) {
      list.add(fieldDescriptor);
    }
    list.add(fd);
    for (FieldDescriptor fieldDescriptor : descriptors) {
      list.add(fieldDescriptor);
    }
    return list.toArray(new FieldDescriptor[list.size()]);
  }

  /**
   * @param descriptors
   * @return
   * @author Just Burrow
   * @since 2016. 8. 30.
   */
  protected FieldDescriptor[] concatFieldDescriptors(Object... descriptors) {
    List<FieldDescriptor> list = new ArrayList<>();
    for (int i = 0; i < descriptors.length; i++) {
      Object descriptor = descriptors[i];
      if (descriptor instanceof FieldDescriptor) {
        list.add((FieldDescriptor) descriptor);
      } else if (descriptor instanceof FieldDescriptor[]) {
        for (FieldDescriptor fieldDescriptor : (FieldDescriptor[]) descriptor) {
          list.add(fieldDescriptor);
        }
      } else {
        throw new IllegalArgumentException(String.format("illegal class at %d : %s", i, descriptors));
      }
    }
    return list.toArray(new FieldDescriptor[list.size()]);
  }
}
