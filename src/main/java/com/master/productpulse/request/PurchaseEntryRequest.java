package com.master.productpulse.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
@NoArgsConstructor
@Data
public class PurchaseEntryRequest {
    private Long supplierId;
    private String purchaseNote;
    private List<PurchaseEntryProductFields> purchaseEntryProductFieldsList;

    public PurchaseEntryRequest(Long supplierId, String purchaseNote, List<PurchaseEntryProductFields> purchaseEntryProductFieldsList) {
        this.supplierId = supplierId;
        this.purchaseNote = purchaseNote;
        this.purchaseEntryProductFieldsList = purchaseEntryProductFieldsList;
    }
}
