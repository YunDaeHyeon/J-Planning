package com.meonjicompany.planning.DTO;

public class Piece {
    private String piece_time;
    private String piece_contents;

    public Piece(String piece_time, String piece_contents) {
        this.piece_time = piece_time;
        this.piece_contents = piece_contents;
    }

    public String getPiece_time() {
        return piece_time;
    }

    public void setPiece_time(String piece_time) {
        this.piece_time = piece_time;
    }

    public String getPiece_contents() {
        return piece_contents;
    }

    public void setPiece_contents(String piece_contents) {
        this.piece_contents = piece_contents;
    }
}
