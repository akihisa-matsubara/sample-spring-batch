package jp.co.springbatch.sample.data.dto;

import jp.co.springbatch.sample.common.data.dto.ItemDtoBase;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 郵便番号FileDto.
 */
// sampleではコード簡易化のためLombokを利用しますが、実装ではLombokを利用しないでください
// ExecutionContextへ格納するためにSerializableをimplementsします
@NoArgsConstructor
@AllArgsConstructor
@Data()
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PostCodeFileDto extends ItemDtoBase implements Serializable {

  /** serialVersionUID. */
  private static final long serialVersionUID = 1L;

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
