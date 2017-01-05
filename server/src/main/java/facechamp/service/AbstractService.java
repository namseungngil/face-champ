package facechamp.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

abstract class AbstractService {
  protected ModelMapper mapper;

  /**
   * 매퍼 인스턴스를 초기화한다.
   *
   * @param propertyMaps
   * @author Just Burrow
   * @since 2016. 8. 27.
   */
  protected void initMapper(PropertyMap<?, ?>... propertyMaps) {
    this.mapper = new ModelMapper();
    for (PropertyMap<?, ?> propertyMap : propertyMaps) {
      this.mapper.addMappings(propertyMap);
    }
  }
}
