package jp.co.springbatch.sample.common.code;

import lombok.Getter;

//sampleではコード簡易化のためLombokを利用しますが、実装ではLombokを利用しないでください
@Getter
public enum FileOperationVo implements CodeBase {

	CHECK_SAVE("CHECK_SAVE", "保存チェック処理"),
	CHECK_DELETE("CHECK_DELETE", "削除チェック処理"),
	SAVE("SAVE", "保存処理"),
	DELETE("DELETE", "削除処理")
	;

	private String code;
	private String decode;

	private FileOperationVo(String code, String decode) {
		this.code = code;
		this.decode = decode;
	}

}
