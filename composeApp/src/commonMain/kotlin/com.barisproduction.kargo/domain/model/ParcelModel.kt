package com.barisproduction.kargo.domain.model

enum class ParcelModel(
    val parcelName: String,
    val trackingUrl: String,
    val logo: String
) {
    YURTICI("Yurtiçi Kargo", "https://gonderitakip.yurticikargo.com/t?t=", "logo_yurtici"),
    ARAS("Aras Kargo", "http://kargotakip.araskargo.com.tr/mainpage.aspx?code=", "logo_aras"),
    MNG("MNG Kargo", "http://service.mngkargo.com.tr/iactive/popup/kargotakip.asp?k=", "logo_mng"),
    SURAT("Sürat Kargo", "http://kargoweb.suratkargo.com.tr/kargotakip.aspx?p=", "logo_surat"),
    PTT("PTT Kargo", "https://gonderitakip.ptt.gov.tr/Track/Verify?q=", "logo_ptt"),
    UPS("UPS Kargo", "https://www.ups.com.tr/WaybillQueryResult.aspx?Waybill=", "logo_ups"),
    OTHER("Diğer", "", "logo_default");

    companion object {
        fun fromName(name: String): ParcelModel? {
            return entries.find { it.parcelName.equals(name, ignoreCase = true) }
        }
    }
}
