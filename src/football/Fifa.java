package football;

import java.util.ArrayList;
import java.util.Scanner;

public class Fifa {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		ArrayList<Player> players = new ArrayList<>();

		PlayerService service = new PlayerService(players);

		service.loadCsv();

		while (true) {

			System.out.println();
			System.out.println("======================");
			System.out.println(" MY FOOTBALL ");
			System.out.println("======================");

			System.out.println("1. 選手登録");
			System.out.println("2. 選手一覧");
			System.out.println("3. 選手更新");
			System.out.println("4. 選手削除");
			System.out.println("5. 選手検索");
			System.out.println("6. スカッド作成");
			System.out.println("7. 対戦");
			System.out.println("0. 終了");

			System.out.println("=======================");

			System.out.print("選択してください: ");

			String input = scanner.nextLine();

			switch (input) {

			case "1":
				service.createPlayer();
				break;

			case "2":
				service.showPlayers();
				break;

			case "3":
				service.updatePlayer();
				break;

			case "4":
				service.deletePlayer();
				break;

			case "5":
				service.searchPlayer();
				break;

			case "6":
				service.createSquad();
				break;

			case "7":
				service.battle();
				break;

			case "0":

				System.out.println(
						"アプリを終了します。");

				scanner.close();
				return;

			default:

				System.out.println(
						"正しい番号を入力してください。");
			}
		}
	}
}