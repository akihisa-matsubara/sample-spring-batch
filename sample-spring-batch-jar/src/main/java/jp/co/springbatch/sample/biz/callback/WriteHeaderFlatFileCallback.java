package jp.co.springbatch.sample.biz.callback;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jp.co.springbatch.sample.common.code.ScopeVo;

@Scope(ScopeVo.SINGLETON)
@Component
public class WriteHeaderFlatFileCallback implements FlatFileHeaderCallback {

	@Override
	public void writeHeader(Writer writer) throws IOException {
		writer.write("sample header");
	}

}
