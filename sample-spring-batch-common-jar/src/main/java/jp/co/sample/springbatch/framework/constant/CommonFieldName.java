package jp.co.sample.springbatch.framework.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 共通フィールド名.
 */
@AllArgsConstructor
@Getter
public enum CommonFieldName {

  /** itemCount. */
  ITEM_COUNT("itemCount"),
  ;

  /** フィールド名. */
  private String name;

}
