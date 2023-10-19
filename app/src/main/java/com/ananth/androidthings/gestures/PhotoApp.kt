package com.ananth.androidthings.gestures

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.onLongClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.toIntRect
import kotlinx.coroutines.delay

@Composable
fun PhotoApp(photos: List<Photo>) {

    var activeId by rememberSaveable {
        mutableStateOf<Int?>(null)
    }
    val gridState = rememberLazyGridState()
    var autoScrollSpeed by remember { mutableFloatStateOf(0f) }
    var scrim = remember(activeId) { FocusRequester() }


    PhotoGrid(
        photos = photos,
        state = gridState,
        setAutoScrollSpeed = { autoScrollSpeed = it },
        navigateToPhoto = {
            activeId = it
        },
        modifier = Modifier.focusProperties { canFocus = activeId == null }
    )

    if (activeId != null) {
        FullScreenPhoto(
            photo = photos.first { activeId == it.id },
            onDismiss = { activeId = null },
            modifier = Modifier.focusRequester(scrim)
        )

        LaunchedEffect(activeId) {
            scrim.requestFocus()
        }
    }

    LaunchedEffect(autoScrollSpeed) {
        if (autoScrollSpeed != 0f) {
            gridState.scrollBy(autoScrollSpeed)
            delay(10)
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoGrid(
    photos: List<Photo>,
    state: LazyGridState,
    modifier: Modifier = Modifier,
    setAutoScrollSpeed: (Float) -> Unit = {},
    navigateToPhoto: (Int) -> Unit = {}
) {

    var selectedIds by rememberSaveable {
        mutableStateOf(emptySet<Int>())
    }
    val inSelectionMode by remember { derivedStateOf { selectedIds.isEmpty() } }

    LazyVerticalGrid(
        state = state,
        columns = GridCells.Adaptive(128.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        modifier = modifier.photoGridDragHandler(
            lazyGridState = state,
            selectedIds = { selectedIds },
            setSelectIds = {
                selectedIds = it
            },
            setAutoScrollSpeed = setAutoScrollSpeed,
            autoScrollThreshold = with(LocalDensity.current) { 40.dp.toPx() }
        )
    ) {

        items(photos, key = { it.id }) { photo ->
            val selected by remember { derivedStateOf { selectedIds.contains(photo.id) } }
            PhotoItem(
                photo = photo,
                isSelectionMode = inSelectionMode,
                selected = selected,
                modifier = Modifier
                    .semantics {
                        if (!inSelectionMode) {
                            onLongClick("Select") {
                                selectedIds += photo.id
                                true
                            }
                        }
                    }
                    .then(if (inSelectionMode) {
                        Modifier.toggleable(
                            value = selected,
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = null,//do not show ripple
                            onValueChange = {
                                if (it) {
                                    selectedIds += photo.id
                                } else {
                                    selectedIds -= photo.id
                                }
                            }
                        )
                    } else {
                        Modifier.combinedClickable(
                            onClick = { navigateToPhoto(photo.id) },
                            onLongClick = { selectedIds += photo.id }
                        )
                    })
            )
        }
    }
}

fun Modifier.photoGridDragHandler(
    lazyGridState: LazyGridState,
    selectedIds: () -> Set<Int>,
    autoScrollThreshold: Float,
    setSelectIds: (Set<Int>) -> Unit = {},
    setAutoScrollSpeed: (Float) -> Unit = {}
) = pointerInput(autoScrollThreshold, setSelectIds, setAutoScrollSpeed) {
    fun photoIdAtOffset(hitPoint: Offset): Int? =
        lazyGridState.layoutInfo.visibleItemsInfo.find { itemInfo ->
            itemInfo.size.toIntRect().contains(hitPoint.round() - itemInfo.offset)
        }?.key as? Int

    var initialPhotoId: Int? = null
    var currentPhotoId: Int? = null

    detectDragGesturesAfterLongPress(
        onDragStart = { offset ->
            photoIdAtOffset(offset)?.let { key ->
                if (!selectedIds().contains(key)) {
                    initialPhotoId = key
                    currentPhotoId = key
                    setSelectIds(selectedIds() + key)
                }
            }
        },
        onDragCancel = { setAutoScrollSpeed(0f); initialPhotoId = null },
        onDragEnd = {
            setAutoScrollSpeed(0f); initialPhotoId = null
        },
        onDrag = { change, _ ->
            if (initialPhotoId != null) {
                val distanceFromBottom =
                    lazyGridState.layoutInfo.viewportSize.height - change.position.y
                val distanceFromTop = change.position.y
                setAutoScrollSpeed(
                    when {
                        distanceFromBottom < autoScrollThreshold -> autoScrollThreshold - distanceFromBottom
                        distanceFromTop < autoScrollThreshold -> -(autoScrollThreshold - distanceFromTop)
                        else -> 0f
                    }
                )

                photoIdAtOffset(change.position)?.let { pointerPhotoId ->
                    if (currentPhotoId != pointerPhotoId) {
                        setSelectIds(
                            selectedIds().addOrRemoveUpTo(
                                pointerPhotoId,
                                currentPhotoId,
                                initialPhotoId
                            )
                        )
                        currentPhotoId = pointerPhotoId
                    }
                }
            }
        }
    )
}

private fun Set<Int>.addOrRemoveUpTo(
    pointerKey: Int?,
    previousPointerKey: Int?,
    initialKey: Int?
): Set<Int> {
    return if (pointerKey == null || previousPointerKey == null || initialKey == null) {
        return this
    } else {
        this.minus(initialKey..previousPointerKey)
            .minus(previousPointerKey..initialKey)
            .plus(initialKey..pointerKey)
            .plus(pointerKey..initialKey)
    }
}
