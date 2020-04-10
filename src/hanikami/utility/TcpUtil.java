/**
 * 
 */
package hanikami.utility;

import java.io.IOException;
import java.net.Socket;

/**
 * @author hanikami
 *
 */
public class TcpUtil {

	public static Socket getSocket(String ip, int port) {
		Socket socket = null;
		
		try {
			socket = new Socket(ip, port);
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}

		return socket;
	}
}
