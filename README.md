# Final Project Team 5: Enterprise Procurement & Vendor Management System

## Overview

```mermaid
flowchart TB
    A[Enterprise Procurement & Vendor Management System] 
    N["Network (System Admin)"]

    A --> N
    N --> Google
    N --> TSMC["TSMC Vendor(Hardware)"]
    N --> FedEx
    N --> SAMSUNG["SAMSUNG Vendor(Hardware)"]

    %% Google
    subgraph Google
        direction LR

        subgraph IT
            A1_1[IT Admin]
            A1_2[IT Engineer]
        end

        subgraph Finance
            A2_1[Finance Analyst]
        end

        subgraph Procurement
            A3_1[Procurement Specialist]
        end

        subgraph Legal
            A4_1[Legal Reviewer]
        end

        subgraph Contract_Management
            A5_1[Contract Coordinator]
        end

        subgraph Vendor_Management
            A6_1[Vendor Compliance Officer]
        end

        subgraph Warehouse
            A7_1[Warehouse Specialist]
        end
    end

    %% TSMC
    subgraph TSMC["TSMC Vendor(Hardware)"]
        direction TB

        subgraph TSMC_Sales["Sales Department"]
            B1_1[Sales Manager]
            B1_2[Vendor Compliance Officer]
        end
    end
    
    %% FedEx
    subgraph FedEx
        direction TB

        subgraph Logistics
            C1[FedEx Logistics]
            C1_1[Shipping Coordinator]
        end
    end
    
    %% SAMSUNG
    subgraph SAMSUNG["SAMSUNG Vendor(Hardware)"]
        direction TB

        subgraph SAMSUNG_Sales["Sales Department"]
            D1_1[Sales Manager]
            D1_2[Vendor Compliance Officer]
        end
    end
```

## PR Approval Flow
```mermaid
flowchart LR
    IT_Engineer["IT Engineer"] -->|Submit PR| Procurement_Specialist["Procurement Specialist"]
    Procurement_Specialist -->|Review PR<br>Add supplier info| Finance_Analyst["Finance Analyst"]
    Finance_Analyst -->|Approve Budget| Procurement_Specialist
    Procurement_Specialist -->|Request Tech Confirmation| IT_Engineer
    IT_Engineer -->|Confirm Specs| Procurement_Specialist
```

## RFQ and Quotation
```mermaid
flowchart LR
    Procurement_Specialist["Procurement_Specialist (Google)"] -->|Issue RFQ| Sales_Manager["Sales Manager (TSMC)"]
    Sales_Manager -->|Submit Quotation| Procurement_Specialist
    Procurement_Specialist -->|Review / Counter / Accept| Sales_Manager
```

In cases where multiple vendors are invited, the system supports side-by-side quotation comparison. The procurement team evaluates submitted offers and selects the most suitable vendor to proceed with contract finalization and PO issuance.
```mermaid
flowchart TD
    Procurement_Specialist["Procurement Specialist (Google)"] -->|1. Issue RFQ| TSMC_Sales["Sales Manager (TSMC)"]
    Procurement_Specialist -->|1. Issue RFQ| Samsung_Sales["Sales Manager (Samsung)"]

    TSMC_Sales -->|2. Submit Quotation A| Procurement_Specialist
    Samsung_Sales -->|2. Submit Quotation B| Procurement_Specialist

    Procurement_Specialist -->|3. Compare & Select Winner| Selected_Vendor["Selected Vendor (e.g., TSMC)"]

    Selected_Vendor -->|4. Proceed to Contract & PO| Procurement_Specialist
```

## Contract & PO Flow

Once the quotation has been accepted, the procurement process moves into the contract and purchase order phase. This phase ensures that all legal and financial considerations are addressed before placing a binding order.

```mermaid
flowchart TD
    Sales_Manager["Sales Manager (TSMC)"] -->|1. Send Draft Contract & Compliance Docs| Legal_Reviewer["Legal Reviewer (Google)"]
    Legal_Reviewer -->|2. Review Passed| Procurement_Specialist["Procurement Specialist (Google)"]
    Procurement_Specialist -->|3. Request Final Confirmation| Finance_Analyst["Finance Analyst (Google)"]
    Finance_Analyst -->|4. Confirm Budget| Procurement_Specialist
    Procurement_Specialist -->|5. Send PO| Sales_Manager
```

> While the Procurement Specialist has the interface to initiate a PO, the actual transmission of the order to the vendor is only enabled after a Finance Analyst confirms the final budget. This ensures financial control without overly slowing down procurement operations.

## Shipment & Delivery

- FedEx ships the goods to Googleâ€™s warehouse and provides tracking information.
- Google Warehouse Specialist confirms delivery and condition of the goods, closing the delivery loop.

```mermaid
flowchart TD
    %% Google
    Procurement_Specialist["Procurement Specialist (Google)"] -->|1. Send PO| Sales_Manager["Sales Manager (TSMC)"]

    %% TSMC to FedEx
    Sales_Manager -->|2. Request Shipment| Shipping_Coordinator["Shipping Coordinator (FedEx)"]

    %% FedEx to Google
    Shipping_Coordinator -->|3. Ship Goods + Tracking Info| Warehouse_Specialist["Warehouse Specialist (Google)"]

    %% Google Internal Confirmation
    Warehouse_Specialist -->|4. Confirm Delivery & Condition| Procurement_Specialist
```
