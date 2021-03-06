// Copyright (C) 2003,2004,2005 by Object Mentor, Inc. All rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.
package fitnesse.authentication;

import fitnesse.FitNesseContext;
import fitnesse.Responder;
import fitnesse.http.MockRequest;
import fitnesse.responders.UnauthorizedResponder;
import fitnesse.responders.WikiPageResponder;
import fitnesse.testutil.SimpleAuthenticator;
import fitnesse.wiki.InMemoryPage;
import fitnesse.wiki.PageData;
import fitnesse.wiki.WikiPage;
import junit.framework.TestCase;

public class AuthenticatorTest extends TestCase {
  SimpleAuthenticator authenticator;
  private WikiPage root;
  private MockRequest request;
  private Responder responder;
  private Class<? extends Responder> responderType;
  private WikiPageResponder privilegedResponder;
  private FitNesseContext context;

  public void setUp() throws Exception {
    root = InMemoryPage.makeRoot("RooT");
    WikiPage frontpage = root.addChildPage("FrontPage");
    makeReadSecure(frontpage);
    authenticator = new SimpleAuthenticator();
    privilegedResponder = new WikiPageResponder();

    request = new MockRequest();
    request.setResource("FrontPage");
    context = new FitNesseContext(root);
  }

  private void makeReadSecure(WikiPage frontpage) throws Exception {
    PageData data = frontpage.getData();
    data.setAttribute(WikiPage.SECURE_READ);
    frontpage.commit(data);
  }

  public void tearDown() throws Exception {
  }

  public void testNotAuthenticated() throws Exception {
    makeResponder();
    assertEquals(UnauthorizedResponder.class, responderType);
  }

  public void testAuthenticated() throws Exception {
    authenticator.authenticated = true;
    makeResponder();
    assertEquals(WikiPageResponder.class, responderType);
  }

  private void makeResponder() throws Exception {
    responder = authenticator.authenticate(context, request, privilegedResponder);
    responderType = responder.getClass();
  }
}
