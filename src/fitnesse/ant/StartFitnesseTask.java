package fitnesse.ant;

import fitnesse.FitNesse;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * Task to start fitnesse.
 * <p/>
 * <pre>
 *    Usage:
 *    &lt;taskdef name=&quot;start-fitnesse&quot; classname=&quot;fitnesse.ant.StartFitnesseTask&quot; classpathref=&quot;classpath&quot; /&gt;
 *    OR
 *    &lt;taskdef classpathref=&quot;classpath&quot; resource=&quot;tasks.properties&quot; /&gt;
 * <p/>
 *    &lt;start-fitnesse wikidirectoryrootpath=&quot;.&quot; fitnesseport=&quot;8082&quot; /&gt;
 * </pre>
 */
public class StartFitnesseTask extends Task {
  private String wikiDirectoryRootPath;
  private int fitnessePort = 8082;

  @Override
  public void execute() throws BuildException {
    try {
      FitNesse.main(new String[]
        {"-p", String.valueOf(fitnessePort), "-d", wikiDirectoryRootPath, "-e", "0", "-o"});
      log("Sucessfully Started Fitnesse on port " + fitnessePort);
    }
    catch (Exception e) {
      throw new BuildException("Failed to start FitNesse. Error Msg: " + e.getMessage(), e);
    }
  }

  /**
   * Port on which fitnesse would run. Defaults to <b>8082</b>.
   *
   * @param fitnessePort
   */
  public void setFitnessePort(int fitnessePort) {
    this.fitnessePort = fitnessePort;
  }

  /**
   * Path to the FitnesseRoot filder which contains all the wiki pages. <b>MUST SET</b>.
   *
   * @param wikiDirectoryRootPath
   */
  public void setWikiDirectoryRootPath(String wikiDirectoryRootPath) {
    this.wikiDirectoryRootPath = wikiDirectoryRootPath;
  }
}
