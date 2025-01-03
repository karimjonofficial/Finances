package com.orka.product.parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.orka.components.VerticalSpacer
import com.orka.product.ProductScreenStates
import com.orka.product.models.CarouselItem
import com.orka.res.Drawables
import com.orka.res.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InitializedContent(
    modifier: Modifier,
    state: ProductScreenStates.Initialized,
    onEditClick: () -> Unit
) {
    val carouselItems = listOf(
        CarouselItem(1, Drawables.furniture1, Strings.furniture),
        CarouselItem(2, Drawables.furniture1, Strings.furniture),
        CarouselItem(3, Drawables.furniture1, Strings.furniture),
        CarouselItem(4, Drawables.furniture1, Strings.furniture),
        CarouselItem(5, Drawables.furniture1, Strings.furniture),
        CarouselItem(6, Drawables.furniture1, Strings.furniture),
    )

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalMultiBrowseCarousel(
            state = rememberCarouselState { carouselItems.size },
            modifier = Modifier.height(428.dp),
            preferredItemWidth = 428.dp,
            itemSpacing = 8.dp,
            contentPadding = PaddingValues(16.dp)
        ) { index ->

            Image(
                modifier = Modifier
                    .height(428.dp)
                    .maskClip(MaterialTheme.shapes.extraLarge)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                painter = painterResource(carouselItems[index].imgRes),
                contentDescription = stringResource(carouselItems[index].descriptionRes),
                contentScale = ContentScale.Crop
            )
        }
        VerticalSpacer(16)

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = state.product.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            VerticalSpacer(8)

            Text(
                text = stringResource(Strings.description),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            VerticalSpacer(4)

            Text(text = state.product.description)
        }
        Button(onClick = onEditClick) { Text("Edit") }
    }
}