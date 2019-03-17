package jp.co.springbatch.framework.code;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

/**
 * 性別VO.
 */
@Getter
public enum GenderVo implements DictionaryVo<String> {

  /** 男性. */
  MALE("0", "男性"),
  /** 女性. */
  FEMALE("1", "女性"),
  ;

  /** コード. */
  private String code;

  /** デコード. */
  private String decode;

  /** Vo逆引きMap. */
  private static final Map<String, GenderVo> TO_ENUM;

  static {
    Map<String, GenderVo> tmpMap = new HashMap<>();
    // Stream APIは遅いので forで実装
    for (GenderVo vo : values()) {
      tmpMap.put(vo.getCode(), vo);
    }
    TO_ENUM = Collections.unmodifiableMap(tmpMap);
  }

  /**
   * デフォルトコンストラクタ.
   *
   * @param code コード
   * @param decode デコード
   */
  private GenderVo(String code, String decode) {
    this.code = code;
    this.decode = decode;
  }

  /**
   * Voを取得します.
   *
   * @param code コード
   * @return Vo
   */
  @Override
  public GenderVo get(String code) {
    return TO_ENUM.get(code);
  }

}
