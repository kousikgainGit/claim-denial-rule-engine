package rules;

import model.Claim;

/**
 * Each denial rule checks a specific denial code and sets claim status accordingly.
 * New rules can be plugged in without touching existing code — Open/Closed Principle.
 */
public interface DenialRule {
    boolean applies(Claim claim);
    void apply(Claim claim);
    String getRuleDescription();
}
