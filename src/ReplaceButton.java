import java.io.IOException;
import java.io.RandomAccessFile;

class ReplaceButton extends CommandButton {
	public ReplaceButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(DO_REPLACE);
	}

	@Override
	public void Execute() {
		try {
			int index1 = this.getPane().getCombo1() - 1;
			int index2 = this.getPane().getCombo2() - 1;
			if (index1 == 0 || index2 == 0)
				return;
			if (index1 != index2) {
				long originalRafLength = getFile().length();
				long Position1 = index1 * 2 * RECORD_SIZE;
				long Position2 = index2 * 2 * RECORD_SIZE;
				readAddress(Position1);
				writeAddress(originalRafLength);
				readAddress(Position2);
				writeAddress(Position1);
				readAddress(originalRafLength);
				writeAddress(Position2);
				getFile().setLength(originalRafLength);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
} // end class PreviousButton
