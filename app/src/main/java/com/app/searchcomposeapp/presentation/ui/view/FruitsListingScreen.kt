package com.app.searchcomposeapp.presentation.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.searchcomposeapp.R
import com.app.searchcomposeapp.data.local.entity.Fruit
import com.app.searchcomposeapp.presentation.ui.viewmodel.FruitsViewModel
import com.app.searchcomposeapp.ui.theme.SearchComposeAppTheme

@Composable
fun FruitsListingScreen(viewModel: FruitsViewModel = viewModel(), navController: NavController) {
    var searchText by remember {
        mutableStateOf("")
    }

    val filteredFruits by viewModel.filteredFruits.collectAsState(initial = emptyList())

    LaunchedEffect(searchText) {
        viewModel.getFilteredFruits(searchText)
    }

    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        OutlinedTextField(value = searchText, onValueChange = { searchText = it }, label = {
            Text(
                text = stringResource(R.string.search)
            )
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
            shape = RoundedCornerShape(12.dp)
        )

        LazyColumn {
            items(filteredFruits, key = { fruit -> fruit.id }) { fruit ->
                FruitItem(
                    fruit,
                    onCheckedClicked = { viewModel.updateFruitCheckedStatus(fruit, it) },
                    onClickItem = { navController.navigate("itemDetails/${fruit.name}") })
            }
            item {
                SelectionContainer(
                    viewModel.checkedList.value
                )
            }
        }
    }
}


@Composable
fun FruitItem(fruit: Fruit, onCheckedClicked: (Boolean) -> Unit, onClickItem: (Fruit) -> Unit) {
    var checkValue by remember {
        mutableStateOf(fruit.isChecked)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 12.dp)
            .fillMaxWidth()
            .clickable {
                onClickItem(fruit)
            }
    ) {
        Checkbox(
            checked = checkValue,
            onCheckedChange = { isChecked ->
                onCheckedClicked(isChecked)
                checkValue = isChecked
            },
        )
        Text(
            text = fruit.name,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
fun SelectionContainer(nameList: String) {
    val selectedValues = nameList.ifEmpty { stringResource(R.string.str_no_item_selected) }
    val textValue = stringResource(R.string.selected_values_are, selectedValues)
    OutlinedCard(modifier = Modifier.padding(12.dp)) {
        Text(
            text = textValue,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}
