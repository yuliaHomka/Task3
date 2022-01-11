import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class History {

    private final List<Trade> tradeList;

    public History() {
        this.tradeList = new ArrayList<>();
    }

    public void readFromFile (String fileName) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            String line;
            boolean flag = false;
            while ((line = reader.readLine()) != null) {
                if (!flag){
                    flag = true;
                    continue;
                }
                String[] buffer = line.split("\t");
                if (buffer.length != 9) throw new Exception("wrong sting " + line);
                tradeList.add(new Trade(buffer));
            }

        }   catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void print() {
        for(var trade: tradeList) {
            trade.print();
        }
    }

    public void CheckPrices() {

        Comparator<List<Trade>> priceGrowCmp = (list1, list2) -> {
            Double diff1 = list1.get(list1.size() - 1).getPrice() - list1.get(0).getPrice();
            Double diff2 = list2.get(list2.size() - 1).getPrice() - list2.get(0).getPrice();
            return diff1.compareTo(diff2);
        };
        var sortedList = tradeList.stream()
                 .filter((trade) -> trade.getSecBoard().equals("TQBR") || trade.getSecBoard().equals("FQBR"))
                 .collect(Collectors.groupingBy(Trade::getSecCode))
                 .values()
                 .stream()
                 .sorted(priceGrowCmp)
                 .toList();
        int size = Math.min(sortedList.size(), 10);
        var worstResult = sortedList.subList(0, size);
        var bestResult = sortedList.subList(sortedList.size() - size, sortedList.size());

        System.out.println("Worst:");
        this.print(worstResult);
        System.out.println(" \n Best:");
        this.print(bestResult);
    }

    public void print(List<List<Trade>> lists) {

        lists.forEach((list) -> {
            Trade first = list.get(0);
            Trade last = list.get(list.size() - 1);

            double priceDiffPercent = 100. * (last.getPrice() - first.getPrice()) / first.getPrice();
            Long totalVolume = list.stream()
                                .map(Trade::getVolume)
                                .reduce(Long::sum)
                                .get();

            System.out.print(first.getSecCode() + ": "
                            + " startPrice = " + first.getPrice()
                            + " finishPrice = " + last.getPrice()
                            + " totalVolume = " + totalVolume
                            + " changingPrice = ");
            System.out.printf("%+3.2f%%", priceDiffPercent);
            System.out.println();
        });
    }
}

