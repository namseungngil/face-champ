package facechamp.api.security;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class JwtResponseBodyAdvice implements ResponseBodyAdvice<Object> {
  private static final Logger        log = LoggerFactory.getLogger(JwtResponseBodyAdvice.class);

  @Autowired
  private TokenAuthenticationService tokenAuthenticationService;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>ResponseBodyAdvice<Object>
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
      ServerHttpResponse response) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (null != authentication && log.isTraceEnabled()) {
      log.trace(authentication.toString());
    }

    if (null != authentication && authentication instanceof UserAuthentication
        && response instanceof ServletServerHttpResponse) {
      HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();
      this.tokenAuthenticationService.addAuthentication(resp, (UserAuthentication) authentication);
    }
    return body;
  }
}
