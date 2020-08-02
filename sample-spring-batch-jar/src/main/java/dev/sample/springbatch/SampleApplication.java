package dev.sample.springbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * サンプルアプリケーション.
 */
@SpringBootApplication
public class SampleApplication {

  /**
   * メイン処理.
   * 
   * @param args パラメーター
   */
  public static void main(String[] args) {
    System.exit(SpringApplication.exit(SpringApplication.run(SampleApplication.class, args)));
  }
}
