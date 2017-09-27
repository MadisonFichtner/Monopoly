/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esof322.pa1;

/**
 *
 * @author q68p799
 */
public class Vector3DTest 
{
    public static void main(String[] args) 
    {
        // TODO code application logic here
        boolean testingScale = testScale();
    }
    
            
    //I haven't actually run any of this code as the machine I'm on doesn't even have java, so bear with me
    //but after minimal research this looks like it is kind of what we're looking for if anyone is confused
    public boolean testScale()
    {
        int f = 3;
        int x = 2;
        int y = 1;
        int z = 0;
        
        Vector3D expectedVector = new Vector3D(6,3,0);
        Vector3D actualVector = new Vector3D(2,1,0);
        actualVector = actualVector.scale(f);
        assertEquals(expectedVector, actualVector);
    }
}
