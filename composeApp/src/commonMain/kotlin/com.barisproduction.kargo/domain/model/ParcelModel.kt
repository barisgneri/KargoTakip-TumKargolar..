package com.barisproduction.kargo.domain.model

enum class ParcelModel(
    val parcelName: String,
    val trackingUrl: String,
    val logo: String
) {
    YURTICI("Yurtiçi Kargo", "https://www.yurticikargo.com/tr/online-servisler/gonderi-sorgula?code=", "logo_yurtici"),
    ARAS("Aras Kargo", "https://ebranch.araskargo.com.tr/track/", "logo_aras"),
    MNG("DHL Kargo (MNG)", "http://service.mngkargo.com.tr/iactive/popup/kargotakip.asp?k=", "logo_mng"),
    SURAT("Sürat Kargo", "http://kargoweb.suratkargo.com.tr/kargotakip.aspx?p=", "logo_surat"),
    PTT("PTT Kargo", "https://gonderitakip.ptt.gov.tr/Track/Verify?q=", "logo_ptt"),
    UPS("HepsiJet", "https://www.ups.com.tr/WaybillQueryResult.aspx?Waybill=", "logo_ups"),
    OTHER("Diğer", "", "logo_default");

    companion object {
        fun fromName(name: String): ParcelModel? {
            return entries.find { it.parcelName.equals(name, ignoreCase = true) }
        }
    }
}
