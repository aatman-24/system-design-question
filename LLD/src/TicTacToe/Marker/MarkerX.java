package TicTacToe.Marker;

public class MarkerX implements Marker{

    MarkerEnum sign = MarkerEnum.X;

    public void setSign(MarkerEnum sign) {
        this.sign = sign;
    }

    public MarkerEnum getSign() {
        return this.sign;
    }
}
