package sound;

import javax.sound.midi.*;

import java.util.*;

public class CopyOfGroove
{
	public static final String[] INSTRUMENTS = { "Acoustic bass drum",
			"Bass drum 1", "Side stick", "Acoustic snare", "Hand clap",
			"Electric snare", "Low floor tom", "Closed hi-hat",
			"High floor tom", "Pedal hi-hat", "Low tom", "Open hi-hat",
			"Low-mid tom", "Hi-mid tom", "Crash cymbal 1", "High tom",
			"Ride cymbal 1", "Chinese cymbal", "Ride bell", "Tambourine",
			"Splash cymbal", "Cowbell", "Crash cymbal 2", "Vibraslap",
			"Ride cymbal 2", "Hi bongo", "Low bongo", "Mute hi conga",
			"Open hi conga", "Low conga", "High timbale", "Low timbale",
			"High agogo", "Low agogo", "Cabasa", "Maracas", "Short whistle",
			"Long whistle", "Short guiro", "Long guiro", "Claves",
			"Hi wood block", "Low wood block", "Mute cuica", "Open cuica",
			"Mute triangle", "Open triangle" };

	private static final int INSTRUMENT_ID_OFFSET = 35;

	private Sequencer sequencer;
	
	private List<GrooveListener> listeners;

	public CopyOfGroove()
	{
		sequencer = null;
		listeners = new LinkedList<GrooveListener>();
	}

	// ===================================打开和关闭===================================

	public void open() throws MidiUnavailableException
	{
		if (sequencer != null)
		{
			throw new IllegalStateException("already opened");
		}

		try
		{
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
		}
		catch (MidiUnavailableException ex)
		{
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
	}

	// =============================播放功能====================================

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
			throw new IllegalStateException("already closed");
		}

		try
		{
			// create the track to play
			Sequence sequence = new Sequence(Sequence.PPQ, comp.getResolution());
			Track track = sequence.createTrack();

			for (Staff s : comp.getStaff())
			{
				for (StaffData d : s.getData())
				{
					track.add(createMidiEvent(
							d.isNoteOn() ? ShortMessage.NOTE_ON
									: ShortMessage.NOTE_OFF,
							d.getChannel(),
							s.getInstrument() + INSTRUMENT_ID_OFFSET,
							d.getVelocity(),
							d.getTick()));
				}
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

	private MidiEvent createMidiEvent(int type, int channel, int data,
			int velocity, long tick) throws InvalidMidiDataException
	{
		ShortMessage message = new ShortMessage();
		message.setMessage(type, channel, data, velocity);
		MidiEvent event = new MidiEvent(message, tick);

		return event;

	}

	public void stop()
	{
		if (!isOpen())
		{
			throw new IllegalStateException("already closed");
		}

		sequencer.stop();
	}

	public boolean isPlaying()
	{
		if (!isOpen())
		{
			throw new IllegalStateException("already closed");
		}

		return sequencer.isRunning();
	}

	// =========================事件机制============================

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

	// =======================================测试======================================

	public static void main(String[] args) throws MidiUnavailableException,
			InvalidMidiDataException
	{
		CopyOfGroove groove = new CopyOfGroove();
		groove.open();

		// 随机生成Composition
		java.util.Random rand = new java.util.Random();
		Composition comp = new Composition();

		for (int i_staff = 0; i_staff < 5; i_staff++)
		{
			Staff staff = new Staff(rand.nextInt(INSTRUMENTS.length));
			final int TOTAL_TICK = 100;
			int numData = rand.nextInt(TOTAL_TICK);
			for (int i = 0; i < numData; i++)
			{
				staff.getData().add(
						new StaffData(rand.nextInt(), 90 + rand.nextInt(30),
								rand.nextInt(TOTAL_TICK), 9));
			}
			comp.getStaff().add(staff);
		}

		System.out.println(comp.toString());

		// 注册监听器
		groove.addListener(new GrooveListener()
		{
			public void handleEvent(GrooveEvent e)
			{
				System.out.println(e.getType());
			}
		});

		// 启动groove
		groove.loop(comp, 120);
	}

}
