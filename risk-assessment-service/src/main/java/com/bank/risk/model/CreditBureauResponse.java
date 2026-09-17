package com.bank.risk.model;

/** Shape of the payload returned by the (mocked) third-party credit bureau API. */
public class CreditBureauResponse {

    private int bureauScore;
    private boolean defaulterFlag;
    private int activeCreditLines;

    public CreditBureauResponse() {
    }

    public CreditBureauResponse(int bureauScore, boolean defaulterFlag, int activeCreditLines) {
        this.bureauScore = bureauScore;
        this.defaulterFlag = defaulterFlag;
        this.activeCreditLines = activeCreditLines;
    }

    public int getBureauScore() { return bureauScore; }
    public void setBureauScore(int bureauScore) { this.bureauScore = bureauScore; }

    public boolean isDefaulterFlag() { return defaulterFlag; }
    public void setDefaulterFlag(boolean defaulterFlag) { this.defaulterFlag = defaulterFlag; }

    public int getActiveCreditLines() { return activeCreditLines; }
    public void setActiveCreditLines(int activeCreditLines) { this.activeCreditLines = activeCreditLines; }
}
