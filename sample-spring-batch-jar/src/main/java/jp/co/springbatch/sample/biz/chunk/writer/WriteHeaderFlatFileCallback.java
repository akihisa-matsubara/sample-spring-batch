package jp.co.springbatch.sample.biz.chunk.writer;

import java.io.IOException;
import java.io.Writer;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import jp.co.springbatch.sample.common.code.RecodeTypeVo;
import jp.co.springbatch.sample.common.constant.ScopeCode;
import jp.co.springbatch.sample.common.util.SampleDateUtils;

/**
 * ヘッダーWriterCallback.
 */
@Scope(ScopeCode.SINGLETON)
@Component
public class WriteHeaderFlatFileCallback implements FlatFileHeaderCallback {

  /** 日付ユーティリティー. */
  @Autowired
  private SampleDateUtils dateUtils;

  /**
   * ヘッダー書き込み.
   * 01,yyyyMMdd
   *
   * @param writer ヘッダーの書き込みに使用するWriter
   * @throws IOException 書き込み中にエラーが発生した場合
   */
  @Override
  public void writeHeader(Writer writer) throws IOException {
    writer.write(RecodeTypeVo.HEADER_RECODE.getCode() + "," + dateUtils.getNowDateString());
  }

}
