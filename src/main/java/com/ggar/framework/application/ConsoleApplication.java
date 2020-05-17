package com.ggar.framework.application;

import java.io.IOException;
import java.util.Collection;

public interface ConsoleApplication<T extends MenuItem> {

	public void showMenu();
	public MenuItem askChoice();
	public void clearConsole() throws IOException;
	public void run(T choice);
}
