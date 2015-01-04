package org.accela.midi.groove;

import java.util.*;

import javax.sound.midi.*;

public class Groove
{
	private Sequencer sequencer;

	private Transmitter seqTransmitter;

	private Synthesizer synthesizer;

	private Receiver synthReceiver;

	private List<GrooveListener> listeners;

	public Groove()
	{
		sequencer = null;
		synthesizer = null;
		listeners = new LinkedList<GrooveListener>();

	}

	public void open() throws MidiUnavailableException
	{
		if (sequencer != null)
		{
			throw new IllegalStateException("already opened");
		}

		try
		{
			// initialize sequencer
			sequencer = MidiSystem.getSequencer();
			sequencer.open();

			sequencer.addMetaEventListener(new MetaEventListener()
			{
				public void meta(MetaMessage message)
				{
					GrooveEvent event = null;

					if (47 == message.getType()) // end of track
					{
						assert (!sequencer.isRunning());
						event = new GrooveEvent(GrooveEvent.END_OF_PLAY);
					}
					else
					{
						// do nothing
					}

					for (GrooveListener l : listeners)
					{
						assert (l != null);
						l.handleEvent(event);
					}
				}
			});

			// initialize synthesizer
			synthesizer = MidiSystem.getSynthesizer();
			synthesizer.open();

			// connecting sequencer and synthesizer
			seqTransmitter = sequencer.getTransmitter();
			synthReceiver = synthesizer.getReceiver();

			seqTransmitter.setReceiver(synthReceiver);

			// 加载默认的乐器
			synthesizer.loadInstruments(
					synthesizer.getDefaultSoundbank(),
					new Patch[] { new Patch(0, 0) });

		}
		catch (MidiUnavailableException ex)
		{
			close();
			throw ex;
		}
	}

	public boolean isOpen()
	{
		return sequencer != null;
	}

	public void close()
	{
		if (sequencer != null)
		{
			sequencer.close();
		}
		sequencer = null;

		if (synthesizer != null)
		{
			synthesizer.close();
		}
		synthesizer = null;

	}

	// ==============================设定channel属性的功能============================

	public Instrument[] getInstruments()
	{
		if (!isOpen())
		{
			throw new IllegalStateException("not opened");
		}

		javax.sound.midi.Instrument[] instruments = synthesizer
				.getDefaultSoundbank().getInstruments();
		Instrument[] retVal = new Instrument[Math.min(127, instruments.length)];
		for (int i = 0; i < Math.min(127, instruments.length); i++)
		{
			retVal[i] = new Instrument(instruments[i]);
		}

		return retVal;
	}

	public Channel[] getChannels()
	{
		if (!isOpen())
		{
			throw new IllegalStateException("not opened");
		}

		MidiChannel[] channels = synthesizer.getChannels();
		Channel[] retVal = new Channel[channels.length];
		for (int i = 0; i < channels.length; i++)
		{
			retVal[i] = new Channel(channels[i], synthesizer);
		}

		return retVal;
	}

	// =============================播放乐谱的功能====================================

	public void play(Composition comp, float tempoInBpm)
			throws InvalidMidiDataException
	{
		play(comp, false, tempoInBpm);
	}

	public void loop(Composition comp, float tempoInBpm)
			throws InvalidMidiDataException
	{
		play(comp, true, tempoInBpm);
	}

	public void play(Composition comp, boolean loop, float tempoInBpm)
			throws InvalidMidiDataException
	{
		if (null == comp)
		{
			throw new IllegalArgumentException("comp should not be null");
		}
		if (tempoInBpm < 0)
		{
			throw new IllegalStateException("tempoInBpm should not be negative");
		}
		if (!isOpen())
		{
			throw new IllegalStateException("not opened");
		}

		try
		{
			// create the track to play
			Sequence sequence = new Sequence(Sequence.PPQ, comp.getResolution());
			Track track = sequence.createTrack();

			for (Note n : comp)
			{
				track.add(createMidiEvent(n.isNoteOn() ? ShortMessage.NOTE_ON
						: ShortMessage.NOTE_OFF, n.getChannel(), n.getData(), n
						.getVelocity(), n.getTick()));
			}

			// play the track
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(loop ? Integer.MAX_VALUE : 0);
			sequencer.start();
			sequencer.setTempoInBPM(tempoInBpm);

		}
		catch (InvalidMidiDataException ex)
		{
			throw ex;
		}
	}

	private ShortMessage createShortMessage(int type, int channel, int data,
			int velocity) throws InvalidMidiDataException
	{
		ShortMessage message = new ShortMessage();
		message.setMessage(type, channel, data, velocity);

		return message;
	}

	private MidiEvent createMidiEvent(int type, int channel, int data,
			int velocity, long tick) throws InvalidMidiDataException
	{

		MidiEvent event = new MidiEvent(createShortMessage(
				type,
				channel,
				data,
				velocity), tick);

		return event;
	}

	public void stop()
	{
		if (!isOpen())
		{
			throw new IllegalStateException("not opened");
		}

		sequencer.stop();
	}

	public boolean isPlaying()
	{
		if (!isOpen())
		{
			throw new IllegalStateException("not opened");
		}

		return sequencer.isRunning();
	}

	// ==========================即时播放音符的功能=============================

	// 即时播放，忽略n.getTick()所指定的值
	public void note(Note n) throws InvalidMidiDataException
	{
		if (!isOpen())
		{
			throw new IllegalStateException("not opened");
		}

		synthReceiver.send(createShortMessage(
				n.isNoteOn() ? ShortMessage.NOTE_ON : ShortMessage.NOTE_OFF,
				n.getChannel(),
				n.getData(),
				n.getVelocity()), -1);
	}

	// =========================事件机制======================================

	public void addListener(GrooveListener l)
	{
		if (null == l)
		{
			throw new IllegalArgumentException("l should not be null");
		}

		this.listeners.add(l);
	}

	public boolean removeListener(GrooveListener l)
	{
		if (null == l)
		{
			throw new IllegalArgumentException("l should not be null");
		}

		return this.listeners.remove(l);
	}

}
