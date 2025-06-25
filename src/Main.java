import com.github.javafaker.Faker;
import entities.User;
import functional_interfaces.StringModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {
	public static void main(String[] args) {
		StringModifier starsWrapper = str -> "***" + str + "***";
		StringModifier dotsWrapper = str -> "..." + str + "...";
		// L'interfaccia di riferimento (StringModifier) deve essere funzionale perché
		// così la lambda va ad implementare esattamente quell'unico metodo (modify in questo caso)
		// e così Java può controllare se stiamo rispettando i tipi dei parametri e il tipo del ritorno

		System.out.println(starsWrapper.modify("CIAO"));
		System.out.println(dotsWrapper.modify("CIAO"));

		StringModifier stringInverter = str -> { // Se ho bisogno di più righe di codice mi apro le graffe
			String[] splitted = str.split("");

			String inverted = "";

			for (int i = splitted.length - 1; i >= 0; i--) {
				inverted += splitted[i];
			}
			return inverted;
		};

		System.out.println(stringInverter.modify("CIAO"));

		// ************************* PREDICATES ******************************
		// Nelle parentesi angolari specifico il TIPO del parametro della lambda
		Predicate<Integer> isMoreThanZero = number -> number > 0;
		Predicate<Integer> isLessThanHundred = number -> number < 100;

		System.out.println(isMoreThanZero.test(100));
		System.out.println(isLessThanHundred.test(50));

		System.out.println(isMoreThanZero.and(isLessThanHundred).test(50));
		System.out.println(isMoreThanZero.negate().test(-20));


		User aldo = new User("Aldo", "Baglio", 20);
		Predicate<User> isAgeMoreThan17 = user -> user.getAge() > 17;

		System.out.println(isAgeMoreThan17.test(aldo));

		// ****************************** SUPPLIERS *****************************

		Supplier<Integer> randomIntSupplier = () -> {
			Random rndm = new Random();
			return rndm.nextInt(1, 10000);
		};

		List<Integer> randomNumbers = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			randomNumbers.add(randomIntSupplier.get());
		}

		System.out.println(randomNumbers);

		Supplier<User> userSupplier = () -> {
			Random rndm = new Random();
			Faker faker = new Faker(Locale.ITALY);
			return new User(faker.lordOfTheRings().character(), faker.name().lastName(), rndm.nextInt(0, 101));
		};


		Supplier<List<User>> randomListSupplier = () -> {
			List<User> users = new ArrayList<>();
			for (int i = 0; i < 100; i++) {
				users.add(userSupplier.get());
			}

			return users;
		};

		List<User> userList = randomListSupplier.get();

		// userList.forEach(user -> System.out.println(user.getName() + " " + user.getSurname()));
		userList.forEach(user -> System.out.println(user));
		// userList.forEach(System.out::println); // Alternativa a sopra più compatta
		
	}
}
