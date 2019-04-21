package jp.co.sample.springbatch.framework.util;

import jp.co.sample.springbatch.framework.data.dto.ItemDtoBase;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * Bean Validationユーティリティー.
 */
@UtilityClass
@Slf4j
public class BeanValidationUtils {

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
    constraintViolations.forEach(cv -> log.warn("a validation error occurred. {}", cv.getMessage()));
    log.warn("{}", bean);

    throw new ConstraintViolationException(constraintViolations);
  }

}
