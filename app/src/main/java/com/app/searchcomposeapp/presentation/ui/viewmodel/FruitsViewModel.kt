package com.app.searchcomposeapp.presentation.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.searchcomposeapp.common.CommonUtils.fruits
import com.app.searchcomposeapp.data.local.entity.Fruit
import com.app.searchcomposeapp.data.repository.FruitsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FruitsViewModel @Inject constructor(private val repository: FruitsRepository) : ViewModel() {
    private val _filteredFruits = MutableStateFlow<List<Fruit>>(emptyList())
    val filteredFruits: Flow<List<Fruit>> get() = _filteredFruits

    val checkedList = mutableStateOf("")

    init {
        viewModelScope.launch {
            if (repository.getAllFruits().isEmpty()) {
                repository.insertFruit(fruits)
            }
            getCheckedList()
        }
    }

    fun getFilteredFruits(searchText: String) {
        viewModelScope.launch {
            repository.getFilteredFruits(searchText).collect {
                _filteredFruits.value = it
            }
        }
    }

    fun updateFruitCheckedStatus(fruit: Fruit, isChecked: Boolean) {
        viewModelScope.launch {
            fruit.isChecked = isChecked
            repository.updateFruit(fruit)
            getCheckedList()
        }
    }

    private fun getCheckedList() {
        viewModelScope.launch {
            checkedList.value =
                repository.getCheckedList().filter { it.isChecked }.joinToString { it.name }
        }
    }
}