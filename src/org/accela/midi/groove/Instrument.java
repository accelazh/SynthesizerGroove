package org.accela.midi.groove;

public class Instrument
{
	private javax.sound.midi.Instrument instrument;

	public Instrument(javax.sound.midi.Instrument instrument)
	{
		if (null == instrument)
		{
			throw new IllegalArgumentException("instrument should not be null");
		}

		this.instrument = instrument;
	}

	public String getName()
	{
		return instrument.getName();
	}

	protected javax.sound.midi.Instrument getInstrument()
	{
		return instrument;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Instrument))
		{
			return false;
		}

		Instrument other = (Instrument) obj;

		return instrument.equals(other.instrument);
	}

	@Override
	public int hashCode()
	{
		return instrument.hashCode();
	}

	@Override
	public String toString()
	{
		return getClass().getName()+"[name="+getName()+"]";
	}

}
