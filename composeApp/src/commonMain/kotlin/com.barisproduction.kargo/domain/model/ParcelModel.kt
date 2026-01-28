package com.barisproduction.kargo.domain.model

enum class ParcelModel(
    val parcelName: String,
    val trackingUrl: String,
    val logo: String
) {
    YURTICI("Yurtiçi Kargo", "https://www.yurticikargo.com/tr/online-servisler/gonderi-sorgula?code=", "logo_yurtici"),
    ARAS("Aras Kargo", "https://ebranch.araskargo.com.tr/track/", "logo_aras"),
    HEPSIJET("HepsiJet", "https://www.hepsijet.com/gonderi-takibi/", "logo_ups"),
    MNG("DHL Kargo (MNG)", "https://kargotakip.dhlecommerce.com.tr/?takipNo=", "logo_mng"),
    SURAT("Sürat Kargo", "https://suratkargo.com.tr/KargoTakip/?KARGOTAKIPNO=", "logo_surat"),
    PTT("PTT Kargo", "https://gonderitakip.ptt.gov.tr/Track/Verify?q=", "logo_ptt"),
    TRENDYOL("Trendyol Express", "https://kargotakip.trendyol.com/?orderNumber=", "logo_trendyol"),
    KARGOIST("Kargo İst", "https://kargoist.com/tracking/", ""),
    UPS("UPS Kargo", "https://www.ups.com/track?tracknum=", "logo_ups"),
    JETIZZ("Jetizz Kargo", "https://app.jetizz.com/gonderi-takip?q=", ""),
    FEDEX("FedEx Kargo", "https://fedex.com/wtrk/track/?action=track&tracknumbers=", "logo_fedex"),
    OTHER("Diğer", "", "logo_default");

    companion object {
        fun fromName(name: String): ParcelModel? {
            return entries.find { it.parcelName.equals(name, ignoreCase = true) }
        }
    }
}
