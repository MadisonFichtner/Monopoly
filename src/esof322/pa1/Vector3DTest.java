package esof322.pa1;

import static org.junit.Assert.*;

import org.junit.Test;

public class Vector3DTest {

	@Test
	public void testScale() {
	int f = 3;
        int x = 2;
        int y = 1;
        int z = 0;
        
        Vector3D expectedVector = new Vector3D(6,3,0);
        Vector3D actualVector = new Vector3D(x,y,z);
        actualVector = actualVector.scale(f);
        assertEquals(expectedVector, actualVector);
	}

	@Test
	public void testAdd() {
		
		fail("Not yet implemented");
	}

	@Test
	public void testSubtract() {
		fail("Not yet implemented");
	}

	@Test
	public void testNegate() {
		fail("Not yet implemented");
	}

	@Test
	public void testDot() {
		fail("Not yet implemented");
	}

	@Test
	public void testMagnitude() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}

