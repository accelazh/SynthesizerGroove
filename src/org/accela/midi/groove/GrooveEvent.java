package org.accela.midi.groove;

public class GrooveEvent
{
	public static final int END_OF_COMPOSITION = 0;
	public static final int END_OF_PLAY = 1;

	private int type;

	public GrooveEvent(int type)
	{
		this.type = type;
	}

	public int getType()
	{
		return type;
	}

}
