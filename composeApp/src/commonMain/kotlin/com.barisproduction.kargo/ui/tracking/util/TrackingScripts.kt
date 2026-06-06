package com.barisproduction.kargo.ui.tracking.util

object TrackingScripts {
    fun getPasteTrackingNumberScript(trackingNumber: String): String {
        return """
            (function() {
                function fillInput(el) {
                    if (!el) return false;
                    var tag = el.tagName.toLowerCase();
                    var type = (el.getAttribute('type') || '').toLowerCase();
                    if (tag === 'input' || tag === 'textarea') {
                        el.value = '$trackingNumber';
                        el.dispatchEvent(new Event('input', { bubbles: true }));
                        el.dispatchEvent(new Event('change', { bubbles: true }));
                        el.dispatchEvent(new Event('blur', { bubbles: true }));

                        el.focus();
                        el.scrollIntoView({ behavior: 'smooth', block: 'center' });
                        return true;
                    }
                    return false;
                }

                var active = document.activeElement;
                if (!fillInput(active)) {
                    var inputs = document.querySelectorAll('input[type="text"], input[type="search"], input:not([type]), textarea');
                    for (var i = 0; i < inputs.length; i++) {
                        if (fillInput(inputs[i])) break;
                    }
                }
            })();
        """.trimIndent()
    }
}
