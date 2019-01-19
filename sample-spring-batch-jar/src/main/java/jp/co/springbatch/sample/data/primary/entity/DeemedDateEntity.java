package jp.co.springbatch.sample.data.primary.entity;

import java.time.LocalDateTime;

public class DeemedDateEntity extends AdepDbBaseEntity {
    private LocalDateTime deemedDate;

    public DeemedDateEntity() {
        super();
    }

    public LocalDateTime getDeemedDate() {
        return deemedDate;
    }

    public void setDeemedDate(LocalDateTime deemedDate) {
        this.deemedDate = deemedDate;
    }
}
