package foi.air.szokpt.views.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.ui.theme.danger
import foi.air.szokpt.ui.theme.success

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
                tileSizeMode = TileSizeMode.FILL_MAX_WIDTH,
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
            ValueTile()
        }
        item {
            TileSegment(
                tileSizeMode = TileSizeMode.WRAP_CONTENT,
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
                tileSizeMode = TileSizeMode.FILL_MAX_WIDTH,
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
        item(span = { GridItemSpan(2) }) {
            TileSegment(
                tileSizeMode = TileSizeMode.FILL_MAX_WIDTH,
                // For spanning 2 rows
                modifier = Modifier.aspectRatio(1.5f),
                color = BGLevelOne,
                content = {
                    Text(
                        text = "Item 4 Span 1.5 row 2 col",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
            )
        }
    }
}

@Composable
fun ValueTile() {
    TileSegment(
        tileSizeMode = TileSizeMode.FILL_MAX_HEIGHT,
        innerPadding = 4.dp,
        outerMargin = 8.dp,
        minWidth = 150.dp,
        minHeight = 200.dp,
        color = BGLevelOne
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Value",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "€1 002 123",
                        color = success,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }


            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "This week:",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "-9.8%",
                    color = danger,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "+ €26 017",
                    color = success,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
