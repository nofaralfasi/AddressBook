import java.io.IOException;
import java.io.RandomAccessFile;

class LastButton extends CommandButton {
	public LastButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(LAST);
	}

	@Override
	public void Execute() {
		try {
			long currentPosition = getFile().getFilePointer() - (2 * RECORD_SIZE);
			long lastPosition = getFile().length();
			if (lastPosition > 0) {
				readAddress(lastPosition - 2 * RECORD_SIZE);
				if (currentPosition >= 0 && currentPosition != getPane().getState()) {
					getPane().setState(currentPosition);
					System.out.println("AddressBookPane: added state to ArrayList: " + currentPosition);
					getPane().careTaker.add(getPane().saveStateToMemento());
				} else {
					currentPosition = getFile().getFilePointer() - (2 * RECORD_SIZE);
					getPane().setState(currentPosition);
					System.out.println("CareTaker: adding last state to ArrayList " + currentPosition);
					getPane().careTaker.add(getPane().saveStateToMemento());
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
} // end class LastButton
