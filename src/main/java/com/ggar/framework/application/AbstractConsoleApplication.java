package com.ggar.framework.application;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;
import java.util.function.Function;

public abstract class AbstractConsoleApplication<T extends MenuItem> extends AbstractApplication implements ConsoleApplication<T> {
	
	private final Collection<T> items;
	
	public AbstractConsoleApplication(Collection<T> items) {
		this.items = items;
	}

	private void showMenu(Collection<T> items) {
		items.stream()
			.sorted((a,b) -> a.getValue() > b.getValue() ? 1 : -1)
			.map(e -> String.format("%s. %s", e.getValue(), e.getName()))
			.forEach(System.out::println);
	}
	
	@Override
	public void showMenu() {
		this.showMenu(this.items);
	}

	@Override
	public T askChoice() {		
		final Integer choice = ask("Insert your choice: ", 10, e -> Integer.parseInt(e));
		System.out.println(choice);
		return items.stream()
				.filter(e -> choice != null && e.getValue().equals(choice))
				.findFirst().orElse(null);
	}

	@Override
	public void clearConsole() throws IOException {
		Runtime.getRuntime().exec("cmd /c cls");
	}
	
	public String ask(String message) {
		return this.ask(message, "", str -> str);
	}
	
	public <T> T ask(String message, Function<String, T> fn) {
		return this.ask(message, (T) "", fn);
	}

	public String ask(String message, String defaultValue) {
		return this.ask(message, defaultValue, str -> str);
	}
	
	public <T> T ask(String message, T defaultValue, Function<String, T> fn) {
		T result = defaultValue;
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(System.in);
			scanner.useDelimiter("(\\r\\n|\\r|\\n)");
			System.out.print(message);
			result = fn.apply(scanner.next().replace("\n", ""));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println();
			if (scanner != null) {
				//scanner.close();
			}
		}
		
		return result;
	}
}
