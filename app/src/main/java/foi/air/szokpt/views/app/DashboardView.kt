package foi.air.szokpt.views.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.TileSizeMode

@Composable
fun DashboardView(navController: NavController){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ){
        item(span = { GridItemSpan(2) }) {
            TileSegment(
                tileSizeMode = TileSizeMode.FillMaxWidth,
                minHeight = 200.dp,
                color = BGLevelOne,
                content = {
                    Text(
                        text = "Item 1 Span 1 row 2 col",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
            )
        }
        item {
            TileSegment(
                tileSizeMode = TileSizeMode.WrapContent,
                minWidth = 200.dp,
                minHeight = 200.dp,
                color = BGLevelOne,
                content = {
                    Text(
                        text = "Item 1",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
            )
        }
        item {
            TileSegment(
                tileSizeMode = TileSizeMode.WrapContent,
                minWidth = 200.dp,
                minHeight = 200.dp,
                color = BGLevelOne,
                content = {
                    Text(
                        text = "Item 2",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
            )
        }
        item(span = { GridItemSpan(2) }) {
            TileSegment(
                tileSizeMode = TileSizeMode.FillMaxWidth,
                // For spanning 2 rows
                modifier = Modifier.aspectRatio(1f),
                color = BGLevelOne,
                content = {
                    Text(
                        text = "Item 4 Span 2 row 2 col",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
            )
        }
    }
}