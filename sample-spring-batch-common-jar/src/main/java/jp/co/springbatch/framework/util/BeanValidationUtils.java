package jp.co.springbatch.framework.util;

import jp.co.springbatch.framework.data.dto.ItemDtoBase;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bean Validationユーティリティー.
 */
public class BeanValidationUtils {

  /** Logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(BeanValidationUtils.class);

  /**
   * デフォルトコンストラクタ.
   */
  private BeanValidationUtils() {
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
    constraintViolations.forEach(cv -> LOGGER.warn("a validation error occurred. {}", cv.getMessage()));
    LOGGER.warn("{}", bean);

    throw new ConstraintViolationException(constraintViolations);
  }

}
