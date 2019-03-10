import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

class AddressBookPane extends GridPane implements AddressBookNew1Finals, AddressBookEvent1 {
	private RandomAccessFile raf;
	private TextField jtfName = new TextField();
	private TextField jtfStreet = new TextField();
	private TextField jtfCity = new TextField();
	private TextField jtfState = new TextField();
	private TextField jtfZip = new TextField();
	private AddButton jbtAdd;
	private FirstButton jbtFirst;
	private NextButton jbtNext;
	private PreviousButton jbtPrevious;
	private LastButton jbtLast;
	private ClearButton jbtClear;
	private ReverseButton jbtReverse;
	private ComboBox<String> sortFileCombo = new ComboBox<String>();
	private ComboBox<String> sortTypeCombo = new ComboBox<String>();
	 private ObservableList<String> mementoList = FXCollections.observableArrayList(fielfNames);
	private ReplaceButton doReplace;
	private UndoButton jbtUndo;
	private RedoButton jbtRedo;
	private EventHandler<ActionEvent> ae = e -> ((Command) e.getSource()).Execute();
	private long pos;
	CareTaker careTaker;

	public static void setCountWindows(int i) throws IOException {
		RandomAccessFile rcount;
		try {
			rcount = new RandomAccessFile(FILECOUNTER, FILE_MODE);
			int count = getCountWindows(rcount);
			count = count + i;
			rcount.setLength(0);
			rcount.writeInt(count);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static int getCountWindows(RandomAccessFile rcount) throws FileNotFoundException {
		try {
			if (rcount.length() != 0)
				return rcount.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static AddressBookPane getInstance() throws IOException {
		RandomAccessFile rcount = new RandomAccessFile(FILECOUNTER, FILE_MODE);
		int count = getCountWindows(rcount);
		if (count < NUMBER_OF_OBJECTS) {
			setCountWindows(+1);
			return new AddressBookPane();
		}
		return null;
	}

	private AddressBookPane() {
		setState(0);
		careTaker=new CareTaker();
		try {
			raf = new RandomAccessFile(FILE_NAME, FILE_MODE);
			if (raf.length() >= (2 * RECORD_SIZE)) {
				int numberOfRecords = (int) (raf.length()) / (2 * RECORD_SIZE);
				for (int i = 0; i < numberOfRecords; i++) {
					combo1.getItems().add(i + 1);
					combo2.getItems().add(i + 1);
				}
			}
		} catch (IOException ex) {
			System.out.println(ex);
			System.exit(0);
		}

		jtfState.setAlignment(Pos.CENTER_LEFT);
		jtfState.setPrefWidth(80);
		jtfZip.setPrefWidth(70);
		jbtAdd = new AddButton(this, raf);
		jbtFirst = new FirstButton(this, raf);
		jbtNext = new NextButton(this, raf);
		jbtPrevious = new PreviousButton(this, raf);
		jbtLast = new LastButton(this, raf);
		jbtClear = new ClearButton(this, raf);
		jbtReverse = new ReverseButton(this, raf);
		doReplace = new ReplaceButton(this, raf);
		jbtUndo = new UndoButton(this, raf);
		jbtRedo = new RedoButton(this, raf);

		Label state = new Label(STATE);
		Label zp = new Label(ZIP);
		Label name = new Label(NAME);
		Label street = new Label(STREET);
		Label city = new Label(CITY);
		GridPane p1 = new GridPane();
		p1.add(name, 0, 0);
		p1.add(street, 0, 1);
		p1.add(city, 0, 2);
		p1.setAlignment(Pos.CENTER_LEFT);
		p1.setVgap(8);
		p1.setPadding(new Insets(0, 2, 0, 2));
		GridPane.setVgrow(name, Priority.ALWAYS);
		GridPane.setVgrow(street, Priority.ALWAYS);
		GridPane.setVgrow(city, Priority.ALWAYS);
		GridPane adP = new GridPane();
		adP.add(jtfCity, 0, 0);
		adP.add(state, 1, 0);
		adP.add(jtfState, 2, 0);
		adP.add(zp, 3, 0);
		adP.add(jtfZip, 4, 0);
		adP.setAlignment(Pos.CENTER_LEFT);
		GridPane.setHgrow(jtfCity, Priority.ALWAYS);
		GridPane.setVgrow(jtfCity, Priority.ALWAYS);
		GridPane.setVgrow(jtfState, Priority.ALWAYS);
		GridPane.setVgrow(jtfZip, Priority.ALWAYS);
		GridPane.setVgrow(state, Priority.ALWAYS);
		GridPane.setVgrow(zp, Priority.ALWAYS);
		GridPane p4 = new GridPane();
		p4.add(jtfName, 0, 0);
		p4.add(jtfStreet, 0, 1);
		p4.add(adP, 0, 2);
		p4.setVgap(1);
		GridPane.setHgrow(jtfName, Priority.ALWAYS);
		GridPane.setHgrow(jtfStreet, Priority.ALWAYS);
		GridPane.setHgrow(adP, Priority.ALWAYS);
		GridPane.setVgrow(jtfName, Priority.ALWAYS);
		GridPane.setVgrow(jtfStreet, Priority.ALWAYS);
		GridPane.setVgrow(adP, Priority.ALWAYS);
		GridPane jpAddress = new GridPane();
		jpAddress.add(p1, 0, 0);
		jpAddress.add(p4, 1, 0);
		GridPane.setHgrow(p1, Priority.NEVER);
		GridPane.setHgrow(p4, Priority.ALWAYS);
		GridPane.setVgrow(p1, Priority.ALWAYS);
		GridPane.setVgrow(p4, Priority.ALWAYS);
		jpAddress.setStyle(STYLE_COMMAND);
		FlowPane jpCombo = new FlowPane();
		jpCombo.setHgap(20);
		if (eventType.REPLACE.getDoEvent()) {
			combo1.setValue(0);
			combo2.setValue(0);
			jpCombo.getChildren().add(combo1);
			jpCombo.getChildren().add(combo2);
			jpCombo.getChildren().add(doReplace);
		}
		if (eventType.UNDO.getDoEvent())
			jpCombo.getChildren().add(jbtUndo);
		if (eventType.REDO.getDoEvent())
			jpCombo.getChildren().add(jbtRedo);
		FlowPane jpButton = new FlowPane();
		jpButton.setHgap(5);
		if (eventType.ADD.getDoEvent())
			jpButton.getChildren().add(jbtAdd);
		if (eventType.FIRST.getDoEvent())
			jpButton.getChildren().add(jbtFirst);
		if (eventType.NEXT.getDoEvent())
			jpButton.getChildren().add(jbtNext);
		if (eventType.PREVIOUS.getDoEvent())
			jpButton.getChildren().add(jbtPrevious);
		if (eventType.LAST.getDoEvent())
			jpButton.getChildren().add(jbtLast);
		if (eventType.CLEAR.getDoEvent())
			jpButton.getChildren().add(jbtClear);
		if (eventType.REVERSE.getDoEvent())
			jpButton.getChildren().add(jbtReverse);
		jpButton.setAlignment(Pos.CENTER);
		GridPane.setVgrow(jpButton, Priority.NEVER);
		GridPane.setVgrow(jpAddress, Priority.ALWAYS);
		GridPane.setHgrow(jpButton, Priority.ALWAYS);
		GridPane.setHgrow(jpAddress, Priority.ALWAYS);
		this.setVgap(5);
		this.add(jpAddress, 0, 0);
		this.add(jpButton, 0, 1);
		this.add(jpCombo, 0, 2);
		jbtAdd.setOnAction(ae);
		jbtFirst.setOnAction(ae);
		jbtNext.setOnAction(ae);
		jbtPrevious.setOnAction(ae);
		jbtLast.setOnAction(ae);
		jbtClear.setOnAction(ae);
		jbtReverse.setOnAction(ae);
		doReplace.setOnAction(ae);
		jbtUndo.setOnAction(ae);
		jbtRedo.setOnAction(ae);
		jbtFirst.Execute();
	}

	public void SetName(String text) {
		jtfName.setText(text);
	}

	public void SetStreet(String text) {
		jtfStreet.setText(text);
	}

	public void SetCity(String text) {
		jtfCity.setText(text);
	}

	public void SetState(String text) {
		jtfState.setText(text);
	}

	public void SetZip(String text) {
		jtfZip.setText(text);
	}

	public String GetName() {
		return jtfName.getText();
	}

	public String GetStreet() {
		return jtfStreet.getText();
	}

	public String GetCity() {
		return jtfCity.getText();
	}

	public String GetState() {
		return jtfState.getText();
	}

	public String GetZip() {
		return jtfZip.getText();
	}

	/******* Memento Methods ******/
	public void setState(long pos) {
		this.pos = pos; // ?
	}

	public Memento saveStateToMemento() {
		if (this.getState() >= 0)
			return new Memento(this.getState());
		return null;
	}

	public void getStateFromMemento(Memento memento) throws IOException {
		setState(memento.getState());
	}

	public void increaseComboBoxes() {
		combo1.getItems().add(new Integer(combo1.getItems().size()) + 1);
		combo2.getItems().add(new Integer(combo2.getItems().size()) + 1);
	}

	public int getCombo1() {
		return combo1.getValue().intValue();
	}

	public int getCombo2() {
		return combo2.getValue().intValue();
	}

	public void clearTextFields() {
		jtfName.setText("");
		jtfStreet.setText("");
		jtfCity.setText("");
		jtfState.setText("");
		jtfZip.setText("");
		combo1.getItems().clear();
		combo2.getItems().clear();
	}

	 long getState() {
		return pos;
	}

//	CareTaker getCareTakers() {
//		return careTakers;
//	}
//
//	 void setCareTakers(CareTaker careTakers) {
//		this.careTakers = careTakers;
//	}	 
	 /******* Memento Class ******/
	 class Memento {
		private long state;

		public Memento(long pos) {
			this.state = pos;
		}

		public long getState() {
			return this.state;
		}
	}
} // end class AddressBookPane
