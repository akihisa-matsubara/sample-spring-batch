package jp.co.springbatch.sample.common.data.dto;

import lombok.Data;

import org.springframework.batch.item.ItemCountAware;

/**
 * ItemDto基底クラス.
 */
//sampleではコード簡易化のためLombokを利用しますが、実装ではLombokを利用しないでください
@Data
public abstract class ItemDtoBase implements ItemCountAware {

  /** item件数. */
  private int itemCount;

}
