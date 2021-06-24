package com.company.nc_project.model;

import java.util.Date;
import java.util.UUID;

public interface StoredItemProjection {

    String getId();
    String getName();
    String getPlace();
    Date getExpirationDate();
}
