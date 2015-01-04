package test1;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.accela.midi.groove.*;

public class Test7
{
	public static void main(String[] args) throws InvalidMidiDataException, InterruptedException, MidiUnavailableException
	{
		Groove groove = new Groove();
		groove.open();
		
		for (int c = 0; c < 16; c++)
		{
			System.out.println("Testing channel "+c);
			
			for (int i = 0; i < 128; i++)
			{
				System.out.println("\ti = "+i);
				groove.note(new Note(i, 90, 0, c));
				Thread.sleep(200);
			}
		}

	}
}
