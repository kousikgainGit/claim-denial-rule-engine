package model;

public class Claim {

    private String claimId;
    private String patientName;
    private String payer;
    private String serviceDate;
    private double amount;
    private String procedureCode;
    private String denialCode;
    private String status; // PAYABLE, DENIED, PENDING

    public Claim(String claimId, String patientName, String payer,
                 String serviceDate, double amount, String procedureCode, String denialCode) {
        this.claimId    = claimId;
        this.patientName = patientName;
        this.payer       = payer;
        this.serviceDate = serviceDate;
        this.amount      = amount;
        this.procedureCode = procedureCode;
        this.denialCode  = denialCode;
        this.status      = "UNPROCESSED";
    }

    // Getters
    public String getClaimId()       { return claimId; }
    public String getPatientName()   { return patientName; }
    public String getPayer()         { return payer; }
    public String getServiceDate()   { return serviceDate; }
    public double getAmount()        { return amount; }
    public String getProcedureCode() { return procedureCode; }
    public String getDenialCode()    { return denialCode; }
    public String getStatus()        { return status; }

    // Setter
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("[%s] %s | Payer: %-12s | Code: %-6s | $%-8.2f | Status: %s",
                claimId, patientName, payer, denialCode.isEmpty() ? "N/A" : denialCode,
                amount, status);
    }
}
