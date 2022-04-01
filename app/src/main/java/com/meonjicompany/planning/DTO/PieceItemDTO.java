package com.meonjicompany.planning.DTO;

public class PieceItemDTO {
    private int pieceId;
    private String pieceTime;
    private String pieceContents;

    public PieceItemDTO(int pieceId, String pieceTime, String pieceContents) {
        this.pieceId = pieceId;
        this.pieceTime = pieceTime;
        this.pieceContents = pieceContents;
    }

    public int getPieceId() {
        return pieceId;
    }

    public void setPieceId(int pieceId) {
        this.pieceId = pieceId;
    }

    public String getPieceTime() {
        return pieceTime;
    }

    public void setPieceTime(String pieceTime) {
        this.pieceTime = pieceTime;
    }

    public String getPieceContents() {
        return pieceContents;
    }

    public void setPieceContents(String pieceContents) {
        this.pieceContents = pieceContents;
    }
}
