package jp.co.springbatch.sample.common.util;

import jp.co.springbatch.sample.common.data.dto.ItemDtoBase;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bean Validationユーティリティー.
 */
public class SampleBeanValidationUtils {

  /** Logger. */
  private static final Logger log = LoggerFactory.getLogger(SampleBeanValidationUtils.class);

  private SampleBeanValidationUtils() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Bean Validaionを実行します.
   *
   * @param bean 検証ターゲット
   * @throws ConstraintViolationException 検証例外が発生した場合
   */
  public static void validate(ItemDtoBase bean) {
    Set<ConstraintViolation<ItemDtoBase>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(bean);

    // normal
    if (constraintViolations.isEmpty()) {
      return;
    }

    // fault
    for (ConstraintViolation<ItemDtoBase> cv : constraintViolations) {
      log.warn("a validation error occurred. {}", cv.getMessage());

    }

    log.warn("{}", bean);

    throw new ConstraintViolationException(constraintViolations);
  }

}
