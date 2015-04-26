public class RegExp {

	public static void main(String[] args) {

		String s = "findByxxxx";

		System.out.println(s.matches("(find.*)|(get.*)"));

	}
}
