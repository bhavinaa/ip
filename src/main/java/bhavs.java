import java.util.Scanner;
public class bhavs {
    public static void main(String[] args) {
        String logo = "bhavs";

        System.out.println(" Hello from\n" + logo);
        System.out.println("I repeat whatever you say, just say \"bye\" if you want to end it with me");
        System.out.println("____________________________________________________________");
        System.out.println(" What is your name?");
        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine();
        System.out.println(" Hi " + answer +"!");
        System.out.println(" You have a cool name.");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
        Scanner sc1 = new Scanner(System.in);
        String answer1 = sc1.nextLine();
        while (!"bye".equals(answer1)) {
            System.out.println("You said: " + answer1 + ". That's a good one!");
            System.out.println("do you have anything else to say");
            sc1 = new Scanner(System.in);
            answer1 = sc1.nextLine();
        }

        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}
