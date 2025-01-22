import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class bhavs {
    public static void main(String[] args) {
        // initialisation
        String logo = "bhavs";
        List<Task> list = new ArrayList<>();
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
        Task t1 = new Task(answer1);
        list.add(t1);

        while (true) {
            System.out.println("do you want to add anything else");
            sc1 = new Scanner(System.in);
            answer1 = sc1.nextLine();
            Task t2 = new Task(answer1);
            if("bye".equals(answer1)) {
                System.out.println(" Bye. " + answer + "! Hope to see you again soon!");
                break;
            }
            if("list".equals(answer1)) {
                for (int i = 0; i < list.size(); i ++) {
                    System.out.println((i+1) + ". " + list.get(i).toString());
                }
                continue;
            }

            if ("mark".equals(answer1)) {
                System.out.println("which number on the list do you want to mark as done!");
                Scanner sc3 = new Scanner(System.in);
                int answer3 = sc3.nextInt();
                list.get(answer3 - 1).mark_as_complete();
                System.out.println("ok, i have marked this task as done");
                System.out.println(list.get(answer3 -1).toString());
                continue;
            }

            if("unmark".equals(answer1)) {
                System.out.println("which number on the list do you want to unmark!");
                Scanner sc3 = new Scanner(System.in);
                int answer3 = sc3.nextInt();
                list.get(answer3 - 1).mark_as_incomplete();
                System.out.println("ok, i have marked this task as not done yet");
                System.out.println(list.get(answer3 -1).toString());
                continue;
            }

            if("all_completed_tasks".equals(answer1)) {
                System.out.println("All completed tasks");
                for (int i = 0; i < list.size(); i++) {
                    if(list.get(i).is_completed()) {
                        System.out.println(list.get(i).toString());
                    }
                }
                continue;
            }

            if("uncompleted tasks".equals(answer1)) {
                System.out.println("All incomplete tasks");
                for (int i = 0; i < list.size(); i++) {
                    if(list.get(i).is_completed()) {
                        System.out.println(list.get(i).toString());
                    }
                }
                continue;
            }
            list.add(t2);

            System.out.println("added: " + answer1);

        }

        System.out.println("____________________________________________________________");
    }
}
