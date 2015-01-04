package sound;

import java.util.*;

public class Staff
{
	private int instrument;
	private List<StaffData> data;
	
	public Staff()
	{
		this(0);
	}
	
	public Staff(int instrument)
	{
		if(instrument<0)
		{
			throw new IllegalArgumentException("instrument should not be negative");
		}
		
		this.instrument=instrument;
		data=new LinkedList<StaffData>();
	}

	public int getInstrument()
	{
		return instrument;
	}

	public void setInstrument(int instrument)
	{
		if(instrument<0)
		{
			throw new IllegalArgumentException("instrument should not be negative");
		}
		
		this.instrument = instrument;
	}

	public List<StaffData> getData()
	{
		assert(this.data!=null);
		return data;
	}

	@Override
	public String toString()
	{
		StringBuffer retVal=new StringBuffer();
		retVal.append(getClass().getName());
		retVal.append("[");
		
		retVal.append("instrument="+instrument+":"+Groove.INSTRUMENTS[instrument]+",");
		
		for(StaffData d : data)
		{
			retVal.append(d.toString());
			retVal.append(",");
		}
		retVal.deleteCharAt(retVal.length()-1);
		
		retVal.append("]");
		
		return retVal.toString();
	}

}
