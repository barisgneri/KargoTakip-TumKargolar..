package com.barisproduction.kargo.data.repository

import com.barisproduction.kargo.domain.model.AnalyticsEvent
import com.barisproduction.kargo.domain.repository.AnalyticsTracker
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.analytics

class FirebaseAnalyticsTracker : AnalyticsTracker {
    override fun trackEvent(event: AnalyticsEvent) {
        val nonNullParams = event.params.filterValues { it != null }.mapValues { it.value!! }
        Firebase.analytics.logEvent(event.name, nonNullParams)
    }
}
