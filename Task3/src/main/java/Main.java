public class Main {

    public static void main (String args []) {
        var h = new History();
        h.readFromFile("trades.txt");
        h.CheckPrices();

    }
}
