package test1;

import javax.sound.midi.*;

public class Test3
{

	/**
	 * @param args
	 * @throws InvalidMidiDataException
	 * @throws MidiUnavailableException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InvalidMidiDataException,
			MidiUnavailableException, InterruptedException
	{
		while (true)
		{
			Receiver recv = MidiSystem.getReceiver();

			ShortMessage myMsg = new ShortMessage();
			myMsg.setMessage(ShortMessage.NOTE_ON, 0, 60, 93);
			recv.send(myMsg, -1);
			
			myMsg.setMessage(ShortMessage.NOTE_ON, 1, 80, 93);
			recv.send(myMsg, -1);
			
			myMsg.setMessage(ShortMessage.NOTE_ON, 2, 100, 93);
			recv.send(myMsg, -1);

			Thread.sleep(500);
		}

	}

}
