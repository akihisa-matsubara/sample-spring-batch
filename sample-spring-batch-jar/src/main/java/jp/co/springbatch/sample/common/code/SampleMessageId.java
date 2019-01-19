package jp.co.springbatch.sample.common.code;

public enum SampleMessageId {
	/** */
	SAMPLE0001("SAMPLE0001"),
	;

	private String messageId;

	private SampleMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageId() {
		return messageId;
	}
}
