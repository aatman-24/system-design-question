package TicTacToe.Marker;

import static TicTacToe.Marker.MarkerEnum.*;

public class MarkerFactory {

    public static Marker getMarker(MarkerEnum type) {

        return switch (type) {
            case X -> new Marker(X);
            case O -> new Marker(O);
            default -> new Marker($);
        };
    }
}
