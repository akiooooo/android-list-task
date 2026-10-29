package com.akio.app01

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.akio.app01.ui.theme.TestApp01Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestApp01Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NumberGrid(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun NumberGrid(modifier: Modifier = Modifier) {
    var itemCount by rememberSaveable { mutableIntStateOf(0) }
    val configuration = LocalConfiguration.current
    val resources = LocalResources.current
    val columnsResId = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        R.integer.columns_landscape
    } else {
        R.integer.columns_portrait
    }
    val columns = resources.getInteger(columnsResId)

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                start = dimensionResource(id = R.dimen.grid_padding),
                end = dimensionResource(id = R.dimen.grid_padding),
                top = dimensionResource(id = R.dimen.grid_padding),
                bottom = dimensionResource(id = R.dimen.button_spacing)
            ),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.item_spacing)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.item_spacing))
        ) {
            items(itemCount) { index ->
                NumberBox(number = index)
            }
        }

        Button(
            onClick = { itemCount++ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.button_padding))
        ) {
            Text(text = stringResource(id = R.string.add_item))
        }
    }
}

@Composable
fun NumberBox(number: Int) {
    val backgroundColor = if (number % 2 == 0) {
        colorResource(id = R.color.even_background)
    } else {
        colorResource(id = R.color.odd_background)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(backgroundColor)
            .border(dimensionResource(id = R.dimen.border_width), colorResource(id = R.color.border_color)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            fontSize = dimensionResource(id = R.dimen.text_size).value.sp,
            color = colorResource(id = R.color.text_color)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NumberGridPreview() {
    TestApp01Theme {
        NumberGrid()
    }
}
