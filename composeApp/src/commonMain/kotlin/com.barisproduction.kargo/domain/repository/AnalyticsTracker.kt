package com.barisproduction.kargo.domain.repository

import com.barisproduction.kargo.domain.model.AnalyticsEvent

interface AnalyticsTracker {
    fun trackEvent(event: AnalyticsEvent)
}
