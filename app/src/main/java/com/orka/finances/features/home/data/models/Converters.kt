package com.orka.finances.features.home.data.models

import com.orka.finances.R
import com.orka.finances.features.home.models.Category

fun toCategory(model: CategoryModel): Category {
    return Category(model.id, model.name, getIcon(model.iconName), model.description)
}

fun toModel(category: Category): CategoryModel {
    return category.run {
        CategoryModel(id, name, getIconName(iconRes), description)
    }
}

private fun getIcon(name: String): Int {
    return when(name) {
        "sofa" -> R.drawable.sofa
        else -> R.drawable.chair
    }
}

private fun getIconName(iconRes: Int): String {
    return when(iconRes) {
        R.drawable.sofa -> "sofa"
        else -> "chair"
    }
}