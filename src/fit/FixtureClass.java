// Copyright (C) 2003,2004,2005 by Object Mentor, Inc. All rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.
package fit;

import fit.exception.*;

public class FixtureClass
{
  private Class klass;

  public FixtureClass(Class klass)
  {
    this.klass = klass;
  }

  public Fixture newInstance() throws IllegalAccessException
  {
    // Instantiate according to policies?
    // Example: policy #1 -- has default constructor
    // ...

    String fixtureClassName = klass.getName();

    try
    {
      Object fixtureAsObject = klass.newInstance();

      if (fixtureAsObject instanceof Fixture)
      {
        return (Fixture) fixtureAsObject;
      }
      else
      {
        throw new ClassIsNotFixtureException(fixtureClassName);
      }
    }
    catch (IllegalAccessException unhandled)
    {
      // TODO: Handle constructor not public?
      throw unhandled;
    }
    catch (InstantiationException e)
    {
      // TODO: Handle interface/abstract class case?
      throw new NoDefaultConstructorFixtureException(fixtureClassName);
    }
  }
}
