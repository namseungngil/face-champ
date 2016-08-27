package facechamp.service;

import org.modelmapper.ModelMapper;

abstract class AbstractService {
  protected ModelMapper mapper;

  /**
   * 매퍼 인스턴스를 초기화한다.
   *
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  protected void initMapper() {
    this.mapper = new ModelMapper();
  }
}
