import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        File file = new File("basket.txt");

        Basket basket1 = new Basket(new String[]{"Торт", "Молоко", "Кофе", "Творог", "Пирог"},
                new int[]{1000, 70, 500, 130, 150});

        if (file.exists()) {
            basket1 = Basket.loadFromTextFile(file);
            basket1.printCart();
        }

        basket1.PrintItems();
        while (true) {
            System.out.println();
            System.out.println("Выберите товар и его количество или введите end.");
            String input = sc.nextLine();
            if ("end".equals(input)) {
                break;
            }
            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]);
            int productQuantity = Integer.parseInt(parts[1]);
            basket1.addToCart(productNumber, productQuantity);
            basket1.saveTxt(file);
        }
        basket1.printCart();

    }
}
