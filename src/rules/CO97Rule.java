package rules;

import model.Claim;

/**
 * CO-97: Service is bundled into another billed procedure — not separately payable.
 * Common when an add-on code is billed without its primary procedure code.
 */
public class CO97Rule implements DenialRule {

    @Override
    public boolean applies(Claim claim) {
        return claim.getDenialCode().equalsIgnoreCase("CO-97");
    }

    @Override
    public void apply(Claim claim) {
        claim.setStatus("DENIED");
    }

    @Override
    public String getRuleDescription() {
        return "CO-97: Service bundled with another — claim DENIED";
    }
}
