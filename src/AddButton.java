import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.scene.control.Tooltip;

class AddButton extends CommandButton {
	private Tooltip toolTip=new Tooltip("Tooltip") ;
	public AddButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(ADD);
		Tooltip.install(this, toolTip);
	}

	@Override
	public void Execute() {
		try {			
			writeAddress(getFile().length());
			getPane().increaseComboBoxes();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
} // end class AddButton

