# Claim Denial Rule Engine
### A Core Java project for automated healthcare claim classification


## Overview
A rule-based Java application that reads healthcare insurance claims from a CSV file, applies real-world denial codes to classify each claim as **PAYABLE**, **DENIED**, or **PENDING**, and generates a structured summary report grouped by payer and denial category.
This project is built from direct experience in healthcare AR and Denial Management, replicating the manual triage process that billing associates perform daily — but automated through Java.


## How It Works

Input: claims.csv
        ↓
  [ CSV Loader ]  →  reads and parses each claim record
        ↓
  [ Rule Engine ] →  checks denial code against registered rules
        ↓
   CO-4?  → DENIED   (modifier mismatch)
   CO-97? → DENIED   (bundled service)
   PR-1?  → PENDING  (deductible not met)
   CO-16? → PENDING  (missing information)
   blank  → PAYABLE  (clean claim)
        ↓
  [ Report Generator ] → prints formatted summary to console



## Sample Input (`data/claims.csv`)
ClaimID,PatientName,Payer,ServiceDate,Amount,ProcedureCode,DenialCode
C001,Rajesh Kumar,Aetna,2025-01-05,1500.00,99213,CO-4
C002,Sunita Sharma,BlueCross,2025-01-07,2300.00,99214,
C003,Michael Fernandez,UnitedHealth,2025-01-09,800.00,99215,CO-97
C004,Priya Menon,Cigna,2025-01-10,1200.00,99201,PR-1



## Sample Output

===========================================================================
         CLAIM DENIAL RULE ENGINE — PROCESSING REPORT
===========================================================================

  CLAIM-BY-CLAIM RESULTS:

  [C001] Rajesh Kumar      | Payer: Aetna        | Code: CO-4   | $1500.00  | Status: DENIED
  [C002] Sunita Sharma     | Payer: BlueCross     | Code: N/A    | $2300.00  | Status: PAYABLE
  [C003] Michael Fernandez | Payer: UnitedHealth  | Code: CO-97  | $800.00   | Status: DENIED
  [C004] Priya Menon       | Payer: Cigna         | Code: PR-1   | $1200.00  | Status: PENDING
  ...

---------------------------------------------------------------------------
  STATUS SUMMARY
---------------------------------------------------------------------------
  PAYABLE    :  5 claims   |   Total Amount: $9450.00
  DENIED     :  6 claims   |   Total Amount: $8750.00
  PENDING    :  4 claims   |   Total Amount: $5290.00

---------------------------------------------------------------------------
  DENIED/PENDING BREAKDOWN BY PAYER
---------------------------------------------------------------------------
  Payer: Aetna
    → C001 [CO-4] — DENIED
    → C010 [PR-1] — PENDING
  Payer: UnitedHealth
    → C003 [CO-97] — DENIED
    ...

---------------------------------------------------------------------------
  DENIAL CODE FREQUENCY (Top Issues)
---------------------------------------------------------------------------
  CO-97    : 3 occurrence(s)
  CO-4     : 3 occurrence(s)
  PR-1     : 2 occurrence(s)
  CO-16    : 2 occurrence(s)

===========================================================================
  Total Claims Processed : 15
  Total Amount in System : $23490.00
===========================================================================


## Tech Stack
- **Language:** Core Java (JDK 8+)
- **Concepts:** OOP, Interfaces, Collections Framework, File I/O, Exception Handling
- **Input:** CSV file
- **Output:** Console-based formatted report
- **IDE:** Eclipse / IntelliJ / VS Code (any)


## Background
Built to automate a real process from 15 months of hands-on experience in healthcare Account Receivable and Denial Management. The denial codes (CO-4, CO-97, PR-1, CO-16) are standard CARC (Claim Adjustment Reason Codes) used across major US insurance payers including Aetna, BlueCross, UnitedHealth, and Cigna.
