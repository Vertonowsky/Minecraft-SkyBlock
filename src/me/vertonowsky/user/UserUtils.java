package me.vertonowsky.user;

import java.util.ArrayList;
import java.util.List;

public class UserUtils {

	private static List<User> users = new ArrayList<User>();

	public static List<User> getUsers() {
		return new ArrayList<User>(users);
	}

	public static void addUser(User u) {
		if (!users.contains(u)) {
			users.add(u);
		}
	}

	public static void removeUser(User u) {
		if (users.contains(u)) {
			users.remove(u);
		}
	}

	public static void clearUsers() {
		users.clear();
	}

	public static List<String> toNames(List<User> users) {
		List<String> names = new ArrayList<String>();
		for (User u : users)
			names.add(u.getName());
		return names;
	}

	public static List<User> fromNames(List<String> names) {
		List<User> users = new ArrayList<User>();
		for (String s : names)
			users.add(User.get(s));
		return users;
	}
}
