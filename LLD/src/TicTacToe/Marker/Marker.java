package TicTacToe.Marker;

public class Marker {

    MarkerEnum sign;

    public Marker(MarkerEnum sign) {
        this.sign = sign;
    }

    public void setSign(MarkerEnum sign) {
        this.sign = sign;
    }

    public MarkerEnum getSign() {
        return this.sign;
    }
}
