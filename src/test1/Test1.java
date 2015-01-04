package test1;

import javax.sound.midi.*;

public class Test1
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
		int i=60;
		boolean adding=true;
		
		while (true)
		{
			ShortMessage myMsg = new ShortMessage();
			myMsg.setMessage(ShortMessage.NOTE_ON, 0, i, 93);
			long timeStamp = -1;
			Receiver recv = MidiSystem.getReceiver();
			recv.send(myMsg, timeStamp);
			
			Thread.sleep(100);
			if(adding)
			{
				i++;
			}
			else
			{
				i--;
			}
			if(i>127)
			{
				adding=false;
				i=127;
			}
			if(i<0)
			{
				adding=true;
				i=0;
			}
		}

	}

}
