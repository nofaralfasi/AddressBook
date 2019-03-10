
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddressBookNew1 extends Application implements AddressBookNew1Finals {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		AddressBookPane pane = AddressBookPane.getInstance();
		if (pane == null)
			return;
		Scene scene = new Scene(pane);
		scene.getStylesheets().add(STYLES_CSS);
		primaryStage.setTitle(TITLE);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setAlwaysOnTop(true);
		primaryStage.setOnCloseRequest(e -> {
			try {
				AddressBookPane.setCountWindows(-1);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}
} // end class AddressBookNew1

interface Command {
	public void Execute();
}
