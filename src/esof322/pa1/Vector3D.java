package esof322.pa1;

/**
 *
 * @authors Trent Baker, Madison Fichtner, Cody Stoner, Logan Bonney
 */

public final class Vector3D 
{
    private final double x;
    private final double y;
    private final double z;
    
    
    Vector3D(double x,double y,double z)
    {
        this.x=x;
        this.y=y;
        this.z=z;
    }
    
    
    //Vector3D	scale(double f); which should multiply x, y, and z by a common factor f.
    public Vector3D scale(double f)
    {
        return new Vector3D(x*f,y*f,z*f);
    }
    
    
    //which takes one Vector3D as an argument adds the corresponding coordinates to its own and produces a new Vector3D({x0, y0, z0}+{x1, y1, z1}={x0+x1,y0+y1,z0+z1},where x0, y0, and z0 are "this" object's coordinates and x1, y1, and z1 are the argument v's coordinates)	
    public Vector3D add(Vector3D v)
    {
        return new Vector3D(x+v.x ,y+v.y ,z+v.z);
    }
    
    
    //Like add except you subtract argument v's coordinates from the corresponding coordinates in "this" producing a new Vector3D object.	
    public Vector3D subtract(Vector3D v)
    {
        return new Vector3D(x-v.x ,y-v.y ,z-v.z);
    }
    
    
    //This is shorthand for scale by -1
    public Vector3D negate()
    {
        return new Vector3D(-x,-y,-z);
    }
    

    //Produce the dot product of "this" Vector3D and argument Vector3D v({x0, y0, z0} dot {x1, y1, z1} = x0*x1 + y0*y1 + z0*z1).	
    public double dot(Vector3D v)
    {
        
    }
    
    
    //returns the magnitude of a Vector3D(sqrt(x*x + y*y + z*z)).	
    public double magnitude()
    {
        
    }
    
    
    public String toString()
    {
        
    }
    
    
}
