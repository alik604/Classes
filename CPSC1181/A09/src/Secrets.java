/** A cipher capable of AES encryption/decryption once it is unlocked.
   Start the unlock process by passing it the message "unlock",
   then get the rest of the unlock sequence by calling getMessage().
 */
public interface Secrets extends AutoCloseable {

	String TURNING_TRANSITION = "unlock";

    /** The state of the cipher. Can only enc/decrypt messages when unlokced. */
	enum State {
		LOCKED, TURNING, UNLOCKED
	};

    /** Gets the state of the cipher. */
	State getState();

    /** Gets the message regarding the state of the cipher.
       Will return the sequence necessary to unlock if turning. */
	String getMessage();

    /** Attempt to unlock the cipher with the given key */
	State unlock(String key);

    /** Encrypt a given message with the given key. Uses a hidden salt value.
       @param key the key to use to encrypt
       @param msg the plain-text message to encrypt to cipher-text
       @return the encrypted message, or an error string. */
	String encrypt(String key, String msg);

    /** Decrypt a given message with the given key. Uses a hidden salt value.
       @param key the key to use to decrypt
       @param msg the ciphet-text message to decrypt to plain-text
       @return the decrypted message, or an error string. */
	String decrypt(String key, String msg);
}
