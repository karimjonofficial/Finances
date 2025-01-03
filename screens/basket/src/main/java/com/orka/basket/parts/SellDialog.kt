package com.orka.basket.parts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.basket.Basket
import com.orka.components.Dialog
import com.orka.res.Strings

@Composable
internal fun SellDialog(
    modifier: Modifier = Modifier,
    dismissRequest: () -> Unit,
    basket: Basket,
    print: (GraphicsLayer) -> Unit,
    sell: () -> Unit
) {
    val graphicsLayer = rememberGraphicsLayer()

    Dialog(
        modifier = modifier,
        dismissRequest = dismissRequest,
        title = stringResource(Strings.sell),
        supportingText = "Do you want to print a check",
        onSuccess = {
            print(graphicsLayer)
            sell()
        },
        onCancel = {
            sell()
        }
    ) {
        Column(modifier = Modifier.fillMaxWidth().height(200.dp)) {
            Check(graphicsLayer, basket)
        }
    }
}

@Composable
fun Check(
    graphicsLayer: GraphicsLayer,
    basket: Basket
) {
    Column(
        modifier = Modifier
            .drawWithContent {
                graphicsLayer.record {
                    this@drawWithContent.drawContent()
                }
                drawLayer(graphicsLayer)
            }
    ) {
        Text(text = basket.price.toString())
        Text(text = basket.comment)
    }
}
