import java.io.IOException;
import java.io.RandomAccessFile;

class ReverseButton extends CommandButton {
	public ReverseButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(REVERSE);
	}

	@Override
	public void Execute() {
		try {
			long originalRafLength = getFile().length();
			long numberOfRecords = (getFile().length()) / (2 * RECORD_SIZE);
			if (numberOfRecords <= 1)
				return;
			for (int i = 0; i < numberOfRecords / 2; i++) {
				readAddress(i * 2 * RECORD_SIZE);
				writeAddress(originalRafLength);
				readAddress(originalRafLength - (i + 1) * 2 * RECORD_SIZE);
				writeAddress(i * 2 * RECORD_SIZE);
				readAddress(originalRafLength);
				writeAddress(originalRafLength - (i + 1) * 2 * RECORD_SIZE);
				getFile().setLength(originalRafLength);
			}
			readAddress(0);
			return;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
