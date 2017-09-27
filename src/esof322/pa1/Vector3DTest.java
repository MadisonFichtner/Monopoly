package esof322.pa1;

import static org.junit.Assert.*;

import org.junit.Test;

public class Vector3DTest {
	double f = 3;
    double x = 2;
    double y = 1;
    double z = 0;

	@Test
	public void testScale() {
        Vector3D expectedVector = new Vector3D(6,3,0);
        Vector3D actualVector = new Vector3D(x,y,z);
        actualVector = actualVector.scale(f);
        
        assertTrue(actualVector.equals(expectedVector));
	}

	@Test
	public void testAdd() {
		Vector3D addingVector = new Vector3D(f, f, f);   //Adding vector (3,3,3)
		Vector3D actualVector = new Vector3D(x, y, z);   //Vector being added to (2,1,0)
		Vector3D expectedVector = new Vector3D(5, 4, 3); //Expected result of (2+3, 1+3, 0+3)
		actualVector = actualVector.add(addingVector);

		assertTrue(actualVector.equals(expectedVector));
	}

	@Test
	public void testSubtract() {
		Vector3D subtractingVector = new Vector3D(f, f, f);
		Vector3D actualVector = new Vector3D(x, y, z);
		Vector3D expectedVector = new Vector3D(-1, -2, -3);
		actualVector = actualVector.subtract(subtractingVector);
		
		assertTrue(actualVector.equals(expectedVector));
	}

	@Test
	public void testNegate() {
		Vector3D actualVector = new Vector3D(x, y, z);
		Vector3D expectedVector = new Vector3D(-x, -y, -z);
		actualVector = actualVector.negate();
		
		assertTrue(actualVector.equals(expectedVector));
	}

	@Test
	public void testDot() {
		Vector3D dotVector = new Vector3D(f, f, f);
		Vector3D actualVector = new Vector3D(x, y, z);
		double actualDotProduct = actualVector.dot(dotVector);
		double expectedDotProduct = ((f*x) + (f*y) + (f*z));
		
		assertTrue(expectedDotProduct == actualDotProduct);
	}

	@Test
	public void testMagnitude() {
		Vector3D actualVector = new Vector3D(x, y, z);
		double actualVectorMagnitude = actualVector.magnitude();
		double expectedVectorMagnitude = Math.sqrt(5);
		assertTrue(expectedVectorMagnitude == actualVectorMagnitude);
	}

	@Test
	public void testToString() {
		Vector3D actualVector = new Vector3D(x, y, z);
		String actualVectorString = actualVector.toString();
		String expectedVectorString = "[ 2.0, 1.0, 0.0 ]";
		assertEquals(expectedVectorString, actualVectorString);
	}
	
	@Test
	public void equals() {
		
	}

}

