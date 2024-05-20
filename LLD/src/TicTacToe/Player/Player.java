package TicTacToe.Player;

import TicTacToe.Marker.Marker;

public class Player {

    String email;
    Marker marker;

    public Player(Marker marker, String email) {
        this.marker = marker;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }
}
