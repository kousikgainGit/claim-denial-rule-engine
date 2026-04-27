package rules;

import model.Claim;

/**
 * CO-16: Claim lacks information needed for adjudication.
 * Common when required fields (diagnosis code, NPI, auth number) are missing.
 * Flagged as PENDING — needs resubmission with complete data.
 */
public class CO16Rule implements DenialRule {

    @Override
    public boolean applies(Claim claim) {
        return claim.getDenialCode().equalsIgnoreCase("CO-16");
    }

    @Override
    public void apply(Claim claim) {
        claim.setStatus("PENDING");
    }

    @Override
    public String getRuleDescription() {
        return "CO-16: Missing information — claim PENDING (resubmit with complete data)";
    }
}
