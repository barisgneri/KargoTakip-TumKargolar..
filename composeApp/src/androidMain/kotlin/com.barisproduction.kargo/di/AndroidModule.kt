package com.barisproduction.kargo.di

import com.barisproduction.kargo.common.service.ClipboardService
import org.koin.dsl.module
import com.barisproduction.kargo.service.AndroidClipboardService

val androidModule = module {
    single<ClipboardService> { AndroidClipboardService(context = get()) }
}