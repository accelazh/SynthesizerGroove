package sound;

import java.util.*;

public class Composition
{
	public static final int DEFAULT_RESOLUTION=4;
	
	private int resolution;
	
	private List<Staff> staff;
	
	public Composition()
	{
		this(DEFAULT_RESOLUTION);
	}
	
	public Composition(int resolution)
	{
		if(resolution<0)
		{
			throw new IllegalArgumentException("resolution should not be negative");
		}
		
		this.resolution=resolution;
		this.staff=new LinkedList<Staff>();
	}
	
	public List<Staff> getStaff()
	{
		assert(this.staff!=null);
		return this.staff;
	}

	public int getResolution()
	{
		return resolution;
	}

	public void setResolution(int resolution)
	{
		if(resolution<0)
		{
			throw new IllegalArgumentException("resolution should not be negative");
		}
		
		this.resolution = resolution;
	}

	@Override
	public String toString()
	{
		StringBuffer retVal=new StringBuffer();
		
		retVal.append(this.getClass().getName());
		retVal.append("[");
		
		retVal.append("resolution="+resolution);
		retVal.append(",");
		
		retVal.append("\n");
		
		for(Staff s : staff)
		{
			retVal.append(s.toString());
			retVal.append(",");
			
			retVal.append("\n");
		}
		
		retVal.deleteCharAt(retVal.length()-1);
		retVal.deleteCharAt(retVal.length()-1);
		
		retVal.append("]");
		
		return retVal.toString();
	}
	
}
