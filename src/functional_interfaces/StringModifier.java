package functional_interfaces;

@FunctionalInterface
public interface StringModifier {
	public String modify(String str);
	// public void blabla(); <-- Un'INTERFACCIA si dice FUNZIONALE se possiede un unico metodo astratto
}
