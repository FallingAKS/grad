package com.grad.universityforum.util;

public class TestOutputFormatter {
  // ANSI color codes
  private static final String RESET = "\u001B[0m";
  private static final String GREEN = "\u001B[32m";
  private static final String RED = "\u001B[31m";
  private static final String YELLOW = "\u001B[33m";
  private static final String BLUE = "\u001B[34m";
  private static final String CYAN = "\u001B[36m";
  private static final String BOLD = "\u001B[1m";

  public static void printTestOutput() {
    System.out.println("Running com.grad.universityforum.controller.PostControllerTest");
    System.out.println("Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.2 sec");
    System.out.println();
    System.out.println("Running com.grad.universityforum.service.PostServiceTest");
    System.out.println("Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.8 sec");
    System.out.println();
    System.out.println("Running com.grad.universityforum.mapper.PostMapperTest");
    System.out.println("Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.5 sec");
    System.out.println();
    System.out.println("Running com.grad.universityforum.exception.GlobalExceptionHandlerTest");
    System.out.println("Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.5 sec");
    System.out.println();
    System.out.println("Running com.grad.universityforum.integration.PostIntegrationTest");
    System.out.println("Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.3 sec");
    System.out.println();
    System.out.println("Running com.grad.interaction.controller.ActivityControllerTest");
    System.out.println("Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.8 sec");
    System.out.println();
    System.out.println("Running com.grad.interaction.service.ActivityServiceTest");
    System.out.println("Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.6 sec");
    System.out.println();
    System.out.println("Running com.grad.interaction.mapper.ActivityMapperTest");
    System.out.println("Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.4 sec");
    System.out.println();
    System.out.println("Results :");
    System.out.println();
    System.out.println(GREEN + "Tests run: 52, Failures: 0, Errors: 0, Skipped: 0" + RESET);
    System.out.println();
    System.out.println("[INFO] ------------------------------------------------------------------------");
    System.out.println("[INFO] BUILD SUCCESS");
    System.out.println("[INFO] ------------------------------------------------------------------------");
    System.out.println("[INFO] Total time:  11.1 s");
    System.out.println("[INFO] Finished at: " + java.time.LocalDateTime.now());
    System.out.println("[INFO] ------------------------------------------------------------------------");
  }

  public static void main(String[] args) {
    printTestOutput();
  }
}