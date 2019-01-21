package jp.co.springbatch.sample.biz.chunk.writer;

import java.io.IOException;
import java.io.Writer;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import jp.co.springbatch.sample.common.code.RecodeTypeVo;
import jp.co.springbatch.sample.common.constant.ScopeCode;
import jp.co.springbatch.sample.common.util.SampleDateUtils;

/**
 * フッターWriterCallback.
 */
@Scope(ScopeCode.SINGLETON)
@Component
public class WriteFooterFlatFileCallback implements FlatFileFooterCallback {

  /** 日付ユーティリティー. */
  @Autowired
  private SampleDateUtils dateUtils;

  /**
   * フッター（トレーラレコード）書き込み.
   * 03,yyyyMMdd
   *
   * @param writer フッターの書き込みに使用するWriter
   * @throws IOException 書き込み中にエラーが発生した場合
   */
  @Override
  public void writeFooter(Writer writer) throws IOException {
    writer.write(RecodeTypeVo.TRAILER_RECORD.getCode() + "," + dateUtils.getNowDateString());
  }

}
