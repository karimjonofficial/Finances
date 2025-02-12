package com.orka.history

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import com.orka.components.EmptyScreen
import com.orka.components.InitialScreen
import com.orka.components.InitializingScreen
import com.orka.components.OfflineScreen
import com.orka.core.Formatter
import com.orka.history.parts.ReceiveHistoryList

@Composable
internal fun ReceiveContent(
    state: ReceiveContentState,
    receiveListState: LazyListState,
    formatter: Formatter
) {
    when (state) {

        ReceiveContentState.Initial -> {
            InitialScreen()
        }

        is ReceiveContentState.Initialized -> {
            ReceiveHistoryList(
                map = state.receivesMap,
                state = receiveListState,
                formatter = formatter
            )
        }

        ReceiveContentState.Empty -> {
            EmptyScreen()
        }

        ReceiveContentState.Initializing -> {
            InitializingScreen()
        }

        ReceiveContentState.Offline -> {
            OfflineScreen()
        }
    }
}