package engine;

import model.Claim;

import java.io.*;
import java.util.*;

/**
 * Reads claim records from a CSV file and returns a list of Claim objects.
 * Expected CSV format (with header row):
 *   ClaimID, PatientName, Payer, ServiceDate, Amount, ProcedureCode, DenialCode
 */
public class CSVLoader {

    public List<Claim> loadClaims(String filePath) {
        List<Claim> claims = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) { isHeader = false; continue; } // skip header row
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",", -1);
                if (parts.length < 7) {
                    System.out.println("  [WARN] Skipping malformed row: " + line);
                    continue;
                }

                try {
                    String claimId       = parts[0].trim();
                    String patientName   = parts[1].trim();
                    String payer         = parts[2].trim();
                    String serviceDate   = parts[3].trim();
                    double amount        = Double.parseDouble(parts[4].trim());
                    String procedureCode = parts[5].trim();
                    String denialCode    = parts[6].trim();

                    claims.add(new Claim(claimId, patientName, payer,
                                        serviceDate, amount, procedureCode, denialCode));
                } catch (NumberFormatException e) {
                    System.out.println("  [WARN] Invalid amount in row — skipping: " + line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("  [ERROR] File not found: " + filePath);
        } catch (IOException e) {
            System.out.println("  [ERROR] Could not read file: " + e.getMessage());
        }

        return claims;
    }
}
