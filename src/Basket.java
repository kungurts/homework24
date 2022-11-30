import java.io.*;
import java.util.Arrays;
import java.util.PrimitiveIterator;

public class Basket implements Serializable {
    private String[] items;
    private int[] prices;
    private int[] cart;
    private File file = new File("src");

    public Basket(String[] items, int[] prices) {
        this.items = items;
        this.prices = prices;
        cart = new int[items.length];
    }

    public Basket(String[] items, int[] prices, int[] cart) {
        this.items = items;
        this.prices = prices;
        this.cart = cart;
    }

    public void addToCart(int productNum, int amount) {
        cart[productNum - 1] += amount;
    }

    public void printCart() {
        System.out.println("Ваша корзина: ");
        int cartSum = 0;
        for (int i = 0; i < cart.length; i++) {
            if (cart[i] != 0) {
                int sumItem = cart[i] * prices[i];
                System.out.println(items[i] + " " + cart[i] +
                        " шт. По цене " + prices[i] + " руб. за 1 шт. ВСЕГО " + sumItem + " руб.");
                cartSum += sumItem;
            }
        }
        System.out.println("Итого: " + cartSum + " руб." + "\n");
    }

    public void saveTxt(File textFile) throws Exception {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (String item : items) {
                out.print(item + " ");
            }
            out.println();
            for (int price : prices) {
                out.print(price + " ");
            }
            out.println();
            for (int position : cart) {
                out.print(position + " ");
            }
        }
    }

    public static Basket loadFromTextFile(File textFile) throws Exception {
        Basket basket = new Basket(null, null, null);
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            String[] items = reader.readLine().trim().split(" ");
            String[] pricesStr = reader.readLine().trim().split(" ");
            String[] basketStr = reader.readLine().trim().split(" ");
            int[] prices = new int[pricesStr.length];
            int[] cart = new int[basketStr.length];
            for (int i = 0; i < pricesStr.length; i++) {
                prices[i] = Integer.parseInt(pricesStr[i]);
            }
            for (int i = 0; i < basketStr.length; i++) {
                cart[i] = Integer.parseInt(basketStr[i]);
            }
            basket = new Basket(items, prices, cart);
        }
        return basket;
    }

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    public int[] getPrices() {
        return prices;
    }

    public void setPrices(int[] prices) {
        this.prices = prices;
    }

    public void PrintItems() {
        System.out.println("Товары, доступные для покупки: ");
        for (int i = 0; i < items.length; i++) {
            System.out.println("\t" + (i +1) + " " + items[i] + " по цене " + prices[i] + " руб. за шт.");
        }
    }
}
