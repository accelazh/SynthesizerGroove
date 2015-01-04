package org.accela.midi.groove;

import java.util.*;

public class Composition implements Iterable<Note>
{
	public static final int DEFAULT_RESOLUTION = 4;

	private int resolution;

	private List<Note> notes;

	public Composition()
	{
		this(DEFAULT_RESOLUTION);
	}

	public Composition(int resolution)
	{
		if (resolution < 0)
		{
			throw new IllegalArgumentException(
					"resolution should not be negative");
		}

		this.resolution = resolution;
		this.notes = new LinkedList<Note>();
	}

	public int getResolution()
	{
		return resolution;
	}

	public void setResolution(int resolution)
	{
		if (resolution < 0)
		{
			throw new IllegalArgumentException(
					"resolution should not be negative");
		}

		this.resolution = resolution;
	}

	public boolean add(Note note)
	{
		if(null==note)
		{
			throw new IllegalArgumentException("note should not be null");
		}
		
		return notes.add(note);
	}
	
	public boolean remove(Note note)
	{
		if(null==note)
		{
			throw new IllegalArgumentException("note should not be null");
		}
		
		return notes.remove(note);
	}
	
	public boolean contains(Note note)
	{
		if(null==note)
		{
			throw new IllegalArgumentException("note should not be null");
		}
		
		return notes.contains(note);
	}
	
	public int size()
	{
		return notes.size();
	}
	
	public boolean isEmpty()
	{
		return notes.isEmpty();
	}
	
	public void clear()
	{
		notes.clear();
	}
	
	@Override
	public Iterator<Note> iterator()
	{
		return notes.iterator();
	}
	
	@Override
	public String toString()
	{
		StringBuffer retVal = new StringBuffer();

		retVal.append(this.getClass().getName());
		retVal.append("[");

		retVal.append("resolution=" + resolution);
		retVal.append(",");

		retVal.append("\n");

		for (Note n : notes)
		{
			retVal.append(n.toString());
			retVal.append(",");

			retVal.append("\n");
		}

		retVal.deleteCharAt(retVal.length() - 1);
		retVal.deleteCharAt(retVal.length() - 1);

		retVal.append("]");

		return retVal.toString();
	}

	
}
