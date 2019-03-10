import java.io.IOException;
import java.io.RandomAccessFile;

class ClearButton extends CommandButton {
	public ClearButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(CLEAR);
	}

	@Override
	public void Execute() {
		try {
			getFile().setLength(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		getPane().clearTextFields();
	}
} // end class ClearButton
