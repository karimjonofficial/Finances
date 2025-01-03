package com.orka.product.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

internal data class CarouselItem(
    val id: Int,
    @DrawableRes val imgRes: Int,
    @StringRes val descriptionRes: Int
)