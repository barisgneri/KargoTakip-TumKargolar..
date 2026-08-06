package com.barisproduction.kargo.domain.model

sealed class AnalyticsEvent(val name: String, val params: Map<String, Any?> = emptyMap()) {
    data class ScreenView(val screenName: String) : AnalyticsEvent(
        name = "screen_view",
        params = mapOf("screen_name" to screenName)
    )

    data class CargoAdded(val carrier: String) : AnalyticsEvent(
        name = "cargo_added",
        params = mapOf("carrier" to carrier)
    )

    data class CargoDeleted(val cargoId: String?) : AnalyticsEvent(
        name = "cargo_deleted",
        params = mapOf("cargo_id" to cargoId)
    )

    data class CopiedTrackNumberUse(val parcelName: String) : AnalyticsEvent(
        name = "copied_track_number_use",
        params = mapOf("parcel_name" to parcelName)
    )

    object BarcodeScanned : AnalyticsEvent(name = "barcode_scanned")
    object SettingsOpened : AnalyticsEvent(name = "settings_opened")
    object PasteClicked : AnalyticsEvent(name = "paste_clicked")
    object AppOpened : AnalyticsEvent(name = "app_open")
}
