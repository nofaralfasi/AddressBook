
import java.io.IOException;
import java.io.RandomAccessFile;

public class UndoButton extends CommandButton {
	public UndoButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(UNDO);
	}

	@Override
	public void Execute() {
		try {
			long currentPosition = getFile().getFilePointer() - (2 * RECORD_SIZE);
			AddressBookPane.Memento memento = getPane().careTaker.getPrev();
			if (memento != null) {
				if (currentPosition != memento.getState()) {
					getPane().setState(currentPosition);
					getPane().careTaker.add(getPane().saveStateToMemento());
				}
				getPane().getStateFromMemento(memento);
				readAddress(getPane().getState());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
