package com.nichesoftware.dto;

import java.io.Serializable;

/**
 * Created by n_che on 09/09/2016.
 */
public class DTO<T extends Serializable, M extends Metadata> {
    // Fields   --------------------------------------------------------------------------------------------------------
    M metadata;
    T data;

    // Getters   -------------------------------------------------------------------------------------------------------
    public M getMetadata() {
        return metadata;
    }

    public T getData() {
        return data;
    }

    // Setters   -------------------------------------------------------------------------------------------------------
    public void setMetadata(M metadata) {
        this.metadata = metadata;
    }

    public void setData(T data) {
        this.data = data;
    }
}
