package sound;

public class StaffData
{
	private int data;

	private int velocity;

	private int tick;

	private boolean noteOn;

	private int channel;

	public StaffData()
	{
		this(0, 100, 0, true, 0);
	}

	public StaffData(int data, int velocity, int tick)
	{
		this(data, velocity, tick, true, 0);
	}

	public StaffData(int data, int velocity, int tick, boolean noteOn)
	{
		this(data, velocity, tick, noteOn, 0);
	}

	public StaffData(int data, int velocity, int tick, int channel)
	{
		this(data, velocity, tick, true, channel);
	}

	public StaffData(int data, int velocity, int tick, boolean noteOn,
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

	public int getTick()
	{
		return tick;
	}

	public void setTick(int tick)
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
