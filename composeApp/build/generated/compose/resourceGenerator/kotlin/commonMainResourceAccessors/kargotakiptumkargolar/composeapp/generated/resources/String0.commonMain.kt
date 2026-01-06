@file:OptIn(InternalResourceApi::class)

package kargotakiptumkargolar.composeapp.generated.resources

import kotlin.OptIn
import kotlin.String
import kotlin.collections.MutableMap
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.ResourceItem
import org.jetbrains.compose.resources.StringResource

private const val MD: String =
    "composeResources/kargotakiptumkargolar.composeapp.generated.resources/"

internal val Res.string.add_new_cargo: StringResource by lazy {
      StringResource("string:add_new_cargo", "add_new_cargo", setOf(
        ResourceItem(setOf(), "${MD}values/strings.commonMain.cvr", 10, 41),
      ))
    }

internal val Res.string.app_name: StringResource by lazy {
      StringResource("string:app_name", "app_name", setOf(
        ResourceItem(setOf(), "${MD}values/strings.commonMain.cvr", 52, 32),
      ))
    }

internal val Res.string.btn_save: StringResource by lazy {
      StringResource("string:btn_save", "btn_save", setOf(
        ResourceItem(setOf(), "${MD}values/strings.commonMain.cvr", 85, 24),
      ))
    }

internal val Res.string.check_connection_and_try_again: StringResource by lazy {
      StringResource("string:check_connection_and_try_again", "check_connection_and_try_again", setOf(
        ResourceItem(setOf(), "${MD}values/strings.commonMain.cvr", 110, 114),
      ))
    }

internal val Res.string.connection_error: StringResource by lazy {
      StringResource("string:connection_error", "connection_error", setOf(
        ResourceItem(setOf(), "${MD}values/strings.commonMain.cvr", 225, 48),
      ))
    }

internal val Res.string.error_network: StringResource by lazy {
      StringResource("string:error_network", "error_network", setOf(
        ResourceItem(setOf(), "${MD}values/strings.commonMain.cvr", 274, 57),
      ))
    }

internal val Res.string.new_cargo_search: StringResource by lazy {
      StringResource("string:new_cargo_search", "new_cargo_search", setOf(
        ResourceItem(setOf(), "${MD}values/strings.commonMain.cvr", 332, 48),
      ))
    }

@InternalResourceApi
internal fun _collectCommonMainString0Resources(map: MutableMap<String, StringResource>) {
  map.put("add_new_cargo", Res.string.add_new_cargo)
  map.put("app_name", Res.string.app_name)
  map.put("btn_save", Res.string.btn_save)
  map.put("check_connection_and_try_again", Res.string.check_connection_and_try_again)
  map.put("connection_error", Res.string.connection_error)
  map.put("error_network", Res.string.error_network)
  map.put("new_cargo_search", Res.string.new_cargo_search)
}
