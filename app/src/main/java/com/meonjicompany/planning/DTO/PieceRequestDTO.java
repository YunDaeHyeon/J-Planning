package com.meonjicompany.planning.DTO;

public class PieceRequestDTO {
    private int planId;
    private String pieceTime;
    private String pieceContents;

    public PieceRequestDTO(int planId, String pieceTime, String pieceContents) {
        this.planId = planId;
        this.pieceTime = pieceTime;
        this.pieceContents = pieceContents;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
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
