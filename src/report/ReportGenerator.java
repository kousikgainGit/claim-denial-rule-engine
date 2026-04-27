package report;

import model.Claim;

import java.util.*;

/**
 * Generates a structured denial summary report:
 * - Full claim-by-claim breakdown
 * - Summary grouped by payer
 * - Summary grouped by denial code
 * - Financial totals per status category
 */
public class ReportGenerator {

    public void generateReport(List<Claim> claims) {
        printDivider("=", 75);
        System.out.println("         CLAIM DENIAL RULE ENGINE — PROCESSING REPORT");
        printDivider("=", 75);

        // ── Individual Claim Results ──────────────────────────────────────
        System.out.println("\n  CLAIM-BY-CLAIM RESULTS:\n");
        for (Claim c : claims) {
            System.out.println("  " + c);
        }

        // ── Status Summary ────────────────────────────────────────────────
        System.out.println();
        printDivider("-", 75);
        System.out.println("  STATUS SUMMARY");
        printDivider("-", 75);

        Map<String, Integer>  countByStatus  = new LinkedHashMap<>();
        Map<String, Double>   amountByStatus = new LinkedHashMap<>();

        for (String s : new String[]{"PAYABLE", "DENIED", "PENDING"}) {
            countByStatus.put(s, 0);
            amountByStatus.put(s, 0.0);
        }

        for (Claim c : claims) {
            String s = c.getStatus();
            countByStatus.put(s,  countByStatus.getOrDefault(s, 0) + 1);
            amountByStatus.put(s, amountByStatus.getOrDefault(s, 0.0) + c.getAmount());
        }

        for (String s : countByStatus.keySet()) {
            System.out.printf("  %-10s : %2d claims   |   Total Amount: $%.2f%n",
                    s, countByStatus.get(s), amountByStatus.get(s));
        }

        // ── Denial Breakdown by Payer ─────────────────────────────────────
        System.out.println();
        printDivider("-", 75);
        System.out.println("  DENIED/PENDING BREAKDOWN BY PAYER");
        printDivider("-", 75);

        Map<String, List<String>> byPayer = new LinkedHashMap<>();
        for (Claim c : claims) {
            if (!c.getStatus().equals("PAYABLE")) {
                byPayer.computeIfAbsent(c.getPayer(), k -> new ArrayList<>())
                       .add(c.getClaimId() + " [" + c.getDenialCode() + "] — " + c.getStatus());
            }
        }

        if (byPayer.isEmpty()) {
            System.out.println("  No denied or pending claims.");
        } else {
            for (Map.Entry<String, List<String>> entry : byPayer.entrySet()) {
                System.out.println("  Payer: " + entry.getKey());
                for (String detail : entry.getValue()) {
                    System.out.println("    → " + detail);
                }
            }
        }

        // ── Denial Code Frequency ─────────────────────────────────────────
        System.out.println();
        printDivider("-", 75);
        System.out.println("  DENIAL CODE FREQUENCY (Top Issues)");
        printDivider("-", 75);

        Map<String, Integer> codeFreq = new LinkedHashMap<>();
        for (Claim c : claims) {
            if (!c.getDenialCode().isEmpty() && !c.getStatus().equals("PAYABLE")) {
                codeFreq.put(c.getDenialCode(), codeFreq.getOrDefault(c.getDenialCode(), 0) + 1);
            }
        }

        codeFreq.entrySet().stream()
            .sorted((a, b) -> b.getValue() - a.getValue())
            .forEach(e -> System.out.printf("  %-8s : %d occurrence(s)%n", e.getKey(), e.getValue()));

        // ── Footer ────────────────────────────────────────────────────────
        System.out.println();
        printDivider("=", 75);
        System.out.println("  Total Claims Processed : " + claims.size());
        System.out.printf("  Total Amount in System : $%.2f%n",
                claims.stream().mapToDouble(Claim::getAmount).sum());
        printDivider("=", 75);
    }

    private void printDivider(String ch, int len) {
        System.out.println(ch.repeat(len));
    }
}
