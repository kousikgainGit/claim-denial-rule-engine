package engine;

import model.Claim;
import rules.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Core rule engine. Loads all known denial rules and applies them
 * to each claim in sequence. If no rule matches, claim is PAYABLE.
 */
public class RuleEngine {

    private List<DenialRule> rules;

    public RuleEngine() {
        rules = new ArrayList<>();
        // Register all rules here — add new rules without changing process logic
        rules.add(new CO4Rule());
        rules.add(new CO97Rule());
        rules.add(new PR1Rule());
        rules.add(new CO16Rule());
    }

    public void processClaim(Claim claim) {
        boolean ruleMatched = false;

        for (DenialRule rule : rules) {
            if (rule.applies(claim)) {
                rule.apply(claim);
                ruleMatched = true;
                break; // one denial code per claim — stop after first match
            }
        }

        // No denial code found — claim is clean and payable
        if (!ruleMatched) {
            claim.setStatus("PAYABLE");
        }
    }

    public void processAll(List<Claim> claims) {
        for (Claim claim : claims) {
            processClaim(claim);
        }
    }
}
