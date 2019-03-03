package jp.co.springbatch.sample.data.dto;

import jp.co.springbatch.sample.common.data.dto.ItemDtoBase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
@NoArgsConstructor
@AllArgsConstructor
@Data()
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PostCodeFileDto extends ItemDtoBase implements Serializable {

  static {
    List<String> tmpField = new ArrayList<>();
    tmpField.add("postCode");
    tmpField.add("prefectureNameKana");
    tmpField.add("cityNameKana");
    tmpField.add("townNameKana");
    tmpField.add("prefectureNameKanji");
    tmpField.add("cityNameKanji");
    tmpField.add("townNameKanji");
    FIELDS = Collections.unmodifiableList(tmpField);
  }

  /** serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** フィールド定義. */
  private static final List<String> FIELDS;

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

  /**
   * フィールド定義を取得します.
   *
   * @return String[] フィールド定義
   */
  public static String[] getFields() {
    return FIELDS.toArray(new String[0]);
  }
}
