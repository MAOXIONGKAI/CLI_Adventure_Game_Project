import java.util.Scanner;
public class EndMenu extends Menu{
    EndMenu(Game currentGame, String msg){
        super(currentGame, msg);
    }
    @Override
    public Game prompt(Menu start, Menu load, Menu save){
        displayMsg();
        String input = "";
        Scanner scanner = new Scanner(System.in);
        while(input.isEmpty()){
            input = scanner.nextLine();
            if("N".equalsIgnoreCase(input)){
                currentGame = start.prompt(start, load, save);
            }else if("L".equalsIgnoreCase(input)){
                currentGame = load.prompt(start, load, save);
            }else if("Q".equalsIgnoreCase(input)){
                System.exit(0);
            }else{
                System.out.println("无效输入，请重新输入指令...");
                input = "";
            }
        }
        return currentGame;
    }
}
