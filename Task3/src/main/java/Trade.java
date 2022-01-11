public class Trade {
    private String tradeNo;
    private String tradeTime;
    private String secBoard;
    private String secCode;
    private double price;
    private long volume;
    private String accruedInt;
    private String yield;
    private String value;

    public Trade(String[] buffer) {
        this.tradeNo = buffer[0];
        this.tradeTime = buffer[1];
        this.secBoard = buffer[2];
        this.secCode = buffer[3];
        this.price = Double.parseDouble(buffer[4]);
        this.volume = Long.parseLong(buffer[5]);
        this.accruedInt = buffer[6];
        this.yield = buffer[7];
        this.value = buffer[8];
    }

    public String getSecBoard() {
        return secBoard;
    }

    public String getSecCode() {
        return secCode;
    }

    public double getPrice() {
        return price;
    }

    public Long getVolume() {
        return volume;
    }

    public void print() {
        System.out.println(
        tradeNo + "  "
        + tradeTime + "  "
        + secBoard + "  "
        + secCode + "  "
        + price + "  "
        + volume + "  "
        + accruedInt + "  "
        + yield + "  "
        + value
        );
    }
}

