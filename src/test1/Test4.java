package test1;

import javax.sound.midi.*;

public class Test4
{

	/**
	 * @param args
	 * @throws MidiUnavailableException 
	 */
	public static void main(String[] args) throws MidiUnavailableException
	{
		Synthesizer synth=MidiSystem.getSynthesizer();
		Instrument[] instruments=synth.getLoadedInstruments();
		System.out.println(instruments.length);
		for(Instrument instrument : instruments)
		{
			System.out.println(instrument);
		}
		
		instruments=synth.getAvailableInstruments();
		System.out.println(instruments.length);
		for(Instrument instrument : instruments)
		{
			System.out.println(instrument);
		}
		
		System.out.println(synth.getDefaultSoundbank());
		System.out.println(synth);
		
		System.out.println(synth.getChannels().length);
	}

}
