package jp.co.springbatch.sample.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 郵便番号FileDto.
 */
// sampleではコード簡易化のためLombokを利用しますが、実装ではLombokを利用しないでください
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostCodeFileDto {

  /** フィールド定義. */
  public static final String[] FIELD = new String[] {
      "postCode",
      "prefectureNameKana",
      "cityNameKana",
      "townNameKana",
      "prefectureNameKanji",
      "cityNameKanji",
      "townNameKanji"};

  /** 郵便番号. */
  private String postCode;

  /** 都道府県名カナ. */
  private String prefectureNameKana;

  /** 市区町村名カナ. */
  private String cityNameKana;

  /** 町域名カナ. */
  private String townNameKana;

  /** 都道府県名漢字. */
  private String prefectureNameKanji;

  /** 市区町村名漢字. */
  private String cityNameKanji;

  /** 町域名漢字. */
  private String townNameKanji;

}
