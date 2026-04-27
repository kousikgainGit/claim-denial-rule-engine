import engine.CSVLoader;
import engine.RuleEngine;
import model.Claim;
import report.ReportGenerator;

import java.util.List;

/**
 * Claim Denial Rule Engine
 * ─────────────────────────────────────────────────────────────────────────
 * Reads healthcare claims from a CSV file, applies configurable denial rules
 * (CO-4, CO-97, CO-16, PR-1) to classify each claim as PAYABLE / DENIED / PENDING,
 * and generates a structured summary report grouped by payer and denial code.
 *
 * Usage:
 *   java -cp out Main data/claims.csv
 *   java -cp out Main              (defaults to data/claims.csv)
 */
public class Main {

    public static void main(String[] args) {

        String filePath = (args.length > 0) ? args[0] : "data/claims.csv";

        System.out.println("\n  Loading claims from: " + filePath);

        CSVLoader loader = new CSVLoader();
        List<Claim> claims = loader.loadClaims(filePath);

        if (claims.isEmpty()) {
            System.out.println("  No valid claims found. Exiting.");
            return;
        }

        System.out.println("  " + claims.size() + " claims loaded. Running rule engine...\n");

        RuleEngine engine = new RuleEngine();
        engine.processAll(claims);

        ReportGenerator reporter = new ReportGenerator();
        reporter.generateReport(claims);
    }
}
