import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class bhavs {
    public static void main(String[] args) {
        // initialisation
        String logo = "bhavs";
        List<String> list = new ArrayList<>();
        // introduction
        System.out.println(" Hello from\n" + logo);
        System.out.println("I help to keep track of what " +
                "you said, just say \"list\" if you do " +
                "not want to see the current list just " +
                "say \"bye\" if you want to end it with me");
        System.out.println("____________________________________________________________");
        System.out.println(" What is your name?");
        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine();
        System.out.println(" Hi " + answer +"!");
        System.out.println(" You have a cool name.");
        System.out.println(" What can I add in the list");
        System.out.println("____________________________________________________________");
        Scanner sc1 = new Scanner(System.in);
        String answer1 = sc1.nextLine();
        int count = 0;
        list.add(answer1);

        while (true) {
            System.out.println("do you want to add anything else");
            sc1 = new Scanner(System.in);
            answer1 = sc1.nextLine();
            if("bye".equals(answer1)) {
                System.out.println(" Bye." + answer + "! Hope to see you again soon!");
                break;
            }
            if("list".equals(answer1)) {
                for (int i = 0; i < list.size(); i ++) {
                    System.out.println(i + ". " +  list.get(i));
                }
                continue;
            }
            list.add(answer1);
            System.out.println("added: " + answer1);

        }

        System.out.println("____________________________________________________________");
    }
}
