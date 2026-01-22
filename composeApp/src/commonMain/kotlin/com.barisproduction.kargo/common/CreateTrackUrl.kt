package com.barisproduction.kargo.common

import com.barisproduction.kargo.domain.model.ParcelModel

fun createTrackUrl(trackingNumber: String, carrier: ParcelModel?): String {
    return carrier?.trackingUrl + trackingNumber
}