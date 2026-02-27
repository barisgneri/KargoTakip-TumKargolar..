package com.barisproduction.kargo.common


fun createTrackUrl(trackingNumber: String, trackingUrl: String): String {
    return trackingUrl + trackingNumber
}