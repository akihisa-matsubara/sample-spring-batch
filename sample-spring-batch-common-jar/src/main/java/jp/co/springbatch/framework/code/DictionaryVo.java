package jp.co.springbatch.framework.code;

/**
 * 辞書VOインターフェース.
 */
public interface DictionaryVo<T> extends CodeVo {

  /**
   * Voを取得します.
   *
   * @param code コード
   * @return Vo
   */
  DictionaryVo<?> get(T code);

}
