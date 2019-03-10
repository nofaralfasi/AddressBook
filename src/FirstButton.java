import java.io.IOException;
import java.io.RandomAccessFile;

class FirstButton extends CommandButton {
	public FirstButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(FIRST);
	}
	
	@Override
	public void readAddress(long position){				
		try {
			long currentPosition = getFile().getFilePointer() - (2 * RECORD_SIZE);
			super.readAddress(position);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void Execute() {
		try {
			long currentPosition = getFile().getFilePointer() - (2 * RECORD_SIZE);
			if (getFile().length() > 0) {
				readAddress(0);
				if (currentPosition >= 0) {
					getPane().setState(currentPosition);					
					getPane().careTaker.add(getPane().saveStateToMemento());
					System.out.println("CareTaker: added state to ArrayList " + currentPosition);
				} else {
					currentPosition =getFile().getFilePointer() - (2 * RECORD_SIZE);
					getPane().setState(currentPosition);					
					getPane().careTaker.add(getPane().saveStateToMemento());
					System.out.println("CareTaker: added state to ArrayList " + currentPosition);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
} // end class FirstButton