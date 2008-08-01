// Copyright (C) 2003,2004,2005 by Object Mentor, Inc. All rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.
package fitnesse.authentication;

import fitnesse.FitNesseContext;
import fitnesse.http.Request;

public interface SecureOperation
{
	public abstract boolean shouldAuthenticate(FitNesseContext context, Request request) throws Exception;
}
