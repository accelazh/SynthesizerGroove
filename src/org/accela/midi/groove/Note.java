package org.accela.midi.groove;

public class Note
{
	private int data;

	private int velocity;

	private long tick;

	private boolean noteOn;

	private int channel;

	public Note()
	{
		this(0, 100, 0, true, 0);
	}

	public Note(int data, int velocity, long tick)
	{
		this(data, velocity, tick, true, 0);
	}

	public Note(int data, int velocity, long tick, boolean noteOn)
	{
		this(data, velocity, tick, noteOn, 0);
	}

	public Note(int data, int velocity, long tick, int channel)
	{
		this(data, velocity, tick, true, channel);
	}

	public Note(int data, int velocity, long tick, boolean noteOn,
			int channel)
	{
		if (velocity < 0)
		{
			throw new IllegalArgumentException(
					"velocity should not be negative");
		}
		if (tick < 0)
		{
			throw new IllegalArgumentException("tick should not be negative");
		}
		if (channel < 0)
		{
			throw new IllegalArgumentException("channel should not be negative");
		}

		this.data = data;
		this.velocity = velocity;
		this.tick = tick;
		this.noteOn = noteOn;
		this.channel = channel;
	}

	public int getData()
	{
		return data;
	}

	public void setData(int data)
	{
		this.data = data;
	}

	public int getVelocity()
	{
		return velocity;
	}

	public void setVelocity(int velocity)
	{
		if (velocity < 0)
		{
			throw new IllegalArgumentException(
					"velocity should not be negative");
		}

		this.velocity = velocity;
	}

	public long getTick()
	{
		return tick;
	}

	public void setTick(long tick)
	{
		if (tick < 0)
		{
			throw new IllegalArgumentException("tick should not be negative");
		}

		this.tick = tick;
	}

	public boolean isNoteOn()
	{
		return noteOn;
	}

	public void setNoteOn(boolean noteOn)
	{
		this.noteOn = noteOn;
	}

	public int getChannel()
	{
		return channel;
	}

	public void setChannel(int channel)
	{
		if (channel < 0)
		{
			throw new IllegalArgumentException("channel should not be negative");
		}

		this.channel = channel;
	}

	@Override
	public String toString()
	{
		return getClass().getName()+"["
		+"data="+data+","
		+"velocity="+velocity+","
		+"tick="+tick+","
		+"noteOn="+noteOn+","
		+"channel="+channel
		+"]";
	}

}
