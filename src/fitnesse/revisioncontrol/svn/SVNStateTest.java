package fitnesse.revisioncontrol.svn;

import fitnesse.revisioncontrol.RevisionControlOperation;
import static fitnesse.revisioncontrol.RevisionControlOperation.*;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SVNStateTest {

  @Test
  public void canPerformAddOperationIfStateIsUnknown() throws Exception {
    final RevisionControlOperation[] operations = SVNState.UNKNOWN.operations();
    assertEquals("Only 1 operation should be allowed in Unknown state", 1, operations.length);
    assertEquals(ADD, operations[0]);
  }

  @Test
  public void canPerformCheckInUpdateRevertAndDeleteOperationsIfStateIsVersioned() throws Exception {
    final RevisionControlOperation[] operations = SVNState.VERSIONED.operations();
    assertEquals("Only 4 operations should be allowed in Versioned state", 4, operations.length);
    assertContains(operations, CHECKIN, UPDATE, REVERT, DELETE);
  }

  @Test
  public void canPerformCheckInAndRevertOperationsIfStateIsDeleted() throws Exception {
    final RevisionControlOperation[] operations = SVNState.DELETED.operations();
    assertEquals("Only 2 operations should be allowed in Versioned state", 2, operations.length);
    assertContains(operations, CHECKIN, REVERT);
  }

  @Test
  public void canPerformCheckInAndRevertOperationsIfStateIsAdded() throws Exception {
    final RevisionControlOperation[] operations = SVNState.ADDED.operations();
    assertEquals("Only 2 operations should be allowed in Versioned state", 2, operations.length);
    assertContains(operations, CHECKIN, REVERT);
  }

  @Test
  public void testIsNotUnderRevisionControl() throws Exception {
    assertTrue("Files in Unknown State should not be under revision control", SVNState.UNKNOWN.isNotUnderRevisionControl());
    assertFalse("Files in Checked In State should be under revision control", SVNState.VERSIONED.isNotUnderRevisionControl());
    assertTrue("Files in Added State should not be under revision control", SVNState.ADDED.isNotUnderRevisionControl());
    assertFalse("Files in Deleted State should be under revision control", SVNState.DELETED.isNotUnderRevisionControl());
  }

  @Test
  public void testIsCheckedIn() throws Exception {
    assertTrue("Files in Checked In State should be checked in", SVNState.VERSIONED.isCheckedIn());
    assertFalse("Files in Unknown State should not be checked in", SVNState.UNKNOWN.isCheckedIn());
    assertTrue("Files in Deleted State should be checked in", SVNState.DELETED.isCheckedIn());
    assertFalse("Files in Added State should not be checked in", SVNState.ADDED.isCheckedIn());
  }

  @Test
  public void testIsCheckedOut() throws Exception {
    assertTrue("Versioned Files should be checked out", SVNState.VERSIONED.isCheckedOut());
    assertFalse("Files in Unknown State should not be checked out", SVNState.UNKNOWN.isCheckedOut());
    assertTrue("Files in Deleted State should be checked out", SVNState.DELETED.isCheckedOut());
    assertTrue("Files in Added State should not be checked out", SVNState.ADDED.isCheckedOut());
  }

  private void assertContains(RevisionControlOperation[] operations, RevisionControlOperation... expectedOperations) {
    final List<RevisionControlOperation> ops = Arrays.asList(operations);
    for (final RevisionControlOperation operation : expectedOperations)
      assertTrue(ops.contains(operation));
  }
}
