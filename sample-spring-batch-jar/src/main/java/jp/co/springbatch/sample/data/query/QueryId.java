package jp.co.springbatch.sample.data.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Query Id.
 */
@AllArgsConstructor
@Getter
public enum QueryId {

  /** CustomerFamilyRepository.selectAll. */
  CUSTOMER_FAMILY_SELECT_ALL("jp.co.springbatch.sample.data.primary.repository.CustomerFamilyRepository.selectAll"),
  /** PostCodeRepository.insert. */
  POST_CODE_INSERT("jp.co.springbatch.sample.data.primary.repository.PostCodeRepository.insert"),
  ;

  /** Id. */
  private String id;

}
