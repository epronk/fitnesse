// Copyright (C) 2003,2004,2005 by Object Mentor, Inc. All rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.
package fitnesse.updates;

import fitnesse.wiki.FileSystemPage;
import fitnesse.wiki.WikiPage;
import fitnesse.wiki.WikiPageProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

public class PropertiesToXmlUpdate extends PageTraversingUpdate {
  public static final String old_propertiesFilename = "/properties";

  public PropertiesToXmlUpdate(Updater updater) {
    super(updater);
  }

  public String getMessage() {
    return "Converting properties files to XML";
  }

  public String getName() {
    return "PropertiesToXmlUpdate";
  }

  public void processPage(WikiPage page) throws Exception {
    FileSystemPage fsPage = (FileSystemPage) page;
    String path = fsPage.getFileSystemPath();

    File oldPropsFile = new File(path + old_propertiesFilename);
    Properties oldProps = loadOldProperties(oldPropsFile);
    saveNewProperties(path, oldProps);
    oldPropsFile.delete();
  }

  private void saveNewProperties(String path, Properties oldProps) throws Exception {
    File newPropsFile = new File(path + FileSystemPage.propertiesFilename);
    WikiPageProperties newProps = new WikiPageProperties();

    for (Iterator<?> iterator = oldProps.keySet().iterator(); iterator.hasNext();) {
      String key = (String) iterator.next();
      String value = (String) oldProps.get(key);
      if (!"false".equals(value))
        newProps.set(key, value);
    }

    FileOutputStream os = null;
    try {
      os = new FileOutputStream(newPropsFile);
      newProps.save(os);
    } catch (Exception e) {
      System.err.println("Failed to save new properties file: \"" + newPropsFile.getAbsolutePath() + "\" (exception: " + e + ").");
      e.printStackTrace();
      throw e;
    } finally {
      if (os != null)
        os.close();
    }
  }

  private Properties loadOldProperties(File oldPropsFile) throws IOException {
    Properties oldProps = new Properties();
    if (oldPropsFile.exists()) {
      FileInputStream is = null;
      try {
        is = new FileInputStream(oldPropsFile);
        oldProps.load(is);
      } finally {
        if (is != null)
          is.close();
      }
    }
    return oldProps;
  }
}
