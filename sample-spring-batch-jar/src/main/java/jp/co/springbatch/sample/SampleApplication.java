package jp.co.springbatch.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * サンプルアプリケーション.
 */
@SpringBootApplication
public class SampleApplication {

  /**
   * メイン処理.
   * @param args パラメーター
   * @throws Exception 例外
   */
  public static void main(String[] args) throws Exception {
    System.exit(SpringApplication.exit(SpringApplication.run(SampleApplication.class, args)));
  }
}
