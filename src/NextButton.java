import java.io.IOException;
import java.io.RandomAccessFile;

class NextButton extends CommandButton {
	public NextButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(NEXT);
	}

	@Override
	public void Execute() {
		try {
			long currentPosition = getFile().getFilePointer();
			long previousPosition = getFile().getFilePointer() - (2 * RECORD_SIZE);
			if (currentPosition < getFile().length()) {
				readAddress(currentPosition);
				//if (previousPosition != getPane().getState()) {
					if (currentPosition >=0) {
					getPane().setState(previousPosition);
					System.out.println("CareTaker: adding last state to ArrayList " + previousPosition);
					getPane().careTaker.add(getPane().saveStateToMemento());
				} else {
					currentPosition =getFile().getFilePointer() - (2 * RECORD_SIZE);
					getPane().setState(currentPosition);
					System.out.println("CareTaker: adding last state to ArrayList " + currentPosition);
					getPane().careTaker.add(getPane().saveStateToMemento());
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
} // end class NextButton
