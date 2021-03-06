// Copyright (C) 2003,2004,2005 by Object Mentor, Inc. All rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.
package fitnesse.wiki;

import java.util.LinkedList;

public class WikiPageUtil {
  public static LinkedList<WikiPage> getAncestorsOf(WikiPage page) throws Exception {
    PageCrawler crawler = page.getPageCrawler();
    LinkedList<WikiPage> ancestors = new LinkedList<WikiPage>();
    WikiPage parent = page;
    do {
      parent = parent.getParent();
      ancestors.add(parent);
    } while (!crawler.isRoot(parent));

    return ancestors;
  }

  public static LinkedList<WikiPage> getAncestorsStartingWith(WikiPage page) throws Exception {
    LinkedList<WikiPage> ancestors = getAncestorsOf(page);
    ancestors.addFirst(page);
    return ancestors;
  }

  public static void setPageContents(WikiPage page, String pageContents) throws Exception {
    PageData pageData = page.getData();
    pageData.setContent(pageContents);
    page.commit(pageData);
  }
}
