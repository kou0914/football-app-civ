package football;

public class Player {

	private int overall;
	private String name;
	private int age;
	private int number;
	private String position;
	private String team;

	public Player(int overall, String name, int age, int number,
			String position, String team) {
		this.overall = overall;
		this.name = name;
		this.age = age;
		this.number = number;
		this.position = position;
		this.team = team;
	}

	public int getOverall() {
		return overall;
	}

	public void setOverall(int overall) {
		this.overall = overall;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) throws InvalidAgeException {
		if (age < 0 || age > 50) {
			throw new InvalidAgeException("年齢は0〜50歳で入力してください。");
		}
		this.age = age;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public void profile() {
		System.out.println("総合値: " + overall);
		System.out.println("名前: " + name);
		System.out.println("年齢: " + age);
		System.out.println("背番号: " + number);
		System.out.println("ポジション: " + position);
		System.out.println("チーム: " + team);
	}
}