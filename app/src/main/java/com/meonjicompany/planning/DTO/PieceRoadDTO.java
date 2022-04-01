package com.meonjicompany.planning.DTO;

import com.google.gson.annotations.SerializedName;
public class PieceRoadDTO {
    @SerializedName("piece_id")
    private int pieceId;
    @SerializedName("piece_time")
    private String pieceTime;
    @SerializedName("piece_contents")
    private String pieceContents;

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
