package test1;

import javax.sound.midi.*;

public class Test6
{

	/**
	 * @param args
	 * @throws MidiUnavailableException
	 */
	public static void main(String[] args) throws MidiUnavailableException
	{
		Synthesizer synthesizer = MidiSystem.getSynthesizer();
		synthesizer.open();

		Instrument[] instruments = synthesizer.getDefaultSoundbank()
				.getInstruments();
		
		for(int i=0;i<instruments.length;i++)
		{
			System.out.println(instruments[i].getPatch().getBank()+", "+instruments[i].getPatch().getProgram());
		}

	}

}
