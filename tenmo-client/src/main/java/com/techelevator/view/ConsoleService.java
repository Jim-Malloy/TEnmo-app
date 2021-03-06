package com.techelevator.view;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Scanner;

public class ConsoleService {

	private PrintWriter out;
	private Scanner in;

	public ConsoleService(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output, true);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		out.println();
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print(System.lineSeparator() + "Please choose an option >>> ");
		out.flush();
	}

	public String getUserInput(String prompt) {
		out.print(prompt+": ");
		out.flush();
		return in.nextLine();
	}

	public Integer getUserInputInteger(String prompt) {
		Integer result = null;
		do {
			out.print(prompt+": ");
			out.flush();
			String userInput = in.nextLine();
			try {
				result = Integer.parseInt(userInput);
			} catch(NumberFormatException e) {
				out.println(System.lineSeparator() + "*** " + userInput + " is not valid ***" + System.lineSeparator());
			}
		} while(result == null);
		return result;
	}

	public Integer getUserIdInput() {
		System.out.print("Enter ID of user you are sending to (0 to cancel): ");
		String id = in.nextLine();
		Integer userId = null;
		try {
			userId = Integer.parseInt(id);
		} catch (NumberFormatException ex) {
			out.println(System.lineSeparator() + "*** " + userId + " is not valid ***" + System.lineSeparator());
		}

		return userId;
	}

	public Double getAmountInput() {
		System.out.print("Enter amount: ");
		String input = in.nextLine();
		Double amount = null;
		try {
			amount = Double.parseDouble(input);
		} catch (NumberFormatException ex) {
			out.println(System.lineSeparator() + "*** " + amount + " is not valid ***" + System.lineSeparator());
		}
		return amount;
	}

	public Integer getTransferIdInput() {
		System.out.print("Please enter transfer ID to view details (0 to cancel): ");
		String input = in.nextLine();
		Integer transferId = null;
		try {
			transferId = Integer.parseInt(input);
		} catch (NumberFormatException ex) {
			out.println(System.lineSeparator() + "*** " + transferId + " is not valid ***" + System.lineSeparator());
		}
		return transferId;
	}
}
