package football;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class PlayerService {

	ArrayList<Player> players;
	ArrayList<Player> squad;
	String teamName;
	Scanner scanner;

	public PlayerService(ArrayList<Player> players) {
		this.players = players;
		this.squad = new ArrayList<>();
		this.scanner = new Scanner(System.in);
	}

	public void createPlayer() {

		try {
			System.out.print("総合値: ");
			int overall = Integer.parseInt(scanner.nextLine());

			System.out.print("名前: ");
			String name = scanner.nextLine();

			System.out.print("年齢: ");
			int age = Integer.parseInt(scanner.nextLine());

			if (age < 0 || age > 50) {
				throw new InvalidAgeException("年齢は0〜50歳で入力してください。");
			}

			System.out.print("背番号: ");
			int number = Integer.parseInt(scanner.nextLine());

			System.out.print("ポジション: ");
			String position = scanner.nextLine();

			System.out.print("チーム: ");
			String team = scanner.nextLine();

			Player player = new Player(overall, name, age, number, position, team);

			players.add(player);

			saveCsv();

			System.out.println("選手を登録しました。");

		} catch (NumberFormatException e) {
			System.out.println("数字を入力してください。");

		} catch (InvalidAgeException e) {
			System.out.println(e.getMessage());
		}
	}

	public void showPlayers() {

		if (players.isEmpty()) {
			System.out.println("選手が登録されていません。");
			return;
		}

		System.out.println("\n===== 選手一覧 =====");

		for (Player player : players) {
			player.profile();

			System.out.println("-----------------");
		}
	}

	public void searchPlayer() {

		System.out.print("検索する選手名: ");
		String keyword = scanner.nextLine();

		boolean found = false;

		for (Player player : players) {

			if (player.getName().contains(keyword)) {
				player.profile();
				found = true;
			}
		}

		if (!found) {
			System.out.println("該当する選手が見つかりません。");
		}
	}

	public void updatePlayer() {

		System.out.print("更新する選手名: ");
		String name = scanner.nextLine();

		for (Player player : players) {

			if (player.getName().equals(name)) {

				try {
					System.out.print("総合値: ");
					player.setOverall(
							Integer.parseInt(scanner.nextLine()));

					System.out.print("年齢: ");
					int age = Integer.parseInt(scanner.nextLine());
					player.setAge(age);

					System.out.print("背番号: ");
					player.setNumber(
							Integer.parseInt(scanner.nextLine()));

					System.out.print("ポジション: ");
					player.setPosition(scanner.nextLine());

					System.out.print("チーム: ");
					player.setTeam(scanner.nextLine());

					saveCsv();

					System.out.println("更新しました。");
					return;

				} catch (NumberFormatException e) {
					System.out.println("数字を入力してください。");
					return;

				} catch (InvalidAgeException e) {
					System.out.println(e.getMessage());
					return;
				}
			}
		}

		System.out.println("選手が見つかりません。");
	}

	public void deletePlayer() {

		System.out.print("削除する選手名: ");
		String name = scanner.nextLine();

		Iterator<Player> iterator = players.iterator();

		while (iterator.hasNext()) {

			Player player = iterator.next();

			if (player.getName().equals(name)) {

				iterator.remove();

				saveCsv();

				System.out.println("削除しました。");
				return;
			}
		}

		System.out.println("選手が見つかりません。");
	}

	public void createSquad() {

		squad.clear();

		System.out.print("自分のチーム名を入力してください: ");

		while (true) {

			teamName = scanner.nextLine();

			if (teamName.trim().isEmpty()) {
				System.out.print("チーム名を入力してください: ");
				continue;
			}

			break;
		}

		String[] positions = { "GK", "RSB", "CB", "CB", "LSB", "MF", "MF", "MF", "RWG", "CF", "LWG" };

		System.out.println("\n===== スカッド作成 =====");

		for (String position : positions) {

			while (true) {

				System.out.println("\n" + position + "を選択してください。");

				ArrayList<Player> candidates = new ArrayList<>();

				for (Player player : players) {

					if (player.getPosition().equalsIgnoreCase(position)) {
						candidates.add(player);
					}
				}

				if (candidates.isEmpty()) {
					System.out.println(position + "の選手がいません。");
					break;
				}

				for (int i = 0; i < candidates.size(); i++) {

					Player player = candidates.get(i);

					System.out.println(
							(i + 1) + ". " +
									player.getName() +
									" OVR:" +
									player.getOverall());
				}

				System.out.print("番号: ");

				try {

					int choice = Integer.parseInt(scanner.nextLine());

					if (choice < 1 || choice > candidates.size()) {
						System.out.println("正しい番号を入力してください。");
						continue;
					}

					Player selected = candidates.get(choice - 1);

					squad.add(selected);

					System.out.println(selected.getName() + "を選択しました。");

					break;

				} catch (NumberFormatException e) {
					System.out.println("数字を入力してください。");
				}
			}
		}

		System.out.println("\n===== " + teamName + " =====");

		int total = 0;

		for (Player player : squad) {

			System.out.println(player.getPosition() + " | " + player.getName() + " | 総合値 " + player.getOverall());
			total += player.getOverall();
		}

		System.out.println("--------------------");
		System.out.println("総合値合計: " + total);
		System.out.println("スカッド作成完了！");
	}

	public void saveCsv() {

		try (
				PrintWriter writer = new PrintWriter(
						new FileWriter("players.csv"))) {

			writer.println("overall,name,age,number,position,team");

			for (Player player : players) {

				writer.println(
						player.getOverall() + "," +
								player.getName() + "," +
								player.getAge() + "," +
								player.getNumber() + "," +
								player.getPosition() + "," +
								player.getTeam());
			}

		} catch (IOException e) {
			System.out.println("CSV保存中にエラーが発生しました。");
		}
	}

	public void loadCsv() {

		File file = new File("players.csv");

		if (!file.exists()) {
			return;
		}

		try (
				BufferedReader reader = new BufferedReader(new FileReader(file))) {

			String line;

			reader.readLine();

			players.clear();

			while ((line = reader.readLine()) != null) {

				String[] data = line.split(",");

				if (data.length != 6) {
					continue;
				}

				int overall = Integer.parseInt(data[0]);
				String name = data[1];
				int age = Integer.parseInt(data[2]);
				int number = Integer.parseInt(data[3]);
				String position = data[4];
				String team = data[5];

				Player player = new Player(overall, name, age, number, position, team);

				players.add(player);
			}

		} catch (IOException e) {
			System.out.println("CSV読み込み中にエラーが発生しました。");

		} catch (NumberFormatException e) {
			System.out.println("CSVの数字データに問題があります。");
		}
	}

	public void battle() {

		if (squad.size() != 11) {
			System.out.println("先に11人のスカッドを作成してください。");
			return;
		}

		String[] enemyTeams = { "Barcelona", "Real Madrid", "Manchester City", "Liverpool", "Bayern Munich", "PSG",
				"Inter", "Arsenal" };

		Random random = new Random();

		String enemyTeam = enemyTeams[random.nextInt(enemyTeams.length)];

		int myTotal = 0;

		for (Player player : squad) {
			myTotal += player.getOverall();
		}

		int enemyTotal = 0;

		String[] positions = { "GK", "RSB", "CB", "CB", "LSB", "MF", "MF", "MF", "RWG", "CF", "LWG" };

		System.out.println("\n===== TEAM BATTLE =====");

		System.out.println(
				teamName + " VS " + enemyTeam);

		for (String position : positions) {

			ArrayList<Player> candidates = new ArrayList<>();

			for (Player player : players) {

				if (player.getPosition().equalsIgnoreCase(position)) {

					candidates.add(player);
				}
			}

			if (!candidates.isEmpty()) {

				Player enemy = candidates.get(random.nextInt(candidates.size()));

				enemyTotal += enemy.getOverall();
			}
		}

		System.out.println(teamName + ": " + myTotal);

		System.out.println(enemyTeam + ": " + enemyTotal);

		if (myTotal > enemyTotal) {

			System.out.println(teamName + "の勝ち");

		} else if (myTotal < enemyTotal) {

			System.out.println(enemyTeam + "の勝ち");

		} else {

			System.out.println("引き分けです。");
		}
	}
}