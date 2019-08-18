import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * The Class Worker.
 */
public class Worker implements Runnable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		File yourFile = new File("C:\\Users\\kali\\A07\\music.mp3");
		// does work, no point in making it relative link
		try {
			new javafx.embed.swing.JFXPanel();// Conflict with jpanel?
			String uriString = yourFile.toURI().toString();
			new MediaPlayer(new Media(uriString)).play();

		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
