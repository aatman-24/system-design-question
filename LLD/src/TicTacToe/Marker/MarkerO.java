package TicTacToe.Marker;

public class MarkerO implements Marker{

    MarkerEnum sign = MarkerEnum.O;

    public void setSign(MarkerEnum sign) {
        this.sign = sign;
    }

    public MarkerEnum getSign() {
        return this.sign;
    }
}
