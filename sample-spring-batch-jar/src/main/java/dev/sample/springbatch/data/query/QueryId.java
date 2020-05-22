package dev.sample.springbatch.data.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Query Id.
 */
@AllArgsConstructor
@Getter
public enum QueryId {

  /** CustomerFamilyRepository.selectAll. */
  CUSTOMER_FAMILY_SELECT_ALL("dev.sample.springbatch.data.primary.repository.CustomerFamilyRepository.selectAll"),
  /** PostCodeRepository.insert. */
  POST_CODE_INSERT("dev.sample.springbatch.data.primary.repository.PostCodeRepository.insert"),
  ;

  /** Id. */
  private String id;

}
