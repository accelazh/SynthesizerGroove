package test1;

import javax.sound.midi.*;

public class Test2
{
	public static void main(String[] args)
	{
		Sequencer seq=null;
		Transmitter seqTrans=null;
		Synthesizer synth=null;
		Receiver synthRcvr=null;

		try
		{
			seq=MidiSystem.getSequencer();
			seqTrans=seq.getTransmitter();
			synth=MidiSystem.getSynthesizer();
			synthRcvr=synth.getReceiver();
			seqTrans.setReceiver(synthRcvr);
			
			System.out.println(synth.getMaxPolyphony());
			
		}
		catch (MidiUnavailableException ex)
		{
			System.out.println(ex);
		}
		
		seq.close();
		synth.close();
		
		
	}
		
}
