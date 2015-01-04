package org.accela.midi.groove;

import javax.sound.midi.*;

public class Channel
{
	private MidiChannel channel;

	private Synthesizer synthesizer;

	protected Channel(MidiChannel channel, Synthesizer synthesizer)
	{
		if (null == channel)
		{
			throw new IllegalArgumentException("channel should not be null");
		}
		if (null == synthesizer)
		{
			throw new IllegalArgumentException("synthesizer should not be null");
		}

		this.channel = channel;
		this.synthesizer = synthesizer;

	}

	public Instrument getInstrument()
	{
		int bank = (channel.getController(0) * 128) + channel.getController(32);
		int program = channel.getProgram();
		
		javax.sound.midi.Instrument[] instruments = synthesizer
				.getLoadedInstruments();
		for (javax.sound.midi.Instrument i : instruments)
		{
			if (i.getPatch().getBank() == bank
					&& i.getPatch().getProgram() == program)
			{
				return new Instrument(i);
			}
		}

		assert(false);
		return null;
	}

	public void setInstrument(Instrument instrument)
	{
		synthesizer.loadInstruments(
				instrument.getInstrument().getSoundbank(),
				new Patch[] { instrument.getInstrument().getPatch() });
		channel.programChange(
				instrument.getInstrument().getPatch().getBank(),
				instrument.getInstrument().getPatch().getProgram());
	}

	public boolean getMono()
	{
		return channel.getMono();
	}

	public boolean getMute()
	{
		return channel.getMute();
	}

	public boolean getSolo()
	{
		return channel.getSolo();
	}

	public void setMono(boolean on)
	{
		channel.setMono(on);
	}

	public void setMute(boolean mute)
	{
		channel.setMute(mute);
	}

	public void setSolo(boolean soloState)
	{
		channel.setSolo(soloState);
	}

	public int getChannelPressure()
	{
		return channel.getChannelPressure();
	}

	public void setChannelPressure(int pressure)
	{
		channel.setChannelPressure(pressure);
	}

	public int getPitchBend()
	{
		return channel.getPitchBend();
	}

	public void setPitchBend(int bend)
	{
		channel.setPitchBend(bend);
	}

	public boolean getOmni()
	{
		return channel.getOmni();
	}

	public void setOmni(boolean on)
	{
		channel.setOmni(on);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Channel))
		{
			return false;
		}

		Channel other = (Channel) obj;

		return channel.equals(other.channel);
	}

	@Override
	public int hashCode()
	{
		return channel.hashCode();
	}
	
}
