public class SecretsFactory {
	private SecretsFactory() {
	}

	public static Secrets makeSecrets() {
		return new SecretsImpl();
	}
}
