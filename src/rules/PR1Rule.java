package rules;

import model.Claim;

/**
 * PR-1: Patient responsibility — deductible amount not yet met.
 * Claim goes to PENDING since it awaits patient payment before payer reimburses.
 */
public class PR1Rule implements DenialRule {

    @Override
    public boolean applies(Claim claim) {
        return claim.getDenialCode().equalsIgnoreCase("PR-1");
    }

    @Override
    public void apply(Claim claim) {
        claim.setStatus("PENDING");
    }

    @Override
    public String getRuleDescription() {
        return "PR-1: Deductible not met — claim PENDING (patient responsibility)";
    }
}
