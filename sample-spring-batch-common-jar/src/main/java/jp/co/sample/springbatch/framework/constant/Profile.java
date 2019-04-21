package jp.co.sample.springbatch.framework.constant;

/**
 * Profile定数.
 */
public final class Profile {

  /** production, disaster recovery, staging. */
  public static final String PROD = "prod";

  /** system test. */
  public static final String ST = "st";

  /** integration test. */
  public static final String IT = "it";

  /** development. */
  public static final String DEV = "dev";

  /** test. */
  public static final String TEST = "test";

  /**
   * デフォルトコンストラクタ.
   */
  private Profile() {
    // do nothing
  }

}
