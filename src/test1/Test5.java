package test1;

import javax.sound.midi.*;

public class Test5
{

	/**
	 * @param args
	 * @throws MidiUnavailableException 
	 */
	public static void main(String[] args) throws MidiUnavailableException
	{
		Synthesizer synth=MidiSystem.getSynthesizer();
		synth.open();
		Soundbank sb=synth.getDefaultSoundbank();
		System.out.println(synth.getLoadedInstruments().length);
		System.out.println(synth.getAvailableInstruments().length);
		synth.loadInstrument(sb.getInstruments()[0]);
		
	}

}
