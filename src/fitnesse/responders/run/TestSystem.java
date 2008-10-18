package fitnesse.responders.run;

import fitnesse.FitNesseContext;
import fitnesse.components.CommandRunner;
import fitnesse.wiki.PageData;
import fitnesse.wiki.WikiPage;

public abstract class TestSystem {
  public static final String DEFAULT_COMMAND_PATTERN = "java -cp %p %m";
  protected FitNesseContext context;
  protected WikiPage page;
  protected TestSystemListener listener;

  public TestSystem(FitNesseContext context, WikiPage page, TestSystemListener listener) {
    this.context = context;
    this.page = page;
    this.listener = listener;
  }

  public abstract ExecutionLog createRunner(
    String classPath, String className
  ) throws Exception;

  protected String buildCommand(String program, String classPath) throws Exception {
    String testRunner = page.getData().getVariable("COMMAND_PATTERN");
    if (testRunner == null)
      testRunner = DEFAULT_COMMAND_PATTERN;
    String command = replace(testRunner, "%p", classPath);
    command = replace(command, "%m", program);
    return command;
  }

  // String.replaceAll(...) is not trustworthy because it seems to remove all '\' characters.
  protected static String replace(String value, String mark, String replacement) {
    int index = value.indexOf(mark);
    if (index == -1)
      return value;

    return value.substring(0, index) + replacement + value.substring(index + mark.length());
  }

  public abstract void bye() throws Exception;

  public abstract void send(String s) throws Exception;

  public abstract boolean isSuccessfullyStarted();

  public abstract void kill() throws Exception;

  public abstract void start() throws Exception;

  public static class TestSummary {
    public int right = 0;
    public int wrong = 0;
    public int ignores = 0;
    public int exceptions = 0;

    public TestSummary(int right, int wrong, int ignores, int exceptions) {
      this.right = right;
      this.wrong = wrong;
      this.ignores = ignores;
      this.exceptions = exceptions;
    }

    public TestSummary() {
    }

    public String toString() {
      return
        right + " right, " +
          wrong + " wrong, " +
          ignores + " ignored, " +
          exceptions + " exceptions";
    }

    public void tally(TestSummary source) {
      right += source.right;
      wrong += source.wrong;
      ignores += source.ignores;
      exceptions += source.exceptions;
    }

    public boolean equals(Object o) {
      if (o == null || !(o instanceof TestSummary))
        return false;
      TestSummary other = (TestSummary) o;
      return right == other.right &&
        wrong == other.wrong &&
        ignores == other.ignores &&
        exceptions == other.exceptions;
    }

    public void tallyPageCounts(TestSummary counts) {
      if (counts.wrong > 0)
        wrong += 1;
      else if (counts.exceptions > 0)
        exceptions += 1;
      else if (counts.ignores > 0 && counts.right == 0)
        ignores += 1;
      else
        right += 1;
    }

  }
}
