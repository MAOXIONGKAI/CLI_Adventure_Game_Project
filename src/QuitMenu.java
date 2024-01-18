import java.util.Scanner;

public class QuitMenu extends Menu{
    QuitMenu(Game game, String msg){
        super(game, msg);
    }
    public Game prompt(Menu start, Menu load, Menu save){
        if(!currentGame.getSaveName().isEmpty()){
            System.exit(0);
        }
        displayMsg();
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while(input.isEmpty()){
            input = scanner.nextLine();
            if("S".equalsIgnoreCase(input)){
                Game.save(currentGame);
                System.exit(0);
            }else if("M".equalsIgnoreCase(input)){
                return currentGame;
            }else if("Q".equalsIgnoreCase(input)){
                System.exit(0);
            }else{
                System.out.println("无效输入...请重新输入指令");
                input = "";
            }
        }
        return currentGame;
    }
}
