package com.manageairproducts.petinfomate.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manageairproducts.petinfomate.data.database.AnimalEntity
import com.manageairproducts.petinfomate.data.database.AnimalRepo
import com.manageairproducts.petinfomate.data.network.AnimalRetrofit
import com.manageairproducts.petinfomate.model.Animal
import kotlinx.coroutines.launch
import com.manageairproducts.petinfomate.base.Result


class MainViewModel : ViewModel() {
    val animalData: MutableLiveData<Result<List<Animal>>?> =
        MutableLiveData<Result<List<Animal>>?>()
    private val _items = MutableLiveData<List<AnimalEntity>>()
    val items: LiveData<List<AnimalEntity>>
        get() = _items
    var animalEntity : AnimalEntity? = null
    var selectedItem: Animal? = null

    fun fetchPetAnimalData(name: String) {
        val apiService = AnimalRetrofit.api
        viewModelScope.launch {
            try {
                val response = apiService.getPetInfo(name)

                if (response.isSuccessful) {
                    animalData.postValue(Result.Success(response.body() ?: arrayListOf()))
                } else {
                    animalData.postValue(Result.Error(IllegalArgumentException("")))
                }
            } catch (e: Exception) {
                animalData.postValue(Result.Error(e))
            }
        }
    }


    fun getAll(repository: AnimalRepo) {
        viewModelScope.launch {
            val result = repository.getAll()
            _items.value = result
        }
    }

    fun insert(repository: AnimalRepo, item: AnimalEntity) {
        viewModelScope.launch {
            repository.insert(item)
        }
    }
    fun delete(repository: AnimalRepo, item: AnimalEntity) {
        viewModelScope.launch {
            repository.delete(item)
        }
    }
}
