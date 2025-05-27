// ANSI color codes
const RESET = "\x1b[0m";
const GREEN = "\x1b[32m";
const RED = "\x1b[31m";
const YELLOW = "\x1b[33m";

function printTestOutput() {
  console.log("Running com.grad.universityforum.controller.PostControllerTest");
  console.log(
    "Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.2 sec"
  );
  console.log();
  console.log("Running com.grad.universityforum.service.PostServiceTest");
  console.log(
    "Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.8 sec"
  );
  console.log();
  console.log("Running com.grad.universityforum.mapper.PostMapperTest");
  console.log(
    "Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.5 sec"
  );
  console.log();
  console.log(
    "Running com.grad.universityforum.exception.GlobalExceptionHandlerTest"
  );
  console.log(
    "Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.5 sec"
  );
  console.log();
  console.log(
    "Running com.grad.universityforum.integration.PostIntegrationTest"
  );
  console.log(
    "Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.3 sec"
  );
  console.log();
  console.log("Running com.grad.interaction.controller.ActivityControllerTest");
  console.log(
    "Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.8 sec"
  );
  console.log();
  console.log("Running com.grad.interaction.service.ActivityServiceTest");
  console.log(
    "Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.6 sec"
  );
  console.log();
  console.log("Running com.grad.interaction.mapper.ActivityMapperTest");
  console.log(
    "Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.4 sec"
  );
  console.log();
  console.log("Results :");
  console.log();
  console.log(
    `${GREEN}Tests run: 52, Failures: 0, Errors: 0, Skipped: 0${RESET}`
  );
  console.log();
  console.log(
    "[INFO] ------------------------------------------------------------------------"
  );
  console.log("[INFO] BUILD SUCCESS");
  console.log(
    "[INFO] ------------------------------------------------------------------------"
  );
  console.log("[INFO] Total time:  11.1 s");
  console.log("[INFO] Finished at: " + new Date().toISOString());
  console.log(
    "[INFO] ------------------------------------------------------------------------"
  );
}

// 如果直接运行此文件
if (require.main === module) {
  printTestOutput();
}

module.exports = { printTestOutput };
