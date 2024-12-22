package com.orka.finances.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.res.Drawables
import com.orka.res.Strings

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun StockItemScreenMock() {
    data class CarouselItem(
        val id: Int,
        @DrawableRes val imgRes: Int,
        @StringRes val descriptionRes: Int
    )

    val carouselItems = listOf(
        CarouselItem(1, Drawables.furniture1, Strings.furniture),
        CarouselItem(2, Drawables.furniture1, Strings.furniture),
        CarouselItem(3, Drawables.furniture1, Strings.furniture),
        CarouselItem(4, Drawables.furniture1, Strings.furniture),
        CarouselItem(5, Drawables.furniture1, Strings.furniture),
        CarouselItem(6, Drawables.furniture1, Strings.furniture),
    )

    Scaffold(topBar = { TopAppBar(title = { Text("Stock items") }) }) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {

            HorizontalMultiBrowseCarousel(
                state = rememberCarouselState { carouselItems.size },
                modifier = Modifier.height(300.dp),
                preferredItemWidth = 186.dp,
                itemSpacing = 8.dp,
                contentPadding = PaddingValues(16.dp)
            ) { index ->

                Image(
                    modifier = Modifier
                        .height(205.dp)
                        .maskClip(MaterialTheme.shapes.extraLarge)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    painter = painterResource(carouselItems[index].imgRes),
                    contentDescription = stringResource(carouselItems[index].descriptionRes),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}