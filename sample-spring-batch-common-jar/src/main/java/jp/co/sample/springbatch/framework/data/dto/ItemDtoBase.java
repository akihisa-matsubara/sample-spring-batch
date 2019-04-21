package jp.co.sample.springbatch.framework.data.dto;

import lombok.Data;
import org.springframework.batch.item.ItemCountAware;

/**
 * ItemDto基底クラス.
 */
@Data
public abstract class ItemDtoBase implements ItemCountAware {

  /** item件数. */
  private int itemCount;

}
