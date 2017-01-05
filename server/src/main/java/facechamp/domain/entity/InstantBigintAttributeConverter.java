package facechamp.domain.entity;

import java.time.Instant;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Just Burrow
 * @since 2016. 8. 26.
 */
@Converter(autoApply = true)
public class InstantBigintAttributeConverter implements AttributeConverter<Instant, Long> {
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>AttributeConverter<Instant, Long>
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Long convertToDatabaseColumn(Instant instant) {
    return null == instant ? null : instant.toEpochMilli();
  }

  @Override
  public Instant convertToEntityAttribute(Long utcMillis) {
    return null == utcMillis ? null : Instant.ofEpochMilli(utcMillis);
  }
}
