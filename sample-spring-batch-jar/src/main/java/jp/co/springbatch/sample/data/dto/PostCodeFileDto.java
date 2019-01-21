package jp.co.springbatch.sample.data.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.batch.item.ItemCountAware;
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
public class PostCodeFileDto implements ItemCountAware {

  /** フィールド定義. */
  public static final String[] FIELD = new String[] {
      "postCode",
      "prefectureNameKana",
      "cityNameKana",
      "townNameKana",
      "prefectureNameKanji",
      "cityNameKanji",
      "townNameKanji"};

  /** item件数. */
  private int itemCount;

  /** 郵便番号. */
  @NotNull
  @Size(min = 7, max = 7)
  private String postCode;

  /** 都道府県名カナ. */
  private String prefectureNameKana;

  /** 市区町村名カナ. */
  private String cityNameKana;

  /** 町域名カナ. */
  private String townNameKana;

  /** 都道府県名漢字. */
  @NotNull
  @Size(min = 1, max = 60)
  private String prefectureNameKanji;

  /** 市区町村名漢字. */
  @NotNull
  @Size(min = 1, max = 60)
  private String cityNameKanji;

  /** 町域名漢字. */
  @NotNull
  @Size(min = 1, max = 60)
  private String townNameKanji;

}
