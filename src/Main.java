import com.github.javafaker.Faker;
import entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) {
		/*
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
*/
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
			return new User(faker.lordOfTheRings().character(), faker.name().lastName(), rndm.nextInt(1, 101));
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


		// ************************************************************* STREAMS *******************************************************
		// Gli Stream vanno prima di tutto "aperti" tramite il metodo .stream()
		// Così facendo potrò "sbloccare" tutta una serie di operazioni INTERMEDIE tipo .filter() .map() ecc. (Intermedie signfica che ritornano
		// uno STREAM)
		// Alla fine per poter ottenere un risultato "utilizzabile" devo TERMINARE lo stream. Per terminarlo ci sono più possibilità:
		// matching, reduction, collection, foreach


		System.out.println("-------------------------------------- STREAMS - FILTER -----------------------------------------------");

		// Nei Filter utilizzo delle lambda Predicate (quindi devono tornare un booleano), posso o riutilizzare dei Predicate già creati
		// in precedenza oppure passare a filter una lambda "al volo"

		Predicate<Integer> isMoreThanZero = number -> number > 0;
		Predicate<Integer> isLessThanHundred = number -> number < 100;

		randomNumbers.stream().filter(isLessThanHundred.and(isMoreThanZero)).forEach(num -> System.out.println(num)); // .forEach NON TORNA UNA NUOVA LISTA

		userList.stream().filter(user -> user.getAge() >= 18).forEach(user -> System.out.println(user));


		System.out.println("-------------------------------------- STREAMS - MAP -----------------------------------------------");
		userList.stream().map(user -> user.getName() + " " + user.getSurname()).forEach(fullName -> System.out.println(fullName)); // Torna uno stream di stringhe
		userList.stream().map(user -> user.getAge()).forEach(age -> System.out.println(age));
		// userList.stream().map(User::getAge).forEach(System.out::println); <-- Equivalente alla riga sopra ma più compatta

		userList.stream().filter(user -> user.getAge() > 50).map(user -> user.getName()).forEach(name -> System.out.println(name));


		// ******************************************************* COME TERMINARE GLI STREAMS *******************************************************
		System.out.println("-------------------------------------- STREAMS - ALLMATCH & ANYMATCH -----------------------------------------------");
		// .anyMach() e .allMatch() corrispondo a .some() e .every() di JavaScript
		// con anyMatch controlliamo se ALMENO UN elemento di una lista/stream passa una certa condizione (Predicate)
		// con allMatch controoliamo se TUTTI gli elementi di una lista/stream passano una certa condizione (Predicate)
		// Entrambi terminano lo Stream con un BOOLEANO

		if (userList.stream().allMatch(user -> user.getAge() > 17)) {
			System.out.println("Sono tutti maggiorenni!");
		} else {
			System.out.println("C'è qualche minorenne");
		}

		if (userList.stream().anyMatch(user -> user.getAge() > 17)) {
			System.out.println("C'è almeno un maggiorenne");
		} else {
			System.out.println("Sono tutti minorenni");
		}

		System.out.println("-------------------------------------- STREAMS - REDUCE -----------------------------------------------");
		int totale = userList.stream().filter(user -> user.getAge() < 18)
				.map(user -> user.getAge())
				.reduce(1, (partialSum, currentElement) -> partialSum * currentElement);

		System.out.println(totale);


		System.out.println("-------------------------------------- STREAMS - COME OTTENERE UNA LISTA DA UNO STREAM -----------------------------------------------");
		List<Integer> agesList = userList.stream()
				.filter(user -> user.getAge() < 18)
				.map(user -> user.getAge())
				.collect(Collectors.toList());

		List<Integer> agesList2 = userList.stream()
				.filter(user -> user.getAge() < 18)
				.map(user -> user.getAge())
				.toList(); // Alternativa più compatta al .collect() di sopra


		// *********************************************************** DATE IN JAVA ************************************************************
		// Per trattare le date in Java è sempre consigliato usare LocalDate
		LocalDate today = LocalDate.now();
		System.out.println(today);
		LocalDate tomorrow = today.plusDays(1);
		LocalDate yesterday = today.minusDays(1);

		LocalDate sameDayNextYear = today.plusYears(1);

		System.out.println(today.isAfter(tomorrow));

		LocalDate date = LocalDate.parse("2025-06-25"); // converte stringa in oggetto LocalDate
		LocalDate date2 = LocalDate.of(2025, 6, 25);

		LocalDateTime now = LocalDateTime.now();
		System.out.println(now);
	}
}
