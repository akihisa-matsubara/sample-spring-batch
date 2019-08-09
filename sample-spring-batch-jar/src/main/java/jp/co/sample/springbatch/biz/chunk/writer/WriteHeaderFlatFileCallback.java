package jp.co.sample.springbatch.biz.chunk.writer;

import jp.co.sample.springbatch.framework.code.RecodeTypeVo;
import jp.co.sample.springbatch.framework.constant.ScopeConst;
import jp.co.sample.springbatch.framework.util.SystemDateUtils;
import java.io.IOException;
import java.io.Writer;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * ヘッダーWriterCallback.
 */
@Scope(ScopeConst.SINGLETON)
@Service
public class WriteHeaderFlatFileCallback implements FlatFileHeaderCallback {

  /**
   * ヘッダー書き込み.
   * 01,yyyyMMdd
   *
   * @param writer {@link Writer} ヘッダーの書き込みに使用するWriter
   * @throws IOException 書き込み中にエラーが発生した場合
   */
  @Override
  public void writeHeader(Writer writer) throws IOException {
    writer.write(RecodeTypeVo.HEADER_RECODE.getCode() + "," + SystemDateUtils.getNowDateAsString());
  }

}
