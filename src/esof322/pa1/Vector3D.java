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
        return new Vector3D(this.x*f, this.y*f, this.z*f);
    }
    
    
    //which takes one Vector3D as an argument adds the corresponding coordinates to its own and produces a new Vector3D({x0, y0, z0}+{x1, y1, z1}={x0+x1,y0+y1,z0+z1},where x0, y0, and z0 are "this" object's coordinates and x1, y1, and z1 are the argument v's coordinates)	
    public Vector3D add(Vector3D v)
    {
        return new Vector3D(this.x+v.x, this.y+v.y, this.z+v.z);
    }
    
    
    //Like add except you subtract argument v's coordinates from the corresponding coordinates in "this" producing a new Vector3D object.	
    public Vector3D subtract(Vector3D v)
    {
        return new Vector3D(this.x-v.x, this.y-v.y, this.z-v.z);
    }
    
    
    //This is shorthand for scale by -1
    public Vector3D negate()
    {
        return new Vector3D(-this.x, -this.y, -this.z);
    }
    

    //Produce the dot product of "this" Vector3D and argument Vector3D v({x0, y0, z0} dot {x1, y1, z1} = x0*x1 + y0*y1 + z0*z1).	
    public double dot(Vector3D v)
    {
    	double dotProduct;
    	dotProduct = ((this.x * v.x) + (this.y * v.x) + (this.z * v.y));
        return dotProduct;
    }
    
    
    //returns the magnitude of a Vector3D(sqrt(x*x + y*y + z*z)).	
    public double magnitude()
    {
    	double magnitude;
    	magnitude = Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z));
        return magnitude;
    }
    
    
    public String toString()
    {
    	String vector;
    	vector = "[ " + this.x +", " + this.y + ", " + this.z + " ]";
        return vector;
    }
    
    public boolean equals(Vector3D v)
    {
    	if(this.x == v.x && this.y == v.y && this.z == v.z)
			return true;
		else
			return false;
    }
    
    
}
