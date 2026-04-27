package rules;

import model.Claim;

/**
 * CO-4: Service is inconsistent with the modifier used.
 * These claims are outright denied — incorrect billing modifier attached.
 */
public class CO4Rule implements DenialRule {

    @Override
    public boolean applies(Claim claim) {
        return claim.getDenialCode().equalsIgnoreCase("CO-4");
    }

    @Override
    public void apply(Claim claim) {
        claim.setStatus("DENIED");
    }

    @Override
    public String getRuleDescription() {
        return "CO-4: Modifier mismatch — claim DENIED";
    }
}
