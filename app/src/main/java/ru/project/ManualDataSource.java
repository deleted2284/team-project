import java.util.Scanner;

public class ManualDataSource implements DataSource{
    @Override
    public MyList<Student> fill(int size) {
        String strToParse;
        Scanner scanner = new Scanner(System.in);
        while(true){
            strToParse = scanner.next();
            if (strToParse.equals("0"))
                break;
        }
        scanner.close();
        return null;
    }
}
